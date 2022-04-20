package com.example.librarybe.controller;

import com.example.librarybe.model.Book;
import com.example.librarybe.model.BookStatus;
import com.example.librarybe.model.LendingBook;
import com.example.librarybe.model.dto.LendingBookDTO;
import com.example.librarybe.repository.LendingBookRepository;
import com.example.librarybe.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("api/lendingbooks")
@CrossOrigin
public class LendingBookController {
    @Autowired
    private LendingBookRepository lendingBookRepository;
    @Autowired
    private BookService bookService;

    @GetMapping("")
    public ResponseEntity<List<LendingBookDTO>> findAllByReturnDateIsNotNull() {
        List<LendingBook> lendingBookList = lendingBookRepository.findAllByReturnDateIsNotNull();
        if (lendingBookList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<LendingBookDTO> lendingBookDTOList = new ArrayList<>();
        for (int i = 0; i < lendingBookList.size(); i++) {
            LendingBookDTO lendingBookDTO = new LendingBookDTO();
            lendingBookDTO.setId(lendingBookList.get(i).getId());
            lendingBookDTO.setBook_isbn(lendingBookList.get(i).getBook().getIsbn());
            lendingBookDTO.setBook_id(lendingBookList.get(i).getBook().getId());
            lendingBookDTO.setBook_title(lendingBookList.get(i).getBook().getTitle());
            lendingBookDTO.setStudent_name(lendingBookList.get(i).getLending().getStudent().getName());
            lendingBookDTO.setLoanDate(lendingBookList.get(i).getLending().getLoanDate());
            lendingBookDTO.setReturnDate(lendingBookList.get(i).getReturnDate());

            lendingBookDTOList.add(lendingBookDTO);

            Collections.sort(lendingBookDTOList, (o1, o2) -> o2.getId()-o1.getId());
        }

        return new ResponseEntity<>(lendingBookDTOList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<LendingBookDTO> findAllByBook_IdAndReturnDateIsNull(@PathVariable String id) {
        LendingBook lendingBook = lendingBookRepository.findAllByBook_IdAndReturnDateIsNull(id);
        if (lendingBook == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LendingBookDTO lendingBookDTO = new LendingBookDTO();
        lendingBookDTO.setId(lendingBook.getId());
        lendingBookDTO.setBook_id(lendingBook.getBook().getId());
        lendingBookDTO.setBook_isbn(lendingBook.getBook().getIsbn());
        lendingBookDTO.setBook_title(lendingBook.getBook().getTitle());
        lendingBookDTO.setStudent_name(lendingBook.getLending().getStudent().getName());
        lendingBookDTO.setLoanDate(lendingBook.getLending().getLoanDate());

        return new ResponseEntity<>(lendingBookDTO, HttpStatus.OK);
    }

    @PostMapping("comfirm")
    public ResponseEntity<String> comfirmReturn(@RequestBody List<LendingBookDTO> lendingBookDTOList) {

        for (int i = 0; i < lendingBookDTOList.size(); i++) {

            LendingBook lendingBook = lendingBookRepository.findById(lendingBookDTOList.get(i).getId()).orElse(null);
            lendingBook.setReturnDate(LocalDate.now());
            lendingBookRepository.save(lendingBook);

            Book book = bookService.findById(lendingBookDTOList.get(i).getBook_id());
            book.setStatus(BookStatus.AVAILABLE);
            bookService.save(book);

        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
