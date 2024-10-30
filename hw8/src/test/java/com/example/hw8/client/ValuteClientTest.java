package com.example.hw8.client;

import com.example.hw8.dto.ListValute;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ValuteClientTest {


    @Autowired
    private RestClient restClient;


    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().dynamicPort())
            .build();


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("https://cbr.ru/scripts", wireMockServer::baseUrl);
    }


    @Test
    public void fetchingValutes() throws Exception {

        wireMockServer.stubFor(
                WireMock.get(urlEqualTo("/XML_daily.asp/"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                                .withBody("<ValCurs Date=\"02.03.2002\" name=\"Foreign Currency Market\">\n" +
                                        "<Valute ID=\"R01010\">\n" +
                                        "<NumCode>036</NumCode>\n" +
                                        "<CharCode>AUD</CharCode>\n" +
                                        "<Nominal>1</Nominal>\n" +
                                        "<Name>Австралийский доллар</Name>\n" +
                                        "<Value>16,0102</Value>\n" +
                                        "<VunitRate>16,0102</VunitRate>\n" +
                                        "</Valute>\n" +
                                        "<Valute ID=\"R01035\">\n" +
                                        "<NumCode>826</NumCode>\n" +
                                        "<CharCode>GBP</CharCode>\n" +
                                        "<Nominal>1</Nominal>\n" +
                                        "<Name>Фунт стерлингов Соединенного королевства</Name>\n" +
                                        "<Value>43,8254</Value>\n" +
                                        "<VunitRate>43,8254</VunitRate>\n" +
                                        "</Valute>\n" +
                                        "</ValCurs>"))
        );


        ListValute listValute = getData(wireMockServer.baseUrl() + "/XML_daily.asp/");


        assertAll(
                () -> {
                    assertThat(listValute.getValutes().size()).isEqualTo(2);
                    assertThat(listValute.getValutes().get(0).getCharCode()).isEqualTo("AUD");
                    assertThat(listValute.getValutes().get(1).getCharCode()).isEqualTo("GBP");
                }
        );

    }

    @Test
    public void fetchingValutesAndStatusIs5XX() throws Exception {

        wireMockServer.stubFor(
                WireMock.get(urlEqualTo("/XML_daily.asp/"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.SERVICE_UNAVAILABLE.value())
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                                .withBody("<ValCurs Date=\"02.03.2002\" name=\"Foreign Currency Market\">\n" +
                                        "<Valute ID=\"R01010\">\n" +
                                        "<NumCode>036</NumCode>\n" +
                                        "<CharCode>AUD</CharCode>\n" +
                                        "<Nominal>1</Nominal>\n" +
                                        "<Name>Австралийский доллар</Name>\n" +
                                        "<Value>16,0102</Value>\n" +
                                        "<VunitRate>16,0102</VunitRate>\n" +
                                        "</Valute>\n" +
                                        "<Valute ID=\"R01035\">\n" +
                                        "<NumCode>826</NumCode>\n" +
                                        "<CharCode>GBP</CharCode>\n" +
                                        "<Nominal>1</Nominal>\n" +
                                        "<Name>Фунт стерлингов Соединенного королевства</Name>\n" +
                                        "<Value>43,8254</Value>\n" +
                                        "<VunitRate>43,8254</VunitRate>\n" +
                                        "</Valute>\n" +
                                        "</ValCurs>"))
        );


        try {
            ListValute listValute = getData(wireMockServer.baseUrl() + "/XML_daily.asp/");
        } catch (HttpServerErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    public ListValute getData(String url) {

        ListValute listValute = restClient.get().uri(url)
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, (response, request) -> {
                    throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "проблемы на стороне цб , попробуйте заново через час ");
                })
                .toEntity(ListValute.class)
                .getBody();


        return listValute;
    }
}


