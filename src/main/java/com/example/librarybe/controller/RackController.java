package com.example.librarybe.controller;

import com.example.librarybe.model.Rack;
import com.example.librarybe.model.dto.RackDTO;
import com.example.librarybe.service.RackService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("api/racks")
@CrossOrigin

public class RackController {
    @Autowired
    private RackService rackService;
    @Autowired
    private ModelMapper modelMapper;
    @GetMapping("")
    public ResponseEntity<List<RackDTO>> findAll(){
        return new ResponseEntity<>(rackService.findAll().stream().map(bookcase -> modelMapper.map(bookcase, RackDTO.class)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Rack> findById(@PathVariable int id){
      Rack rack = rackService.findById(id);
      return new ResponseEntity<>(rack,HttpStatus.OK);
    }
}
