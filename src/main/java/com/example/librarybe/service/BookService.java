package com.example.librarybe.service;

import com.example.librarybe.model.Book;
import com.example.librarybe.model.BookStatus;

import java.util.List;
import java.util.Optional;

public interface BookService {
    void save(Book book);
    List<Book> findAll();
    void delete (Book book);
    Book findById(String id);
    List<Book> findAllByRack_Id(int id);
    List<Book> search(String isbn, String title, String publisher, BookStatus status, Optional<Integer> idLibrary,Optional<Integer> idRack);
}
