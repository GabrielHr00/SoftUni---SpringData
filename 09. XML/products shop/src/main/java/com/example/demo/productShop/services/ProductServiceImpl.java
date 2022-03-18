package com.example.demo.productShop.services;

import com.example.demo.productShop.entities.CategoryStats;
import com.example.demo.productShop.entities.ProductWithoutBuyerDTO;
import com.example.demo.productShop.repositories.ProductsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Override
    public List<ProductWithoutBuyerDTO> getProductsInPriceRangeForSell(float from, float to) {
        return null;
    }

    @Override
    public List<CategoryStats> getCategoryStatistics() {
        return null;
    }
}
