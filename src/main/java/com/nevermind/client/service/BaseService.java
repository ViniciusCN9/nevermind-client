package com.nevermind.client.service;

import org.springframework.beans.factory.annotation.Value;

public class BaseService {

    @Value("${api.url}")
    protected String basePath;

    @Value("${websocket.url}")
    protected String websocketPath;
}
