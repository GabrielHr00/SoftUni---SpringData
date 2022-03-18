package com.example.demo.productShop.repositories;

import com.example.demo.productShop.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(BigDecimal start, BigDecimal end);

    @Query("SELECT new com.example.demo.productShop.entities.CategoryPropsDTO(c.name, COUNT(p), AVG(p.price), SUM(p.price)) FROM Product p JOIN p.categories c GROUP BY c")
    List<CategoryPropsDTO> getCategoryStats();
}
