package org.example.test.repository;

import org.example.test.entity.Cart;
import org.example.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c where c.user.id = ?1")
    Optional<Cart> findByUser_Id(Integer id);


//    @Query("select c from Cart c where c.user = ?1")
//    Cart findByUser(User user);
}