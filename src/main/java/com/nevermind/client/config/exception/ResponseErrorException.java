package com.nevermind.client.config.exception;

public class ResponseErrorException extends RuntimeException {

    public ResponseErrorException(String details) {
        super(details);
    }
}
