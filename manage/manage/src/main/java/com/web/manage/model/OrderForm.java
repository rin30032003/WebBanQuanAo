package com.web.manage.model;

import java.util.List;

public class OrderForm {
    private String customerName;
    private String shippingAddress;
    private String phoneNumber;
    private String email;
    private String note;
    private String paymentMethod;
    private boolean invoice;
    private List<OrderDetailForm> orderDetails;
    private double totalPrice;

    // Các getter và setter
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public boolean isInvoice() { return invoice; }
    public void setInvoice(boolean invoice) { this.invoice = invoice; }

    public List<OrderDetailForm> getOrderDetails() { return orderDetails; }
    public void setOrderDetails(List<OrderDetailForm> orderDetails) { this.orderDetails = orderDetails; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}
