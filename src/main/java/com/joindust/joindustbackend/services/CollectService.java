package com.joindust.joindustbackend.services;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.joindust.joindustbackend.exceptions.BadRequestException;
import com.joindust.joindustbackend.exceptions.ResourceNotFoundException;
import com.joindust.joindustbackend.models.Collect;
import com.joindust.joindustbackend.models.User;
import com.joindust.joindustbackend.payloads.requests.CollectRequest;
import com.joindust.joindustbackend.payloads.responses.CollectResponse;
import com.joindust.joindustbackend.payloads.responses.PagedResponse;
import com.joindust.joindustbackend.repositories.CollectRepository;
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
public class CollectService {

  @Autowired
  private CollectRepository collectRepository;

  @Autowired
  private UserRepository userRepository;

  private static final Logger logger = LoggerFactory.getLogger(CollectService.class);

  public PagedResponse<CollectResponse> getAllCollections(UserPrincipal currentUser, int page, int size) {
    validatePageNumberAndSize(page, size);

    // Retrieve Polls
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
    Page<Collect> collections = collectRepository.findAll(pageable);

    if (collections.getNumberOfElements() == 0) {
      return new PagedResponse<>(Collections.emptyList(), collections.getNumber(), collections.getSize(),
          collections.getTotalElements(), collections.getTotalPages(), collections.isLast());
    }

    List<CollectResponse> collectionsResponses = collections.map(collect -> {
      return ModelMapper.mapCollectToCollectReponse(collect, collect.getUser());
    }).getContent();

    return new PagedResponse<>(collectionsResponses, collections.getNumber(), collections.getSize(),
        collections.getTotalElements(), collections.getTotalPages(), collections.isLast());
  }

  public PagedResponse<CollectResponse> getCollectionsByUsername(String username, UserPrincipal currentUser, int page,
      int size) {
    validatePageNumberAndSize(page, size);

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

    // Retrieve all polls created by the given username
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
    Page<Collect> collections = collectRepository.findByUserId(user.getId(), pageable);

    if (collections.getNumberOfElements() == 0) {
      return new PagedResponse<>(Collections.emptyList(), collections.getNumber(), collections.getSize(),
          collections.getTotalElements(), collections.getTotalPages(), collections.isLast());
    }

    List<CollectResponse> collectResponses = collections.map(collect -> {
      return ModelMapper.mapCollectToCollectReponse(collect, collect.getUser());
    }).getContent();

    return new PagedResponse<>(collectResponses, collections.getNumber(), collections.getSize(),
        collections.getTotalElements(), collections.getTotalPages(), collections.isLast());
  }

  private void validatePageNumberAndSize(int page, int size) {
    if (page < 0) {
      throw new BadRequestException("Page number cannot be less than zero.");
    }

    if (size > AppConstants.MAX_PAGE_SIZE) {
      throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
    }
  }

  public Collect createCollect(CollectRequest collectRequest) {
    Collect collect = new Collect();
    Instant now = Instant.now();
    User user = userRepository.getOne(collectRequest.getUserId());

    collect.setPrice(collectRequest.getPrice());
    collect.setQuantity(collectRequest.getQuantity());
    collect.setCreatedAt(now);
    collect.setUser(user);

    return collectRepository.save(collect);
  }

  public CollectResponse getCollectById(Long collectId, UserPrincipal currentUser) {
    Collect collect = collectRepository.findById(collectId)
        .orElseThrow(() -> new ResourceNotFoundException("Poll", "id", collectId));

    // Retrieve poll creator details
    User creator = userRepository.findById(collect.getUser().getId())
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", collect.getUser().getId()));

    return ModelMapper.mapCollectToCollectReponse(collect, creator);
  }
}