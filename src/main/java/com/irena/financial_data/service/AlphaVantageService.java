package com.irena.financial_data.service;

import com.irena.financial_data.dto.AlphaVantageResponse;
import com.irena.financial_data.dto.StockItemDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class AlphaVantageService {

    private final WebClient webClient;

    @Value("${alphavantage.api.key}")
    private String apiKey;

    public AlphaVantageService() {
        this.webClient = WebClient.create("https://www.alphavantage.co");
    }

    public List<StockItemDto> fetchDailyStockData(String symbol) {


        String url = "/query?function=TIME_SERIES_DAILY&symbol=" + symbol + "&apikey=" + apiKey;
        AlphaVantageResponse response =  null;

        try {
            response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(AlphaVantageResponse.class)
                    .onErrorResume(e -> {
                        log.error("Error fetching data from Alpha Vantage: {}", e.getMessage());
                        return Mono.empty();
                    })
                    .block();
        } catch (Exception e) {
            log.error("Error fetching stocks", e);
            return new ArrayList<>();
        }



        if (response == null || response.getTimeSeries() == null) {
            log.error("Invalid response from Alpha Vantage");
            return new ArrayList<>();
        }

        List<StockItemDto> stockItems = new ArrayList<>();
        for (Map.Entry<String, AlphaVantageResponse.DailyData> entry : response.getTimeSeries().entrySet()) {
            LocalDate date = LocalDate.parse(entry.getKey());
            AlphaVantageResponse.DailyData data = entry.getValue();

            if (data.getOpen() == null || data.getClose() == null ||
                    data.getHigh() == null || data.getLow() == null ||
                    data.getVolume() == null) {
                continue;
            }

            long epochMilli = Instant.from(date.atStartOfDay(ZoneOffset.UTC)).toEpochMilli();

            StockItemDto stockItem = new StockItemDto();
            stockItem.setId(UUID.randomUUID().toString());
            stockItem.setSymbol(response.getMetaData().getSymbol());
            stockItem.setTimestamp(epochMilli);
            stockItem.setOpen(new BigDecimal(data.getOpen()));
            stockItem.setHigh(new BigDecimal(data.getHigh()));
            stockItem.setLow(new BigDecimal(data.getLow()));
            stockItem.setClose(new BigDecimal(data.getClose()));
            stockItem.setVolume(Long.parseLong(data.getVolume()));

            stockItems.add(stockItem);
        }

        return stockItems;
    }

}
