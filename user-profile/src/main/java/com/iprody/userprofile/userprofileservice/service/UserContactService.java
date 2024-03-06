package com.iprody.userprofile.userprofileservice.service;

import com.iprody.userprofile.userprofileservice.entity.UserContact;
import com.iprody.userprofile.userprofileservice.repository.UserContactRepository;
import com.iprody.userprofile.userprofileservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

/**
 * Service class for managing user contact-related operations.
 */
@Service
@AllArgsConstructor
public class UserContactService {

    /**
     * Injection of error message.
     */
    private static final String NOT_FOUND_MASSAGE = "Could not find user contact with id ";
    /**
     * Injection of UserContactRepository.
     */

    private final UserContactRepository userContactRepository;

    /**
     * Injection of UserRepository.
     */

    private final UserRepository userRepository;

    /**
     * Creates a new user contact.
     *
     * @param userContact The user object to be created.
     * @return The created user object.
     */
    @Transactional
    public Mono<UserContact> createUserContact(UserContact userContact) {
        return Mono.just(userContactRepository.save(userContact));
    }

    /**
     * Find a user contact by id.
     *
     * @param id The id of user contact object to be found.
     * @return The found user contact object.
     */
    @Transactional
    public Mono<UserContact> findById(Long id) {
        var foundedUser = userContactRepository.findById(id);
        return foundedUser.map(Mono::just).orElseGet(() ->
                Mono.error(new NoSuchElementException(NOT_FOUND_MASSAGE + id)));
    }

    /**
     * Find a user contact by user id.
     *
     * @param userId The user id of user contact object to be found.
     * @return The found user contact object.
     */
    public Mono<UserContact> findByUserId(Long userId) {
        var userOptional =  userRepository.findById(userId);
        return userOptional.map(user -> Mono.just(user.getUserContact())).orElseGet(() ->
                Mono.error(new NoSuchElementException(NOT_FOUND_MASSAGE + userId)));
    }

    /**
     * Update a user contact by id.
     *
     * @param id The id of user object to be updated.
     * @param userContact The user object to be updated.
     * @return The updated user contact object.
     */
    @Transactional
    public Mono<UserContact> update(Long id, UserContact userContact) {
        var userContactOptional = userContactRepository.findById(id);
        if (userContactOptional.isPresent()) {
            var existingUserContact = userContactOptional.get();
            existingUserContact.setMobilePhone(userContact.getMobilePhone());
            existingUserContact.setTelegramId(userContact.getTelegramId());
            return Mono.just(userContactRepository.save(existingUserContact));
        } else {
            return Mono.error(new NoSuchElementException(NOT_FOUND_MASSAGE + id));
        }
    }
}
