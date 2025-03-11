package com.irena.financial_data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlphaVantageResponse {

    @JsonProperty("Meta Data")
    private MetaData metaData;

    @JsonProperty("Time Series (Daily)")
    private Map<String, DailyData> timeSeries;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MetaData {
        @JsonProperty("2. Symbol")
        private String symbol;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DailyData {
        @JsonProperty("1. open")
        private String open;

        @JsonProperty("2. high")
        private String high;

        @JsonProperty("3. low")
        private String low;

        @JsonProperty("4. close")
        private String close;

        @JsonProperty("5. volume")
        private String volume;
    }
}
