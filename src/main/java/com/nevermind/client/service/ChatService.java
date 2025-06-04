package com.nevermind.client.service;

import com.nevermind.client.config.ClientConfiguration;
import com.nevermind.client.handler.ChatWebSocketHandler;
import com.nevermind.client.model.UserModel;
import com.nevermind.client.util.CryptUtil;
import javafx.application.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.net.URI;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ChatService extends BaseService {

    private final StandardWebSocketClient client;
    private final WebSocketHttpHeaders headers;

    private WebSocketSession session;
    private Consumer<String> onMessageReceived;

    public void connect() throws Exception {
        UserModel currentUser = ClientConfiguration.getCurrentUser();
        URI uri = new URI(websocketPath +
                "?token=" + currentUser.getToken() +
                "&public=" + CryptUtil.encodePublicKey(currentUser.getPublicKey()));
        session = client.execute(new ChatWebSocketHandler(onMessageReceived), headers, uri).get();
    }

    public void sendMessage(String message) throws Exception {
        if (session == null || !session.isOpen()) {
            throw new IllegalStateException("Connection is closed");
        }
        session.sendMessage(new TextMessage(message));
    }

    public void disconnect() {
        if (session != null && session.isOpen()) {
            try {
                session.close();
            } catch (Exception e) {
                Platform.exit();
            }
        }
    }

    public void setOnMessageReceived(Consumer<String> onMessageReceived) {
        this.onMessageReceived = onMessageReceived;
    }
}
