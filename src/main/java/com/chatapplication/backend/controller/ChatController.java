package com.chatapplication.backend.controller;

import com.chatapplication.backend.model.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Tag(name = "Chat WebSocket API", description = "Handles real-time chat messaging over WebSocket")
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    @Operation(summary = "Send Message to Public Chatroom",
            description = "Client sends a message via WebSocket, and the server broadcasts it to all connected clients.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message successfully broadcasted",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "500", description = "Server error during message handling")
    })
    public Message receiveMessage(@Payload Message message) {
        return message;
    }

    @MessageMapping("/private-message")
    @Operation(summary = "Send Private Message",
            description = "Client sends a private message to a specific user via WebSocket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Private message successfully sent",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "500", description = "Server error during private message handling")
    })
    public Message recMessage(@Payload Message message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
        System.out.println(message.toString());
        return message;
    }
}
