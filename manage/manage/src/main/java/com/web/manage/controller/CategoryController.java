package com.web.manage.controller;

import com.web.manage.model.Category;
import com.web.manage.model.Product;
import com.web.manage.service.CategoryService;
import com.web.manage.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories/categories";
    }

    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "categories/add-category";
    }

    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "categories/add-category";
        }
        categoryService.saveCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/edit")
    public String showEditCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories/view-edit";
    }

    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));
        model.addAttribute("category", category);
        return "categories/edit-category";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable("id") Long id, @Valid @ModelAttribute("category") Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "categories/edit-category";
        }
        category.setId(id);
        categoryService.saveCategory(category);
        return "redirect:/categories/edit";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories/edit";
    }

    @GetMapping("/{id}/products")
    public String showProductsByCategory(@PathVariable("id") Long categoryId, Model model) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        model.addAttribute("products", products);
        return "categories/products-from-category"; // Tên của trang hiển thị sản phẩm
    }
}
