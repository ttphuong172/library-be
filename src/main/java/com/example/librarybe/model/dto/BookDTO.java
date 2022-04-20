package com.example.librarybe.model.dto;

import com.example.librarybe.model.Authors;
import com.example.librarybe.model.Book;
import com.example.librarybe.model.BookStatus;
import com.example.librarybe.model.Rack;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String id;
    private String isbn;
    private String title;
    private String cover;
    private List<Authors> authors;
    private int number_of_pages;
    private String publisher;
    private String publish_date;
    private BookStatus status;
    private Rack rack;
    private LocalDate loanDate;
    private LocalDate returnDate;
}
