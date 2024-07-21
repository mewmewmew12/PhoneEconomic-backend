package org.example.test.repository;

import org.example.test.entity.Comment;
import org.example.test.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("select c from Comment c where c.product = ?1")
    List<Comment> findByProduct(Product product);
}