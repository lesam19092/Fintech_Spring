package com.example.fintech_spring.client;

import com.example.fintech_spring.configuration.RestTemplateConfiguration;
import com.example.fintech_spring.dto.ApiEvent;
import com.example.fintech_spring.dto.Event;
import com.example.fintech_spring.utils.ClientUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnBean(RestTemplateConfiguration.class)
public class RestTemplateClient implements ApiClient {

    private final RestTemplate restTemplate;


    @Override
    public List<Event> getEvents(LocalDate dateFrom, LocalDate dateTo) {
        ApiEvent apiEvent = restTemplate.getForObject(ClientUtils.getUrl(dateFrom, dateTo), ApiEvent.class);
        List<Event> events = apiEvent.getResults();
        log.debug("Fetched {} events", events.size());
        return ClientUtils.setEventPrices(events);
    }

}