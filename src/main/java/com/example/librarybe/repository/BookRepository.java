package com.example.librarybe.repository;

import com.example.librarybe.model.Book;
import com.example.librarybe.model.BookStatus;
import com.example.librarybe.model.dto.BookDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,String> {
    List<Book> findAllByRack_Id(int id);

    @Query("select b from Book b where (b.isbn like %:isbn%) and (b.title like %:title%) and (b.publisher like %:publisher%) and (:status is null or b.status=:status) and (:idLibrary is null or b.library.id=:idLibrary) and (:idRack is null or b.rack.id=:idRack)")
    List<Book> search(@Param("isbn") String isbn, @Param("title") String title, @Param("publisher") String publisher, @Param("status") BookStatus status, @Param("idLibrary")Optional<Integer> idLibrary,@Param("idRack")Optional<Integer> idRack);


}
