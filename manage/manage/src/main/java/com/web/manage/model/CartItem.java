package com.web.manage.model;

public class CartItem {
    private Product product;
    private int quantity;

    // Constructor mặc định
    public CartItem() {
    }

    // Constructor với tham số
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters và setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
