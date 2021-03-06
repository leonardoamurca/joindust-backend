package com.joindust.joindustbackend;

import com.joindust.joindustbackend.controllers.AuthController;

import static org.assertj.core.api.Assertions.assertThat;

import com.joindust.joindustbackend.payloads.requests.LoginRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith (SpringRunner.class)
@SpringBootTest
public class AuthControllerTests {

  @Autowired
  private AuthController controller;

  @Test
  public void contexLoads() throws Exception {
    assertThat(controller).isNotNull();
  }

  @Test
  public void authenticateUser() {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsernameOrEmail("leo");
    loginRequest.setPassword("leo123");
    controller.authenticateUser(loginRequest);
  }
}