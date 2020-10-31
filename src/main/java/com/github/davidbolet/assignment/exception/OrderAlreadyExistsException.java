package com.github.davidbolet.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Order with given Id already exists")
public class OrderAlreadyExistsException extends RuntimeException {
    public OrderAlreadyExistsException() {
        super("Order with given Id already exists");
    }
}
