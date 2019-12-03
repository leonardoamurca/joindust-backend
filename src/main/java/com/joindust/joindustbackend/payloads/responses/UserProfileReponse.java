package com.joindust.joindustbackend.payloads.responses;

import java.time.Instant;

public class UserProfileReponse {

  private Long id;

  private String corporateName;

  private String username;

  private String email;

  private Instant joinedAt;

  private String cnpj;

  private String phone;

  private Long collectionsCount;

  private String profileImage;

  public UserProfileReponse(Long id, String corporateName, String username, String email, Instant joinedAt, String cnpj,
      String phone, Long collectionsCount, String profileImage) {
    this.id = id;
    this.corporateName = corporateName;
    this.username = username;
    this.email = email;
    this.joinedAt = joinedAt;
    this.cnpj = cnpj;
    this.phone = phone;
    this.collectionsCount = collectionsCount;
    this.profileImage = profileImage;
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Instant getJoinedAt() {
    return joinedAt;
  }

  public void setJoinedAt(Instant joinedAt) {
    this.joinedAt = joinedAt;
  }

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Long getCollectionsCount() {
    return collectionsCount;
  }

  public void setCollectionsCount(Long collectionsCount) {
    this.collectionsCount = collectionsCount;
  }

  public String getProfileImage() {
    return profileImage;
  }

  public void setProfileImage(String profileImage) {
    this.profileImage = profileImage;
  }

}