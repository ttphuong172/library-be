package com.example.librarybe.service.impl;

import com.example.librarybe.model.Rack;
import com.example.librarybe.repository.RackRepository;
import com.example.librarybe.service.RackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RackServiceImpl implements RackService {
    @Autowired
    private RackRepository rackRepository;
    @Override
    public List<Rack> findAll() {
        return rackRepository.findAll();
    }

    @Override
    public Rack findById(int id) {
        return rackRepository.findById(id).orElse(null);
    }
}
