package com.example.demo.services;

import com.example.demo.entities.Author;
import com.example.demo.entities.Category;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.repositories.BookRepository;
import com.example.demo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SeedServiceImpl implements SeedService{
    private static final String RESOURCE_PATH = "/Users/mac/Downloads/2022-feb-spring-data-main/SpringDataEx/src/main/resources/files/";
    private static final String AUTHORS_FILE_PATH = RESOURCE_PATH + "authors.txt";
    private static final String BOOKS_FILE_PATH = RESOURCE_PATH + "books.txt";
    private static final String CATEGORIES_FILE_PATH = RESOURCE_PATH + "categories.txt";

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private static BookRepository bookRepository;

    @Override
    public void seedAuthors() throws IOException {
        Files.readAllLines(Path.of(AUTHORS_FILE_PATH))
                .stream()
                .filter(e -> !e.isBlank())
                .map(e -> e.split("\\s+"))
                .map(names -> new Author(names[0], names[1]))
                .forEach(authorRepository::save);
    }

    @Override
    public void seedCategories() throws IOException {
        Files.readAllLines(Path.of(CATEGORIES_FILE_PATH))
                .stream()
                .filter(e -> !e.isBlank())
                .map(Category::new)
                .forEach(categoryRepository::save);
    }

    @Override
    public void seedBooks() {

    }
}
