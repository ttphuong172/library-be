package com.example.librarybe.service;

import com.example.librarybe.model.Lending;

import java.util.List;

public interface LendingService {
    List<Lending> findAll();
    void save(Lending lending);
    Lending findById(int id);
}
