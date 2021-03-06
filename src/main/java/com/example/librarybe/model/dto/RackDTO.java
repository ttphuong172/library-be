package com.example.librarybe.model.dto;

import com.example.librarybe.model.Book;
import com.example.librarybe.model.Library;
import lombok.Data;

import java.util.List;

@Data
public class RackDTO {
    private int id;
    private String name;
//    private String description;
    private int bookQuantity;
    private int loanedBook;
    private int keepingBook;
    private Library library;
}
