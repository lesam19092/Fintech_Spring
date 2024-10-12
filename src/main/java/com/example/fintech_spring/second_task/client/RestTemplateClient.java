package com.example.fintech_spring.second_task.client;

import com.example.fintech_spring.second_task.dto.ApiEvent;
import com.example.fintech_spring.second_task.dto.CurrencyRequest;
import com.example.fintech_spring.second_task.dto.CurrencyResponse;
import com.example.fintech_spring.second_task.dto.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "api.client", havingValue = "non-reactive", matchIfMissing = true)
public class RestTemplateClient implements ApiClient {

    private final RestTemplate restTemplateForEvents;
    private final RestTemplate restTemplateForConvertValute;

    private static final ZoneOffset MOSCOW_TIME_ZONE = ZoneOffset.ofHours(+3);


    @Override
    public List<Event> getEvents(LocalDate dateFrom, LocalDate dateTo) {
        ApiEvent apiEvent = restTemplateForEvents.getForObject(getUrl(dateFrom, dateTo), ApiEvent.class);
        List<Event> events = apiEvent.getResults();
        log.debug("Fetched {} events", events.size());
        return setEventPrices(events);
    }

    @Override
    public Double getAmountInRub(Double budget, String fromCurrency) {
        log.info("Converting budget {} {} to RUB", budget, fromCurrency);
        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .fromCurrency(fromCurrency)
                .amount(budget).build();
        CurrencyResponse response = restTemplateForConvertValute
                .postForObject("/currencies/convert", currencyRequest, CurrencyResponse.class);
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
        log.debug("Generated URL: {}", url);
        return url;
    }

    private List<Event> setEventPrices(List<Event> events) {
        log.debug("Setting prices for events");
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
