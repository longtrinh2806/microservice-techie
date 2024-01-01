package com.trinhkimlong.orderservice.controller;

import com.trinhkimlong.orderservice.request.OrderRequest;
import com.trinhkimlong.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest request) {
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(request));
    }
    public CompletableFuture<String> fallBackMethod(OrderRequest request, RuntimeException exception) {
        return CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, please order later!");
    }
}
