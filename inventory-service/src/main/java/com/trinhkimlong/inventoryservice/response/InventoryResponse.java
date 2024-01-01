package com.trinhkimlong.inventoryservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {
    private String skuCode;
    private boolean isInStock;
}
