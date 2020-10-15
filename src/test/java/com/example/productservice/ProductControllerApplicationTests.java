package com.example.productservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import com.example.productservice.common.ProductSearchParameters;
import com.example.productservice.service.ProductService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductControllerApplicationTests {

  @Autowired
  private ProductService service;

  @ParameterizedTest
  @MethodSource("parametersForTestService")
  void testService(ProductSearchParameters searchParameters,
                   int expectedSize) {
    var data = service.findProducts(searchParameters);

    assertEquals(expectedSize, data.getData().size());
    data.getData().forEach(element -> {
      if (searchParameters.getType() != null) {
        Assertions.assertTrue(element.getType().equalsIgnoreCase(searchParameters.getType()));
      }
      if (searchParameters.getMinPrice() != null) {
        Assertions.assertTrue(element.getPrice() >= searchParameters.getMinPrice());
      }
      if (searchParameters.getMaxPrice() != null) {
        Assertions.assertTrue(element.getPrice() <= searchParameters.getMaxPrice());
      }
      if (searchParameters.getCity() != null) {
        Assertions.assertTrue(element.getAddress().toUpperCase().contains(" " + searchParameters.getCity().toUpperCase()));
      }
      if (searchParameters.getProperty() != null) {
        Assertions.assertTrue(element.getProperties().toUpperCase().contains(searchParameters.getProperty().toUpperCase()));
      }
      if (searchParameters.getPropertyColor() != null) {
        Assertions.assertTrue(element.getProperties().toUpperCase().contains(searchParameters.getPropertyColor().toUpperCase()));
      }
      if (searchParameters.getMinGBLimit() != null) {
        Assertions.assertTrue(Integer.parseInt(element.getProperties().split(":")[1]) >= searchParameters.getMinGBLimit());
      }
      if (searchParameters.getMaxGBLimit() != null) {
        Assertions.assertTrue(Integer.parseInt(element.getProperties().split(":")[1]) <= searchParameters.getMaxGBLimit());
      }
    });
  }

  private static Stream<Arguments> parametersForTestService() {
    return Stream.of(
        Arguments.of(new ProductSearchParameters(null, null, null, null, null, null, null, null), 99),

        Arguments.of(new ProductSearchParameters("pHone", null, null, null, null, null, null, null), 42),
        Arguments.of(new ProductSearchParameters("PHONE", 80.0, null, null, null, null, null, null), 36),
        Arguments.of(new ProductSearchParameters("phone", 80.0, 200.0, null, null, null, null, null), 6),
        Arguments.of(new ProductSearchParameters("phone", 80.0, 200.0, "Stockholm", null, null, null, null), 2),
        Arguments.of(new ProductSearchParameters("phone", 80.0, 200.0, "Stockholm", "coLor", "grön", null, null), 1),

        Arguments.of(new ProductSearchParameters("phone", 80.0, 200.0, null, "", "", null, null), 6),
        Arguments.of(new ProductSearchParameters("phone", 80.0, 20.0, "STOckholm", "coLor", "grön", null, null), 0),
        Arguments.of(new ProductSearchParameters("phone", 80.0, 200.0, "stockholm", "coLor", "grön", null, null), 1),
        Arguments.of(new ProductSearchParameters("phone", 80.0, 200.0, "STOCKHOLM", "coLor", "grön", 10, null), 0),
        Arguments.of(new ProductSearchParameters("phone", 80.0, 200.0, "Stockholm", "coLor", "grön", null, 0), 0),

        Arguments.of(new ProductSearchParameters("subscription", null, null, null, null, null, null, null), 57),
        Arguments.of(new ProductSearchParameters("subscription", null, null, null, "coLor", null, null, null), 0),
        Arguments.of(new ProductSearchParameters("SUBSCRIPTION", 10.0, 500.0, null, null, null, null, null), 32),
        Arguments.of(new ProductSearchParameters("subscriptioN", 10.0, 500.0, null, "gb_limit", null, null, null), 32),
        Arguments.of(new ProductSearchParameters("subscriptioN", 10.0, 500.0, null, null, null, 10, null), 32),
        Arguments.of(new ProductSearchParameters("subscriptioN", 10.0, 500.0, null, null, null, 10, 50), 32),
        Arguments.of(new ProductSearchParameters("subscriptioN", 10.0, 500.0, null, null, null, 10, 40), 13),
        Arguments.of(new ProductSearchParameters("subscriptioN", 10.0, 500.0, null, null, null, 11, 40), 0),

        Arguments.of(new ProductSearchParameters(null, 10.0, 500.0, null, null, null, 10, 40), 13),
        Arguments.of(new ProductSearchParameters(null, 10.0, 500.0, null, "color", null, null, null), 27),
        Arguments.of(new ProductSearchParameters(null, 10.0, 500.0, "STOckholm", null, null, null, null), 23));
  }
}
