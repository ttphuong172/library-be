package com.example.librarybe.controller;

import com.example.librarybe.model.Book;
import com.example.librarybe.model.BookStatus;
import com.example.librarybe.model.LendingBook;
import com.example.librarybe.model.dto.ReturnDTO;
import com.example.librarybe.repository.LendingBookRepository;
import com.example.librarybe.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/lendingbooks")
@CrossOrigin
public class LendingBookController {
    @Autowired
    private LendingBookRepository lendingBookRepository;
    @Autowired
    private BookService bookService;

    ///Danh sach sach da tra
    @GetMapping("")
    public ResponseEntity<List<ReturnDTO>> findAllByReturnDateIsNotNull() {
        List<LendingBook> lendingBookList = lendingBookRepository.findAllByReturnDateIsNotNull();
        if (lendingBookList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ReturnDTO> returnDTOList = new ArrayList<>();
        for (int i = 0; i < lendingBookList.size(); i++) {
            ReturnDTO returnDTO = new ReturnDTO();
            returnDTO.setId(lendingBookList.get(i).getId());
            returnDTO.setBook_isbn(lendingBookList.get(i).getBook().getIsbn());
            returnDTO.setBook_id(lendingBookList.get(i).getBook().getId());
            returnDTO.setBook_title(lendingBookList.get(i).getBook().getTitle());
            returnDTO.setAccountName(lendingBookList.get(i).getLending().getAccount().getFullname());
            returnDTO.setLoanDate(lendingBookList.get(i).getLending().getLoanDate());
            returnDTO.setReturnDate(lendingBookList.get(i).getReturnDate());
            returnDTOList.add(returnDTO);
            Collections.sort(returnDTOList, (o1, o2) -> o2.getId() - o1.getId());
        }
        return new ResponseEntity<>(returnDTOList, HttpStatus.OK);
    }

    //Tim sach chua tra theo id
    @GetMapping("books/{id}")
    public ResponseEntity<ReturnDTO> findAllByBook_IdAndReturnDateIsNull(@PathVariable String id) {
        LendingBook lendingBook = lendingBookRepository.findAllByBook_IdAndReturnDateIsNull(id);
        if (lendingBook == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        ReturnDTO returnDTO = new ReturnDTO();
        returnDTO.setId(lendingBook.getId());
        returnDTO.setBook_id(lendingBook.getBook().getId());
        returnDTO.setBook_isbn(lendingBook.getBook().getIsbn());
        returnDTO.setBook_title(lendingBook.getBook().getTitle());
        returnDTO.setAccountName(lendingBook.getLending().getAccount().getFullname());
        returnDTO.setLoanDate(lendingBook.getLending().getLoanDate());

        return new ResponseEntity<>(returnDTO, HttpStatus.OK);
    }

    @PostMapping("comfirm")
    @Transactional
    public ResponseEntity<String> comfirmReturn(@RequestBody List<ReturnDTO> returnDTOList) {
        for (int i = 0; i < returnDTOList.size(); i++) {
            LendingBook lendingBook = lendingBookRepository.findById(returnDTOList.get(i).getId()).orElse(null);
            lendingBook.setReturnDate(LocalDate.now());
            lendingBookRepository.save(lendingBook);

            Book book = bookService.findById(returnDTOList.get(i).getBook_id());
            book.setStatus(BookStatus.AVAILABLE);
            bookService.save(book);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
