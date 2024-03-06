package com.iprody.userprofile.userprofileservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "user_contact")
public class UserContact {

    /**
     * The id of the user contact.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private Long id;

    /**
     * The telegram id of the user contact.
     */

    @Column(name = "telegramId")
    @Pattern(regexp = "^@[a-zA-Z0-9_]{4,}$")
    private String telegramId;

    /**
     * The mobile phone of the user contact.
     */

    @Column(name = "mobile_phone")
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$")
    private String mobilePhone;

    /**
     * The user id of the user contact.
     */

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

}
