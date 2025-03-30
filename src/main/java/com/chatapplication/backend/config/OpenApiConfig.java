package com.chatapplication.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Chat Application WebSocket API")
                        .version("1.0")
                        .description("""
                                This API provides real-time messaging via WebSockets.
                                ### How to Use WebSocket:
                                - Connect to WebSocket at `ws://localhost:8080/ws`
                                - Subscribe to `/chatroom/public` for public messages.
                                - Subscribe to `/user/{username}/private` for private messages.
                                - Send a message to `/app/message` for public chat.
                                - Send a message to `/app/private-message` for private chat.
                                """)
                        .contact(new Contact().name("Support Team").email("support@example.com")))
                .tags(List.of(
                        new Tag().name("Chat WebSocket API").description("Handles real-time chat messaging over WebSocket")
                ))
                .servers(List.of(new Server().url("http://localhost:8080").description("Local Server")));
    }
}
