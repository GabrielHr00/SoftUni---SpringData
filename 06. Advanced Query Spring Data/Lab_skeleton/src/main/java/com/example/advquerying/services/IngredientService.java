package com.example.advquerying.services;

import java.util.List;
import java.util.Set;

public interface IngredientService {

    Set<String> findAllDistinctByNameStartingWith(String start);

    Set<String> findAllDistinctByNameInOrderByPriceAsc(List<String> names);

    int deleteIngredientsByName(String ingName);

}
