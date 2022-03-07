package com.example.springintro.service;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllByAgeRestriction(String ageRestr);

    List<String> findAllByEditionTypeAndCopiesLessThan(EditionType ed, int copies);

    List<Book> findAllByPriceLessThanAndPriceGreaterThan(BigDecimal first, BigDecimal second);

    List<String> findByReleaseDateYearNot(int releaseYear);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

}
