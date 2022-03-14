package com.example.demo.productShop.repositories;

import com.example.demo.productShop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
