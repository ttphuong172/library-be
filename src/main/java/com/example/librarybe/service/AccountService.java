package com.example.librarybe.service;


import com.example.librarybe.model.Account;
import com.example.librarybe.model.Book;

import java.util.List;

public interface AccountService {
    void save(Account account);

    Account findById(String username);

    List<Account> findAll();

    Account findByCode(String code);

    List<Account> search(String code,String username,String fullname,String position);

    void delete(Account account);

}
