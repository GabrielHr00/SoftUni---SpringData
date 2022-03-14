package com.example.demo.productShop.repositories;

import com.example.demo.productShop.entities.CategoryStats;
import com.example.demo.productShop.entities.Product;
import com.example.demo.productShop.entities.ProductWithoutBuyerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT new com.example.demo.productShop.entities.ProductWithoutBuyerDTO(" +
    "p.name, p.price, p.seller.firstName, p.seller.lastName)" +
    " FROM Product p" +
    " WHERE p.price > :start AND p.price < :end AND p.buyer IS NULL" +
    " ORDER BY p.price ASC")
    List<ProductWithoutBuyerDTO> findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(BigDecimal start, BigDecimal end);

    @Query("SELECT new com.example.demo.productShop.entities.CategoryStats(c.name, COUNT(p), AVG(p.price), SUM(p.price)) FROM Product p JOIN p.categories c GROUP BY c")
    List<CategoryStats> getCategoryStats();
}
