package com.joindust.joindustbackend.controllers;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import com.joindust.joindustbackend.exceptions.AppException;
import com.joindust.joindustbackend.models.Role;
import com.joindust.joindustbackend.models.User;
import com.joindust.joindustbackend.payloads.requests.LoginRequest;
import com.joindust.joindustbackend.payloads.requests.SignUpRequest;
import com.joindust.joindustbackend.payloads.responses.ApiResponse;
import com.joindust.joindustbackend.payloads.responses.JwtAuthenticationResponse;
import com.joindust.joindustbackend.repositories.RoleRepository;
import com.joindust.joindustbackend.repositories.UserRepository;
import com.joindust.joindustbackend.security.JwtTokenProvider;
import com.joindust.joindustbackend.utils.RoleName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;

@RestController
@Api(value = "Authentication", description = "REST API for Authentication", tags = { "Authentication" })
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JwtTokenProvider tokenProvider;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = tokenProvider.generateToken(authentication);

    return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
    }

    User user = new User(signUpRequest.getCorporateName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
        signUpRequest.getPassword(), signUpRequest.getCnpj(), signUpRequest.getPhone());

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    // TODO: Refactor this
    RoleName role = null;
    if (signUpRequest.getRoleId() == 1) {
      role = RoleName.ROLE_PRODUCER;
    } else if (signUpRequest.getRoleId() == 2) {
      role = RoleName.ROLE_RECYCLER;
    }

    Role userRole = roleRepository.findByName(role).orElseThrow(() -> new AppException("User Role not set."));

    user.setRoles(Collections.singleton(userRole));

    User result = userRepository.save(user);

    URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{username}")
        .buildAndExpand(result.getUsername()).toUri();

    return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
  }

}