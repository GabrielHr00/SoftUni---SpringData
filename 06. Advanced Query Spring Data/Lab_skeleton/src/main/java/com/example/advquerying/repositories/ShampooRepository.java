package com.example.advquerying.repositories;

import com.example.advquerying.entities.Label;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findAllDistinctBySizeOrderById(Size size);

    List<Shampoo> findAllDistinctBySizeOrLabelIdOrderByPriceAsc(Size size, long id);

    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    Set<Shampoo> findAllDistinctByIngredientsNameStartingWith(char start);

    Set<Shampoo> findAllDistinctByIngredientsNameInOrderByPriceAsc(List<String> names);

}
