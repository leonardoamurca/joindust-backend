package com.joindust.joindustbackend.payloads.responses;

import com.joindust.joindustbackend.models.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class UserSummary {

  private Long id;
  private String corporateName;
  private String username;
  private String email;
  private Set<Role> roles = new HashSet<>();

}
