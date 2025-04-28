package com.nevermind.client.service;

import com.nevermind.client.config.ClientConfiguration;
import com.nevermind.client.model.LoginRequest;
import com.nevermind.client.model.LoginResponse;
import com.nevermind.client.model.SignupRequest;
import com.nevermind.client.model.SignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        ClientConfiguration.setCurrentToken(loginResponse.getToken());

        return loginResponse;
    }

    public void logout() {
        ClientConfiguration.setCurrentToken(null);
    }
}
