package com.example.advquerying;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Runner implements CommandLineRunner {

    private ShampooRepository shampooRepository;

    public Runner(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("im starting Spring Data");

        _01_findShampoosBySizeOrderedById();
        _02_findShampoosBySizeOrLabelIdOrderByPrice();
        _03_findShampoosByPriceGreaterThanOrderedByPrice();
        _04_findIngredientsStartingWith();
        _05_findIngredientsInList();

    }

    private void _05_findIngredientsInList() {
        Set<String> names = new HashSet<>();
        shampooRepository.findAllDistinctByIngredientsNameInOrderByPriceAsc(List.of("Lavender", "Herbs", "Apple")).stream().forEach(e -> e.getIngredients().stream().forEach(f -> names.add(f.getName())));
        names.stream().forEach(System.out::println);
    }

    private void _04_findIngredientsStartingWith() {
        Set<String> names = new HashSet<>();
        shampooRepository.findAllDistinctByIngredientsNameStartingWith('M').stream().forEach(e -> e.getIngredients().stream().forEach(f -> names.add(f.getName())));
        names.stream().forEach(System.out::println);
    }

    private void _03_findShampoosByPriceGreaterThanOrderedByPrice() {
        shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(new BigDecimal(5)).stream().forEach(System.out::println);
    }

    private void _02_findShampoosBySizeOrLabelIdOrderByPrice() {
        shampooRepository.findAllDistinctBySizeOrLabelIdOrderByPriceAsc(Size.MEDIUM, 10).stream().forEach(System.out::println);
    }

    private void _01_findShampoosBySizeOrderedById() {
        shampooRepository.findAllDistinctBySizeOrderById(Size.MEDIUM).stream().forEach(System.out::println);
    }
}
