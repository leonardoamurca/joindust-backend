package com.joindust.joindustbackend.controllers;

import java.net.URI;
import io.swagger.annotations.Api;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.joindust.joindustbackend.exceptions.ResourceNotFoundException;
import com.joindust.joindustbackend.models.Contact;
import com.joindust.joindustbackend.models.User;
import com.joindust.joindustbackend.payloads.requests.ContactRequest;
import com.joindust.joindustbackend.payloads.responses.ApiResponse;
import com.joindust.joindustbackend.payloads.responses.CollectResponse;
import com.joindust.joindustbackend.payloads.responses.ContactResponse;
import com.joindust.joindustbackend.payloads.responses.DeletedResponse;
import com.joindust.joindustbackend.payloads.responses.PagedResponse;
import com.joindust.joindustbackend.payloads.responses.UserIdentityAvailability;
import com.joindust.joindustbackend.payloads.responses.UserProfileReponse;
import com.joindust.joindustbackend.payloads.responses.UserSummary;
import com.joindust.joindustbackend.repositories.CollectRepository;
import com.joindust.joindustbackend.repositories.UserRepository;
import com.joindust.joindustbackend.security.CurrentUser;
import com.joindust.joindustbackend.security.UserPrincipal;
import com.joindust.joindustbackend.services.CollectService;
import com.joindust.joindustbackend.services.ContactService;
import com.joindust.joindustbackend.utils.AppConstants;

@RestController
@RequestMapping("/api/users")
@Api(value = "User", description = "REST API for User", tags = { "User" })
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CollectRepository collectRepository;

  @Autowired
  private CollectService collectService;

  @Autowired
  private ContactService contactService;

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @GetMapping("/me")
  public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
    UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getName(), currentUser.getUsername(),
        currentUser.getEmail());

    return userSummary;
  }

  @GetMapping("/checkUsernameAvailability")
  public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
    Boolean isAvailable = !userRepository.existsByUsername(username);

    return new UserIdentityAvailability(isAvailable);
  }

  @GetMapping("/checkEmailAvailability")
  public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
    Boolean isAvailable = !userRepository.existsByEmail(email);

    return new UserIdentityAvailability(isAvailable);
  }

  @GetMapping("/{username}")
  public UserProfileReponse getUserProfile(@PathVariable(value = "username") String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

    long collectionsCount = collectRepository.countByUserId(user.getId());

    UserProfileReponse userProfile = new UserProfileReponse(user.getId(), user.getCorporateName(), user.getUsername(),
        user.getEmail(), user.getCreatedAt(), user.getCnpj(), user.getPhone(), collectionsCount, user.getProfileImage(),
        user.getRoles());

    return userProfile;
  }

  @GetMapping("/{username}/collections")
  public PagedResponse<CollectResponse> getCollectionsCreatedBy(@PathVariable(value = "username") String username,
      @CurrentUser UserPrincipal currentUser,
      @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
      @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

    return collectService.getCollectionsByUsername(username, currentUser, page, size);
  }

  @PreAuthorize("hasRole('ROLE_RECYCLER')")
  @GetMapping("/{username}/contacts")
  public PagedResponse<ContactResponse> getContactsByUsername(@PathVariable(value = "username") String username,
      @CurrentUser UserPrincipal currentUser,
      @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
      @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

    return contactService.getAllContacts(currentUser, page, size);
  }

  @PostMapping("/{username}/contacts")
  @PreAuthorize("hasRole('ROLE_RECYCLER')")
  public ResponseEntity<?> createContact(@Valid @RequestBody ContactRequest contactRequest,
      @CurrentUser UserPrincipal currentUser, @PathVariable(value = "username") String username) {
    Contact contact = contactService.createContact(currentUser, contactRequest);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{contactId}").buildAndExpand(contact.getId())
        .toUri();

    return ResponseEntity.created(location).body(new ApiResponse(true, "Contact Created Successfully"));
  }

  @DeleteMapping("/{contactId}")
  @PreAuthorize("hasRole('ROLE_RECYCLER')")
  public DeletedResponse deleteCollectById(@CurrentUser UserPrincipal currentUser, @PathVariable Long contactId) {
    return collectService.deleteCollectById(contactId, currentUser);
  }

}