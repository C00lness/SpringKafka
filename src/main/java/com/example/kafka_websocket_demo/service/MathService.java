package com.example.kafka_websocket_demo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class MathService {
    public int addSync(int a, int b) {
        return a + b;
    }

    @Async
    public CompletableFuture<Integer> addAsync(int a, int b) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return CompletableFuture.completedFuture(a + b);
    }

    @Async
    public void logSumAsync(int a, int b) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Сумма вычислена: " + (a + b));
    }
}
