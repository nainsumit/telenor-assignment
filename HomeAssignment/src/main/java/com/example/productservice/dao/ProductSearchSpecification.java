package com.example.productservice.dao;

import static com.example.productservice.dao.Product.COLUMN_ADDRESS;
import static com.example.productservice.dao.Product.COLUMN_PRICE;
import static com.example.productservice.dao.Product.COLUMN_PROPERTIES;
import static com.example.productservice.dao.Product.COLUMN_TYPE;

import lombok.AllArgsConstructor;

import java.util.ArrayList;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.example.productservice.common.ProductSearchParameters;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

@AllArgsConstructor
public class ProductSearchSpecification implements Specification<Product> {

  private static final String GB_LIMIT = "gb_limit:";
  private final ProductSearchParameters searchParameters;

  @Override
  public Predicate toPredicate(Root<Product> root,
                               CriteriaQuery<?> query,
                               CriteriaBuilder builder) {
    var predicates = new ArrayList<Predicate>();
    if (!StringUtils.isEmpty(searchParameters.getType())) {
      predicates.add(builder.equal(builder.upper(root.get(COLUMN_TYPE)), searchParameters.getType().toUpperCase()));
    }
    if (searchParameters.getMinPrice() != null) {
      predicates.add(builder.greaterThanOrEqualTo(root.get(COLUMN_PRICE), searchParameters.getMinPrice()));
    }
    if (searchParameters.getMaxPrice() != null) {
      predicates.add(builder.lessThanOrEqualTo(root.get(COLUMN_PRICE), searchParameters.getMaxPrice()));
    }
    if (!StringUtils.isEmpty(searchParameters.getCity())) {
      predicates.add(builder.like(builder.upper(root.get(COLUMN_ADDRESS)), "% " + searchParameters.getCity().toUpperCase()));
    }
    if (!StringUtils.isEmpty(searchParameters.getProperty())) {
      predicates.add(builder.like(builder.upper(root.get(COLUMN_PROPERTIES)), searchParameters.getProperty().toUpperCase() + "%"));
    }
    if (!StringUtils.isEmpty(searchParameters.getPropertyColor())) {
      predicates
          .add(builder.equal(builder.upper(root.get(COLUMN_PROPERTIES)), ("color:" + searchParameters.getPropertyColor()).toUpperCase()));
    }

    if (searchParameters.getMinGBLimit() != null || searchParameters.getMaxGBLimit() != null) {
      var isGbLimitProperty = builder.like(builder.upper(root.get(COLUMN_PROPERTIES)), GB_LIMIT.toUpperCase() + "%");
      var gbLimit = builder.substring(root.get(COLUMN_PROPERTIES), builder.sum(builder.locate(root.get(COLUMN_PROPERTIES), ":"), 1))
          .as(Integer.class);
      var gbLimitCase = builder.selectCase().when(isGbLimitProperty, gbLimit).as(Integer.class);
      if (searchParameters.getMinGBLimit() != null) {
        predicates.add(builder.ge(gbLimitCase, searchParameters.getMinGBLimit()));
      }
      if (searchParameters.getMaxGBLimit() != null) {
        predicates.add(builder.le(gbLimitCase, searchParameters.getMaxGBLimit()));
      }
    }

    return builder.and(predicates.toArray(new Predicate[0]));
  }
}
