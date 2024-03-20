package com.iprody.userprofile.userprofileservice.aop;

import com.iprody.userprofile.userprofileservice.exception.ResourceNotFoundException;
import com.iprody.userprofile.userprofileservice.exception.ResourceProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Class for exceptions handling.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     *  Constant response code BAD_REQUEST.
     */
    private static final int RESPONSE_CODE_BAD_REQUEST = 400;

    /**
     * Constant response code NOT_FOUND.
     */
    private static final int RESPONSE_CODE_NOT_FOUND = 404;

    /**
     * Constant response code INTERNAL_SERVER_ERROR.
     */
    private static final int RESPONSE_CODE_INTERNAL_SERVER_ERROR = 500;

    /**
     * Exception handler for HttpMessageNotReadableException.class.
     *
     * @param exception The exception object.
     * @param request   The request from HttpServletRequest.
     * @return ExceptionResponse class with details.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleHttpMessageNotReadableException(
            final HttpMessageNotReadableException exception,
            final HttpServletRequest request) {
        ExceptionResponse error = new ExceptionResponse();
        error.setMessage("Required request body is missing");
        error.setStatus(RESPONSE_CODE_BAD_REQUEST);
        error.setDetails(null);
        return error;
    }

    /**
     * Exception handler for MethodArgumentNotValidException.class.
     *
     * @param exception The exception object.
     * @param request   The request from HttpServletRequest.
     * @return ExceptionResponse class with details.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleResourceNotFoundException(
            final MethodArgumentNotValidException exception,
            final HttpServletRequest request) {
        ExceptionResponse error = new ExceptionResponse();
        error.setMessage("Validation failed for argument");
        error.setStatus(RESPONSE_CODE_BAD_REQUEST);
        error.setDetails(exception.getBindingResult().getAllErrors()
                .stream()
                .map(e -> e.toString())
                .toList());
        return error;
    }

    /**
     * Exception handler for ResourceNotFoundException.class.
     *
     * @param exception The exception object.
     * @param request   The request from HttpServletRequest.
     * @return ExceptionResponse class with details.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ExceptionResponse handleResourceNotFoundException(final ResourceNotFoundException exception,
                                                                           final HttpServletRequest request) {
        ExceptionResponse error = new ExceptionResponse();
        error.setMessage(exception.getMessage());
        error.setStatus(RESPONSE_CODE_NOT_FOUND);
        error.setDetails(null);
        return error;
    }

    /**
     * Exception handler for ResourceProcessingException.class.
     *
     * @param exception The exception object.
     * @param request   The request from HttpServletRequest.
     * @return ExceptionResponse class with details.
     */
    @ExceptionHandler(ResourceProcessingException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ExceptionResponse handleResourceProcessingException(final Exception exception,
                                                                             final HttpServletRequest request) {
        ExceptionResponse error = new ExceptionResponse();
        error.setMessage(exception.getMessage());
        error.setStatus(RESPONSE_CODE_INTERNAL_SERVER_ERROR);
        error.setDetails(null);
        return error;
    }

    /**
     * Exception handler for Exception.class.
     *
     * @param exception The exception object.
     * @param request   The request from HttpServletRequest.
     * @return ExceptionResponse class with details.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ExceptionResponse handleException(final Exception exception,
                                                           final HttpServletRequest request) {
        ExceptionResponse error = new ExceptionResponse();
        error.setMessage(exception.getMessage());
        error.setStatus(RESPONSE_CODE_INTERNAL_SERVER_ERROR);
        error.setDetails(null);
        return error;
    }
}
