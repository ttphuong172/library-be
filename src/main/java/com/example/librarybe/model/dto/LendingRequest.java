package com.example.librarybe.model.dto;

import com.example.librarybe.model.Book;
import com.example.librarybe.model.LendingBook;
import com.example.librarybe.model.Student;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LendingRequest {
    private int id;
    private LocalDate loanDate;
    private Student student;
    private List<Book> bookList;
}
