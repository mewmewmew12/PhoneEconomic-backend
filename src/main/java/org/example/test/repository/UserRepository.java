package org.example.test.repository;

import org.example.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailLikeIgnoreCase(String email);

    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);


}