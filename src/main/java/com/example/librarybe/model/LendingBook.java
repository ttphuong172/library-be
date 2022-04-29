package com.example.librarybe.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LendingBook implements Comparable<LendingBook> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn
    private Book book;

    @ManyToOne
    @JoinColumn
    private Lending lending;


    @Override
    public int compareTo(LendingBook o) {
        int result=0;
        if (this.getReturnDate()==null && o.getReturnDate()!=null){
            result =-1;
        }
        else if (this.getReturnDate()!=null && o.getReturnDate()==null){
            result =1;
        }
        if (this.getReturnDate()!=null && o.getReturnDate()!=null){
            result= o.getReturnDate().compareTo(this.getReturnDate());
        }
        return result;
//        return o.getLending().getLoanDate().compareTo(this.getLending().getLoanDate());
    }
}
