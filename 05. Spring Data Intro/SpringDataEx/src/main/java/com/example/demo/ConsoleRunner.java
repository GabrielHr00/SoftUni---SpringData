package com.example.demo;

import com.example.demo.entities.Author;
import com.example.demo.entities.Book;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.repositories.BookRepository;
import com.example.demo.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private SeedService seedService;
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;


    @Autowired
    public ConsoleRunner(SeedService seedService, BookRepository bookRepository, AuthorRepository authorRepository){
        this.seedService = seedService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.seedService.seedAuthors();
//        this.seedService.seedCategories();
//        this.seedService.seedBooks();
//        this.seedService.seedAll();
        this.getAfter2000();
        this.getAllAuthorsWithAtLeastOneBookAndReleaseDateBefore1990();
        this.getAllAuthorsOrderedByCount();
    }

    private void getAllAuthorsOrderedByCount() {
      authorRepository.findAll().stream()
                .sorted((a,b) -> Integer.compare(a.getBooks().size(), b.getBooks().size()))
                .forEach(e -> System.out.println(e.getFirstName() + " " + e.getLastName() + " with " + e.getBooks().size() + " books."));
    }

    private void getAllAuthorsWithAtLeastOneBookAndReleaseDateBefore1990() {
        List<Author> collect = authorRepository.findDistinctByBooksReleaseDateBefore(LocalDate.of(1990,1,1));
        collect.forEach(e -> System.out.println(e.getFirstName() + " " + e.getLastName()));
    }

    private void getAfter2000() {
        List<Book> books = bookRepository.findAll().stream().filter(e -> e.getReleaseDate().getYear() >= 2000).collect(Collectors.toList());

        books.stream().forEach(e -> System.out.println(e.getReleaseDate() + " -> " + e.getTitle()));
        System.out.println(books.size());
    }
}
