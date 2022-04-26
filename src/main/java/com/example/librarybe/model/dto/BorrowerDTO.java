package com.example.librarybe.model.dto;

import com.example.librarybe.model.EPosition;
import lombok.Data;

import java.util.List;

@Data
public class BorrowerDTO {
    private int id;
    private String username;
    private String fullname;
    private EPosition position;
    List<BookDTO> bookDTOList;
}
