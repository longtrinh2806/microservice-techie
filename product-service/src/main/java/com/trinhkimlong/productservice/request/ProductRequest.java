package com.trinhkimlong.productservice.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductRequest {
    private String name;
    private String description;
    private Long price;
}
