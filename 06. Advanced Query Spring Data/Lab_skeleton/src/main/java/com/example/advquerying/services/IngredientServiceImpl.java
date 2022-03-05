package com.example.advquerying.services;

import com.example.advquerying.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService{

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Set<String> findAllDistinctByNameStartingWith(String start){
        return ingredientRepository.findAllDistinctByNameStartingWith(start).stream().map(e -> e.getName()).collect(Collectors.toSet());
    }

    public Set<String> findAllDistinctByNameInOrderByPriceAsc(List<String> names){
        return ingredientRepository.findAllDistinctByNameInOrderByPriceAsc(names).stream().map(e -> e.getName()).collect(Collectors.toSet());
    }

    public int deleteIngredientsByName(String ingName){
        return ingredientRepository.deleteIngredientsByName(ingName);
    }

}
