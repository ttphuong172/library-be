package com.example.librarybe.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Book {
    @Id
    private String id;
    private String isbn;
    private String title;
    private String cover;

    @ElementCollection
    @CollectionTable(
            name="authors",
            joinColumns=@JoinColumn(name="book_id")
    )
    private List<Authors> authors;


    private int number_of_pages;

    private String publisher;

    private String publish_date;
    private BookStatus status;

    @ManyToOne
    @JoinColumn
    private Rack rack;

//    @ManyToMany(mappedBy = "bookList")
//    @JsonBackReference
//    private List<Lending> lendingList;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<LendingBook> lendingBookList;

}
