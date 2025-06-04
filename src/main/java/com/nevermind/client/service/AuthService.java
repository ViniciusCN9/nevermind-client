package com.nevermind.client.service;

import com.nevermind.client.config.ClientConfiguration;
import com.nevermind.client.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

@Service
@RequiredArgsConstructor
public class AuthService extends BaseService {

    private final RestTemplate restTemplate;

    public SignupResponse signup(SignupRequest request) {
        ResponseEntity<SignupResponse> response = restTemplate.postForEntity(
                basePath + "auth/signup",
                request,
                SignupResponse.class);
        return response.getBody();
    }

    public LoginResponse login(LoginRequest request) {
        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
                basePath + "auth/login",
                request,
                LoginResponse.class);

        LoginResponse loginResponse = response.getBody();

        KeyPair keyPar = generateKeyPair();
        UserModel currentUser = new UserModel();
        currentUser.setUsername(request.getUsername());
        currentUser.setToken(loginResponse.getToken());
        currentUser.setExpiresIn(loginResponse.getExpiresIn());
        currentUser.setPrivateKey(keyPar.getPrivate());
        currentUser.setPublicKey(keyPar.getPublic());
        ClientConfiguration.setCurrentUser(currentUser);

        return loginResponse;
    }

    public void logout() {
        ClientConfiguration.setCurrentUser(null);
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            return generator.generateKeyPair();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error in key pair generate");
        }
    }
}
