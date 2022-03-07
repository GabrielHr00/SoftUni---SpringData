package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookSummary;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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

    List<Book> findAllByAuthorLastNameStartingWith(String startsWith);

    @Query("SELECT COUNT(b) FROM Book b WHERE length(b.title) > :lengthTitle")
    int countBooksWithTitleLongerThan(int lengthTitle);

    @Query("SELECT b.title as title, b.editionType as editionType, b.ageRestriction as ageRestriction, b.price as price FROM Book b WHERE b.title = :title")
    BookSummary findSummaryForTitle(String title);

    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.copies = b.copies + :amount WHERE b.releaseDate > :after")
    int addCopiesToBooksAfter(LocalDate after, int amount);

    @Modifying
    @Transactional
    int deleteByCopiesLessThan(int copy);


    @Query(value = "CALL totalBooksByAuthor(:firstName, :lastName);", nativeQuery = true)
    long storedBooksForAuthor(@Param("firstName") String firstName, @Param("lastName")String lastName);

    List<Book> findAllByTitleContaining(String word);
}
