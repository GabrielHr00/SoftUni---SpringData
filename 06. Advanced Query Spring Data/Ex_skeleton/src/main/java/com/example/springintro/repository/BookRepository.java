package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    //List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestr);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType ed, int copies);

    @Query("SELECT b FROM Book b WHERE b.price < :firstBook OR b.price > :secondBook")
    List<Book> findAllByPriceLessThanAndPriceGreaterThan(BigDecimal firstBook, BigDecimal secondBook);

    @Query("SELECT b FROM Book b WHERE YEAR(release_date) is not :releaseYear")
    List<Book> findByReleaseDateYearNot(int releaseYear);

    List<Book> findAllByReleaseDateBefore(LocalDate date);
}
