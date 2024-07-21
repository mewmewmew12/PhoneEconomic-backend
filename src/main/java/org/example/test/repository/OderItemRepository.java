package org.example.test.repository;

import org.example.test.entity.OderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OderItemRepository extends JpaRepository<OderItem, Integer> {


    @Query("select o from OderItem o where o.cart.user.id = ?1")
    List<OderItem> findByCart_User_Id(Integer id);
}