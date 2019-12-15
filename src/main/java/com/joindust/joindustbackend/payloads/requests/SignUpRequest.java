package com.joindust.joindustbackend.payloads.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
  @NotBlank
  @Size(min = 4, max = 40)
  private String corporateName;

  @NotBlank
  @Size(min = 3, max = 15)
  private String username;

  @NotBlank
  @Size(max = 40)
  @Email
  private String email;

  @NotBlank
  @Size(min = 6, max = 20)
  private String password;

  @NotNull
  @Size(max = 14, min = 14)
  private String cnpj;

  @Size(min = 9, max = 9)
  private String phone;

  private Integer roleId;

}