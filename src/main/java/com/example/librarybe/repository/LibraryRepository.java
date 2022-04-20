package com.example.librarybe.repository;

import com.example.librarybe.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Integer> {

}
