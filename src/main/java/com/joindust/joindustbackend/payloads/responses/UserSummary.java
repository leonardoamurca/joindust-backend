package com.joindust.joindustbackend.payloads.responses;

public class UserSummary {

  private Long id;
  private String corporateName;
  private String username;
  private String email;

  public UserSummary(Long id, String corporateName, String username, String email) {
    this.id = id;
    this.corporateName = corporateName;
    this.username = username;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
