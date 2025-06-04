package com.nevermind.client.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nevermind.client.config.exception.GlobalResponseErrorHandler;
import com.nevermind.client.model.UserModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Configuration()
@PropertySource("classpath:application.properties")
public class ClientConfiguration {

    @Getter
    @Setter
    private static UserModel currentUser = null;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setErrorHandler(new GlobalResponseErrorHandler(objectMapper()));
        if (currentUser != null) {
            String currentToken = currentUser.getToken();
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
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public StandardWebSocketClient standardWebSocketClient() {
        return new StandardWebSocketClient();
    }

    @Bean
    public WebSocketHttpHeaders webSocketHttpHeaders() {
        return new WebSocketHttpHeaders();
    }
}
