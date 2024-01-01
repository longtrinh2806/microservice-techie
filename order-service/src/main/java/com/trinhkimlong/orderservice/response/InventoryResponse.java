package com.trinhkimlong.orderservice.response;

import lombok.*;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryResponse {
    private String skuCode;
    private boolean isInStock;
}
