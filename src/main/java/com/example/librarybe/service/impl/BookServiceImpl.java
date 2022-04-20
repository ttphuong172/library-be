package com.example.librarybe.service.impl;

import com.example.librarybe.model.Book;
import com.example.librarybe.model.BookStatus;
import com.example.librarybe.repository.BookRepository;
import com.example.librarybe.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public Book findById(String id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public List<Book> findAllByBookcase_Id(int id) {
        return bookRepository.findAllByRack_Id(id);
    }

    @Override
    public List<Book> search(String isbn, String title, String publisher, BookStatus bookStatus) {
        return bookRepository.search(isbn,title,publisher,bookStatus);
    }
}
