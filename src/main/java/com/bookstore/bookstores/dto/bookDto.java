package com.bookstore.bookstores.dto;

import com.bookstore.bookstores.model.Category;
import lombok.Data;

@Data
public class bookDto {
    private int bookid;
    private String bookname;
    private String author;
    private int price;
    private String catename;


}

