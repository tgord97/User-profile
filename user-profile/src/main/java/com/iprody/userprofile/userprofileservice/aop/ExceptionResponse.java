package com.iprody.userprofile.userprofileservice.aop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Response class in case of exception.
 */
@Getter
@Setter
@NoArgsConstructor
public class ExceptionResponse {

    /**
     * The server response code.
     */
    private int status;

    /**
     * The message from exception.
     */
    private String message;

    /**
     * The exception details list.
     */
    private List<String> details;
}
