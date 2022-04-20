package com.example.librarybe.model.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LendingBookDTO {
    private int id;
    private String book_id;
    private String book_isbn;
    private String book_title;
    private String student_name;
    private LocalDate loanDate;
    private LocalDate returnDate;
}
