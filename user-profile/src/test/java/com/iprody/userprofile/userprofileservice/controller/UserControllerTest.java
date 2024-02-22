package com.iprody.userprofile.userprofileservice.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

    private static final String ADD_USER_ENDPOINT = "/api/user/add";
    private static final String FIND_USER_ENDPOINT = "/api/user";
    private static final String EMAIL = "$.email";
    private static final String BASE_URL = "http://localhost:";
    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:latest");

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Method executed before running tests.
     */
    @BeforeAll
    public static void beforeAll() {
        POSTGRES.start();
        System.setProperty("spring.datasource.url", POSTGRES.getJdbcUrl());
        System.setProperty("spring.datasource.username", POSTGRES.getUsername());
        System.setProperty("spring.datasource.password", POSTGRES.getPassword());
    }

    /**
     * Method executed after running tests.
     */
    @AfterAll
    public static void afterAll() {
        POSTGRES.stop();
    }


    /**
     * Test case for adding a user.
     *
     * @throws Exception if any error occurs during the test execution.
     */
    @Test
    @Order(1)
    public void testAddUser() throws Exception {
        var userJson = "{\"firstName\":\"Ivan\",\"lastName\":\"Ivanov\",\"email\":\"ivanovmail.ru\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + port + ADD_USER_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    /**
     * Test case for finding a user by id.
     *
     * @throws Exception if any error occurs during the test execution.
     */
    @Test
    @Order(2)
    public void testFindUserById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + port + FIND_USER_ENDPOINT + "?id=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath(EMAIL).value("ivanovmail.ru"));
    }

    /**
     * Test case for updating a user.
     *
     * @throws Exception if any error occurs during the test execution.
     */
    @Test
    @Order(3)
    public void testUpdateUser() throws Exception {

        var updateUserJson = "{\"firstName\":\"Ivan\",\"lastName\":\"Ivanov\",\"email\":\"ivanov@mail.ru\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + port + "/api/user/update?id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserJson))
                .andExpect(jsonPath(EMAIL).value("ivanov@mail.ru"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }



    /**
     * Test case for finding a user by id but user doesn't exist.
     *
     * @throws Exception if any error occurs during the test execution.
     */
    @Test
    @Order(4)
    public void testFindUserByIdWhenUserNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + port + FIND_USER_ENDPOINT + "?id=2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
