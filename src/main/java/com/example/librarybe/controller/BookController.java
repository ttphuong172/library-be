package com.example.librarybe.controller;

import com.example.librarybe.model.Book;
import com.example.librarybe.model.BookStatus;
import com.example.librarybe.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/books")
@CrossOrigin
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private ModelMapper modelMapper;
    @GetMapping("")
    public ResponseEntity<List<Book>> findAll(){
        return new ResponseEntity<>(bookService.findAll(),HttpStatus.OK);
//        return new ResponseEntity<>(bookService.findAll().stream().map(book -> modelMapper.map(book, BookDTO.class)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> findById(@PathVariable String id){
        return new ResponseEntity<>(bookService.findById(id),HttpStatus.OK);
    }

    @GetMapping("bookcases/{id}")
    public ResponseEntity<List<Book>> findAllByBookcase_Id(@PathVariable int id){
        return new ResponseEntity<>(bookService.findAllByBookcase_Id(id),HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> save(@RequestBody Book book){
        book.setStatus(BookStatus.AVAILABLE);
        bookService.save(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        Book book=bookService.findById(id);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.delete(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("search")
    public ResponseEntity<List<Book>> search(@RequestParam(required = false) String isbn,@RequestParam(required = false) String title,@RequestParam(required = false) String publisher,@RequestParam(required = false) BookStatus status){
        return new ResponseEntity<>(bookService.search(isbn,title,publisher,status),HttpStatus.OK);
    }

}
