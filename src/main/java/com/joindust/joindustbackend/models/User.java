package com.joindust.joindustbackend.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.joindust.joindustbackend.models.audits.DateAudit;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
    @UniqueConstraint(columnNames = { "email" }) })
public class User extends DateAudit {

  private static final long serialVersionUID = 4651194649320610018L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 70)
  @Column(name = "corporate_name")
  private String corporateName;

  @NotBlank
  @Size(max = 15)
  private String username;

  @NaturalId
  @NotBlank
  @Size(max = 60)
  @Email
  private String email;

  @NotBlank
  @Size(max = 100)
  private String password;

  @NotNull
  @Column(length = 14)
  private String cnpj;

  @Column(length = 9)
  private String phone;

  @Column(name = "profile_image")
  private String profileImage;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonManagedReference
  private Set<Collect> collections = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User() {
  }

  public User(@NotBlank @Size(max = 70) String corporateName, @NotBlank @Size(max = 15) String username,
      @NotBlank @Size(max = 60) @Email String email, @NotBlank @Size(max = 100) String password, @NotNull String cnpj,
      String phone, String profileImage, Set<Role> roles) {
    this.corporateName = corporateName;
    this.username = username;
    this.email = email;
    this.password = password;
    this.cnpj = cnpj;
    this.phone = phone;
    this.profileImage = profileImage;
    // this.collections = collections;
    this.roles = roles;
  }

  public User(@NotBlank @Size(max = 70) String corporateName, @NotBlank @Size(max = 15) String username,
      @NotBlank @Size(max = 60) @Email String email, @NotBlank @Size(max = 100) String password, @NotNull String cnpj,
      String phone) {
    this.corporateName = corporateName;
    this.username = username;
    this.email = email;
    this.password = password;
    this.cnpj = cnpj;
    this.phone = phone;

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

  public String getProfileImage() {
    return profileImage;
  }

  public void setProfileImage(String profileImage) {
    this.profileImage = profileImage;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
