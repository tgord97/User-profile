package com.iprody.userprofile.userprofileservice.controller;

import com.iprody.userprofile.userprofileservice.AbstractIntegrationTest;
import com.iprody.userprofile.userprofileservice.dto.UserContactDto;
import com.iprody.userprofile.userprofileservice.dto.UserDto;
import com.iprody.userprofile.userprofileservice.entity.User;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

/**
 * This class contains integration tests for UserContactController endpoints.
 */
class UserContactControllerTest extends AbstractIntegrationTest {

    private static final String TEST_TELEGRAM_ID = "@testusername";
    private static final String TEST_INVALID_TELEGRAM_ID = "@test.username";
    private static final String TEST_MOBILE_PHONE = "+79531234567";
    private static final String TEST_FIRST_NAME = "testFirstName";
    private static final String TEST_LAST_NAME = "testLastName";
    private static final String TEST_EMAIL = "test@mail.ru";
    private static final String ID_ENDPOINT = "?id={id}";
    private static final String USER_ID_ENDPOINT = "?userId={userId}";
    private static final String BASE_URL = "http://localhost:";

    private static final String ADD_USER_ENDPOINT = "/api/user/add";
    private static final String ADD_USER_CONTACT_ENDPOINT = "/api/user-contact/add";
    private static final String FIND_USER_CONTACT_ENDPOINT = "/api/user-contact";
    private static final String FIND_USER_CONTACT_BY_USER_ID_ENDPOINT = "/api/user-contact/user";
    private static final String TELEGRAM_ID = "$.telegramId";
    private static final String MOBILE_PHONE = "$.mobilePhone";

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
    private UserContactDto getValidUserContactDto() {
        return UserContactDto.builder().mobilePhone(TEST_MOBILE_PHONE).telegramId(TEST_TELEGRAM_ID)
                .user(getValidUser()).build();
    }

    /**
     * Retrieves a valid User for testing.
     *
     * @return A valid User instance.
     */
    private User getValidUser() {
        return User.builder().email(TEST_EMAIL).firstName(TEST_FIRST_NAME).lastName(TEST_LAST_NAME).id(1L).build();
    }

    /**
     * Retrieves a valid UserDto for testing.
     *
     * @return a invalid UserDto instance.
     */
    private UserContactDto getInvalidUserContactDto() {
        return UserContactDto.builder().mobilePhone(TEST_MOBILE_PHONE).telegramId(TEST_INVALID_TELEGRAM_ID).build();
    }

    /**
     * Retrieves a valid UserDto for testing.
     *
     * @return A valid UserDto instance.
     */
    private UserDto getValidUserDto() {
        return UserDto.builder().email(TEST_EMAIL).firstName(TEST_FIRST_NAME).lastName(TEST_LAST_NAME).build();
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
                .expectStatus().isCreated();
    }

    /**
     * Tests the scenario where a valid user contact is provided, expecting a successful creation response.
     */
    @Test
    @Order(2)
    public void givenValidUserContact_ThenReturnIsCreated() {
        webTestClient
                .post()
                .uri(BASE_URL + port + ADD_USER_CONTACT_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getValidUserContactDto()))
                .exchange()
                .expectStatus().isCreated()
                .expectBody().jsonPath(TELEGRAM_ID).isEqualTo(getValidUserContactDto().getTelegramId())
                .jsonPath(MOBILE_PHONE).isEqualTo(getValidUserContactDto().getMobilePhone());
    }

    /**
     * Test case for adding an invalid user contact.
     */
    @Test
    @Order(3)
    public void givenInvalidUser_ThenReturnIsBadRequest() {
        webTestClient
                .post()
                .uri(BASE_URL + port + ADD_USER_CONTACT_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getInvalidUserContactDto()))
                .exchange()
                .expectStatus().isBadRequest();
    }

    /**
     * Tests the scenario where a valid user ID is provided, expecting a successful response.
     */
    @Test
    @Order(4)
    public void givenValidUserContactId_ThenReturnIsOk() {
        webTestClient
                .get()
                .uri(BASE_URL + port + FIND_USER_CONTACT_ENDPOINT + ID_ENDPOINT, 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath(TELEGRAM_ID).isEqualTo(getValidUserContactDto().getTelegramId())
                .jsonPath(MOBILE_PHONE).isEqualTo(getValidUserContactDto().getMobilePhone());
    }

    /**
     * Tests the scenario where an invalid user ID is provided, expecting a not found response.
     */
    @Test
    @Order(5)
    public void givenInvalidContactId_ThenReturnNotFound() {
        webTestClient
                .get()
                .uri(BASE_URL + port + FIND_USER_CONTACT_ENDPOINT + ID_ENDPOINT, 2)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    /**
     * Test case for finding a user contact by user ID.
     */
    @Test
    @Order(6)
    public void givenValidUserid_ThenReturnIsOk() {
        webTestClient
                .get()
                .uri(BASE_URL + port + FIND_USER_CONTACT_BY_USER_ID_ENDPOINT + USER_ID_ENDPOINT, 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath(TELEGRAM_ID).isEqualTo(getValidUserContactDto().getTelegramId())
                .jsonPath(MOBILE_PHONE).isEqualTo(getValidUserContactDto().getMobilePhone());
    }

}
