package com.joindust.joindustbackend.payloads.responses;

public class UserSummary {

  private Long id;
  private String corporateName;
  private String phone;
  private String email;

  public UserSummary(Long id, String corporateName, String phone, String email) {
    this.id = id;
    this.corporateName = corporateName;
    this.phone = phone;
    this.email = email;
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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
