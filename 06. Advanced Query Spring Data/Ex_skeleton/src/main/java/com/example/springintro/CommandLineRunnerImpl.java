package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookSummary;
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
import java.util.Scanner;

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
        String word = "sK";
        this.bookService.findAllByTitleContaining(word).stream().forEach(System.out::println);


        // _08
        // this.bookService.findAllByAuthorLastNameStartingWith("Ric").forEach(e -> System.out.println(e.getTitle() + " from " + e.getAuthor().getFirstName() + " " + e.getAuthor().getLastName()));


        // _09
//        int count = this.bookService.countBooksWithTitleLongerThan(12);
//        System.out.println(String.format("There are %d books with longer title than %d symbols", count, 12));

        // _10



        // _11
//        BookSummary things_fall_apart = this.bookService.getInformationForTitle("Things Fall Apart");
//        System.out.println(things_fall_apart.getTitle() + " " + things_fall_apart.getEditionType() + " " + things_fall_apart.getAgeRestriction() + " " + things_fall_apart.getPrice());

        // _12
//        Scanner sc = new Scanner(System.in);
//        String date = sc.nextLine();
//        int amount = Integer.parseInt(sc.nextLine());
//        int booksUpdated = this.bookService.addCopiesToBooksAfter(date, amount);
//        System.out.printf("%d books are released after %s, so total of %d book copies were added\n",booksUpdated, date, amount * booksUpdated);

        // _13
//        Scanner sc = new Scanner(System.in);
//        int amount = Integer.parseInt(sc.nextLine());
//        this.bookService.deleteWithCopiesLessThan(amount);

        // _14
//        long books = this.bookService.storedBooksForAuthor("Amanda", "Rice");
//        System.out.printf("%s %s has written %d books\n","Amanda", "Rice", books);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
