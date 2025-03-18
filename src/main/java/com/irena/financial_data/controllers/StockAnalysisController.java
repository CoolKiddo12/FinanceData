package com.irena.financial_data.controllers;

import com.irena.financial_data.entity.StockAnalysis;
import com.irena.financial_data.entity.StockCassandra;
import com.irena.financial_data.service.StockAnalysisService;
import com.irena.financial_data.service.StockCassandraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("/api/stockanalysis")
public class StockAnalysisController {

    @Autowired
    private StockAnalysisService stockAnalysisService;
    @Autowired
    private StockCassandraService stockCassandraService;

    @GetMapping("/all")
    public List<StockAnalysis> getAllStockAnalysis() {
        return stockAnalysisService.getAllStockAnalysis();
    }

    @GetMapping("/{symbol}")
    public StockAnalysis getStockAnalysisBySymbol(@PathVariable String symbol) {
        return stockAnalysisService.getStockAnalysisBySymbol(symbol);
    }
    @PostMapping("/analyze")
    public void analyzeStockData(
            @RequestParam String symbol,
            @RequestParam long startTime,
            @RequestParam long endTime) {

        Instant fromInstant = Instant.ofEpochMilli(startTime);
        LocalDateTime start = LocalDateTime.ofInstant(fromInstant, ZoneOffset.UTC);
        Instant toInstant = Instant.ofEpochMilli(endTime);
        LocalDateTime end = LocalDateTime.ofInstant(toInstant, ZoneOffset.UTC);

        List<StockCassandra> stockDataList = stockCassandraService.findStockDataForTimeRange(symbol, start, end);

        stockAnalysisService.analyzeStockData(stockDataList, symbol, start.toLocalDate());
    }
}
