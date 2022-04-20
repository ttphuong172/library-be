package com.example.librarybe.model.dto;

import com.example.librarybe.model.Rack;
import lombok.Data;

import java.util.List;

@Data
public class LibraryDTO {
    private int id;
    private String name;
    List<Rack> rackList;
}
