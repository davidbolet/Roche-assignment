package com.github.davidbolet.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingRequiredFieldsException extends RuntimeException {
    public MissingRequiredFieldsException() {
        super("Some required fields are missing!");
    }
}
