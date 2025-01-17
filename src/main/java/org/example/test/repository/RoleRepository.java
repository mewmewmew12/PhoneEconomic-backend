package org.example.test.repository;

import org.example.test.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
//    Optional<Role> findByName(String name);

    @Query("select r from Role r where r.name = ?1")
    Optional<Role> findByName(String name);
}