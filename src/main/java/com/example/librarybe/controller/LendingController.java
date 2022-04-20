package com.example.librarybe.controller;

import com.example.librarybe.model.Book;
import com.example.librarybe.model.BookStatus;
import com.example.librarybe.model.Lending;
import com.example.librarybe.model.LendingBook;
import com.example.librarybe.model.dto.BookDTO;
import com.example.librarybe.model.dto.LendingRequest;
import com.example.librarybe.model.dto.LendingDTO;
import com.example.librarybe.service.BookService;
import com.example.librarybe.service.LendingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("api/lendings")
@CrossOrigin
public class LendingController {
    @Autowired
    private LendingService lendingService;
    @Autowired
    private BookService bookService;

    @GetMapping("")
    public ResponseEntity<List<LendingRequest>> findAll(){

        List<Lending> lendingList = lendingService.findAll();
        List<LendingRequest> lendingRequestList =new ArrayList<>();

        for(int i=0;i<lendingList.size();i++){
            LendingRequest lendingRequest = new LendingRequest();
            lendingRequest.setId(lendingList.get(i).getId());
            lendingRequest.setLoanDate(lendingList.get(i).getLoanDate());
            lendingRequest.setStudent(lendingList.get(i).getStudent());
            List<Book> bookList  = new ArrayList<>();
            for (int j=0;j<lendingList.get(i).getLendingBookList().size();j++){
                bookList.add(lendingList.get(i).getLendingBookList().get(j).getBook());
            }
            lendingRequest.setBookList(bookList);
            lendingRequestList.add(lendingRequest);
        }
        Collections.sort(lendingRequestList, (o1, o2) -> o2.getId()-o1.getId());
        return new ResponseEntity<>(lendingRequestList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<LendingDTO> findById(@PathVariable int id){

        Lending lending = lendingService.findById(id);
        if (lending == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LendingDTO lendingDTO =new LendingDTO();
        lendingDTO.setId(lending.getId());
        lendingDTO.setLoanDate(lending.getLoanDate());
        lendingDTO.setStudent(lending.getStudent());
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (int i=0;i<lending.getLendingBookList().size();i++){
            BookDTO bookDTO =new BookDTO();

            bookDTO.setId(lending.getLendingBookList().get(i).getBook().getId());
            bookDTO.setIsbn(lending.getLendingBookList().get(i).getBook().getIsbn());
            bookDTO.setTitle(lending.getLendingBookList().get(i).getBook().getTitle());
            bookDTO.setCover(lending.getLendingBookList().get(i).getBook().getCover());
            bookDTO.setAuthors(lending.getLendingBookList().get(i).getBook().getAuthors());
            bookDTO.setNumber_of_pages(lending.getLendingBookList().get(i).getBook().getNumber_of_pages());
            bookDTO.setPublisher(lending.getLendingBookList().get(i).getBook().getPublisher());
            bookDTO.setPublisher(lending.getLendingBookList().get(i).getBook().getPublisher());
            bookDTO.setPublish_date(lending.getLendingBookList().get(i).getBook().getPublish_date());
            bookDTO.setStatus(lending.getLendingBookList().get(i).getBook().getStatus());
            bookDTO.setRack(lending.getLendingBookList().get(i).getBook().getRack());

            bookDTO.setReturnDate(lending.getLendingBookList().get(i).getReturnDate());
            bookDTOList.add(bookDTO);
        }

        lendingDTO.setBookDTOList(bookDTOList);

        return new ResponseEntity<>(lendingDTO,HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> save(@RequestBody LendingRequest lendingRequest) throws Exception {

        Lending lending=new Lending();
//        lending.setId(lendingDTO.getId());
        lending.setLoanDate(lendingRequest.getLoanDate());
        lending.setStudent(lendingRequest.getStudent());
        List<LendingBook> lendingBookList = new ArrayList<>();
        for (int i = 0; i< lendingRequest.getBookList().size(); i++){
            LendingBook lendingBook=new LendingBook();
            lendingBook.setLending(lending);
            if(lendingRequest.getBookList().get(i).equals(BookStatus.LOANED)){
                throw new Exception("Book have been lent!");
            }
            lendingRequest.getBookList().get(i).setStatus(BookStatus.LOANED);
            bookService.save(lendingRequest.getBookList().get(i));
            lendingBook.setBook(lendingRequest.getBookList().get(i));
            lendingBookList.add(lendingBook);
        }
        lending.setLendingBookList(lendingBookList);

        lendingService.save(lending);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
