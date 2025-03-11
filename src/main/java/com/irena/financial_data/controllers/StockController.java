package com.irena.financial_data.controllers;

import com.irena.financial_data.dto.StockItemDto;
import com.irena.financial_data.service.AlphaVantageService;
import com.irena.financial_data.service.StockServiceImpl;
import com.irena.financial_data.service.YahooFinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final YahooFinanceService yahooFinanceService;
    private final AlphaVantageService alphaVantageService;

    @GetMapping("/yahoo/{symbol}")
    public List<StockItemDto> getYahooStockData(@PathVariable String symbol) {
        return yahooFinanceService.fetchStockData(symbol);
    }

    @GetMapping("/alpha/{symbol}")
    public List<StockItemDto> getAlphaStockData(@PathVariable String symbol) {
        return alphaVantageService.fetchDailyStockData(symbol);
    }
}