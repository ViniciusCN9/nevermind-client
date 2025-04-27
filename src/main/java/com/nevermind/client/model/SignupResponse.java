package com.nevermind.client.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
public class SignupResponse {

    private Integer id;
    private String username;
    private String email;
    private Set<String> roles;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
