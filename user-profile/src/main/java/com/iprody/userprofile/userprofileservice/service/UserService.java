package com.iprody.userprofile.userprofileservice.service;

import com.iprody.userprofile.userprofileservice.entity.User;
import com.iprody.userprofile.userprofileservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing user-related operations.
 */
@Service
@AllArgsConstructor
public class UserService {

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
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Find a user by id.
     *
     * @param id The id of user object to be found.
     * @return The found user object.
     */
    @Transactional
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Find a user by id.
     *
     * @param id The id of user object to be updated.
     * @param updatedUser The user object to be updated.
     * @return The updated user object.
     */
    @Transactional
    public User updateUser(Long id, User updatedUser) {
        var userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            var existingUser = userOptional.get();
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User with id" + id + "not found");
        }
    }
}
