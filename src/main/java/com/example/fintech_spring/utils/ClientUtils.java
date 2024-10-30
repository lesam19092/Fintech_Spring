package com.example.fintech_spring.utils;

import com.example.fintech_spring.dto.Event;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ClientUtils {

    private static final ZoneOffset MOSCOW_TIME_ZONE = ZoneOffset.ofHours(+3);

    public static long getActualSince(LocalDate dateFrom) {
        return dateFrom != null ?
                dateFrom.atStartOfDay().toEpochSecond(MOSCOW_TIME_ZONE) :
                LocalDate.now().minusDays(7).atStartOfDay().toEpochSecond(MOSCOW_TIME_ZONE);
    }

    public static long getActualUntil(LocalDate dateTo) {
        return dateTo != null ?
                dateTo.atStartOfDay().toEpochSecond(MOSCOW_TIME_ZONE) :
                LocalDate.now().atStartOfDay().toEpochSecond(MOSCOW_TIME_ZONE);
    }

    public static String getUrl(LocalDate dateFrom, LocalDate dateTo) {
        long actual_since = getActualSince(dateFrom);
        long actual_until = getActualUntil(dateTo);
        String url = String.format("/events/?location=spb&fields=price,id,title,dates&actual_since=%d&actual_until=%d",
                actual_since, actual_until);
        log.debug("Generated URL: {}", url);
        return url;
    }

    public static List<Event> setEventPrices(List<Event> events) {
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