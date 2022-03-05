package com.example.advquerying;

import com.example.advquerying.repositories.IngredientRepository;
import com.example.advquerying.repositories.ShampooRepository;
import com.example.advquerying.services.IngredientService;
import com.example.advquerying.services.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    private ShampooRepository shampooRepository;
    private ShampooService shampooService;
    private IngredientService ingredientService;

    public Runner(ShampooRepository shampooRepository, ShampooService shampooService, IngredientService ingredientService) {
        this.shampooRepository = shampooRepository;
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Transactional
    public void run(String... args) throws Exception {
        //shampooService.findAllDistinctBySizeOrderById(Size.MEDIUM)
        //shampooService.findAllByPriceGreaterThanOrderByPriceDesc(new BigDecimal(5))
        //shampooService.findAllDistinctBySizeOrLabelIdOrderByPriceAsc(Size.MEDIUM, 10)
        //ingredientService.findAllDistinctByNameStartingWith("M")
        //ingredientService.findAllDistinctByNameInOrderByPriceAsc(List.of("Lavender", "Herbs", "Apple"))
        //shampooService.findByIngredientsNameIn(List.of("Berry", "Mineral-Collagen"))
        //.stream().forEach(System.out::println);

        //System.out.println(shampooService.findByIngredientsCount(2));
        //System.out.println(shampooService.findAllByPriceLessThan(BigDecimal.valueOf(8.50)));
        System.out.println(ingredientService.deleteIngredientsByName("Nettle"));
    }


}
