package com.iprody.userprofile.userprofileservice.service;

import com.iprody.userprofile.userprofileservice.entity.User;
import com.iprody.userprofile.userprofileservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

/**
 * Service class for managing user-related operations.
 */
@Service
@AllArgsConstructor
public class UserService {

    /**
     * Injection of error message.
     */
    private static final String NOT_FOUND_MASSAGE = "Could not find user with id ";
    /**
     * Injection of UserRepository.
     */
    private final UserRepository userRepository;

    /**
     * Creates a new user.
     *
     * @param user The user object to be created.
     * @return The created user object.
     */
    @Transactional
    public Mono<User> createUser(User user) {
        return Mono.just(userRepository.save(user));
    }

    /**
     * Find a user by id.
     *
     * @param id The id of user object to be found.
     * @return The found user object.
     */
    @Transactional
    public Mono<User> findUserById(Long id) {
        var foundedUser = userRepository.findById(id);
        return foundedUser.map(Mono::just).orElseGet(() ->
                Mono.error(new  NoSuchElementException(NOT_FOUND_MASSAGE + id)));
    }

    /**
     * Update a user by id.
     *
     * @param id The id of user object to be updated.
     * @param updatedUser The user object to be updated.
     * @return The updated user object.
     */
    @Transactional
    public Mono<User> updateUser(Long id, User updatedUser) {
        var userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            var existingUser = userOptional.get();
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            return Mono.just(userRepository.save(existingUser));
        } else {
            return Mono.error(new NoSuchElementException(NOT_FOUND_MASSAGE + id));
        }
    }

}
