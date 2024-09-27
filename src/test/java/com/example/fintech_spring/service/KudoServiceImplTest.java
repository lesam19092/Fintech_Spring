package com.example.fintech_spring.service;


import com.example.fintech_spring.dto.Category;
import com.example.fintech_spring.dto.Location;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.Optional;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest
@AutoConfigureMockMvc
public class KudoServiceImplTest {

    private final ObjectMapper mapper = new ObjectMapper();


    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().dynamicPort())
            .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("https://kudago.com/public-api/v1.4", wireMockServer::baseUrl);
    }


    @Test
    public void fetchingCategories() throws Exception {
        String expectedJson = mapper
                .writeValueAsString(new Category(1, "slug1", "name1"));
        wireMockServer.stubFor(
                WireMock.get(urlEqualTo("/place-categories"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(expectedJson))
        );
        var data = getContent(wireMockServer.baseUrl() + "/place-categories");
        Assertions.assertEquals(data.get(), expectedJson);
    }

    @Test
    public void fetchingLocations() throws Exception {
        String expectedJson = mapper
                .writeValueAsString(new Location(UUID.randomUUID(), "slug1", "name1"));
        wireMockServer.stubFor(
                WireMock.get(urlEqualTo("/locations"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(expectedJson))
        );
        var data = getContent(wireMockServer.baseUrl() + "/locations");
        Assertions.assertEquals(data.get(), expectedJson);
    }


    @Test
    public void testGetAllCategoriesError() {
        wireMockServer.stubFor(get(urlEqualTo("/place-categories"))
                .willReturn(aResponse()
                        .withStatus(500)));
        var data = getContent(wireMockServer.baseUrl() + "/place-categories");
        Assertions.assertTrue(data.isEmpty());

    }

    @Test
    public void testGetAllLocationsError() {
        wireMockServer.stubFor(get(urlEqualTo("/locations"))
                .willReturn(aResponse()
                        .withStatus(500)));
        var data = getContent(wireMockServer.baseUrl() + "/locations");
        Assertions.assertTrue(data.isEmpty());
    }


    private Optional<String> getContent(String url) {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<String> rateResponse =
                testRestTemplate.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });

        return Optional.ofNullable(rateResponse.getBody());
    }

}