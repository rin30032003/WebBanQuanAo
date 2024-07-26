package com.web.manage.repository;

import com.web.manage.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    List<Product> findByKeyword(String keyword);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByNameContainingIgnoreCase(String keyword);
}
