package com.iprody.userprofile.userprofileservice.service;

import com.iprody.userprofile.userprofileservice.AbstractIntegrationTest;
import com.iprody.userprofile.userprofileservice.entity.User;
import com.iprody.userprofile.userprofileservice.entity.UserContact;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.util.NoSuchElementException;

@ExtendWith(SoftAssertionsExtension.class)
class UserContactServiceTest extends AbstractIntegrationTest {

    private static final String TEST_TELEGRAM_ID = "@testusername";
    private static final String TEST_UPDATED_TELEGRAM_ID = "@updatedusername";
    private static final String TEST_INVALID_TELEGRAM_ID = "@test.username";
    private static final String TEST_MOBILE_PHONE = "+79531234567";
    private static final String TEST_INVALID_MOBILE_PHONE = "+A79531234567";
    private static final String TEST_FIRST_NAME = "testFirstName";
    private static final String TEST_LAST_NAME = "testLastName";
    private static final String TEST_EMAIL = "test@mail.ru";

    @Autowired
    private UserContactService userContactService;

    @Autowired
    private UserService userService;

    /**
     * Retrieves a valid User Contact for testing.
     *
     * @return A valid User Contact instance.
     */
    private UserContact getValidUserContact() {
        return UserContact.builder().mobilePhone(TEST_MOBILE_PHONE).telegramId(TEST_UPDATED_TELEGRAM_ID)
                .user(userService.findUserById(1L).block()).build();
    }

    /**
     * Retrieves a valid User Contact for testing.
     *
     * @return A valid User Contact instance.
     */
    private UserContact getInvalidTelegramUserContact() {
        return UserContact.builder().mobilePhone(TEST_MOBILE_PHONE).telegramId(TEST_INVALID_TELEGRAM_ID).build();
    }

    private UserContact getInvalidMobileUserContact() {
        return UserContact.builder().mobilePhone(TEST_INVALID_MOBILE_PHONE).telegramId(TEST_TELEGRAM_ID).build();
    }

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

    private UserContact getUpdatedValidUserContact() {
        return UserContact.builder().mobilePhone(TEST_MOBILE_PHONE).telegramId(TEST_UPDATED_TELEGRAM_ID)
                .user(userService.findUserById(1L).block()).build();
    }

    /**
     * Test case for creating a user.
     * Expects successful creation with valid user data.
     * @param assertion SoftAssertions instance for making multiple assertions in a single test
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
     * Test case for creating a user contact.
     * Expects successful creation with valid user contact data.
     * @param assertion SoftAssertions instance for making multiple assertions in a single test
     */
    @Test
    @Order(2)
    public void testCreateUserContact(SoftAssertions assertion) {
        StepVerifier.create(userContactService.createUserContact(getValidUserContact()))
                .assertNext(createdUserDto -> {
                    assertion.assertThat(createdUserDto.getId()).isEqualTo(1L);
                    assertion.assertThat(createdUserDto.getMobilePhone())
                            .isEqualTo(getValidUserContact().getMobilePhone());
                    assertion.assertThat(createdUserDto.getTelegramId())
                            .isEqualTo(getValidUserContact().getTelegramId());

                })
                .verifyComplete();
    }

