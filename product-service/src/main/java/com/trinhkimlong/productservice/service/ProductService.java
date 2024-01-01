package com.trinhkimlong.productservice.service;

import com.trinhkimlong.productservice.request.ProductRequest;
import com.trinhkimlong.productservice.response.ProductResponse;

import java.util.List;

public interface ProductService {
    void createProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();
}
