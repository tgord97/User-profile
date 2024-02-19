package com.iprody.userprofile.userprofileservice.controller;

import com.iprody.userprofile.userprofileservice.dto.UserDto;
import com.iprody.userprofile.userprofileservice.entity.User;
import com.iprody.userprofile.userprofileservice.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

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
    public Mono<ResponseEntity<UserDto>> findUserById(@RequestParam Long id) {
        var userMono = userService.findUserById(id);
        return userMono.flatMap(foundedUser -> Mono.just(ResponseEntity.ok(modelMapper.map(foundedUser, UserDto.class)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build())));
    }

    /**
     * Endpoint for creating a new user.
     *
     * @param userDto The DTO object representing the user to be created.
     * @return ResponseEntity with the created UserDto object and HTTP status code.
     */

    @PostMapping("/add")
    public Mono<ResponseEntity<UserDto>> saveUser(@RequestBody UserDto userDto) {
        var createdUser = userService.createUser(modelMapper.map(userDto, User.class));
        return createdUser.flatMap(value ->
                        Mono.just(ResponseEntity.created(URI.create("/users/" + value.getId()))
                        .body(modelMapper.map(value, UserDto.class))))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    /**
     * Endpoint for creating a new user.
     *
     * @param id The id of the user to be updated.
     * @param userDto The DTO object representing the user to be updated.
     * @return ResponseEntity with the updated UserDto object and HTTP status code.
     */
    @PostMapping("/update")
    public Mono<ResponseEntity<UserDto>> updateUser(@RequestParam Long id, @RequestBody UserDto userDto) {
        var userMono = userService.updateUser(id, modelMapper.map(userDto, User.class));
        return userMono.flatMap(updatedUser -> Mono.just(ResponseEntity.ok(modelMapper.map(updatedUser, UserDto.class)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build())));
    }

}
