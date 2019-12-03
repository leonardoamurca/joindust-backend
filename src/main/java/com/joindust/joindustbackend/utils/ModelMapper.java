package com.joindust.joindustbackend.utils;

import com.joindust.joindustbackend.models.Collect;
import com.joindust.joindustbackend.models.User;
import com.joindust.joindustbackend.payloads.responses.CollectResponse;
import com.joindust.joindustbackend.payloads.responses.UserSummary;

public class ModelMapper {
  public static CollectResponse mapCollectToCollectReponse(Collect collect, User creator) {
    CollectResponse collectResponse = new CollectResponse();
    UserSummary userSummary = new UserSummary(creator.getId(), creator.getCorporateName(), creator.getPhone(),
        creator.getEmail());

    collectResponse.setId(collect.getId());
    collectResponse.setQuantity(collect.getQuantity());
    collectResponse.setPrice(collect.getPrice());
    collectResponse.setUser(userSummary);

    return collectResponse;

  }
}