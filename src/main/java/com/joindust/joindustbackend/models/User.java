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

import org.hibernate.annotations.NaturalId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.joindust.joindustbackend.models.audits.DateAudit;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
    @UniqueConstraint(columnNames = { "email" }) })
@Getter
@Setter
@NoArgsConstructor
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
  private Set<Contact> contacts = new HashSet<>();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonManagedReference
  private Set<Collect> collections = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User(@NotBlank @Size(max = 70) String corporateName, @NotBlank @Size(max = 15) String username,
      @NotBlank @Size(max = 60) @Email String email, @NotBlank @Size(max = 100) String password, @NotNull String cnpj,
      String phone, String profileImage, Set<Role> roles, Set<Contact> contacts) {
    this.corporateName = corporateName;
    this.username = username;
    this.email = email;
    this.password = password;
    this.cnpj = cnpj;
    this.phone = phone;
    this.profileImage = profileImage;
    this.roles = roles;
    this.contacts = contacts;
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

}
