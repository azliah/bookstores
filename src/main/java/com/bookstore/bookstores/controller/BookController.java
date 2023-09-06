package com.bookstore.bookstores.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bookstore.bookstores.dto.BookSaveDTO;
import com.bookstore.bookstores.model.Book;
import com.bookstore.bookstores.model.Category;
import com.bookstore.bookstores.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bookstore.bookstores.dto.bookDto;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
//@CrossOrigin(origins = "http://localhost:8080")
public class BookController {

    @Autowired
    BookService booksService;

    @Autowired
    private ModelMapper modelMapper;


    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    //get all books stored in the table -- version 1 without Category
//    @GetMapping()
//    private List<bookDto> getAllBooks()
//    {
//        return booksService.getAllBookswithCate().stream().map(book -> modelMapper.map(book, bookDto.class))
//                .collect(Collectors.toList());
//    }

    @GetMapping("/details")
    public List<bookDto> getAllBook() {
        List<Book> books = booksService.getAllBookswithCate();

        return books.stream().map(book -> {
            bookDto bookDto = modelMapper.map(book, bookDto.class);
            bookDto.setCatename(book.getCategory().getCatename());
            return bookDto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/category")
    private List<Category> getAllCategory() {
        return booksService.getAllCategory();
    }


    //retrieve a book using ID -- version 1 without Category
//    @GetMapping("/retrieve/{id}")
//    public ResponseEntity<bookDto> getBookById(@PathVariable(name = "id") int id) {
//        Book book = booksService.getBooksById(id);
//
//        // convert entity to DTO
//        bookDto bookResponse = modelMapper.map(book, bookDto.class);
//        bookResponse.setCatename(book.getCategory().getCatename());
//
//        return ResponseEntity.ok().body(bookResponse);
//    }

    @GetMapping("/retrieveWithCategory/{id}")
    public ResponseEntity<bookDto> retrieveWithCategory(@PathVariable(name = "id") int id) {
        Book book = booksService.getBooksById(id);

        // convert entity to DTO
        bookDto bookResponse = modelMapper.map(book, bookDto.class);
        bookResponse.setCatename(book.getCategory().getCatename());

        return ResponseEntity.ok().body(bookResponse);
    }

    @GetMapping("/get/{bookname}")
    public ResponseEntity<List<bookDto>> getBooksByName(@PathVariable String bookname) {
        List<Book> books = booksService.getBooksByName(bookname);

        List<bookDto> bookResponses = books.stream()
                .map(book -> modelMapper.map(book, bookDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(bookResponses);
    }

    //delete book using their ID
    @DeleteMapping("/delete/{id}")
    void deleteBook(@PathVariable int id) {
        booksService.delete(id);
    }


    //add new book into table -- version 1 without Category
//    @PostMapping("/add")
//    private ResponseEntity<bookDto> addBook (@RequestBody bookDto books) throws Exception {
//
//        Book bookRequest = modelMapper.map(books, Book.class);
//
//        Book book = new Book();
//        if(!booksService.existsById(bookRequest.getBookid())){
//            book = booksService.saveOrUpdate(bookRequest);
//        }
//       else{
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }
//
//        bookDto bookResponse = modelMapper.map(book, bookDto.class);
//        return new ResponseEntity<bookDto>(bookResponse, HttpStatus.CREATED);
//    }

    @PostMapping("/addWithCategory")
    private ResponseEntity<bookDto> addBook(@RequestBody BookSaveDTO books) throws Exception {
        logger.debug("Log1: {}", books);

        Book bookRequest = modelMapper.map(books, Book.class);
        bookRequest.setCategory(new Category());
        bookRequest.getCategory().setCateid(books.getCateid());
        logger.debug("Log2: {}", bookRequest);

        Book book = new Book();
        if (!booksService.existsById(bookRequest.getBookid())) {
            book = booksService.saveOrUpdate(bookRequest);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        bookDto bookResponse = modelMapper.map(book, bookDto.class);
        bookResponse.setCatename(book.getCategory().getCatename());

        logger.debug("Log3: {}", bookResponse);
        return new ResponseEntity<bookDto>(HttpStatus.CREATED);
    }

    @PostMapping("/addCate")
    private Category addCategory(@RequestBody Category categories) throws Exception {
        return booksService.createCategory(categories);
    }


    //update book's details -- version 1 without Category
//    @PutMapping("/update")
//    public ResponseEntity<bookDto> updateBook(@RequestBody bookDto bookDto) {
//
//        // convert DTO to Entity
//        Book bookRequest = modelMapper.map(bookDto, Book.class);
//
//        Book book = booksService.saveOrUpdate(bookRequest);
//
//        // entity to DTO
//        bookDto bookResponse = modelMapper.map(book, bookDto.class);
//
//        return ResponseEntity.ok().body(bookResponse);
//    }


    @PutMapping("/updateWithCategory")
    public ResponseEntity<bookDto> updateWithCategory(@RequestBody BookSaveDTO bookDto) {
        logger.debug("bookDto : {} " + bookDto);
        // convert DTO to Entity
        Book bookRequest = modelMapper.map(bookDto, Book.class);

        bookRequest.setCategory(new Category());
        bookRequest.getCategory().setCateid(bookDto.getCateid());
        logger.debug("bookRequest : {} " + bookRequest);
        Book book = booksService.saveOrUpdate(bookRequest);

        // entity to DTO
        bookDto bookResponse = modelMapper.map(book, bookDto.class);
        logger.debug("bookResponse : {} " + bookResponse);
        return ResponseEntity.ok().body(bookResponse);
    }


    //add multiple new books into the table --for testing purpose only
    @PostMapping("/addMultiple")
    private String saveMultipleBook(@RequestBody List<BookSaveDTO> bookSaveDto) throws Exception {
        List<Book> books = new ArrayList<>();

        if (bookSaveDto.size() > 0) {
            for (BookSaveDTO bookDto : bookSaveDto) {
                Book book = modelMapper.map(bookDto, Book.class);
                book.setCategory(new Category());
                book.getCategory().setCateid(bookDto.getCateid());
                books.add(book);
            }
            booksService.saveMultiple(books);
        }

        return "Multiple books successfully added!";
    }


}
