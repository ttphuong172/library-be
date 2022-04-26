package com.example.librarybe.repository;

import com.example.librarybe.model.Lending;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LendingRepository extends JpaRepository<Lending,Integer> {

}
