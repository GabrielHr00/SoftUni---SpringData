package com.example.advquerying.services;

import com.example.advquerying.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
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

    @Override
    public Set<String> findAllDistinctByNameStartingWith(String start){
        return ingredientRepository.findAllDistinctByNameStartingWith(start).stream().map(e -> e.getName()).collect(Collectors.toSet());
    }

    @Override
    public Set<String> findAllDistinctByNameInOrderByPriceAsc(List<String> names){
        return ingredientRepository.findAllDistinctByNameInOrderByPriceAsc(names).stream().map(e -> e.getName()).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public int deleteByName(String ingName){
        return ingredientRepository.deleteByName(ingName);
    }

    @Override
    @Transactional
    public void increasePriceByPercentage(BigDecimal price){
        ingredientRepository.increasePriceByPercentage(price);
    }

    @Override
    @Transactional
    public void increasePriceByPercentageInList(BigDecimal priceIng, List<String> listIng){
        ingredientRepository.increasePriceByPercentageInList(priceIng, listIng);
    }


}
