package com.web.manage.controller;

import com.web.manage.model.Product;
import com.web.manage.service.CategoryService;
import com.web.manage.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showProducts(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("products", productService.searchProducts(keyword));
        } else {
            model.addAttribute("products", productService.getAllProducts());
        }
        return "products/product-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "products/add-product";
    }

    @PostMapping("/add")
    public String addProduct(@Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "products/add-product";
        }

        productService.addProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products/view-products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Invalid product Id:" + id);
        }

        model.addAttribute("product", product.get());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "products/edit-product";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable("id") Long id, @Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "products/edit-product";
        }

        productService.updateProduct(id, product);
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable("id") Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Invalid product Id:" + id);
        }
        model.addAttribute("product", product.get());
        return "products/product-detail";
    }
}
