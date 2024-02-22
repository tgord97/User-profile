package com.iprody.userprofile.userprofileservice.dto;

import lombok.Data;

/**
 * Data transfer object (DTO) representing a user.
 */
@Data
public class UserDto {

    /**
     * The first name of the user.
     */

    private String firstName;

    /**
     * The second name address of the user.
     */

    private String secondName;

    /**
     * The email address of the user.
     */

    private String email;

}
