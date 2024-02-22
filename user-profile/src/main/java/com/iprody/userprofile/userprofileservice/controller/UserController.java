package com.iprody.userprofile.userprofileservice.controller;

import com.iprody.userprofile.userprofileservice.dto.UserDto;
import com.iprody.userprofile.userprofileservice.entity.User;
import com.iprody.userprofile.userprofileservice.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

/**
 * Controller class for managing user-related API endpoints.
 */

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    /**
     * Injection of UserService.
     */
    private final UserService userService;

    /**
     * Injection of ModelMapper.
     */
    private final ModelMapper modelMapper;

    /**
     * Endpoint for finding a user by id.
     *
     * @param id The representing the user to be created.
     * @return ResponseEntity with the found User object and HTTP status code.
     */
    @GetMapping()
    public ResponseEntity<UserDto> findUserById(@RequestParam Long id) {
        var userOptional = userService.findUserById(id);
        return userOptional.map(value -> new ResponseEntity<>(modelMapper.map(value, UserDto.class), HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint for creating a new user.
     *
     * @param userDto The DTO object representing the user to be created.
     * @return ResponseEntity with the created UserDto object and HTTP status code.
     */
    @PostMapping("/add")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
        try {
            var createdUser = userService.createUser(modelMapper.map(userDto, User.class));
            return new ResponseEntity<>(modelMapper.map(createdUser, UserDto.class), HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint for creating a new user.
     *
     * @param id The id of the user to be updated.
     * @param userDto The DTO object representing the user to be updated.
     * @return ResponseEntity with the updated UserDto object and HTTP status code.
     */
    @PostMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestParam Long id, @RequestBody UserDto userDto) {
        try {
            var user = userService.updateUser(id, modelMapper.map(userDto, User.class));
            return new ResponseEntity<>(modelMapper.map(user, UserDto.class), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
