package com.example.kafka_websocket_demo.controller;

import com.example.kafka_websocket_demo.service.MathService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/math")
public class MathController {
    private final MathService mathService;

    public MathController(MathService mathService) {
        this.mathService = mathService;
    }

    @GetMapping("/sync")
    public int sync(@RequestParam int a, @RequestParam int b) {
        return mathService.addSync(a, b);
    }

    // Асинхронный вызов — ждём результат
    @GetMapping("/async")
    public String async(@RequestParam int a, @RequestParam int b) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = mathService.addAsync(a, b);
        return "Результат: " + future.get(); // ждём завершения
    }

    // Асинхронный вызов — без возврата (fire-and-forget)
    @GetMapping("/log")
    public String log(@RequestParam int a, @RequestParam int b) {
        mathService.logSumAsync(a, b);
        return "Задача отправлена в фоновый поток";
    }
}
