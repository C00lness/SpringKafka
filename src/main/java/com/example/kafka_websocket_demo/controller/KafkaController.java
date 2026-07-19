package com.example.kafka_websocket_demo.controller;

import com.example.kafka_websocket_demo.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        kafkaProducerService.sendMessage("my-topic", message);
        return "✅ Сообщение отправлено: " + message;
    }
}

/*
@RestController	Говорит Spring: «Этот класс обрабатывает HTTP-запросы и возвращает данные (не страницу)».
@RequestMapping("/api/kafka")	Все методы внутри будут доступны по адресу, начинающемуся с /api/kafka.
@Autowired	Внедряет KafkaProducerService (как мы обсуждали).
@PostMapping("/send")	Обрабатывает POST-запросы по адресу /api/kafka/send.
@RequestParam String message	Извлекает параметр message из HTTP-запроса.
Этот класс создаёт REST API, через который можно отправлять сообщения в Kafka.

POST-запрос:
http://localhost:8080/api/kafka/send?message=Hello → контроллер вызовет KafkaProducerService →
тот отправит сообщение в Kafka →
Kafka Consumer его перехватит и отправит через WebSocket.
 */
