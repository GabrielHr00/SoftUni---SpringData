package com.example.demo.productShop.repositories;

import com.example.demo.productShop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u " +
            "WHERE (SELECT COUNT(p) FROM Product p WHERE p.seller = u AND p.buyer IS NOT NULL) > 0 " +
            " ORDER BY u.lastName, u.firstName")
    List<User> findAllWithSoldProducts();

}
