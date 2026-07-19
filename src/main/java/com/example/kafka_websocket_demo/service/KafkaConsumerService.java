package com.example.kafka_websocket_demo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messagingService;

    public KafkaConsumerService(SimpMessagingTemplate messagingTemplate, MessageService messagingService) {
        this.messagingTemplate = messagingTemplate;
        this.messagingService = messagingService;
    }

    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public void consumeMessage(String message) {
        System.out.println("📩 Получено сообщение из Kafka: " + message);
        messagingService.saveMessage(message);
        messagingTemplate.convertAndSend("/topic/messages", message);
    }
}

/*
Этот класс слушает Kafka.
Как только в топик my-topic приходит новое сообщение, этот метод его перехватывает,
выводит в консоль и мгновенно отправляет через WebSocket всем подключённым клиентам.
 */