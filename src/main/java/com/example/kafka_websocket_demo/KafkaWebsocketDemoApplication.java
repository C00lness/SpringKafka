package com.example.kafka_websocket_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class KafkaWebsocketDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaWebsocketDemoApplication.class, args);
	}

}
