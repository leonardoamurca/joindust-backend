package com.joindust.joindustbackend.controllers;

import com.joindust.joindustbackend.models.Contact;
import com.joindust.joindustbackend.payloads.requests.ContactRequest;
import com.joindust.joindustbackend.payloads.responses.ApiResponse;
import com.joindust.joindustbackend.payloads.responses.ContactResponse;
import com.joindust.joindustbackend.payloads.responses.DeletedResponse;
import com.joindust.joindustbackend.payloads.responses.PagedResponse;
import com.joindust.joindustbackend.security.CurrentUser;
import com.joindust.joindustbackend.security.UserPrincipal;
import com.joindust.joindustbackend.services.ContactService;
import com.joindust.joindustbackend.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping ("/api/contacts")
public class ContactController {

  @Autowired
  private ContactService contactService;

  @PreAuthorize ("hasRole('ROLE_RECYCLER')")
  @GetMapping
  public PagedResponse<ContactResponse> getContacts(String username, @CurrentUser UserPrincipal currentUser, @RequestParam (value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page, @RequestParam (value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

    return contactService.getAllContacts(currentUser, page, size);
  }

  @PostMapping
  @PreAuthorize ("hasRole('ROLE_RECYCLER')")
  public ResponseEntity<?> createContact(@Valid @RequestBody ContactRequest contactRequest, @CurrentUser UserPrincipal currentUser) {
    Contact contact = contactService.createContact(currentUser, contactRequest);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{contactId}").buildAndExpand(contact.getId()).toUri();

    return ResponseEntity.created(location).body(new ApiResponse(true, "Contact Created Successfully"));
  }

  @DeleteMapping ("/{contactId}")
  @PreAuthorize ("hasRole('ROLE_RECYCLER')")
  public DeletedResponse deleteCollectById(@CurrentUser UserPrincipal currentUser, @PathVariable Long contactId) {
    return contactService.deleteContactById(contactId, currentUser);
  }
}
