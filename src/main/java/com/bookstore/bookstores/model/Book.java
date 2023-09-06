package com.bookstore.bookstores.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Book {
    @Id
    @Column
    private int bookid;

    @Column
    private String bookname;

    @Column
    private String author;

    @Column
    private int price;

    @ManyToOne
    @JoinColumn(name = "cateid", referencedColumnName = "cateid")
    private Category category;
    public Category gatCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBookname() {
        return bookname;
    }

    public String getAuthor() {
        return author;
    }

    public int getPrice() {
        return price;
    }


}