    /**
     * Test method to verify creating a user contact with an invalid telegram.
     */
    @Test
    @Order(3)
    public void testCreateUserContact_ButTelegramIsInvalid() {
        UserContact userContact = getInvalidTelegramUserContact();
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            userContactService.createUserContact(userContact);
        });
    }

    /**
     * Test method to verify creating a user contact with an invalid mobile number.
     */
    @Test
    @Order(4)
    public void testCreateUserContact_ButMobileIsInvalid() {
        UserContact userContact = getInvalidMobileUserContact();
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            userContactService.createUserContact(userContact);
        });
    }

    /**
     * Test method to verify finding a user contact by ID.
     * @param assertion SoftAssertions instance for making multiple assertions in a single test
     */
    @Test
    @Order(5)
    public void testFindUserContactById(SoftAssertions assertion) {
        StepVerifier.create(userContactService.findById(1L))
                .assertNext(createdUserDto -> {
                    assertion.assertThat(createdUserDto.getId()).isNotNull();
                    assertion.assertThat(createdUserDto.getMobilePhone())
                            .isEqualTo(getValidUserContact().getMobilePhone());
                    assertion.assertThat(createdUserDto.getTelegramId())
                            .isEqualTo(getValidUserContact().getTelegramId());
                    assertion.assertThat(createdUserDto.getUser().getFirstName())
                            .isEqualTo(getValidUserContact().getUser().getFirstName());
                    assertion.assertThat(createdUserDto.getUser().getLastName())
                            .isEqualTo(getValidUserContact().getUser().getLastName());
                    assertion.assertThat(createdUserDto.getUser().getEmail())
                            .isEqualTo(getValidUserContact().getUser().getEmail());
                })
                .verifyComplete();
    }

    /**
     * Test method to verify an exception is thrown when trying to find a non-existing user contact by ID.
     */
    @Test
    @Order(6)
    public void testFindUserContact_ButUserContactDoesNotExist() {
        StepVerifier.create(userContactService.findById(2L))
                .expectError(NoSuchElementException.class).verify();
    }

    /**
     * Test method to verify finding a user contact by user ID.
     * @param assertion SoftAssertions instance for making multiple assertions in a single test
     */
    @Test
    @Order(7)
    public void testFindUserContactByUserId(SoftAssertions assertion) {
        StepVerifier.create(userContactService.findByUserId(1L))
                .assertNext(createdUserDto -> {
                    assertion.assertThat(createdUserDto.getId()).isNotNull();
                    assertion.assertThat(createdUserDto.getMobilePhone())
                            .isEqualTo(getValidUserContact().getMobilePhone());
                    assertion.assertThat(createdUserDto.getTelegramId())
                            .isEqualTo(getValidUserContact().getTelegramId());
                    assertion.assertThat(createdUserDto.getUser().getFirstName())
                            .isEqualTo(getValidUserContact().getUser().getFirstName());
                    assertion.assertThat(createdUserDto.getUser().getLastName())
                            .isEqualTo(getValidUserContact().getUser().getLastName());
                    assertion.assertThat(createdUserDto.getUser().getEmail())
                            .isEqualTo(getValidUserContact().getUser().getEmail());
                })
                .verifyComplete();
    }

    /**
     * Test method to verify an exception is thrown when trying to find a non-existing user contact by user ID.
     */
    @Test
    @Order(8)
    public void testFindUserContactByUserId_ButUserContactDoesNotExist() {
        StepVerifier.create(userContactService.findByUserId(2L))
                .expectError(NoSuchElementException.class).verify();
    }

    /**
     * Test method to verify updating a user contact by ID.
     * @param assertion SoftAssertions instance for making multiple assertions in a single test
     */
    @Test
    @Order(9)
    public void testUpdateUserById(SoftAssertions assertion) {
        UserContact updatedUserContact = getUpdatedValidUserContact();
        StepVerifier.create(userContactService.update(1L, updatedUserContact))
                .assertNext(createdUserDto -> {
                    assertion.assertThat(createdUserDto.getId()).isNotNull();
                    assertion.assertThat(createdUserDto.getMobilePhone())
                            .isEqualTo(getUpdatedValidUserContact().getMobilePhone());
                    assertion.assertThat(createdUserDto.getTelegramId())
                            .isEqualTo(getUpdatedValidUserContact().getTelegramId());
                    assertion.assertThat(createdUserDto.getUser().getFirstName())
                            .isEqualTo(getUpdatedValidUserContact().getUser().getFirstName());
                    assertion.assertThat(createdUserDto.getUser().getLastName())
                            .isEqualTo(getUpdatedValidUserContact().getUser().getLastName());
                    assertion.assertThat(createdUserDto.getUser().getEmail())
                            .isEqualTo(getUpdatedValidUserContact().getUser().getEmail());
                })
                .verifyComplete();

    }

    /**
     * Test method to verify updating a user by ID when the user does not exist.
     */
    @Test
    @Order(10)
    public void testUpdateUserContactById_ButUserDoesNotExist() {
        UserContact updatedUserContact = getUpdatedValidUserContact();
        StepVerifier.create(userContactService.update(2L, updatedUserContact))
                .expectError(NoSuchElementException.class).verify();
    }

}
