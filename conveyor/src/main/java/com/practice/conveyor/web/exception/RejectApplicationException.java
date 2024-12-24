package com.practice.conveyor.web.exception;

public class RejectApplicationException extends RuntimeException{
    public RejectApplicationException(String message) {
        super(message);
    }
}
