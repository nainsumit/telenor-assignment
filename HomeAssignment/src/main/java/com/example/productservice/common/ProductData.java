package com.example.productservice.common;

import lombok.Value;

import java.util.List;

import com.example.productservice.dao.Product;

@Value
public class ProductData {
  private List<Product> data;
}
