package com.example.librarybe.repository;

import com.example.librarybe.model.Lending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LendingRepository extends JpaRepository<Lending,Integer> {

}
