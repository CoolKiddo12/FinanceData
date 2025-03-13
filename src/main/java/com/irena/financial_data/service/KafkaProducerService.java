package com.irena.financial_data.service;

import com.irena.financial_data.dto.StockItemDto;
import com.irena.financial_data.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import static com.irena.financial_data.config.KafkaTopicConfig.KAFKA_TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaProducer kafkaProducer;

    public void sendStockData(StockItemDto stockItemDto) {

        String jsonString = JsonUtils.convertObjectToJsonString(stockItemDto, StockItemDto.class);
        ProducerRecord<String, String> record = new ProducerRecord<>(KAFKA_TOPIC, null, jsonString);
        try {
            kafkaProducer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    exception.printStackTrace();
                } else {
                    System.out.println("Message sent to topic " + metadata.topic() + " with offset " + metadata.offset());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
