package com.example.demo.productShop.services;

import com.example.demo.productShop.entities.User;
import com.example.demo.productShop.entities.UserWithSoldProductsDTO;

import java.util.List;

public interface UserService {
    List<UserWithSoldProductsDTO> getUsersWithSoldProducts();
    List<User> getUsersWithSoldProductsOrderByCount();

}
