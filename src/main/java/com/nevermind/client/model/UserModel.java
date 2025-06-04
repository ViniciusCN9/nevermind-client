package com.nevermind.client.model;

import lombok.Getter;
import lombok.Setter;

import java.security.PrivateKey;
import java.security.PublicKey;

@Getter
@Setter
public class UserModel {

    private String username;
    private String token;
    private Long expiresIn;
    private PrivateKey privateKey;
    private PublicKey publicKey;
}
