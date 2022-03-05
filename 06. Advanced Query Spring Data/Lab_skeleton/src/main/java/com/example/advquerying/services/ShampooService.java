package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {
    List<Shampoo> findAllDistinctBySizeOrderById(Size size);

    List<Shampoo> findAllDistinctBySizeOrLabelIdOrderByPriceAsc(Size size, long id);

    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    Long findAllByPriceLessThan(BigDecimal price);

    List<Shampoo> findByIngredientsNameIn(List<String> ingredientsNames);

    List<Shampoo> findByIngredientsCount(int countIng);

}
