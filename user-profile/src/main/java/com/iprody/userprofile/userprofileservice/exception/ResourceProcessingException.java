package com.iprody.userprofile.userprofileservice.exception;

/**
 * ResourceProcessingException exception class.
 */
public class ResourceProcessingException extends RuntimeException {

    /**
     * Constructor for class.
     */
    public ResourceProcessingException() {
        super();
    }

    /**
     * Constructor with arguments for class.
     * @param message  The exception message.
     */
    public ResourceProcessingException(final String message) {
        super(message);
    }
}
