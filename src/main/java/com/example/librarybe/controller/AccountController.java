package com.example.librarybe.controller;


import com.example.librarybe.model.Account;
import com.example.librarybe.model.dto.*;
import com.example.librarybe.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("api/accounts")
@CrossOrigin
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/borrowers")
    public ResponseEntity<List<BorrowerStatisticsDTO>> findAll() {
        List<Account> accountList = accountService.findAll();
        List<BorrowerStatisticsDTO> borrowerStatisticsDTOList = new ArrayList<>();
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getLendingList().size() > 0) {
                BorrowerStatisticsDTO borrowerStatisticsDTO = new BorrowerStatisticsDTO();
                borrowerStatisticsDTO.setId(accountList.get(i).getId());
                borrowerStatisticsDTO.setUsername(accountList.get(i).getUsername());
                borrowerStatisticsDTO.setFullname(accountList.get(i).getFullname());
                borrowerStatisticsDTO.setPosition(accountList.get(i).getPosition());

                int bookQuantity = 0;
                int returnedQuantity = 0;
                int keepingQuantity = 0;
                for (int j = 0; j < accountList.get(i).getLendingList().size(); j++) {
                    bookQuantity += accountList.get(i).getLendingList().get(j).getLendingBookList().size();
                    for (int k = 0; k < accountList.get(i).getLendingList().get(j).getLendingBookList().size(); k++) {
                        if (accountList.get(i).getLendingList().get(j).getLendingBookList().get(k).getReturnDate() != null) {
                            returnedQuantity++;
                        } else {
                            keepingQuantity++;
                        }

                    }
                }
                borrowerStatisticsDTO.setBookQuantity(bookQuantity);
                borrowerStatisticsDTO.setReturnedQuantity(returnedQuantity);
                borrowerStatisticsDTO.setKeepingQuantity(keepingQuantity);

                borrowerStatisticsDTOList.add(borrowerStatisticsDTO);
            }
        }
        return new ResponseEntity<>(borrowerStatisticsDTOList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Account> findById(@PathVariable int id) {
        return new ResponseEntity<>(accountService.findById(id), HttpStatus.OK);
    }

    @GetMapping("username/{username}")
    public ResponseEntity<Account> findByUsername(@PathVariable String username) {
        return new ResponseEntity<>(accountService.findByUsername(username), HttpStatus.OK);
    }

    @GetMapping("borrowers/{id}")
    protected ResponseEntity<BorrowerDTO> findBorrowerById(@PathVariable int id) {
        Account account = accountService.findById(id);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BorrowerDTO borrowerDTO = new BorrowerDTO();

        borrowerDTO.setId(account.getId());
        borrowerDTO.setUsername(account.getUsername());
        borrowerDTO.setFullname(account.getFullname());
        borrowerDTO.setPosition(account.getPosition());
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (int j = 0; j < account.getLendingList().size(); j++) {
            for (int k = 0; k < account.getLendingList().get(j).getLendingBookList().size(); k++) {
                BookDTO bookDTO = new BookDTO();

                bookDTO.setId(account.getLendingList().get(j).getLendingBookList().get(k).getBook().getId());
                bookDTO.setIsbn(account.getLendingList().get(j).getLendingBookList().get(k).getBook().getIsbn());
                bookDTO.setTitle(account.getLendingList().get(j).getLendingBookList().get(k).getBook().getTitle());
                bookDTO.setCover(account.getLendingList().get(j).getLendingBookList().get(k).getBook().getCover());
                bookDTO.setAuthors(account.getLendingList().get(j).getLendingBookList().get(k).getBook().getAuthors());
                bookDTO.setNumber_of_pages(account.getLendingList().get(j).getLendingBookList().get(k).getBook().getNumber_of_pages());
                bookDTO.setPublisher(account.getLendingList().get(j).getLendingBookList().get(k).getBook().getPublisher());
                bookDTO.setPublisher(account.getLendingList().get(j).getLendingBookList().get(k).getBook().getPublisher());
                bookDTO.setPublish_date(account.getLendingList().get(j).getLendingBookList().get(k).getBook().getPublish_date());
                bookDTO.setStatus(account.getLendingList().get(j).getLendingBookList().get(k).getBook().getStatus());
                bookDTO.setRack(account.getLendingList().get(j).getLendingBookList().get(k).getBook().getRack());
                bookDTO.setLoanDate(account.getLendingList().get(j).getLendingBookList().get(k).getLending().getLoanDate());
                bookDTO.setReturnDate(account.getLendingList().get(j).getLendingBookList().get(k).getReturnDate());

                bookDTOList.add(bookDTO);
            }
        }

        //Sort bookDTOList
        Collections.sort(bookDTOList);

        borrowerDTO.setBookDTOList(bookDTOList);
        return new ResponseEntity<>(borrowerDTO, HttpStatus.OK);
    }

    @PutMapping("changepassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) throws Exception {
        Account account = accountService.findByUsername(changePasswordDTO.getUsername());
        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), account.getPassword())) {
            throw new Exception("old password is incorrect");
        }
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getComfirmNewPassword())) {
            throw new Exception("new password no match");
        }
        account.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        accountService.save(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
