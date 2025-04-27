package com.nevermind.client.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nevermind.client.config.exception.GlobalResponseErrorHandler;
import org.apache.http.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration()
@PropertySource("classpath:application.properties")
public class ClientConfiguration {

    private static String currentToken;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new GlobalResponseErrorHandler(objectMapper()));
        if (currentToken != null) {
            restTemplate.getInterceptors().add((request, body, execution) -> {
                request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + currentToken);
                return execution.execute(request, body);
            });
        }
        return restTemplate;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public static void updateToken(String token) {
        currentToken = token;
    }
}
