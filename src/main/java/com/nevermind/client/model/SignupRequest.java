package com.nevermind.client.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {

    private String username;
    private String password;
    private String email;
}
