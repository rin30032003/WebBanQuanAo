package com.web.manage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String shippingAddress;
    private String phoneNumber;
    private String email;
    private String note;
    private String paymentMethod;
    private boolean invoice;
    private double totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public void addOrderDetail(OrderDetail detail) {
        orderDetails.add(detail);
        detail.setOrder(this);
    }

    public void removeOrderDetail(OrderDetail detail) {
        orderDetails.remove(detail);
        detail.setOrder(null);
    }
}
