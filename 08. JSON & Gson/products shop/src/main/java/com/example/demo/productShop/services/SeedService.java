package com.example.demo.productShop.services;

import java.io.FileNotFoundException;

public interface SeedService {
    void seedUsers() throws FileNotFoundException;

    void seedCategories() throws FileNotFoundException;

    void seedProducts();

    default void seedAll() throws FileNotFoundException {
        seedUsers();
        seedCategories();
        seedProducts();
    }
}