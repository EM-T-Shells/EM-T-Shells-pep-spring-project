package com.example.service;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isEmpty()
            || account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Invalid account information");
        }

        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount != null) {
            throw new DuplicateUsernameException("Username already exists"); 
        }

        return accountRepository.save(account);
    }

    public Account login(Account account) {
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount == null || !existingAccount.getPassword().equals(account.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return existingAccount;
    }
}
