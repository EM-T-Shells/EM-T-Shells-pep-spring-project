package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message postMessage(Message message) {
        if (message.getPosted_by() == null || message.getMessage_text().isEmpty() || message.getMessage_text() == null || message.getMessage_text().length() >= 255) {
            throw new IllegalArgumentException("Invalid message");
        }
        return messageRepository.save(message);
    }
}
