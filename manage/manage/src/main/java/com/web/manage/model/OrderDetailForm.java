package com.web.manage.model;

public class OrderDetailForm {
    private String productName;
    private int quantity;
    private double price;

    // Các getter và setter
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
