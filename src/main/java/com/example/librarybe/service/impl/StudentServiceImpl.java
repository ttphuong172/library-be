package com.example.librarybe.service.impl;

import com.example.librarybe.model.Student;
import com.example.librarybe.repository.StudentRepository;
import com.example.librarybe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student findById(int id) {
        return studentRepository.findById(id).orElse(null);
    }
}
