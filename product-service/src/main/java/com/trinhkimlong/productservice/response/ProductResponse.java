package com.trinhkimlong.productservice.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private Long price;
}
