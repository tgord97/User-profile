package com.iprody.userprofile.userprofileservice.exception;

/**
 * ResourceNotFoundException exception class.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor for class.
     */
    public ResourceNotFoundException() {
        super();
    }

    /**
     * Constructor with arguments for class.
     * @param message The exception message.
     */
    public ResourceNotFoundException(final String message) {
        super(message);
    }
}
