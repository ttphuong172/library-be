package com.example.librarybe.service.impl;

import com.example.librarybe.model.Lending;
import com.example.librarybe.repository.LendingRepository;
import com.example.librarybe.service.LendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LendingServiceImpl implements LendingService {
    @Autowired
    private LendingRepository lendingRepository;
    @Override
    public List<Lending> findAll() {
        return lendingRepository.findAll();
    }

    @Override
    public void save(Lending lending) {
        lendingRepository.save(lending);
    }

    @Override
    public Lending findById(int id) {
        return lendingRepository.findById(id).orElse(null);
    }
}
