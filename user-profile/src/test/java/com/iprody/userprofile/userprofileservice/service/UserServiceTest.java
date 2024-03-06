package com.iprody.userprofile.userprofileservice.service;

import com.iprody.userprofile.userprofileservice.AbstractIntegrationTest;
import com.iprody.userprofile.userprofileservice.entity.User;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import reactor.test.StepVerifier;

import java.util.NoSuchElementException;

/**
 * This class contains unit tests for the UserService class.
 */
@ExtendWith(SoftAssertionsExtension.class)
class UserServiceTest extends AbstractIntegrationTest {

    private static final String TEST_FIRST_NAME = "testFirstName";
    private static final String TEST_LAST_NAME = "testLastName";
    private static final String UPDATED_TEST_LAST_NAME = "updatedLastName";
    private static final String TEST_EMAIL = "test@mail.ru";

    @Autowired
    private  UserService userService;


    /**
     * Retrieves a valid User for testing.
     *
     * @return A valid User instance.
     */
    private User getValidUser() {
        return User.builder().email(TEST_EMAIL).firstName(TEST_FIRST_NAME).lastName(TEST_LAST_NAME).build();
    }

    /**
     * Retrieves an updated valid User for testing.
     *
     * @return An updated valid User instance.
     */

    private User getUpdatedValidUser() {
        return User.builder().email(TEST_EMAIL).firstName(TEST_FIRST_NAME).lastName(UPDATED_TEST_LAST_NAME).build();
    }

    /**
     * Test method to verify the addition of a user.
     * @param assertion SoftAssertions instance for making multiple assertions in a single test.
     */
    @Test
    @Order(1)
    public void testCreateUser(SoftAssertions assertion) {
        StepVerifier.create(userService.createUser(getValidUser()))
                .assertNext(createdUserDto -> {
                    assertion.assertThat(createdUserDto.getId()).isNotNull();
                    assertion.assertThat(createdUserDto.getFirstName()).isEqualTo(getValidUser().getFirstName());
                    assertion.assertThat(createdUserDto.getLastName()).isEqualTo(getValidUser().getLastName());
                    assertion.assertThat(createdUserDto.getEmail()).isEqualTo(getValidUser().getEmail());
                })
                .verifyComplete();
    }

    /**
     * Test method to verify adding a user with a non-unique email.
     */
    @Test
    @Order(2)
    public void testCreateUser_ButEmailDoesNotUnique() {
        User createdUser = getValidUser();
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> userService.createUser(createdUser));
    }

    /**
     * Test method to verify finding a user by ID.
     * @param assertion SoftAssertions instance for making multiple assertions in a single test.
     */
    @Test
    @Order(3)
    public void testFindUserById(SoftAssertions assertion) {
        StepVerifier.create(userService.findUserById(1L))
                .assertNext(createdUserDto -> {
                    assertion.assertThat(createdUserDto.getId()).isEqualTo(1L);
                    assertion.assertThat(createdUserDto.getFirstName()).isEqualTo(getValidUser().getFirstName());
                    assertion.assertThat(createdUserDto.getLastName()).isEqualTo(getValidUser().getLastName());
                    assertion.assertThat(createdUserDto.getEmail()).isEqualTo(getValidUser().getEmail());
                })
                .verifyComplete();
    }

    /**
     * Test method to verify finding a user by ID when the user does not exist.
     */
    @Test
    @Order(4)
    public void testFindUserById_ButUserDoesNotExist() {
        StepVerifier.create(userService.findUserById(2L))
                .expectError(NoSuchElementException.class).verify();
    }
    /**
     * Test method to verify updating a user by ID.
     * @param assertion SoftAssertions instance for making multiple assertions in a single test.
     */
    @Test
    @Order(5)
    public void testUpdateUserById(SoftAssertions assertion) {
        User updatedUser = getUpdatedValidUser();
        StepVerifier.create(userService.updateUser(1L, updatedUser))
                .assertNext(createdUserDto -> {
                    assertion.assertThat(createdUserDto.getId()).isEqualTo(1L);
                    assertion.assertThat(createdUserDto.getFirstName()).isEqualTo(getUpdatedValidUser().getFirstName());
                    assertion.assertThat(createdUserDto.getLastName()).isEqualTo(getUpdatedValidUser().getLastName());
                    assertion.assertThat(createdUserDto.getEmail()).isEqualTo(getUpdatedValidUser().getEmail());
                })
                .verifyComplete();

    }

    /**
     * Test method to verify updating a user by ID when the user does not exist.
     */
    @Test
    @Order(6)
    public void testUpdateUserById_ButUserDoesNotExist() {
        User updatedUser = getUpdatedValidUser();
        StepVerifier.create(userService.updateUser(2L, updatedUser))
                .expectError(NoSuchElementException.class).verify();
    }
}
