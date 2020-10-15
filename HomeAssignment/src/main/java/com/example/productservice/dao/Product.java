package com.example.productservice.dao;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Product {
  static final String COLUMN_ADDRESS = "address";
  static final String COLUMN_PRICE = "price";
  static final String COLUMN_PROPERTIES = "properties";
  static final String COLUMN_TYPE = "type";
  private static final String COLUMN_ID = "ID";
  @Id
  @GeneratedValue(strategy = SEQUENCE)
  @Column(name = COLUMN_ID)
  @JsonIgnore
  private Long id;
  @Column(name = COLUMN_TYPE)
  private String type;
  @Column(name = COLUMN_PROPERTIES)
  private String properties;
  @Column(name = COLUMN_PRICE)
  private double price;
  @Column(name = COLUMN_ADDRESS)
  private String address;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getProperties() {
    return properties;
  }

  public void setProperties(String properties) {
    this.properties = properties;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
