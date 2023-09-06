package com.bookstore.bookstores.service;

import com.bookstore.bookstores.controller.BookController;
import com.bookstore.bookstores.dto.bookDto;
import com.bookstore.bookstores.model.Book;
import com.bookstore.bookstores.model.Category;
import com.bookstore.bookstores.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private BookService bookService;

    @Test
    public void testGetBookById(){
       // Book mockBook = new Book(99, "Test Book");
        Category mockCategory = mock(Category.class);
        Book mockBook = new Book(99, "Test Book", "Aina", 23, mockCategory);
        when(bookRepository.findById(99)).thenReturn(Optional.of(mockBook));

        Book result = bookService.getBooksById(99);

        assertEquals("Test Book", result.getBookname());
        assertEquals("Test Book", result.getBookname());

    }

    @Test
    public void testGetBookName() {
        Category mockCategory = mock(Category.class);
        Book mockBook = new Book(99, "Test Book", "Aina", 23, mockCategory);

        List<Book> mockBookList = Collections.singletonList(mockBook);

        when(bookRepository.findByBooknameContainingIgnoreCase("Test Book")).thenReturn(mockBookList);

        List<Book> result = bookService.getBooksByName("Test");
        assertEquals(0, result.size());
    }


    @Test
    public void testGetBookWithCategory(){
        Category mockCategory = new Category(1, "Motivation");
        Book mockBook = new Book(99, "test", "Caroline", 24, mockCategory);
        Book mockBook2 = new Book(29, "test2", "Caroline", 24, mockCategory);

        List<Book> mockBookList = new ArrayList<>();
        mockBookList.add(mockBook);
        mockBookList.add(mockBook2);

        when(bookService.getAllBookswithCate()).thenReturn(mockBookList);

        List<Book> result = bookService.getAllBookswithCate();
        assertEquals(2, result.size());
        assertEquals(mockBook, result.get(0));
        assertEquals(mockBook2, result.get(1));
    }

    @Test
    public void testBookById(){
        Category mockCategory = new Category(1, "Horror");
        Book mockBook = new Book(89, "Midnight", "Christy", 24, mockCategory);
        Book mockBook2 = new Book(29, "test2", "Caroline", 24, mockCategory);

        List<Book> mockBookList = new ArrayList<>();
        mockBookList.add(mockBook);
        mockBookList.add(mockBook2);

        when(bookRepository.findById(89)).thenReturn(Optional.of(mockBook));
        when(bookRepository.findById(29)).thenReturn(Optional.of(mockBook2));

        Book result = bookService.getBooksById(89);
        assertEquals("Christy", result.getAuthor());
        Book result2 = bookService.getBooksById(29);
        assertEquals("test2", result2.getBookname());

    }


    @Test
    public void testAddBook(){
        Category mockCategory = new Category(1, "Horror");
        Book mockBook = new Book(89, "Midnight", "Christy", 24, mockCategory);

        bookService.saveOrUpdate(mockBook);

        verify(bookRepository).save(mockBook);
    }


}

