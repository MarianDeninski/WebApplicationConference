package com.teamthree.conferencescheduler.exceptions;

public class ApplicationRuntimeException extends RuntimeException {
    public ApplicationRuntimeException(String message) {
        super(message);
    }
}
