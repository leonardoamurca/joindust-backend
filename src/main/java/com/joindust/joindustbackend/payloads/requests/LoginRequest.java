package com.joindust.joindustbackend.payloads.requests;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
  @NotBlank (message = "Nome de usuário ou e-mail não pode ser deixado em branco.")
  private String usernameOrEmail;

  @NotBlank (message = "Senha não pode ser deixada em branco.")
  private String password;

}