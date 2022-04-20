package com.example.librarybe.repository;

import com.example.librarybe.model.LendingBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LendingBookRepository extends JpaRepository<LendingBook,Integer> {
    LendingBook findAllByBook_IdAndReturnDateIsNull(String id);
    List<LendingBook> findAllByReturnDateIsNotNull();
}
