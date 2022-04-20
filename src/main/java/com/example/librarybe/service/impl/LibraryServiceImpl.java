package com.example.librarybe.service.impl;

import com.example.librarybe.model.Library;
import com.example.librarybe.repository.LibraryRepository;
import com.example.librarybe.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LibraryServiceImpl implements LibraryService {
    @Autowired
    private LibraryRepository libraryRepository;
    @Override
    public List<Library> findAll() {
        return libraryRepository.findAll();
    }
}
