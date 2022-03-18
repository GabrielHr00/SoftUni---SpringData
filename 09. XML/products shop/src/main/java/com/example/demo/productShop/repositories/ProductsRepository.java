package com.example.demo.productShop.repositories;

import com.example.demo.productShop.entities.CategoryStats;
import com.example.demo.productShop.entities.Product;
import com.example.demo.productShop.entities.ProductWithoutBuyerDTO;
import com.example.demo.productShop.entities.ProductsInRangeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(BigDecimal start, BigDecimal end);
}
