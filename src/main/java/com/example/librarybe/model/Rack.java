package com.example.librarybe.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Rack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
//    private String description;

    @OneToMany (mappedBy = "rack")
//    @JsonIgnore
    @JsonBackReference
    List<Book> bookList;


    @ManyToOne
    @JoinColumn
//    @JsonManagedReference
    private Library library;

}
