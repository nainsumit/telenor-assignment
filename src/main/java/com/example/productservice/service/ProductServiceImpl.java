package com.example.productservice.service;

import static org.springframework.data.jpa.domain.Specification.where;

import lombok.AllArgsConstructor;

import com.example.productservice.common.ProductData;
import com.example.productservice.common.ProductSearchParameters;
import com.example.productservice.dao.Product;
import com.example.productservice.dao.ProductDao;
import com.example.productservice.dao.ProductSearchSpecification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductDao dao;

  @Override
  public ProductData findProducts(ProductSearchParameters parameters) {
    Specification<Product> specification = new ProductSearchSpecification(parameters);
    return new ProductData(dao.findAll(where(specification)));
  }
}
