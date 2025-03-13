package com.irena.financial_data.service;

import com.irena.financial_data.dto.StockItemDto;
import com.irena.financial_data.dto.YahooFinanceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class YahooFinanceService {

    public List<StockItemDto> fetchStockData(String symbol) {

        WebClient webClient = WebClient.create("https://query1.finance.yahoo.com");
        String url = "/v8/finance/chart/" + symbol + "?interval=60m&range=1mo";

        YahooFinanceResponse response=null;

        try {
            response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(YahooFinanceResponse.class)
                    .onErrorResume(e -> {
                        System.err.println("Error fetching data from Yahoo Finance: " + e.getMessage());
                        return Mono.empty();
                    })
                    .block();
        }
        catch (Exception e) {
            log.error("Error fetching stocks", e);
            return new ArrayList<>();
        }

        if (response == null || response.getChart() == null || response.getChart().getResult() == null) {
            log.error("Invalid response from Yahoo Finance");
            return new ArrayList<>();
        }

        YahooFinanceResponse.Result result = response.getChart().getResult().get(0);
        List<Long> timestamps = result.getTimestamp();
        YahooFinanceResponse.Quote quote = result.getIndicators().getQuote().get(0);

        List<StockItemDto> stockItems = new ArrayList<>();

        for (int i = 0; i < timestamps.size(); i++) {
            if (quote.getOpen().get(i) == null || quote.getClose().get(i) == null ||
                    quote.getHigh().get(i) == null || quote.getLow().get(i) == null ||
                    quote.getVolume().get(i) == null) {
                continue;
            }
            Instant date = Instant.ofEpochSecond(timestamps.get(i));

            StockItemDto stockItem = new StockItemDto();
            stockItem.setId(UUID.randomUUID().toString());
            stockItem.setSymbol(result.getMeta().getSymbol());
            stockItem.setTimestamp(date.toEpochMilli());
            stockItem.setOpen(BigDecimal.valueOf(quote.getOpen().get(i)));
            stockItem.setHigh(BigDecimal.valueOf(quote.getHigh().get(i)));
            stockItem.setLow(BigDecimal.valueOf(quote.getLow().get(i)));
            stockItem.setClose(BigDecimal.valueOf(quote.getClose().get(i)));
            stockItem.setVolume(quote.getVolume().get(i));

            stockItems.add(stockItem);
        }
        log.info("Received data: {}", response);

        return stockItems;
    }
}
