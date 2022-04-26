package com.example.librarybe.repository;

import com.example.librarybe.model.Rack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RackRepository extends JpaRepository<Rack,Integer> {
    List<Rack> findAllByLibrary_Name(String name);

}
