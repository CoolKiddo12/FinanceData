package com.irena.financial_data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockSchedulerService {

    private final StockServiceImpl stockServiceImpl;

    List<String> SYMBOLS = Arrays.asList(
            "AAPL",
            "GOOGL",
            "TSLA",
            "AMZN",
            "MSFT"
    );

    @Scheduled(fixedRate = 3600000)
    public void fetchAllStockData() {
        log.info("Starting scheduled stock data fetch...");



        for (String symbol : SYMBOLS) {
            log.info("Fetching and saving stock data for {}", symbol);
            stockServiceImpl.fetchAndSaveStockData(symbol);
        }

        log.info("Stock data fetch completed.");
    }
}
