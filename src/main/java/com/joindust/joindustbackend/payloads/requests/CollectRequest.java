package com.joindust.joindustbackend.payloads.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CollectRequest {

  private Double price;
  private Double quantity;
  private Long userId;

}