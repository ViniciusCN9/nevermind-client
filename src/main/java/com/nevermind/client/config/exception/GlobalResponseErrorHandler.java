package com.nevermind.client.config.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class GlobalResponseErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        byte[] bodyBytes = response.getBody().readAllBytes();
        String body = new String(bodyBytes);
        if (body.isBlank()) {
            throw new ResponseErrorException("Unknown error");
        }

        DefaultResponseError error = objectMapper.readValue(body, DefaultResponseError.class);
        throw new ResponseErrorException(error.getMessage() + " | " + error.getDetails());
    }
}
