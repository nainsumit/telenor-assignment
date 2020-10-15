package com.example.productservice.service;

import com.example.productservice.common.ProductData;
import com.example.productservice.common.ProductSearchParameters;

public interface ProductService {
  ProductData findProducts(ProductSearchParameters parameters);
}
