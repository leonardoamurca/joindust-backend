package com.joindust.joindustbackend.controllers;

import com.joindust.joindustbackend.payloads.responses.UserSummary;
import com.joindust.joindustbackend.repositories.CollectRepository;
import com.joindust.joindustbackend.repositories.UserRepository;
import com.joindust.joindustbackend.security.CurrentUser;
import com.joindust.joindustbackend.security.UserPrincipal;
import com.joindust.joindustbackend.services.CollectService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CollectRepository collectRepository;

  @Autowired
  private CollectService collectService;

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @GetMapping("/me")
  public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
    UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName();
    return userSummary;
  }
}