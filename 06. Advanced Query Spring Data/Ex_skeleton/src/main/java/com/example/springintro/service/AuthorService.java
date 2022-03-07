package com.example.springintro.service;

import com.example.springintro.model.entity.Author;
import com.example.springintro.model.entity.AuthorNamesTotalCount;
import com.example.springintro.model.entity.Book;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> getAllAuthorsOrderByCountOfTheirBooks();

    List<String> findAllByFirstNameEndingWith(String endsWith);

    List<AuthorNamesTotalCount> findCountByAuthorName();

}
