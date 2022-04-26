package com.example.librarybe.model.dto;

import com.example.librarybe.model.Authors;
import com.example.librarybe.model.Book;
import com.example.librarybe.model.BookStatus;
import com.example.librarybe.model.Rack;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO implements Comparable <BookDTO>   {
    private String id;
    private String isbn;
    private String title;
    private String cover;
    private List<Authors> authors;
    private int number_of_pages;
    private String publisher;
    private String publish_date;
    private BookStatus status;
    private Rack rack;
    private LocalDate loanDate;
    private LocalDate returnDate;

    @Override
    public int compareTo(BookDTO bookDTO) {
        int result=0;
        if (this.getReturnDate()==null && bookDTO.getReturnDate()!=null){
            result =-1;
        }
        else if (this.getReturnDate()!=null && bookDTO.getReturnDate()==null){
            result =1;
        }
        if (this.getReturnDate()!=null && bookDTO.getReturnDate()!=null){
            result= this.getReturnDate().compareTo(bookDTO.getReturnDate());
        }
        return result;
    }
}
