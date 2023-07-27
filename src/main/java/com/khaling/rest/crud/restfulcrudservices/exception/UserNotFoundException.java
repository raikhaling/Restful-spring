package com.khaling.rest.crud.restfulcrudservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The @ResponseStatus annotation in Spring is used to customize the HTTP status
 * code that should be returned to the client when an exception is thrown
 */

//Spring will return a status code of 500 (Internal Server Error) for unhandled exceptions.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

}
