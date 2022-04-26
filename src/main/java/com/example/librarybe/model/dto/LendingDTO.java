package com.example.librarybe.model.dto;

import com.example.librarybe.model.Account;
import com.example.librarybe.model.Student;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LendingDTO {
    private int id;
    private LocalDate loanDate;
    private Account account;
    private List<BookDTO> bookDTOList;
}
