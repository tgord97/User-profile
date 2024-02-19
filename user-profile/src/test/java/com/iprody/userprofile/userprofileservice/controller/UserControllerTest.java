package com.iprody.userprofile.userprofileservice.controller;

import com.iprody.userprofile.userprofileservice.AbstractIntegrationTest;
import com.iprody.userprofile.userprofileservice.dto.UserDto;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

/**
 * This class contains integration tests for UserController endpoints.
 */
public class UserControllerTest extends AbstractIntegrationTest {

    private static final String TEST_FIRST_NAME = "testFirstName";
    private static final String TEST_LAST_NAME = "testLastName";
    private static final String TEST_EMAIL = "test@mail.ru";
    private static final String ID_ENDPOINT = "?id={id}";
    private static final String ADD_USER_ENDPOINT = "/api/user/add";
    private static final String FIND_USER_ENDPOINT = "/api/user";
    private static final String UPDATE_USER_ENDPOINT = "/api/user/update";
    private static final String EMAIL = "$.email";
    private static final String FIRST_NAME = "$.firstName";
    private static final String LAST_NAME = "$.lastName";
    private static final String BASE_URL = "http://localhost:";

    /**
     * Local server port provided by Spring Boot for testing.
     */
    @LocalServerPort
    private int port;

    /**
     * WebTestClient instance provided by Spring Boot for testing web applications.
     */
    @Autowired
    private WebTestClient webTestClient;

    /**
     * Retrieves a valid UserDto for testing.
     *
     * @return A valid UserDto instance.
     */
    private UserDto getValidUserDto() {
       return UserDto.builder().email(TEST_EMAIL).firstName(TEST_FIRST_NAME).lastName(TEST_LAST_NAME).build();
    }

    /**
     * Retrieves an updated UserDto for testing.
     *
     * @return An updated UserDto instance.
     */
    private UserDto getUpdatedUserDto() {
        return UserDto.builder().email("updatedTest@mail.ru").firstName(TEST_FIRST_NAME)
                .lastName(TEST_LAST_NAME).build();
    }

    /**
     * Tests the scenario where a valid user is provided, expecting a successful creation response.
     */
    @Test
    @Order(1)
    public void givenValidUser_ThenReturnIsCreated() {
        webTestClient
                .post()
                .uri(BASE_URL + port + ADD_USER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getValidUserDto()))
                .exchange()
                .expectStatus().isCreated()
                .expectBody().jsonPath(FIRST_NAME).isEqualTo(getValidUserDto().getFirstName())
                .jsonPath(LAST_NAME).isEqualTo(getValidUserDto().getLastName())
                .jsonPath(EMAIL).isEqualTo(getValidUserDto().getEmail());
    }

    /**
     * Tests the scenario where an invalid user is provided, expecting a bad request response.
     *
     */
    @Test
    @Order(2)
    public void givenInvalidUser_ThenReturnBadRequest() {
        webTestClient
                .post()
                .uri(BASE_URL + port + ADD_USER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }

    /**
     * Tests the scenario where a valid user ID is provided, expecting a successful response.
     */
    @Test
    @Order(3)
    public void givenValidUserid_ThenReturnIsOk() {
        webTestClient
                .get()
                .uri(BASE_URL + port + FIND_USER_ENDPOINT + ID_ENDPOINT, 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath(FIRST_NAME).isEqualTo(getValidUserDto().getFirstName())
                .jsonPath(LAST_NAME).isEqualTo(getValidUserDto().getLastName())
                .jsonPath(EMAIL).isEqualTo(getValidUserDto().getEmail());
    }

    /**
     * Tests the scenario where an invalid user ID is provided, expecting a not found response.
     */
    @Test
    @Order(4)
    public void givenInvalidUserId_ThenReturnNotFound() {
        webTestClient
                .get()
                .uri(BASE_URL + port + FIND_USER_ENDPOINT + ID_ENDPOINT, 2)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    /**
     * Tests the scenario where a user is updated, expecting a successful response.
     */
    @Test
    @Order(5)
    public void shouldUpdateUser_ThenReturnIsOk() {
        webTestClient
                .post()
                .uri(BASE_URL + port + UPDATE_USER_ENDPOINT + ID_ENDPOINT, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getUpdatedUserDto()))
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath(FIRST_NAME).isEqualTo(getUpdatedUserDto().getFirstName())
                .jsonPath(LAST_NAME).isEqualTo(getUpdatedUserDto().getLastName())
                .jsonPath(EMAIL).isEqualTo(getUpdatedUserDto().getEmail());
    }

    /**
     * Tests the scenario where a user update request is made without providing user data.
     */
    @Test
    @Order(6)
    public void shouldUpdateUser_ThenReturnIsBadRequest() {
        webTestClient
                .post()
                .uri(BASE_URL + port + UPDATE_USER_ENDPOINT + ID_ENDPOINT, 1)
                .exchange()
                .expectStatus().isBadRequest();
    }

}
