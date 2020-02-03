package com.joindust.joindustbackend.controllers;

import java.net.URI;

import com.joindust.joindustbackend.payloads.requests.ProfileImageRequest;
import io.swagger.annotations.Api;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.joindust.joindustbackend.exceptions.ResourceNotFoundException;
import com.joindust.joindustbackend.models.Contact;
import com.joindust.joindustbackend.models.User;
import com.joindust.joindustbackend.payloads.requests.ContactRequest;
import com.joindust.joindustbackend.payloads.responses.ApiResponse;
import com.joindust.joindustbackend.payloads.responses.CollectResponse;
import com.joindust.joindustbackend.payloads.responses.PagedResponse;
import com.joindust.joindustbackend.payloads.responses.UserIdentityAvailability;
import com.joindust.joindustbackend.payloads.responses.UserProfileReponse;
import com.joindust.joindustbackend.payloads.responses.UserSummary;
import com.joindust.joindustbackend.repositories.CollectRepository;
import com.joindust.joindustbackend.repositories.UserRepository;
import com.joindust.joindustbackend.security.CurrentUser;
import com.joindust.joindustbackend.security.UserPrincipal;
import com.joindust.joindustbackend.services.CollectService;
import com.joindust.joindustbackend.utils.AppConstants;

@RestController
@RequestMapping ("/api/users")
@Api (value = "User", description = "REST API for User", tags = {"User"})
public class UserController {

  private final UserRepository userRepository;

  private final CollectRepository collectRepository;

  private final CollectService collectService;


  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  public UserController(UserRepository userRepository, CollectRepository collectRepository, CollectService collectService) {
    this.userRepository = userRepository;
    this.collectRepository = collectRepository;
    this.collectService = collectService;
  }

  @GetMapping ("/me")
  public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {

    return new UserSummary(currentUser.getId(), currentUser.getName(), currentUser.getUsername(), currentUser.getEmail(), currentUser.getProfileImage(), currentUser.getPhone(), currentUser.getRoles());
  }

  @GetMapping ("/checkUsernameAvailability")
  public UserIdentityAvailability checkUsernameAvailability(@RequestParam (value = "username") String username) {
    Boolean isAvailable = !userRepository.existsByUsername(username);

    return new UserIdentityAvailability(isAvailable);
  }

  @GetMapping ("/checkEmailAvailability")
  public UserIdentityAvailability checkEmailAvailability(@RequestParam (value = "email") String email) {
    Boolean isAvailable = !userRepository.existsByEmail(email);

    return new UserIdentityAvailability(isAvailable);
  }

  @GetMapping ("/{username}")
  public UserProfileReponse getUserProfile(@PathVariable (value = "username") String username) {
    User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

    long collectionsCount = collectRepository.countByUserId(user.getId());

    return new UserProfileReponse(user.getId(), user.getCorporateName(), user.getUsername(), user.getEmail(), user.getCreatedAt(), user.getCnpj(), user.getPhone(), collectionsCount, user.getProfileImage(), user.getRoles());
  }

  @GetMapping ("/{username}/collections")
  public PagedResponse<CollectResponse> getCollectionsCreatedBy(@PathVariable (value = "username") String username, @CurrentUser UserPrincipal currentUser, @RequestParam (value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page, @RequestParam (value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

    return collectService.getCollectionsByUsername(username, currentUser, page, size);
  }

  @PatchMapping ("/image")
  public ApiResponse updateProfileImage(@RequestBody ProfileImageRequest request, @CurrentUser UserPrincipal currentUser) {
    userRepository.updateProfileImage(request.getProfileImage(), currentUser.getId());

    return new ApiResponse(true, "Imagem de perfil atualizada!");
  }


}