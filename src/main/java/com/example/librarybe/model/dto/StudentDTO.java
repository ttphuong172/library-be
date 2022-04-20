package com.example.librarybe.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentDTO {
    private int id;
    private String name;
    List<BookDTO> bookDTOList;
}
