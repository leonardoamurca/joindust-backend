package com.joindust.joindustbackend.models;

import org.hibernate.annotations.NaturalId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.joindust.joindustbackend.models.audits.DateAudit;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
public class Contact extends DateAudit {

  private static final long serialVersionUID = -3863017794181855748L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 70)
  private String corporateName;

  @NaturalId
  @NotBlank
  @Size(max = 60)
  @Email
  private String email;

  @Column(length = 9)
  private String phone;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public Contact(@Size(max = 70) String corporateName, @NotBlank @Size(max = 60) @Email String email, String phone,
      User user) {
    this.corporateName = corporateName;
    this.email = email;
    this.phone = phone;
    this.user = user;
  }

}