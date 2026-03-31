package com.example.order_service.controller;

import com.example.order_service.dto.orderRequest;
import com.example.order_service.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackPlaceOrder")
    @TimeLimiter(name = "inventory")
    @Retry(name= "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody orderRequest orderrequest) {
     return CompletableFuture.supplyAsync(()-> orderService.placeOrder(orderrequest));

    }

    public CompletableFuture<String> fallbackPlaceOrder(orderRequest orderrequest, RuntimeException runtimeException) {
     return CompletableFuture.supplyAsync(()-> "Order Placed Failed. Please try again later.");
    }


}
