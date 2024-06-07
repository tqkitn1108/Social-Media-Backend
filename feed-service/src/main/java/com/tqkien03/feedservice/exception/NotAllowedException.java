package com.tqkien03.feedservice.exception;

public class NotAllowedException extends RuntimeException {
    public NotAllowedException(String user, String resource, String operation) {
        super(String.format("user %s is not allowed to %s resource %s",
                user, operation, resource));
    }
}
