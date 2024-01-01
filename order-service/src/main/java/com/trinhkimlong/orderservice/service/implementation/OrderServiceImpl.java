package com.trinhkimlong.orderservice.service.implementation;

import com.trinhkimlong.orderservice.event.OrderPlacedEvent;
import com.trinhkimlong.orderservice.model.Order;
import com.trinhkimlong.orderservice.model.OrderLineItems;
import com.trinhkimlong.orderservice.repository.OrderRepository;
import com.trinhkimlong.orderservice.request.OrderLineItemsDto;
import com.trinhkimlong.orderservice.request.OrderRequest;
import com.trinhkimlong.orderservice.response.InventoryResponse;
import com.trinhkimlong.orderservice.service.OrderService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public OrderServiceImpl(OrderRepository orderRepository, WebClient.Builder webClientBuilder, KafkaTemplate kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String placeOrder(OrderRequest request) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = request.getOrderLineItemsDtoList()
                .stream()
                .map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
                .toList();
        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList()
                .stream()
                .map(orderLineItem -> orderLineItem.getSkuCode())
                .toList();

        // Call Inventory Service, and place order if product is in stock
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays
                .stream(inventoryResponseArray)
                .allMatch(inventoryResponse -> inventoryResponse.isInStock());

        if (allProductsInStock) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));

            return "Order Placed Successfully";
        } else throw new IllegalArgumentException("Product is not in stock, please try again later");
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        var orderLineItems = OrderLineItems
                .builder()
                .skuCode(orderLineItemsDto.getSkuCode())
                .price(orderLineItemsDto.getPrice())
                .quantity(orderLineItemsDto.getQuantity())
                .build();
        return orderLineItems;
    }
}
