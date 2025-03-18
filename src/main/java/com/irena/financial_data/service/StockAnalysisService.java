package com.irena.financial_data.service;

import com.irena.financial_data.entity.StockAnalysis;
import com.irena.financial_data.entity.StockCassandra;
import com.irena.financial_data.repository.postgres.StockAnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class StockAnalysisService {
    @Autowired
    private TechnicalAnalysisService technicalAnalysisService;

    @Autowired
    private StockAnalysisRepository stockAnalysisRepository;

    public void analyzeStockData(List<StockCassandra> stockDataList, String symbol, LocalDate start) {
        if (stockDataList == null || stockDataList.isEmpty()) {
            System.out.println("No stock data found for symbol: " + symbol);
            return;
        }

        BigDecimal sma = technicalAnalysisService.calculateSMA(stockDataList, 20);
        System.out.println("SMA: " + sma);

        BigDecimal ema = technicalAnalysisService.calculateEMA(stockDataList, 20);
        System.out.println("EMA: " + ema);

        BigDecimal rsi = technicalAnalysisService.calculateRSI(stockDataList, 14);
        System.out.println("RSI: " + rsi);

        BigDecimal macd = technicalAnalysisService.calculateMACD(stockDataList, 12, 26);
        System.out.println("MACD: " + macd);

        BigDecimal stochastic = technicalAnalysisService.calculateStochasticOscillator(stockDataList, 14);
        System.out.println("Stochastic Oscillator: " + stochastic);

        StockAnalysis stockAnalysis = new StockAnalysis();
        stockAnalysis.setSymbol(symbol);
        stockAnalysis.setTimestamp(start);
        stockAnalysis.setSma(sma);
        stockAnalysis.setEma(ema);
        stockAnalysis.setRsi(rsi);
        stockAnalysis.setMacd(macd);
        stockAnalysis.setStochasticOscillator(stochastic);

        stockAnalysisRepository.save(stockAnalysis);
    }
    public List<StockAnalysis> getAllStockAnalysis() {
        return stockAnalysisRepository.findAll();
    }

    public StockAnalysis getStockAnalysisBySymbol(String symbol) {
        return stockAnalysisRepository.findBySymbol(symbol);
    }

}
