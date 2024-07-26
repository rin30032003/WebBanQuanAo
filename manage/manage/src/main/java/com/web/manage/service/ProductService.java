package com.web.manage.service;

import com.web.manage.model.Category;
import com.web.manage.model.Product;
import com.web.manage.repository.CategoryRepository;
import com.web.manage.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());
            product.setCategory(updatedProduct.getCategory());
            product.setDescription(updatedProduct.getDescription());
            product.setImageUrl(updatedProduct.getImageUrl());
            productRepository.save(product);
        }
    }
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
    // Thêm phương thức tìm kiếm sản phẩm
    public List<Product> searchProducts(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return productRepository.findAll();
        }
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }
}
