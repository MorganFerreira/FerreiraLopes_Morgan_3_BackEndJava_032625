package com.p3backEnd.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p3backEnd.model.Messages;
import com.p3backEnd.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    MessageController(MessageService messageService){
        this.messageService = messageService;
    }
    
	/**
	 * Create Message
	 * @param MessageRequest
	 * @return MessageResponse
	 */
    @Operation(summary = "Message", description = "Envoie un message avec en paramètre l'id de la location et le message")
    @PostMapping
    public ResponseEntity<MessageResponse> createMessage(@RequestBody MessageRequest createRequest) {

        Messages newMessage = new Messages();
        newMessage.setUser_id(createRequest.user_id);
        newMessage.setRental_id(createRequest.rental_id);
        newMessage.setMessage(createRequest.message);
        this.messageService.createMessage(newMessage);
        
        return ResponseEntity.ok(new MessageResponse("Message send !!!"));
    }

    public record MessageResponse(String message){}
    public record MessageRequest(String message, Integer user_id, Integer rental_id){}
}
