package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Set<Ingredient> findAllDistinctByNameStartingWith(String start);

    Set<Ingredient> findAllDistinctByNameInOrderByPriceAsc(List<String> names);

    //@Query("DELETE Ingredient i WHERE i.name = :ingName")
    //@Modifying
    int deleteByName(String ingName);

    @Query("UPDATE Ingredient i SET i.price = i.price + i.price * :priceIng")
    @Modifying
    void increasePriceByPercentage(@Param("priceIng") BigDecimal priceIng);


    @Query("UPDATE Ingredient i SET i.price = i.price + i.price * :priceIng WHERE i.name IN :listIng")
    @Modifying
    void increasePriceByPercentageInList(@Param("priceIng")  BigDecimal priceIng, @Param("listIng") List<String> listIng);
}
