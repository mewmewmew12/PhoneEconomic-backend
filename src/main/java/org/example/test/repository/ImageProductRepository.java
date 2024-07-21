package org.example.test.repository;

import org.example.test.entity.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageProductRepository extends JpaRepository<ImageProduct, Integer> {
}