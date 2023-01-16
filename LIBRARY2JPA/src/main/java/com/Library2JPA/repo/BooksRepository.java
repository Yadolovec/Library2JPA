package com.Library2JPA.repo;

import com.Library2JPA.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByBookNameStartingWith(String startingWith);

}