package com.nevermind.client.config.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RequiredArgsConstructor
public class GlobalResponseErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws JsonProcessingException {
        StringBuilder errorResponse = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                errorResponse.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String body = errorResponse.toString();
        DefaultResponseError error = objectMapper.readValue(body, DefaultResponseError.class);

        throw new ResponseErrorException(error.getMessage() + " | " + error.getDetails());
    }
}
