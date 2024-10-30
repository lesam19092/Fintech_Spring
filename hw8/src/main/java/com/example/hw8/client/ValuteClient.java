package com.example.hw8.client;

import com.example.hw8.dto.ListValute;
import com.example.hw8.dto.Valute;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
@RequiredArgsConstructor
public class ValuteClient {

    private final RestClient restClient;

    private static final String DAILY_RATES_URL = "/XML_daily.asp/";


    @CircuitBreaker(name = "bankClient", fallbackMethod = "fallbackDailyRates")
    public ListValute getListValutes() {

        ListValute listValute = restClient.get().uri(DAILY_RATES_URL)
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, (response, request) -> {
                    log.error("Error response: {}", response);
                    throw new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE, "проблемы на стороне цб , попробуйте заново через час ");
                })
                .onStatus(HttpStatusCode::is4xxClientError, (response, request) -> {
                    log.error("Error response: {}", response);
                    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "проблемы c запросом к цб , попробуйте заново через час ");
                })
                .toEntity(ListValute.class)
                .getBody();


        if (listValute == null) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "проблемы на стороне цб , попробуйте заново через час ");
        }


        Valute newValute = new Valute();
        newValute.setValue("1.0");
        newValute.setCharCode("RUB");
        listValute.getValutes().add(newValute);

        return listValute;
    }

    private ListValute fallbackDailyRates(Throwable throwable) {
        log.error("Fallback method called due to: {}", throwable.getMessage());
        ListValute fallbackListValute = new ListValute();
        return fallbackListValute;
    }
}
