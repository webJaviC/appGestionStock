package com.printmanagement.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SequenceGeneratorService {
    private final AtomicInteger routeSheetSequence = new AtomicInteger(1);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public String generateRouteNumber() {
        String date = LocalDate.now().format(formatter);
        return String.format("HR%s%04d", date, routeSheetSequence.getAndIncrement());
    }
}