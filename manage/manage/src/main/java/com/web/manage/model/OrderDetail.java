package com.web.manage.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private int quantity;
    private double price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Phương thức setOrder phải nhận đối số kiểu Order
    public void setOrder(Order order) {
        this.order = order;
    }
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    public void setProduct(Product product) {
        this.product = product;
    }
}
