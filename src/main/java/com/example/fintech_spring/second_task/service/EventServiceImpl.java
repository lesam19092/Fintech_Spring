package com.example.fintech_spring.second_task.service;

import com.example.fintech_spring.second_task.dto.ApiEvent;
import com.example.fintech_spring.second_task.dto.CurrencyRequest;
import com.example.fintech_spring.second_task.dto.CurrencyResponse;
import com.example.fintech_spring.second_task.dto.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final RestTemplate restTemplate;
    private final RestTemplate restTemplateForConvertValute;

    @Qualifier("executorServiceForFetchingData")
    private final ExecutorService executorServiceForFetchingData;

    private static final ZoneOffset MOSCOW_TIME_ZONE = ZoneOffset.ofHours(+3);

    @Override
    public List<Event> getSuitableEvents(Double budget, String currency, LocalDate dateFrom, LocalDate dateTo) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        log.info("Starting to get events and convert budget...");

        CompletableFuture<List<Event>> listOfEvents = CompletableFuture.supplyAsync(() -> getEvents(dateFrom, dateTo), executorServiceForFetchingData);
        CompletableFuture<Double> amountOfMoney = CompletableFuture.supplyAsync(() -> getAmountInRub(budget, currency), executorServiceForFetchingData);

        listOfEvents.thenAcceptBoth(amountOfMoney, (events, budgetInRub) -> {
            log.info("Filtering events based on budget: {}", budgetInRub);
            events.removeIf(event -> {
                try {
                    double price = Double.parseDouble(event.getPrice());
                    return price > budgetInRub;
                } catch (NumberFormatException e) {
                    log.warn("Invalid price format for event: {}", event.getTitle(), e);
                    return true;
                }
            });
        });

        try {
            listOfEvents.get();
            amountOfMoney.get();
            long endTime = System.currentTimeMillis();
            log.info("Completed in {} ms", (endTime - startTime));
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error during data processing", e);
        }
        return listOfEvents.get();
    }

    @Override
    public List<Event> getEvents(LocalDate dateFrom, LocalDate dateTo) {
        log.info("Fetching events from {} to {}", dateFrom, dateTo);
        ApiEvent apiEvent = restTemplate.getForObject(getUrl(dateFrom, dateTo), ApiEvent.class);
        List<Event> events = apiEvent.getResults();
        log.info("Fetched {} events", events.size());
        return setEventPrices(events);
    }

    @Override
    public Double getAmountInRub(Double budget, String fromCurrency) {
        log.info("Converting budget {} {} to RUB", budget, fromCurrency);
        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .fromCurrency(fromCurrency)
                .amount(budget).build();
        CurrencyResponse response = restTemplateForConvertValute
                .postForObject("currencies/convert", currencyRequest, CurrencyResponse.class);
        log.info("Converted amount: {}", response.getConvertedAmount());
        return response.getConvertedAmount();
    }

    private long getActualSince(LocalDate dateFrom) {
        return dateFrom != null ?
                dateFrom.atStartOfDay().toEpochSecond(MOSCOW_TIME_ZONE) :
                LocalDate.now().minusDays(7).atStartOfDay().toEpochSecond(MOSCOW_TIME_ZONE);
    }

    private long getActualUntil(LocalDate dateTo) {
        return dateTo != null ?
                dateTo.atStartOfDay().toEpochSecond(MOSCOW_TIME_ZONE) :
                LocalDate.now().atStartOfDay().toEpochSecond(MOSCOW_TIME_ZONE);
    }

    private String getUrl(LocalDate dateFrom, LocalDate dateTo) {
        long actual_since = getActualSince(dateFrom);
        long actual_until = getActualUntil(dateTo);
        String url = String.format("/events/?location=spb&fields=price,id,title,dates&actual_since=%d&actual_until=%d",
                actual_since, actual_until);
        log.info("Generated URL: {}", url);
        return url;
    }

    private List<Event> setEventPrices(List<Event> events) {
        log.info("Setting prices for events");
        events.forEach(event -> {
            Pattern pattern = Pattern.compile("^\\D*(\\d+)");
            Matcher matcher = pattern.matcher(event.getPrice());
            if (matcher.find()) {
                event.setPrice(matcher.group(1));
            } else {
                event.setPrice("0");
            }
        });
        return events;
    }
}