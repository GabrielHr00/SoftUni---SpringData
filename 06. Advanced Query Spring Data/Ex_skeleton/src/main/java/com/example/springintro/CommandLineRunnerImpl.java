package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.List;
import java.util.Map;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final static String AGE_RESTRICTION_TEST = "miNor";

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        // _01
        //this.bookService.findAllByAgeRestriction(AGE_RESTRICTION_TEST).forEach(System.out::println);


        // _02
        //this.bookService.findAllByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000).forEach(System.out::println);

        // _03
        //        List<Book> allByPriceLessThanAndPriceGreaterThan = this.bookService.findAllByPriceLessThanAndPriceGreaterThan(BigDecimal.valueOf(5), BigDecimal.valueOf(40));
        //        System.out.println(allByPriceLessThanAndPriceGreaterThan.size());
        //        for(var i : allByPriceLessThanAndPriceGreaterThan) {
        //            System.out.println(String.format("%s - $%.2f", i.getTitle(), i.getPrice()));
        //        }

        // 04
        //this.bookService.findByReleaseDateYearNot(2000).forEach(System.out::println);

        // _05
//        LocalDate date = LocalDate.parse("12-04-1992", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//        this.bookService.findAllByReleaseDateBefore(date).forEach(e -> System.out.println(e.getTitle()  + " " +  e.getEditionType() + " " + String.format("%.2f", e.getPrice())));

        // _06
        // this.authorService.findAllByFirstNameEndingWith("e").forEach(System.out::println);

        // _07
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
