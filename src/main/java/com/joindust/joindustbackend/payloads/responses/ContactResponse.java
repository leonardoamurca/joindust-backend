package com.joindust.joindustbackend.payloads.responses;

public class ContactResponse {

  private Long id;
  private String corporateName;
  private String email;
  private String phone;
  private UserSummary recycler;

  public ContactResponse() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCorporateName() {
    return corporateName;
  }

  public void setCorporateName(String corporateName) {
    this.corporateName = corporateName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public UserSummary getRecycler() {
    return recycler;
  }

  public void setRecycler(UserSummary recycler) {
    this.recycler = recycler;
  }

}