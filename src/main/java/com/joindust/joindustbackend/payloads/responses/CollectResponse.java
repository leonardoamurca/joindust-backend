package com.joindust.joindustbackend.payloads.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CollectResponse {

  private Long id;
  private Double quantity;
  private Double price;
  private UserSummary user;

}