package com.example.kafka_websocket_demo.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/hello")
    public String sayHello(Model model) {
        model.addAttribute("message", "Привет из Spring Boot!");
        return "hello"; // Возвращает шаблон hello.html
    }
}
