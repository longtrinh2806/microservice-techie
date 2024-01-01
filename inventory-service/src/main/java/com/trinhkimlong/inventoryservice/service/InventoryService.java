package com.trinhkimlong.inventoryservice.service;

import com.trinhkimlong.inventoryservice.response.InventoryResponse;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> isInStock(List<String> skuCode);
}
