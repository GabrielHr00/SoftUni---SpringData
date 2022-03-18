package com.example.demo.productShop;

import com.example.demo.productShop.entities.CategoryStats;
import com.example.demo.productShop.entities.ProductWithoutBuyerDTO;
import com.example.demo.productShop.entities.UserWithSoldProductsDTO;
import com.example.demo.productShop.services.ProductService;
import com.example.demo.productShop.services.SeedService;
import com.example.demo.productShop.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductShopRunner implements CommandLineRunner {
    private final SeedService seedService;

    @Autowired
    public ProductShopRunner(SeedService seedService) {
        this.seedService = seedService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedService.seedAll();

    }

}
