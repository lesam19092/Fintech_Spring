package com.example.fintech_spring.second_task.client;

import com.example.fintech_spring.configuration.RestTemplateConfiguration;
import com.example.fintech_spring.second_task.dto.ApiEvent;
import com.example.fintech_spring.second_task.dto.CurrencyRequest;
import com.example.fintech_spring.second_task.dto.CurrencyResponse;
import com.example.fintech_spring.second_task.dto.Event;
import com.example.fintech_spring.second_task.utils.ClientUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
@ConditionalOnBean(RestTemplateConfiguration.class)
public class RestTemplateClient implements ApiClient {

    private final RestTemplate restTemplateForEvents;
    private final RestTemplate restTemplateForConvertValute;


    @Override
    public List<Event> getEvents(LocalDate dateFrom, LocalDate dateTo) {
        ApiEvent apiEvent = restTemplateForEvents.getForObject(ClientUtils.getUrl(dateFrom, dateTo), ApiEvent.class);
        List<Event> events = apiEvent.getResults();
        log.debug("Fetched {} events", events.size());
        return ClientUtils.setEventPrices(events);
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
}
