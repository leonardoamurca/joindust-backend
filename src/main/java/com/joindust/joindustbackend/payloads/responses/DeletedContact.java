package com.joindust.joindustbackend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeletedContact {

  private Long contactId;
  private String message;

}
