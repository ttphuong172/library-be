package com.example.librarybe.repository;


import com.example.librarybe.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findByUsername(String username);
}
