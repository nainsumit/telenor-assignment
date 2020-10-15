package com.example.productservice.controller;

import lombok.AllArgsConstructor;

import com.example.productservice.common.ProductData;
import com.example.productservice.common.ProductSearchParameters;
import com.example.productservice.service.ProductService;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
@AllArgsConstructor
public class ProductController {

  private ProductService service;

  @GetMapping(path = "/product",
      produces = "application/json")
  public ProductData findProducts(
      @RequestParam(value = "type", required = false)
          String type,
      @RequestParam(value = "min_price", required = false)
          Double minPrice,
      @RequestParam(value = "max_price", required = false)
          Double maxPrice,
      @RequestParam(value = "city", required = false)
          String city,
      @RequestParam(value = "property", required = false)
          String property,
      @RequestParam(value = "property:color", required = false)
          String propertyColor,
      @RequestParam(value = "property:gb_limit_min", required = false)
          Integer minGBLimit,
      @RequestParam(value = "property:gb_limit_max", required = false)
          Integer maxGBLimit) {
    return service
        .findProducts(new ProductSearchParameters(type, minPrice, maxPrice, city, property, propertyColor, minGBLimit, maxGBLimit));
  }

  @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleException(MethodArgumentTypeMismatchException validationException) {
    return validationException.getMessage();
  }
}
