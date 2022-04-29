package com.example.librarybe.service;


import com.example.librarybe.model.Account;

import java.util.List;

public interface AccountService {
    void save(Account account);
    Account findById(String username);
    List<Account> findAll();
    Account findByCode(String code);

}
