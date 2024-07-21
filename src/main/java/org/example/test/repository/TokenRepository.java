package org.example.test.repository;

import org.example.test.entity.Token;
import org.example.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {



    @Query("select t from Token t where t.tokenUser = ?1")
    Optional<Token> findByTokenUser(String tokenUser);
}