package com.joindust.joindustbackend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserSummary {

  private Long id;
  private String corporateName;
  private String username;
  private String email;

}
