package com.joindust.joindustbackend.payloads.responses;

import java.time.Instant;
import java.util.Set;

import com.joindust.joindustbackend.models.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
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
  private Set<Role> roles;

}