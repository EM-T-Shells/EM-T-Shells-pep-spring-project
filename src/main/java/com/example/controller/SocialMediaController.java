package com.example.controller;

import com.example.entity.Account;
import com.example.exception.UsernameExistsException;
import com.example.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping("/api")
public class SocialMediaController {
    private final AccountService accountService;

    @Autowired
    public SocialMediaController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account verifiedAccount = accountService.getAccount(account.getUsername(), account.getPassword());
        if (verifiedAccount != null) {
            return ResponseEntity.ok(verifiedAccount);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account account) throws JsonProcessingException {
        try {
            Account addedAccount = accountService.addAccount(account);
            if (addedAccount != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(addedAccount);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (UsernameExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }   
    }
}

