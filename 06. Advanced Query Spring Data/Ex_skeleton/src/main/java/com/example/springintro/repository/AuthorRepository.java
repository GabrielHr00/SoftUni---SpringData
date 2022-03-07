package com.example.springintro.repository;

import com.example.springintro.model.entity.Author;
import com.example.springintro.model.entity.AuthorNamesTotalCount;
import com.example.springintro.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a ORDER BY a.books.size DESC")
    List<Author> findAllByBooksSizeDESC();

    List<Author> findAllByFirstNameEndingWith(String endsWith);

    @Query("SELECT a.firstName as firstName, a.lastName as lastName, SUM(b.copies) as copy FROM Author a JOIN a.books as b GROUP BY b.author ORDER BY copy DESC")
    List<AuthorNamesTotalCount> findCountByAuthorName();

}
