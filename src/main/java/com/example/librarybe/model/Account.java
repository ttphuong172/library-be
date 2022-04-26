package com.example.librarybe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {
    @Id
    private int id;

    private String username;
    private String password;
    private String fullname;
    @Enumerated(EnumType.STRING)
    private EPosition position;
    @Enumerated(EnumType.STRING)
    private ERole role;

    @OneToMany(mappedBy = "account")
    @JsonBackReference
    List<Lending> lendingList;


}