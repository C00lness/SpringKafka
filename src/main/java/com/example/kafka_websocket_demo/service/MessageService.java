package com.example.kafka_websocket_demo.service;

import com.example.kafka_websocket_demo.model.Message;
import com.example.kafka_websocket_demo.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveMessage(String text) {
        Message message = new Message(text);
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}
