package com.example.service;

import java.util.Optional;
import java.util.List;
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

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
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

    public Message getMessageById(Integer messageId) {
        if (messageId == null) {
            throw new IllegalArgumentException("Invalid message ID");
        }
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent()) {
            return message.get();
        } else {
            throw new IllegalArgumentException("Message not found");
        }
    }

    public List<Message> getMessagesByAccount(Integer account_id) {
        List<Message> messages = messageRepository.findByPostedBy(account_id);
        return messages;
    }
    
    public int deleteMessage(int messageId) {
        int count = 0;
        Message message = messageRepository.findMessageById(messageId);
        if(message != null){
            messageRepository.deleteById(messageId);
            count++;
        } 
        return count;
    }

    public int updateMessage(Integer messageId, Message newMessage) {
        int count = 0;
        Message existingMessage = messageRepository.findMessageById(messageId);
        if (existingMessage == null) {
            throw new IllegalArgumentException("Message not found with ID: " + messageId);
        }
        String newMessageText = newMessage.getMessage_text();
        if (newMessageText != null && !newMessageText.isEmpty() && newMessageText.length() <= 255) {
            existingMessage.setMessage_text(newMessageText);
            messageRepository.save(existingMessage);
            count++;
            return count; 
        } else {
            throw new IllegalArgumentException("Invalid message text");
        }
    }    
}
    

