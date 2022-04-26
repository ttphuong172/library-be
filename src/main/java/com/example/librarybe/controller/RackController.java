package com.example.librarybe.controller;

import com.example.librarybe.model.BookStatus;
import com.example.librarybe.model.Rack;
import com.example.librarybe.model.dto.RackDTO;
import com.example.librarybe.service.RackService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/racks")
@CrossOrigin

public class RackController {
    @Autowired
    private RackService rackService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<RackDTO>> findAll() {
        List<Rack> rackList = rackService.findAll();
        List<RackDTO> rackDTOList = new ArrayList<>();

        for (int i = 0; i < rackList.size(); i++) {
            RackDTO rackDTO = new RackDTO();
            rackDTO.setId(rackList.get(i).getId());
            rackDTO.setName(rackList.get(i).getName());
            rackDTO.setLibrary(rackList.get(i).getLibrary());

            int bookQuantity = 0;
            int loanedQuantity=0;
            int keepingQuantity=0;
            for (int j = 0; j < rackList.get(i).getBookList().size(); j++) {
                bookQuantity++;
                if (rackList.get(i).getBookList().get(j).getStatus()== BookStatus.LOANED){
                    loanedQuantity++;
                } else {
                    keepingQuantity++;
                }
            }
            rackDTO.setBookQuantity(bookQuantity);
            rackDTO.setLoanedBook(loanedQuantity);
            rackDTO.setKeepingBook(keepingQuantity);

            rackDTOList.add(rackDTO);
        }
        return new ResponseEntity<>(rackDTOList, HttpStatus.OK);
//        return new ResponseEntity<>(rackService.findAll().stream().map(bookcase -> modelMapper.map(bookcase, RackDTO.class)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Rack> findById(@PathVariable int id) {
        Rack rack = rackService.findById(id);
        return new ResponseEntity<>(rack, HttpStatus.OK);
    }

    @GetMapping("library/{name}")
    public ResponseEntity<List<Rack>> findAllByLibrary_Name(@PathVariable String name) {
        return new ResponseEntity<>(rackService.findAllByLibrary_Name(name), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> save(@RequestBody Rack rack) {
        rackService.save(rack);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Rack rack) {
        Rack rackCurrent = rackService.findById(id);
        if (rackCurrent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        rackCurrent.setName(rack.getName());
        rackCurrent.setLibrary(rack.getLibrary());
        rackService.save(rackCurrent);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
