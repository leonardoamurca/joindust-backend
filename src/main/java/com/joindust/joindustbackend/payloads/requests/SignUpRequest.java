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
  @Size (min = 4, max = 40, message = "O nome da empresa deve conter no mínimo {min} e no máximo {max} caracteres")
  private String corporateName;

  @NotBlank (message = "O nome de usuário não pode ser deixado em branco.")
  @Size (min = 3, max = 15, message = "O nome de usuário deve conter no mínimo {min} e no máximo {max} caracteres.")
  private String username;

  @NotBlank (message = "O e-mail não pode ser deixado em branco.")
  @Size (max = 40, message = "Seu e-mail deve conter no máximo {max} caracteres.")
  @Email (message = "O e-mail inserido não é válido.")
  private String email;

  @NotBlank (message = "Sua senha não pode ser deixada em branco.")
  @Size (min = 6, max = 20, message = "Sua senha deve ter no mínimo {min} e no máximo {max} dígitos.")
  private String password;

  @NotNull (message = "O cnpj não pode ser deixado em branco.")
  @Size (max = 14, min = 14, message = "O cnpj deve ter exatamente {max} dígitos.")
  private String cnpj;

  @Size (min = 9, max = 9, message = "O telefone deve ter exatamente {max} dígitos.")
  private String phone;

  private Integer roleId;

}