package com.bookstore.bookstores.service;

import com.bookstore.bookstores.controller.BookController;
import com.bookstore.bookstores.model.Book;
import com.bookstore.bookstores.model.Category;
import com.bookstore.bookstores.repository.BookRepository;
import com.bookstore.bookstores.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository booksRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);


    //getting all books by using the method findAll() of CrudRepository
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<Book>();
        booksRepository.findAll().forEach(books1 -> books.add(books1));
        return books;
    }


    public Book getBooksById(int id) {
        logger.debug("Test");
        return booksRepository.findById(id).get();
    }

    public List<Book> getBooksByName(String bookname) {
        return booksRepository.findByBooknameContainingIgnoreCase(bookname);
    }


    //saving multiple records by using the method saveAll() of CrudRepository
    public List<Book> saveMultiple(List<Book> books) {
        booksRepository.saveAll(books);
        return books;
    }

    public Book saveOrUpdate(Book books) {
        booksRepository.save(books);
        return books;
    }

    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public void update(Book books, int bookid) {
        booksRepository.save(books);
    }

    public boolean existsById(int bookid) {
        return booksRepository.existsById(bookid);
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Book> getAllBookswithCate() {
        return booksRepository.findAllBooks();
    }


}
