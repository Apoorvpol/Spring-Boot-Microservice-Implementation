package com.example.order_service.service;


import com.example.order_service.event.OrderPlacedEvent;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;
import com.example.order_service.dto.InventoryResponse;
import com.example.order_service.dto.OrderLineItemsDto;
import com.example.order_service.dto.orderRequest;
import com.example.order_service.model.Order;
import com.example.order_service.model.OrderLineItems;
import com.example.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
//    private final WebClient.Builder webClientBuilder;
    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;


    public String placeOrder(orderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems =
                orderRequest.getOrderLineItemsDtoList()
                        .stream()
                        .map(this::mapToDto)
                        .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes= order.getOrderLineItemsList().stream()
                        .map(orderLineItem -> orderLineItem.getSkuCode())
                        .toList();

        // call inventory service, and place order if its in stock
        InventoryResponse[] inventoryResponsesArray =
                restTemplate.getForObject(
                        "http://inventory-service/api/inventory?skuCode=" + skuCodes,
                        InventoryResponse[].class
                );
        boolean  allProductsInStock = Arrays.stream(inventoryResponsesArray).allMatch(inventoryResponse -> inventoryResponse.isInStock());

        if(allProductsInStock) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
            return "Order Placed Successfully";
        }else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {

        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

        return orderLineItems;
    }


}
