package com.irena.financial_data.service;

import com.irena.financial_data.entity.StockCassandra;
import com.irena.financial_data.repository.cassandra.StockCassandraRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class StockCassandraService {
    private final StockCassandraRepository stockCassandraRepository;

    public StockCassandraService(StockCassandraRepository stockCassandraRepository) {
        this.stockCassandraRepository = stockCassandraRepository;
    }

    public List<StockCassandra> getAllStockData() {
        return stockCassandraRepository.findAll();
    }

    public List<StockCassandra> getStockDataBySymbol(String symbol) {
        List<StockCassandra> bySymbol = stockCassandraRepository.findBySymbol(symbol);
        return bySymbol;
    }

    public List<StockCassandra> findStockDataForTimeRange(String symbol, LocalDateTime startTime, LocalDateTime endTime) {
        Instant startInstant = startTime.toInstant(ZoneOffset.UTC);
        BigInteger startMicroSec = BigInteger.valueOf(startInstant.toEpochMilli() * 1000);
        Instant endInstant = endTime.toInstant(ZoneOffset.UTC);
        BigInteger endMicroSec = BigInteger.valueOf(endInstant.toEpochMilli() * 1000);


//        Instant parsedStart = Instant.parse("2025-03-13T00:00:00Z");
//        Instant parsedEnd = Instant.parse("2025-03-14T00:00:00Z");
        return stockCassandraRepository.findStockPricesBySymbolAndTimestampRange(symbol, startInstant, endInstant);
    }
}
