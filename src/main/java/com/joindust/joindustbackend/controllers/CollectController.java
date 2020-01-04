package com.joindust.joindustbackend.controllers;

import java.net.URI;
import javax.validation.Valid;

import io.swagger.annotations.Api;
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

import com.joindust.joindustbackend.models.Collect;
import com.joindust.joindustbackend.payloads.requests.CollectRequest;
import com.joindust.joindustbackend.payloads.responses.ApiResponse;
import com.joindust.joindustbackend.payloads.responses.CollectResponse;
import com.joindust.joindustbackend.payloads.responses.DeletedResponse;
import com.joindust.joindustbackend.payloads.responses.PagedResponse;
import com.joindust.joindustbackend.security.CurrentUser;
import com.joindust.joindustbackend.security.UserPrincipal;
import com.joindust.joindustbackend.services.CollectService;
import com.joindust.joindustbackend.utils.AppConstants;

@RestController
@RequestMapping ("/api/collections")
@Api (value = "Collect", description = "REST API for Collect", tags = {"Collect"})
public class CollectController {

  private final CollectService collectService;

  private static final Logger logger = LoggerFactory.getLogger(CollectController.class);

  public CollectController(CollectService collectService) {
    this.collectService = collectService;
  }

  @GetMapping
  public PagedResponse<CollectResponse> getCollections(@CurrentUser UserPrincipal currentUser, @RequestParam (value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page, @RequestParam (value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    return collectService.getAllCollections(currentUser, page, size);
  }

  @PostMapping
  @PreAuthorize ("hasRole('ROLE_PRODUCER')")
  public ResponseEntity<?> createCollect(@Valid @RequestBody CollectRequest collectRequest) {
    Collect collect = collectService.createCollect(collectRequest);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{collectId}").buildAndExpand(collect.getId()).toUri();

    return ResponseEntity.created(location).body(new ApiResponse(true, "Collect Created Successfully"));
  }

  @GetMapping ("/{collectId}")
  public CollectResponse getCollectById(@CurrentUser UserPrincipal currentUser, @PathVariable Long collectId) {
    return collectService.getCollectById(collectId, currentUser);
  }

  @DeleteMapping ("/{collectId}")
  @PreAuthorize ("hasRole('ROLE_PRODUCER')")
  public DeletedResponse deleteCollectById(@CurrentUser UserPrincipal currentUser, @PathVariable Long collectId) {
    return collectService.deleteCollectById(collectId, currentUser);
  }
}