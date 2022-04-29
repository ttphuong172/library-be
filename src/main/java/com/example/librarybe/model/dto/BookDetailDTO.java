package com.example.librarybe.model.dto;

import com.example.librarybe.model.*;
import lombok.Data;
import java.util.List;
@Data
public class BookDetailDTO {
    private String id;
    private String isbn;
    private String title;
    private String cover;
    private List<Authors> authors;
    private int number_of_pages;
    private String publisher;
    private String publish_date;
    private BookStatus status;
    private Library library;
    private Rack rack;
    private List<LendingBook> lendingBookList;
}
