package org.example.test.repository;

import org.example.test.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query("select i from Image i where i.user.id = ?1 and i.id = ?2")
    Image findByUser_IdAndId(Integer userid, Integer imageId);
}