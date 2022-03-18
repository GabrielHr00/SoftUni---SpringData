package com.example.demo.productShop.repositories;

import com.example.demo.productShop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(int id);

    @Query("SELECT u FROM User u JOIN u.sellingItems p " +
            "WHERE p.buyer IS NOT NULL")
    List<User> findAllWithSoldProducts();

    @Query("SELECT u FROM User u JOIN u.sellingItems p " +
            "WHERE p.buyer IS NOT NULL ORDER BY size(u.sellingItems) DESC, u.lastName ASC")
    List<User> findAllWithSoldProductsOrderByItems();
}
