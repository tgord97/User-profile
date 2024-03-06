package com.iprody.userprofile.userprofileservice.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.iprody.userprofile.userprofileservice.entity.UserContact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transfer object (DTO) representing a user.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    /**
     * The first name of the user.
     */

    private String firstName;

    /**
     * The second name address of the user.
     */

    private String lastName;

    /**
     * The email address of the user.
     */

    private String email;

    /**
     * The user contact of the user.
     */
    @JsonBackReference
    private UserContact userContact;

}
