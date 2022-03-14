package com.example.demo.productShop.services;

import com.example.demo.productShop.entities.CategoryStats;
import com.example.demo.productShop.entities.ProductWithoutBuyerDTO;
import com.example.demo.productShop.repositories.ProductsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductsRepository productsRepository;

    public ProductServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }


    @Override
    public List<ProductWithoutBuyerDTO> getProductsInPriceRangeForSell(float from, float to) {
        BigDecimal start = BigDecimal.valueOf(from);
        BigDecimal end = BigDecimal.valueOf(to);

        return this.productsRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(start, end);

    }

    @Override
    public List<CategoryStats> getCategoryStatistics() {
        return this.productsRepository.getCategoryStats();
    }
}
