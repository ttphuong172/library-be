package com.example.librarybe.service;


import com.example.librarybe.model.Account;

import java.util.List;

public interface AccountService {
    Account findByUsername(String username);
    void save(Account account);
    Account findById(int id);
    List<Account> findAll();

}
