package com.joindust.joindustbackend.services;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import com.joindust.joindustbackend.exceptions.BadRequestException;
import com.joindust.joindustbackend.exceptions.ResourceNotFoundException;
import com.joindust.joindustbackend.models.Contact;
import com.joindust.joindustbackend.models.User;
import com.joindust.joindustbackend.payloads.requests.ContactRequest;
import com.joindust.joindustbackend.payloads.responses.ContactResponse;
import com.joindust.joindustbackend.payloads.responses.DeletedResponse;
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

  private final ContactRepository contactRepository;

  private final UserRepository userRepository;

  private static final Logger logger = LoggerFactory.getLogger(CollectService.class);

  public ContactService(ContactRepository contactRepository, UserRepository userRepository) {
    this.contactRepository = contactRepository;
    this.userRepository = userRepository;
  }

  public PagedResponse<ContactResponse> getAllContacts(UserPrincipal currentUser, int page, int size) {
    validatePageNumberAndSize(page, size);

    // Retrieve Polls
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
    Page<Contact> contacts = contactRepository.findAll(pageable);

    if (contacts.getNumberOfElements() == 0) {
      return new PagedResponse<>(Collections.emptyList(), contacts.getNumber(), contacts.getSize(), contacts.getTotalElements(), contacts.getTotalPages(), contacts.isLast());
    }

    List<ContactResponse> contactsResponses = contacts.map(contact -> {
      return ModelMapper.mapContactToContactReponse(contact, contact.getUser());
    }).getContent();

    return new PagedResponse<>(contactsResponses, contacts.getNumber(), contacts.getSize(), contacts.getTotalElements(), contacts.getTotalPages(), contacts.isLast());
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
      throw new BadRequestException("O número da página não pode ser menor que zero!");
    }

    if (size > AppConstants.MAX_PAGE_SIZE) {
      throw new BadRequestException("O tamanho da página não pode ser maior que " + AppConstants.MAX_PAGE_SIZE);
    }
  }

  public DeletedResponse deleteContactById(Long contactId, UserPrincipal currentUser) {

    Contact contact = contactRepository.findById(contactId).orElseThrow(() -> new ResourceNotFoundException("Poll", "id", contactId));

    contactRepository.deleteById(contact.getId());

    return new DeletedResponse(contact.getId(), "Contato removido com sucesso!");
  }
}