package com.example.librarybe.model.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//Display list return
public class ReturnDTO {
    private int id;
    private String book_id;
    private String book_isbn;
    private String book_title;
    private String accountName;
    private LocalDate loanDate;
    private LocalDate returnDate;
}
