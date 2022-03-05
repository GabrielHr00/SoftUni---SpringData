package com.example.advquerying.repositories;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findAllDistinctBySizeOrderById(Size size);

    List<Shampoo> findAllDistinctBySizeOrLabelIdOrderByPriceAsc(Size size, long id);

    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    Set<Shampoo> findAllByPriceLessThan(BigDecimal price);

    @Query("SELECT s FROM Shampoo s JOIN s.ingredients i WHERE i.name IN :ingredientsNames")
    List<Shampoo> findByIngredientsNameIn(@Param("ingredientsNames") List<String> ingredientsNames);

    @Query("SELECT s FROM Shampoo s WHERE s.ingredients.size < :countIng")
    List<Shampoo> findByIngredientsCount(@Param("countIng") int countIng);
}
