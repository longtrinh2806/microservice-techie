package com.trinhkimlong.orderservice.service;

import com.trinhkimlong.orderservice.request.OrderRequest;

public interface OrderService {
    String placeOrder(OrderRequest request);
}
