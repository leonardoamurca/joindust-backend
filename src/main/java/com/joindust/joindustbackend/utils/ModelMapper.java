package com.joindust.joindustbackend.utils;

import com.joindust.joindustbackend.models.Collect;
import com.joindust.joindustbackend.models.Contact;
import com.joindust.joindustbackend.models.User;
import com.joindust.joindustbackend.payloads.responses.CollectResponse;
import com.joindust.joindustbackend.payloads.responses.ContactResponse;
import com.joindust.joindustbackend.payloads.responses.UserSummary;

public class ModelMapper {
  public static CollectResponse mapCollectToCollectReponse(Collect collect, User creator) {
    CollectResponse collectResponse = new CollectResponse();
    UserSummary userSummary = new UserSummary(creator.getId(), creator.getCorporateName(), creator.getPhone(), creator.getEmail(), creator.getRoles());

    collectResponse.setId(collect.getId());
    collectResponse.setQuantity(collect.getQuantity());
    collectResponse.setPrice(collect.getPrice());
    collectResponse.setUser(userSummary);

    return collectResponse;

  }

  public static ContactResponse mapContactToContactReponse(Contact contact, User recycler) {
    ContactResponse contactResponse = new ContactResponse();
    UserSummary userSummary = new UserSummary(recycler.getId(), recycler.getCorporateName(), recycler.getPhone(), recycler.getEmail(), recycler.getRoles());

    contactResponse.setId(contact.getId());
    contactResponse.setEmail(contact.getEmail());
    contactResponse.setCorporateName(contact.getCorporateName());
    contactResponse.setPhone(contact.getPhone());
    contactResponse.setRecycler(userSummary);

    return contactResponse;

  }
}