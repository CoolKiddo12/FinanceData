package com.irena.financial_data.service;

import com.irena.financial_data.dto.StockItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockServiceImpl {

    private final YahooFinanceService yahooFinanceService;
    private final AlphaVantageService alphaVantageService;
    private final KafkaProducerService KafkaProducerService;

    public void fetchAndSaveStockData(String symbol) {
        List<StockItemDto> yahooData = yahooFinanceService.fetchStockData(symbol);
        List<StockItemDto> alphaVantageData = alphaVantageService.fetchDailyStockData(symbol);

        yahooData.forEach(KafkaProducerService::sendStockData);

        alphaVantageData.forEach(KafkaProducerService::sendStockData);
        log.info("📡 Stock data sent to Kafka for symbol: {}", symbol);
    }


}

