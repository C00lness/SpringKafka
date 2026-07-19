package com.example.kafka_websocket_demo.controller;

import com.example.kafka_websocket_demo.model.Message;
import com.example.kafka_websocket_demo.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public Message createMessage(@RequestParam String text) {
        return messageService.saveMessage(text);
    }

    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }
}