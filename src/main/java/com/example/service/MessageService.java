package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message postMessage(Message message) {
        if (message.getMessage_text().isEmpty() || message.getMessage_text() == null || message.getMessage_text().length() >= 255) {
            throw new IllegalArgumentException("Invalid message");
        }

        Account postedByUser = accountRepository.findById(message.getPosted_by()).orElse(null);
        if(postedByUser==null){
            throw new IllegalArgumentException("user does not exist");
        }

        return messageRepository.save(message);
    }
}
