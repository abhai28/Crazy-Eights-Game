package com.example.comp4004f22a3101077008;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/*
 * WebSocketConfig class
 * This class is used to configure the WebSocket
 * It is used to enable the simple broker, set the application destination prefix, and set the user destination prefix
 * The @Configuration annotation is used to indicate that the class is a configuration class
 * The @EnableWebSocketMessageBroker annotation is used to enable WebSocket message handling, backed by a message broker
 * The WebSocketMessageBrokerConfigurer interface is used to configure message handling with simple messaging protocols (e.g. STOMP) through a message broker
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/users");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/crazy8-websocket").withSockJS();
    }

}
