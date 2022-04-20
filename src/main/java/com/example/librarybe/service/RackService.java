package com.example.librarybe.service;

import com.example.librarybe.model.Rack;

import java.util.List;

public interface RackService {
    List<Rack> findAll();
    Rack findById(int id);
}
