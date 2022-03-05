package com.example.advquerying.services;

import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface IngredientService {

    Set<String> findAllDistinctByNameStartingWith(String start);

    Set<String> findAllDistinctByNameInOrderByPriceAsc(List<String> names);

    int deleteByName(String ingName);

    void increasePriceByPercentage(BigDecimal price);

    void increasePriceByPercentageInList(BigDecimal priceIng, List<String> listIng);


}
