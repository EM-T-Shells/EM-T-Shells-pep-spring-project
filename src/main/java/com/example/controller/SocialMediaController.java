package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */


@RestController
@RequestMapping("/")
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        try {
            Account registeredAccount = accountService.registerAccount(account);
            return new ResponseEntity<>(registeredAccount, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        try {
            Account loggedInAccount = accountService.login(account);
            return new ResponseEntity<>(loggedInAccount, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessages(@RequestBody Message message){
        try {
            Message postMessage = messageService.postMessage(message);
            return new ResponseEntity<Message>(postMessage, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages() {
        try {
            List<Message> messages = messageService.getAllMessages();
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }  
    
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccount(@PathVariable Integer account_id) {
        try {
            List<Message> messages = messageService.getMessagesByAccount(account_id);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
      
}