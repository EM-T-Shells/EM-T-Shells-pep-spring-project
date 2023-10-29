package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccount(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password);
    }

    public Account addAccount(Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        
        if (username != null && !username.isBlank() && password != null && password.length() >= 4) {
            if (!accountRepository.existsByUsername(username)) {
                return accountRepository.save(account);
            }
        }
        return null;
    }

}