package com.example.librarybe.service;

import com.example.librarybe.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();
    void save(Student student);
    Student findById(int id);
}
