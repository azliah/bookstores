package com.bookstore.bookstores.repository;

import com.bookstore.bookstores.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
