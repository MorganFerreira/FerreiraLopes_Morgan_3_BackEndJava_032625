package com.p3backEnd.service;

import org.springframework.stereotype.Service;

import com.p3backEnd.model.Messages;
import com.p3backEnd.repository.MessageRepository;

import lombok.Data;

@Data
@Service
public class MessageService {

	private final MessageRepository messageRepository;

	public Messages createMessage(Messages newMessage) {
        return messageRepository.save(newMessage);		
	}

}
