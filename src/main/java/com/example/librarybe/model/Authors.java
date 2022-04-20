package com.example.librarybe.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class Authors {
    @Column(name="name")
    private String name;
}
