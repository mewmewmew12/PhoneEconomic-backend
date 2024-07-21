package org.example.test.repository;

import org.example.test.entity.Favorites;
import org.example.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Integer> {


    Favorites findByUser_Id(Integer id);

    @Query("select (count(f) > 0) from Favorites f where f.user.id = ?1")
    boolean existsByUser_Id(Integer id);

    @Query("select f from Favorites f where f.user = ?1")
    Favorites findByUser(User user);

//    @Query("""
//            select (count(f) > 0) from Favorites f inner join f.products products
//            where f.user.id = ?1 and products.id = ?2""")
//    boolean existsByUser_IdAndProducts_Id(Integer id, Integer id1);


}