package com.nevermind.client.config.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DefaultResponseError {

    private Integer statusCode;
    private String message;
    private String details;
}
