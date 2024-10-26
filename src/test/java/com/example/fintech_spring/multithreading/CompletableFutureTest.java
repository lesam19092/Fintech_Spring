package com.example.fintech_spring.multithreading;

import com.example.fintech_spring.service.KudoServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureTest {

    @Mock
    private static ExecutorService executorServiceForFetchingData;

    @BeforeAll
    public static void setUp() {
        executorServiceForFetchingData = Executors.newFixedThreadPool(2);
    }

    @AfterAll
    public static void tearDown() {
        executorServiceForFetchingData.shutdown();
    }

    @Test
    public void givenAsyncTask_whenProcessingAsyncSucceed_thenReturnSuccess()
            throws ExecutionException, InterruptedException {

        KudoServiceImpl kudoService = Mockito.mock(KudoServiceImpl.class);

        Mockito.doNothing().when(kudoService).fetchingCategories();
        Mockito.doNothing().when(kudoService).fetchingLocations();

        CompletableFuture<Void> categoriesFuture = CompletableFuture.runAsync(kudoService::fetchingCategories, executorServiceForFetchingData);
        CompletableFuture<Void> locationsFuture = CompletableFuture.runAsync(kudoService::fetchingLocations, executorServiceForFetchingData);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(categoriesFuture, locationsFuture);
        allOf.get();

        Mockito.verify(kudoService, Mockito.times(1)).fetchingCategories();
        Mockito.verify(kudoService, Mockito.times(1)).fetchingLocations();
    }

    @Test
    public void givenAsyncTask_whenProcessingAsyncSucceed_thenReturnException()
            throws ExecutionException, InterruptedException {

        KudoServiceImpl kudoService = Mockito.mock(KudoServiceImpl.class);

        Mockito.doThrow(new RuntimeException("Ошибка")).when(kudoService).fetchingCategories();
        Mockito.doNothing().when(kudoService).fetchingLocations();


        try {
            CompletableFuture<Void> categoriesFuture = CompletableFuture.runAsync(kudoService::fetchingCategories, executorServiceForFetchingData);
            CompletableFuture<Void> locationsFuture = CompletableFuture.runAsync(kudoService::fetchingLocations, executorServiceForFetchingData);

            CompletableFuture<Void> allOf = CompletableFuture.allOf(categoriesFuture, locationsFuture);
            allOf.get();
        } catch (Exception e) {
            assert e.getCause().getMessage().equals("Ошибка");
        }
    }

}
