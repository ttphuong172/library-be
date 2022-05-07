package com.example.librarybe.controller;


import com.example.librarybe.model.Account;
import com.example.librarybe.model.Book;
import com.example.librarybe.model.dto.*;
import com.example.librarybe.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

@RestController
@RequestMapping("api/accounts")
@CrossOrigin
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("")
    public ResponseEntity<List<Account>> findAll() {
        return new ResponseEntity<>(accountService.findAll(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Account> save(@RequestBody Account account) {
        Account accountByCode = accountService.findByCode(account.getCode());
        System.out.println("Code" + accountByCode);

        Account accountByUsername = accountService.findById(account.getUsername());
        System.out.println("Username" + accountByUsername);

        if (accountByCode == null && accountByUsername==null )  {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            accountService.save(account);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
    }

    @PutMapping("{username}")
    public ResponseEntity<String> update(@PathVariable String username,@RequestBody Account account){
        Account accountCurrent=accountService.findById(username);
        if (accountCurrent ==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountCurrent.setFullname(account.getFullname());
        accountCurrent.setPassword(passwordEncoder.encode(account.getPassword()));
        accountCurrent.setPosition(account.getPosition());
        accountCurrent.setRole(account.getRole());
        accountCurrent.setEnable(account.isEnable());

        accountService.save(accountCurrent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{username}")
    public ResponseEntity<Account> findById(@PathVariable String username) {
        return new ResponseEntity<>(accountService.findById(username), HttpStatus.OK);
    }

    @GetMapping("code/{code}")
    public ResponseEntity<Account> findByUsername(@PathVariable String code) {
        return new ResponseEntity<>(accountService.findByCode(code), HttpStatus.OK);
    }

    @GetMapping("/borrowers")
    public ResponseEntity<List<BorrowerStatisticsDTO>> findAllBorrower() {
        List<Account> accountList = accountService.findAll();
        List<BorrowerStatisticsDTO> borrowerStatisticsDTOList = new ArrayList<>();
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getLendingList().size() > 0) {
                BorrowerStatisticsDTO borrowerStatisticsDTO = new BorrowerStatisticsDTO();
                borrowerStatisticsDTO.setCode(accountList.get(i).getCode());
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


    @GetMapping("borrowers/{username}")
    public ResponseEntity<BorrowerDTO> findBorrowerById(@PathVariable String username) {
        System.out.println(username);
        Account account = accountService.findById(username);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BorrowerDTO borrowerDTO = new BorrowerDTO();

        borrowerDTO.setCode(account.getCode());
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
        Account account = accountService.findById(changePasswordDTO.getUsername());
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

    @GetMapping("search")
    public ResponseEntity<List<Account>> search(@RequestParam(required = false) String code,@RequestParam(required = false) String username,@RequestParam(required = false) String fullname,@RequestParam(required = false) String position){
        return new ResponseEntity<>(accountService.search(code,username,fullname,position),HttpStatus.OK);
    }

    @DeleteMapping("{username}")
    public ResponseEntity<Account> delete (@PathVariable String username){
        Account account = accountService.findById(username);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try{
            accountService.delete(account);
        } catch (DataIntegrityViolationException e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(account,HttpStatus.OK);
    }
}
