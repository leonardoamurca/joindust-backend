package com.joindust.joindustbackend.controllers;

import java.net.URI;

import javax.validation.Valid;

import com.joindust.joindustbackend.models.Collect;
import com.joindust.joindustbackend.payloads.requests.CollectRequest;
import com.joindust.joindustbackend.payloads.responses.ApiResponse;
import com.joindust.joindustbackend.payloads.responses.CollectResponse;
import com.joindust.joindustbackend.payloads.responses.PagedResponse;
import com.joindust.joindustbackend.repositories.CollectRepository;
import com.joindust.joindustbackend.repositories.UserRepository;
import com.joindust.joindustbackend.security.CurrentUser;
import com.joindust.joindustbackend.security.UserPrincipal;
import com.joindust.joindustbackend.services.CollectService;
import com.joindust.joindustbackend.utils.AppConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
///import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/collections")
public class CollectController {

  @Autowired
  private CollectRepository collectRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CollectService collectService;

  private static final Logger logger = LoggerFactory.getLogger(CollectController.class);

  @GetMapping
  public PagedResponse<CollectResponse> getCollections(@CurrentUser UserPrincipal currentUser,
      @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
      @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    return collectService.getAllCollections(currentUser, page, size);
  }

  @PostMapping
  @PreAuthorize("hasRole('PRODUCER')")
  public ResponseEntity<?> createCollect(@Valid @RequestBody CollectRequest collectRequest) {
    Collect collect = collectService.createCollect(collectRequest);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{collectId}").buildAndExpand(collect.getId())
        .toUri();

    return ResponseEntity.created(location).body(new ApiResponse(true, "Collect Created Successfully"));
  }

  @GetMapping("/{collectId}")
  public CollectResponse getPollById(@CurrentUser UserPrincipal currentUser, @PathVariable Long collectId) {
    return collectService.getCollectById(collectId, currentUser);
  }

}