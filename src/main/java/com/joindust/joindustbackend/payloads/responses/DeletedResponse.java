package com.joindust.joindustbackend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeletedResponse {

  private Long collectId;
  private String message;

}