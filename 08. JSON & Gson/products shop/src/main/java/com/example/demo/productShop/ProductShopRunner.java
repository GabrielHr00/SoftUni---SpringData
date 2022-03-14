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
    private final UserService userService;
    private final ProductService productService;
    private final Gson gson;

    @Autowired
    public ProductShopRunner(SeedService seedService, UserService userService, ProductService productService) {
        this.seedService = seedService;
        this.userService = userService;
        this.productService = productService;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void run(String... args) throws Exception {
        //this.seedService.seedAll();

        //productsBetweenPrice();
        //boughtItems();
        //categoriesCount();

        this.userService.getUsersWithSoldProducts();
    }

    private void categoriesCount() {
        List<CategoryStats> categoryStatistics = this.productService.getCategoryStatistics();
        String json = this.gson.toJson(categoryStatistics);
        System.out.println(json);
    }

    private void boughtItems() {
        List<UserWithSoldProductsDTO> usersWithSoldProducts = this.userService.getUsersWithSoldProducts();
        String json = this.gson.toJson(usersWithSoldProducts);
        System.out.println(json);
    }

    private void productsBetweenPrice() {
        List<ProductWithoutBuyerDTO> productsInPriceRangeForSell = this.productService.getProductsInPriceRangeForSell(500, 1000);
        String json = this.gson.toJson(productsInPriceRangeForSell);

        System.out.println(json);
    }
}
