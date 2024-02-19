package com.iprody.userprofile.userprofileservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * User.
 * Columns:
 *   id (bigserial)
 *   first name (varchar(50))
 *   last name (varchar(50))
 *   email (varchar(255), unique)
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    /**
     * The id of the user.
     */

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    /**
     * The first name of the user.
     */

    @Column(name = "first_name")
    private String firstName;

    /**
     * The last name of the user.
     */

    @Column(name = "last_name")
    private String lastName;

    /**
     * The email of the user.
     */

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /**
     * The user contact of user contacts in the user.
     */

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private UserContact userContact;

}
