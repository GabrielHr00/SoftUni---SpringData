package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShampooServiceImpl implements ShampooService{
    private ShampooRepository shampooRepository;

    @Autowired
    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> findAllDistinctBySizeOrderById(Size size) {
        return shampooRepository.findAllDistinctBySizeOrderById(size);
    }

    @Override
    public List<Shampoo> findAllDistinctBySizeOrLabelIdOrderByPriceAsc(Size size, long id) {
        return shampooRepository.findAllDistinctBySizeOrLabelIdOrderByPriceAsc(size, id);
    }

    @Override
    public List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price) {
        return shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(price);
    }

    @Override
    public Long findAllByPriceLessThan(BigDecimal price) {
        return shampooRepository.findAllByPriceLessThan(price).stream().count();
    }

    @Override
    public List<Shampoo> findByIngredientsNameIn(List<String> ingredientsNames) {
        return shampooRepository.findByIngredientsNameIn(ingredientsNames);
    }

    @Override
    public List<Shampoo> findByIngredientsCount(int countIng) {
        return shampooRepository.findByIngredientsCount(countIng);
    }
}
