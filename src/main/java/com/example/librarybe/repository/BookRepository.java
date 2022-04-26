package com.example.librarybe.repository;

import com.example.librarybe.model.Book;
import com.example.librarybe.model.BookStatus;
import com.example.librarybe.model.dto.BookDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,String> {
    List<Book> findAllByRack_Id(int id);



    @Query("select b from Book b where (b.isbn like %:isbn%) and (b.title like %:title%) and (b.publisher like %:publisher%) and (:status is null or b.status=:status)")
    List<Book> search(@Param("isbn") String isbn,@Param("title") String title,@Param("publisher") String publisher,@Param("status") BookStatus status);
}
