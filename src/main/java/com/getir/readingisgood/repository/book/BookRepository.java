package com.getir.readingisgood.repository.book;

import com.getir.readingisgood.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findByTitle(final String title);
    Book findByIdAndStockGreaterThanEqual(Integer id, int quantity);
}
