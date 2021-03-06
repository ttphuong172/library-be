package com.example.librarybe.model.dto;

import com.example.librarybe.model.Account;
import com.example.librarybe.model.EPosition;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class BorrowerStatisticsDTO {
    private String code;
    private String username;
    private String fullname;
    private String position;
    private int bookQuantity;
    private int returnedQuantity;
    private int keepingQuantity;
}
