package com.example.kafka_websocket_demo.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
        System.out.println("📤 Сообщение отправлено в Kafka: " + message);
    }
}

/*
Это продюсер (отправитель) сообщений в Kafka.
Когда кто-то вызовет метод sendMessage(topic, message), он отправит сообщение в указанный топик.
 */