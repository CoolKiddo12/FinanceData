package com.irena.financial_data.controllers;

import com.irena.financial_data.entity.StockCassandra;
import com.irena.financial_data.service.StockCassandraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stockdata")
public class StockCassandraController {

    @Autowired
    private StockCassandraService stockCassandraService;

    @GetMapping("/all")
    public List<StockCassandra> getAllStockData() {
        return stockCassandraService.getAllStockData();
    }

    @GetMapping("/{symbol}")
    public List<StockCassandra> getStockDataBySymbol(@PathVariable String symbol) {
        return stockCassandraService.getStockDataBySymbol(symbol);
    }
}

