package com.example.librarybe.service.impl;


import com.example.librarybe.model.Account;
import com.example.librarybe.model.Book;
import com.example.librarybe.repository.AccountRepository;
import com.example.librarybe.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Account findById(String username) {
        return accountRepository.findById(username).orElse(null);
//        return accountRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("Not found"));
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findByCode(String code) {
        return accountRepository.findByCode(code).orElse(null);
    }

    @Override
    public List<Account> search(String code,String username,String fullname,String position) {
        return accountRepository.search(code,username,fullname,position);
    }

    @Override
    public void delete(Account account) {
        accountRepository.delete(account);
    }
}
