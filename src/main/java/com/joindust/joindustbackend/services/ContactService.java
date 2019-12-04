package com.joindust.joindustbackend.services;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import com.joindust.joindustbackend.exceptions.BadRequestException;

import com.joindust.joindustbackend.models.Contact;
import com.joindust.joindustbackend.models.User;
import com.joindust.joindustbackend.payloads.requests.ContactRequest;
import com.joindust.joindustbackend.payloads.responses.ContactResponse;
import com.joindust.joindustbackend.payloads.responses.PagedResponse;
import com.joindust.joindustbackend.repositories.ContactRepository;
import com.joindust.joindustbackend.repositories.UserRepository;
import com.joindust.joindustbackend.security.UserPrincipal;
import com.joindust.joindustbackend.utils.AppConstants;
import com.joindust.joindustbackend.utils.ModelMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

  @Autowired
  private ContactRepository contactRepository;

  @Autowired
  private UserRepository userRepository;

  private static final Logger logger = LoggerFactory.getLogger(CollectService.class);

  public PagedResponse<ContactResponse> getAllContacts(UserPrincipal currentUser, int page, int size) {
    validatePageNumberAndSize(page, size);

    // Retrieve Polls
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
    Page<Contact> contacts = contactRepository.findAll(pageable);

    if (contacts.getNumberOfElements() == 0) {
      return new PagedResponse<>(Collections.emptyList(), contacts.getNumber(), contacts.getSize(),
          contacts.getTotalElements(), contacts.getTotalPages(), contacts.isLast());
    }

    List<ContactResponse> contactsResponses = contacts.map(contact -> {
      return ModelMapper.mapContactToContactReponse(contact, contact.getUser());
    }).getContent();

    return new PagedResponse<>(contactsResponses, contacts.getNumber(), contacts.getSize(), contacts.getTotalElements(),
        contacts.getTotalPages(), contacts.isLast());
  }

  public Contact createContact(UserPrincipal currentUser, ContactRequest contactRequest) {
    Contact contact = new Contact();
    Instant now = Instant.now();
    User user = userRepository.getOne(contactRequest.getUserId());
    User recycler = userRepository.getOne(currentUser.getId());

    contact.setCorporateName(user.getCorporateName());
    contact.setCreatedAt(now);
    contact.setEmail(user.getEmail());
    contact.setPhone(user.getPhone());
    contact.setUser(recycler);

    return contactRepository.save(contact);
  }

  private void validatePageNumberAndSize(int page, int size) {
    if (page < 0) {
      throw new BadRequestException("Page number cannot be less than zero.");
    }

    if (size > AppConstants.MAX_PAGE_SIZE) {
      throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
    }
  }

}