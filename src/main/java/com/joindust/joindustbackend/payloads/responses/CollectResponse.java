package com.joindust.joindustbackend.payloads.responses;

public class CollectResponse {

  private Long id;
  private Double quantity;
  private Double price;
  private UserSummary user;

  public CollectResponse() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public UserSummary getUser() {
    return user;
  }

  public void setUser(UserSummary user) {
    this.user = user;
  }

}