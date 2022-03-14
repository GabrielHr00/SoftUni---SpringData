package com.example.demo.productShop.services;

import com.example.demo.productShop.entities.CategoryImportDTO;
import com.example.demo.productShop.entities.Category;
import com.example.demo.productShop.entities.User;
import com.example.demo.productShop.entities.UserImportDTO;
import com.example.demo.productShop.repositories.CategoryRepository;
import com.example.demo.productShop.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService{
    private static final String USERS_JSON = "/Users/mac/products shop/src/main/resources/productShop/productShop/users.json";
    private static final String CATEGORIES_JSON = "/Users/mac/products shop/src/main/resources/productShop/productShop/categories.json";
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void seedUsers() throws FileNotFoundException {
        FileReader fileReader = new FileReader(USERS_JSON);
        UserImportDTO[] userImportDTOS = this.gson.fromJson(fileReader, UserImportDTO[].class);

        List<User> users = Arrays.stream(userImportDTOS)
                .map(importDto -> this.modelMapper.map(importDto, User.class))
                .collect(Collectors.toList());

        this.userRepository.saveAll(users);
    }

    @Override
    public void seedCategories() throws FileNotFoundException {
        FileReader fileReader = new FileReader(CATEGORIES_JSON);
        CategoryImportDTO[] userImportDTOS = this.gson.fromJson(fileReader, CategoryImportDTO[].class);

        List<Category> categories = Arrays.stream(userImportDTOS)
                .map(importDto -> this.modelMapper.map(importDto, Category.class))
                .collect(Collectors.toList());

        this.categoryRepository.saveAll(categories);
    }

    @Override
    public void seedProducts() {

    }
}
