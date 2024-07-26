package com.web.manage.controller;

import com.web.manage.model.OrderForm;
import com.web.manage.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @GetMapping("/checkout")
    public String checkout(Model model) {
        logger.debug("Loading checkout page");
        model.addAttribute("orderForm", new OrderForm());
        return "cart/checkout"; // Tên view là checkout.html
    }
    @PostMapping("/checkout")
    public String submitCheckout(@ModelAttribute OrderForm orderForm) {
        logger.debug("Processing checkout");
        // Xử lý logic đặt hàng ở đây
        return "redirect:/order/confirmation";
    }

    @PostMapping("/submit")
    public String submitOrder(@ModelAttribute OrderForm orderForm) {
        try {
            logger.info("Received order form: {}", orderForm);

            // Kiểm tra tính hợp lệ của đơn hàng
            validateOrderForm(orderForm);

            // Xử lý logic đặt hàng
            orderService.submitOrder(orderForm);
            logger.info("Order submitted successfully for customer: {}", orderForm.getCustomerName());
        } catch (IllegalArgumentException e) {
            logger.error("Validation error: {}", e.getMessage());
            return "error"; // Tên view là error.html
        } catch (Exception e) {
            logger.error("Error submitting order", e);
            return "error"; // Tên view là error.html
        }
        return "redirect:/order/confirmation";
    }

    @GetMapping("/confirmation")
    public String orderConfirmation() {
        logger.debug("Loading order confirmation page");
        return "order_confirmation"; // Tên view là order_confirmation.html
    }

    private void validateOrderForm(OrderForm orderForm) {
        if (orderForm.getCustomerName() == null || orderForm.getCustomerName().isEmpty()) {
            throw new IllegalArgumentException("Tên khách hàng không được để trống");
        }
        if (orderForm.getShippingAddress() == null || orderForm.getShippingAddress().isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ giao hàng không được để trống");
        }
        if (orderForm.getPhoneNumber() == null || orderForm.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Số điện thoại không được để trống");
        }
        if (orderForm.getEmail() == null || orderForm.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống");
        }
        if (orderForm.getPaymentMethod() == null || orderForm.getPaymentMethod().isEmpty()) {
            throw new IllegalArgumentException("Phương thức thanh toán không được để trống");
        }
        if (orderForm.getOrderDetails() == null || orderForm.getOrderDetails().isEmpty()) {
            throw new IllegalArgumentException("Chi tiết đơn hàng không được để trống");
        }
    }
}
