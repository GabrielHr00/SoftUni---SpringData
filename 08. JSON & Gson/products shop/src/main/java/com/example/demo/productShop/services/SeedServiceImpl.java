package com.example.demo.productShop.services;

import com.example.demo.productShop.entities.*;
import com.example.demo.productShop.repositories.CategoryRepository;
import com.example.demo.productShop.repositories.ProductsRepository;
import com.example.demo.productShop.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService{
    private static final String USERS_JSON = "/Users/mac/Desktop/Mac/github/SoftUni---SpringData/08. JSON & Gson/products shop/src/main/resources/productShop/productShop/users.json";
    private static final String CATEGORIES_JSON = "/Users/mac/Desktop/Mac/github/SoftUni---SpringData/08. JSON & Gson/products shop/src/main/resources/productShop/productShop/categories.json";
    private static final String PRODUCTS_JSON = "/Users/mac/Desktop/Mac/github/SoftUni---SpringData/08. JSON & Gson/products shop/src/main/resources/productShop/productShop/products.json";

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductsRepository productsRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, ProductsRepository productsRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productsRepository = productsRepository;
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
    public void seedProducts() throws FileNotFoundException {
        FileReader fileReader = new FileReader(PRODUCTS_JSON);
        ProductImportDTO[] userImportDTOS = this.gson.fromJson(fileReader, ProductImportDTO[].class);

        List<Product> products = Arrays.stream(userImportDTOS)
                .map(importDto -> this.modelMapper.map(importDto, Product.class))
                .map(this::setRandomSeller)
                .map(this::setRandomBuyer)
                .map(this::sendRandomCategories)
                .collect(Collectors.toList());

        this.productsRepository.saveAll(products);
    }

    private Product sendRandomCategories(Product product) {
        long counts = this.categoryRepository.count();

        int count = new Random().nextInt((int) counts);

        Set<Category> categories = new HashSet<>();
        for(int i = 0; i < count; i++){
            int randomId = new Random().nextInt((int) counts) + 1;

            Optional<Category> randomCategory = this.categoryRepository.findById(randomId);
            categories.add(randomCategory.get());
        }

        product.setCategories(categories);
        return product;
    }

    private Product setRandomBuyer(Product product) {
        if(product.getPrice().compareTo(BigDecimal.valueOf(944)) > 0){
            return product;
        }

        Optional<User> buyer = getRandomSeller();
        product.setBuyer(buyer.get());

        return product;
    }

    private Optional<User> getRandomSeller() {
        long usersCount = this.userRepository.count();

        Random random = new Random();

        int randomUserId = random.nextInt((int)usersCount) + 1;

        return this.userRepository.findById(randomUserId);
    }

    private Product setRandomSeller(Product product) {
        long usersCount = this.userRepository.count();

        Random random = new Random();

        int randomUserId = random.nextInt((int)usersCount) + 1;

        Optional<User> seller = this.userRepository.findById(randomUserId);
        product.setSeller(seller.get());

        return product;
    }
}
