package com.example.demo.productShop.services;

import com.example.demo.productShop.entities.CategoryStats;
import com.example.demo.productShop.entities.ProductWithoutBuyerDTO;
import com.example.demo.productShop.entities.ProductsInRangeDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductsInRangeDTO productsInRange(float from, float to);

    CategoryStats categoryStatsWithCount();
}
