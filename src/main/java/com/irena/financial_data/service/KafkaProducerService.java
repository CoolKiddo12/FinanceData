package com.irena.financial_data.service;

import com.irena.financial_data.dto.StockItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.irena.financial_data.config.KafkaTopicConfig.KAFKA_TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendStockData(StockItemDto stockItemDto) {
        try {
            kafkaTemplate.send(KAFKA_TOPIC, stockItemDto);
            log.info("Sent stock data to Kafka topic '{}': {}", KAFKA_TOPIC, stockItemDto);
        } catch (Exception e) {
            log.error("Failed to send stock data to Kafka topic '{}': {}", KAFKA_TOPIC, stockItemDto, e);
        }
    }
}
