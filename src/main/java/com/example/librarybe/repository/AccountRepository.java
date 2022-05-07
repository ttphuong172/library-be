package com.example.librarybe.repository;


import com.example.librarybe.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account,String> {
    Optional<Account> findByCode(String code);

    @Query("select a from Account a where (a.code like %:code%) and (a.username like %:username%) and (a.fullname like %:fullname%)  and (a.position like %:position%)")
    List<Account> search(@Param("code") String code,@Param("username") String username,@Param("fullname") String fullname,@Param("position") String position);
}
