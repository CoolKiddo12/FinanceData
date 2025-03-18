package com.irena.financial_data.service;

import com.irena.financial_data.entity.StockCassandra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class StockAnalysisScheduler {

    private static final List<String> SYMBOLS = Arrays.asList(
            "AAPL",
            "GOOGL",
            "TSLA",
            "AMZN",
            "MSFT"
    );

    @Autowired
    private StockCassandraService stockCassandraService;

    @Autowired
    private StockAnalysisService stockAnalysisService;

    @Scheduled(cron = "0 0 * * * *")
    public void analyzeStockDataForToday() {
            LocalDateTime endTime = LocalDateTime.now();
            LocalDateTime startTime = endTime.minusDays(1); // Time range of last 24 hours

            for (String symbol : SYMBOLS) {
                List<StockCassandra> stockDataForTimeRange = stockCassandraService.findStockDataForTimeRange(symbol, startTime, endTime);
                stockAnalysisService.analyzeStockData(stockDataForTimeRange, symbol, startTime.toLocalDate());
            }

    }
}