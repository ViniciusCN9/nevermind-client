package com.nevermind.client.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class ChatWebSocketHandler extends AbstractWebSocketHandler {

    private final Consumer<String> onMessageReceived;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        if (onMessageReceived != null) {
            onMessageReceived.accept(message.getPayload());
        }
    }
}
