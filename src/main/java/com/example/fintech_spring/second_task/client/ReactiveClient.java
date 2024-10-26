package com.example.fintech_spring.second_task.client;

import com.example.fintech_spring.configuration.ReactiveConfiguration;
import com.example.fintech_spring.second_task.dto.ApiEvent;
import com.example.fintech_spring.second_task.dto.CurrencyRequest;
import com.example.fintech_spring.second_task.dto.CurrencyResponse;
import com.example.fintech_spring.second_task.dto.Event;
import com.example.fintech_spring.second_task.utils.ClientUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static com.example.fintech_spring.second_task.utils.ClientUtils.getUrl;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnBean(ReactiveConfiguration.class)
public class ReactiveClient implements ApiClient {

    private final WebClient webClientForEvents;
    private final WebClient webClientForConvertValute;

    @Override
    public List<Event> getEvents(LocalDate dateFrom, LocalDate dateTo) {
        log.info("Fetching events from {} to {}", dateFrom, dateTo);

        return webClientForEvents.get()
                .uri(getUrl(dateFrom, dateTo))
                .retrieve()
                .bodyToMono(ApiEvent.class)
                .map(ApiEvent::getResults)
                .map(ClientUtils::setEventPrices)
                .doOnNext(events -> log.info("Fetched {} events", events.size())).block();

    }

    @Override
    public Double getAmountInRub(Double budget, String fromCurrency) {
        log.info("Converting budget {} {} to RUB", budget, fromCurrency);
        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .fromCurrency(fromCurrency)
                .amount(budget).build();
        return webClientForConvertValute.post()
                .uri("/currencies/convert")
                .bodyValue(currencyRequest)
                .retrieve()
                .bodyToMono(CurrencyResponse.class)
                .map(CurrencyResponse::getConvertedAmount)
                .doOnNext(amount -> log.info("Converted amount: {}", amount)).block();
    }


    public Mono<List<Event>> getEventsMono(LocalDate dateFrom, LocalDate dateTo) {

        log.info("Fetching events from {} to {}", dateFrom, dateTo);

        return webClientForEvents.get()
                .uri(getUrl(dateFrom, dateTo))
                .retrieve()
                .bodyToMono(ApiEvent.class)
                .map(ApiEvent::getResults)
                .map(ClientUtils::setEventPrices)
                .doOnNext(events -> log.info("Fetched {} events", events.size()));

    }

    public Mono<Double> getAmountInRubMono(Double budget, String fromCurrency) {
        log.info("Converting budget {} {} to RUB", budget, fromCurrency);
        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .fromCurrency(fromCurrency)
                .amount(budget).build();
        return webClientForConvertValute.post()
                .uri("/currencies/convert")
                .bodyValue(currencyRequest)
                .retrieve()
                .bodyToMono(CurrencyResponse.class)
                .map(CurrencyResponse::getConvertedAmount)
                .doOnNext(amount -> log.info("Converted amount: {}", amount));
    }


}
