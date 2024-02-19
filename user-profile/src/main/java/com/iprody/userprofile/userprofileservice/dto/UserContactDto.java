package com.iprody.userprofile.userprofileservice.dto;

import com.iprody.userprofile.userprofileservice.entity.User;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserContactDto {

    /**
     * The telegram id of the user contact.
     */

    @Pattern(regexp = "^@[a-zA-Z0-9_]{4,}$")
    private String telegramId;

    /**
     * The mobile phone of the user contact.
     */
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$")
    private String mobilePhone;

    /**
     * The user of the user contact.
     */
    private User user;
}
