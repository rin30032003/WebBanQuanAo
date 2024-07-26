package com.web.manage.controller;

import com.web.manage.model.Order;
import com.web.manage.model.OrderDetail;
import com.web.manage.model.OrderForm;
import com.web.manage.service.CartService;
import com.web.manage.service.OrderService;
import com.web.manage.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService; // Tiêm PaymentService vào đây

    @GetMapping
    public String showCheckoutPage(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("totalPrice", cartService.calculateTotalPrice());
        model.addAttribute("orderForm", new OrderForm());
        return "cart/checkout"; // Tên view là checkout.html
    }

    @PostMapping
    public String processCheckout(@ModelAttribute OrderForm orderForm, @RequestParam("totalPrice") double totalPrice) {
        Order order = new Order();
        // Chuyển đổi từ orderForm sang Order entity
        order.setCustomerName(orderForm.getCustomerName());
        order.setEmail(orderForm.getEmail());
        order.setShippingAddress(orderForm.getShippingAddress());
        order.setPhoneNumber(orderForm.getPhoneNumber());
        order.setNote(orderForm.getNote());
        order.setInvoice(orderForm.isInvoice());
        order.setTotalPrice(totalPrice); // Sử dụng giá trị tổng tiền từ form

        // Lấy thông tin giỏ hàng và chuyển đổi thành OrderDetail
        List<OrderDetail> orderDetails = cartService.getCartItems().stream().map(item -> {
            OrderDetail detail = new OrderDetail();
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getProduct().getPrice());
            detail.setOrder(order); // Thiết lập liên kết giữa Order và OrderDetail
            return detail;
        }).collect(Collectors.toList());

        order.setOrderDetails(orderDetails);

        // Xử lý đơn hàng
        orderService.processOrder(order);

        // Xử lý thanh toán
        paymentService.processPayment(order);

        return "redirect:/order/confirmation";
    }
}
