package com.example.productservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class ProductSearchParameters implements Serializable {
  private String type;
  private Double minPrice;
  private Double maxPrice;
  private String city;
  private String property;
  private String propertyColor;
  private Integer minGBLimit;
  private Integer maxGBLimit;
}
