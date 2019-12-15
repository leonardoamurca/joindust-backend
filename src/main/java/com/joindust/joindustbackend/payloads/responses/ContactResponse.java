package com.joindust.joindustbackend.payloads.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactResponse {

  private Long id;
  private String corporateName;
  private String email;
  private String phone;
  private UserSummary recycler;

}