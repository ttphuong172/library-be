package com.example.librarybe.controller;

import com.example.librarybe.model.Library;
import com.example.librarybe.model.dto.LibraryDTO;
import com.example.librarybe.service.LibraryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/libraries")
@CrossOrigin
public class LibraryController {
    @Autowired
    private LibraryService libraryService;
    @Autowired
    private ModelMapper modelMapper;
    @GetMapping("")
    public ResponseEntity<List<Library>> findAll(){
        return new ResponseEntity<>(libraryService.findAll(),HttpStatus.OK);
//        return new ResponseEntity<>(libraryService.findAll().stream().map(library -> modelMapper.map(library, LibraryDTO.class)).collect(Collectors.toList()), HttpStatus.OK);
    }

}
