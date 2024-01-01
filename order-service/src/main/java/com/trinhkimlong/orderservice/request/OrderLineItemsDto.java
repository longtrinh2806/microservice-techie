package com.trinhkimlong.orderservice.request;

import lombok.Getter;

@Getter
public class OrderLineItemsDto {
    private Long id;
    private String skuCode;
    private Long price;
    private Integer quantity;
}
