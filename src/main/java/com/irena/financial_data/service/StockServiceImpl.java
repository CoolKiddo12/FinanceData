package com.irena.financial_data.service;

import com.irena.financial_data.dto.StockItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceImpl {

    private final YahooFinanceService yahooFinanceService;

    public List<StockItemDto> getAllStocks() {
        return yahooFinanceService.fetchStockData("AAPL");
    }
}

