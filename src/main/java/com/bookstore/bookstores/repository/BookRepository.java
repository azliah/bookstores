package com.bookstore.bookstores.repository;
import com.bookstore.bookstores.model.Book;
import com.bookstore.bookstores.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByBooknameContainingIgnoreCase(String bookname);


    @Query("SELECT b FROM Book b LEFT JOIN b.category c")
    List<Book> findAllBooks();





}
