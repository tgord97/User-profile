package com.iprody.userprofile.userprofileservice.controller;

import com.iprody.userprofile.userprofileservice.dto.UserContactDto;
import com.iprody.userprofile.userprofileservice.entity.UserContact;
import com.iprody.userprofile.userprofileservice.service.UserContactService;
import jakarta.validation.Valid;
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
 * Controller class for managing user contact-related API endpoints.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/user-contact")
public class UserContactController {

    /**
     * Injection of UserContactService.
     */

    private final UserContactService userContactService;

    /**
     * Injection of ModelMapper.
     */

    private final ModelMapper modelMapper;

    /**
     * Endpoint for finding a user contact by id.
     *
     * @param id The representing the user contact to be found.
     * @return ResponseEntity with the found User contact object and HTTP status code.
     */
    @GetMapping()
    private Mono<ResponseEntity<UserContactDto>> findById(@RequestParam Long id) {
        var userMono = userContactService.findById(id);
        return userMono.flatMap(foundedUser -> Mono
                .just(ResponseEntity.ok(modelMapper.map(foundedUser, UserContactDto.class)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build())));
    }

    /**
     * Endpoint for finding a user contact by id.
     *
     * @param userId The representing the user field of user contact to be found.
     * @return ResponseEntity with the found User contact object and HTTP status code.
     */

    @GetMapping("/user")
    private Mono<ResponseEntity<UserContactDto>> findByUserId(@RequestParam Long userId) {
        var userMono = userContactService.findById(userId);
        return userMono.flatMap(foundedUser -> Mono
                .just(ResponseEntity.ok(modelMapper.map(foundedUser, UserContactDto.class)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build())));
    }

    /**
     * Endpoint for creating a new user contact.
     *
     * @param userContactDto The DTO object representing the user contact to be created.
     * @return ResponseEntity with the created UserDto object and HTTP status code.
     */

    @PostMapping("/add")
    public Mono<ResponseEntity<UserContactDto>> saveUserContact(@Valid @RequestBody UserContactDto userContactDto) {
        var userMono = userContactService.createUserContact(modelMapper.map(userContactDto, UserContact.class));
        return userMono.flatMap(userContact ->
                        Mono.just(ResponseEntity.created(URI.create("/user-contacts/" + userContact.getId()))
                                .body(modelMapper.map(userContact, UserContactDto.class))))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    /**
     * Endpoint for updating a new user contact.
     *
     * @param id The id of the user contact to be updated.
     * @param userContactDto The DTO object representing the user to be updated.
     * @return ResponseEntity with the updated UserDto object and HTTP status code.
     */
    @PostMapping("update")
    private Mono<ResponseEntity<UserContactDto>> update(@RequestParam Long id,
                                                  @Valid @RequestBody UserContactDto userContactDto) {
        var userMono = userContactService.update(id, modelMapper.map(userContactDto, UserContact.class));
        return userMono.flatMap(foundedUser ->
                Mono.just(ResponseEntity.ok(modelMapper.map(foundedUser, UserContactDto.class)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build())));
    }
}
