package com.web.manage.service;

import com.web.manage.model.CartItem;
import com.web.manage.model.Product;
import com.web.manage.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final List<CartItem> cartItems = new ArrayList<CartItem>();

    @Autowired
    private ProductRepository productRepository;

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void addToCart(Long productId, int quantity) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            CartItem cartItem = cartItems.stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElse(null);
            if (cartItem == null) {
                cartItem = new CartItem(product, quantity);
                cartItems.add(cartItem);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            }
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    public void removeFromCart(Long productId) {
        cartItems.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public void clearCart() {
        cartItems.clear();
    }

    public double getTotalPrice() {
        return cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
    public double calculateTotalPrice() {
        return cartItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
    }

}
