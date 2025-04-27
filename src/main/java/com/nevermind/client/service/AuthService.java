package com.nevermind.client.service;

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
}
