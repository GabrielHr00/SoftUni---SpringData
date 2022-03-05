package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Set<Ingredient> findAllDistinctByNameStartingWith(String start);

    Set<Ingredient> findAllDistinctByNameInOrderByPriceAsc(List<String> names);

    @Query("DELETE Ingredient i WHERE i.name = :ingName")
    int deleteIngredientsByName(@Param("ingName") String ingName);
}
