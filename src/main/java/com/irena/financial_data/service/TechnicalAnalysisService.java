package com.irena.financial_data.service;

import com.irena.financial_data.entity.StockCassandra;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TechnicalAnalysisService {
    private BigDecimal roundToTwoDecimals(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateSMA(List<StockCassandra> stockDataList, int period) {
        if (stockDataList.size() < period) {
            return BigDecimal.ZERO;
        }

        List<BigDecimal> closingPrices = stockDataList.stream()
                .map(StockCassandra::getClose)
                .collect(Collectors.toList());

        BigDecimal sum = closingPrices.subList(closingPrices.size() - period, closingPrices.size())
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sum.divide(BigDecimal.valueOf(period), BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal calculateEMA(List<StockCassandra> stockDataList, int period) {
        if (stockDataList.size() < period) {
            return BigDecimal.ZERO;
        }

        double smoothingFactor = 2.0 / (period + 1);
        BigDecimal previousEMA = calculateSMA(stockDataList.subList(0, period), period);

        for (int i = period; i < stockDataList.size(); i++) {
            BigDecimal closePrice = stockDataList.get(i).getClose();
            previousEMA = closePrice.subtract(previousEMA)
                    .multiply(BigDecimal.valueOf(smoothingFactor))
                    .add(previousEMA);
        }

        return previousEMA;
    }

    public BigDecimal calculateRSI(List<StockCassandra> stockDataList, int period) {
        if (stockDataList.size() < period + 1) {
            return BigDecimal.ZERO;
        }

        BigDecimal gainSum = BigDecimal.ZERO;
        BigDecimal lossSum = BigDecimal.ZERO;

        for (int i = 1; i <= period; i++) {
            BigDecimal change = stockDataList.get(i).getClose().subtract(stockDataList.get(i - 1).getClose());

            if (change.compareTo(BigDecimal.ZERO) > 0) {
                gainSum = gainSum.add(change);
            } else {
                lossSum = lossSum.add(change.abs());
            }
        }

        BigDecimal avgGain = gainSum.divide(BigDecimal.valueOf(period), BigDecimal.ROUND_HALF_UP);
        BigDecimal avgLoss = lossSum.divide(BigDecimal.valueOf(period), BigDecimal.ROUND_HALF_UP);

        if (avgLoss.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.valueOf(100);
        }

        BigDecimal rs = avgGain.divide(avgLoss, BigDecimal.ROUND_HALF_UP);
        return BigDecimal.valueOf(100).subtract(BigDecimal.valueOf(100).divide(BigDecimal.ONE.add(rs), BigDecimal.ROUND_HALF_UP));
    }

    public BigDecimal calculateMACD(List<StockCassandra> stockDataList, int shortPeriod, int longPeriod) {
        BigDecimal shortEMA = calculateEMA(stockDataList, shortPeriod);
        BigDecimal longEMA = calculateEMA(stockDataList, longPeriod);

        return shortEMA.subtract(longEMA);
    }

    public BigDecimal calculateStochasticOscillator(List<StockCassandra> stockDataList, int period) {
        if (stockDataList.size() < period) {
            return BigDecimal.ZERO;
        }

        BigDecimal close = stockDataList.get(stockDataList.size() - 1).getClose();
        BigDecimal high = stockDataList.stream().skip(stockDataList.size() - period)
                .map(StockCassandra::getHigh)
                .max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);

        BigDecimal low = stockDataList.stream().skip(stockDataList.size() - period)
                .map(StockCassandra::getLow)
                .min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);

        if (high.equals(low)) {
            return BigDecimal.ZERO;
        }

        return close.subtract(low).divide(high.subtract(low), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
    }
}

