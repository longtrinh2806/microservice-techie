package com.trinhkimlong.orderservice.request;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {
    private List<OrderLineItemsDto> orderLineItemsDtoList;
}
