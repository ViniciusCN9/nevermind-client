package com.nevermind.client.config.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DefaultResponseError {

    private Integer statusCode;
    private String message;
    private String details;
}
