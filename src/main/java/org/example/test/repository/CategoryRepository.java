package org.example.test.repository;

import org.example.test.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("select c from Category c where upper(c.name) = upper(?1)")
    Optional<Category> findByNameIgnoreCase(String name);
}