package com.web.manage.service;

import com.web.manage.PaymentProcessingException;
import com.web.manage.model.CartItem;
import com.web.manage.model.Order;
import com.web.manage.model.OrderDetail;
import com.web.manage.model.OrderForm;
import com.web.manage.repository.OrderDetailRepository;
import com.web.manage.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CartService cartService;
    private PaymentService paymentService;
    public void submitOrder(OrderForm orderForm) {
        logger.debug("Submitting order for customer: {}", orderForm.getCustomerName());

        Order order = new Order();
        order.setCustomerName(orderForm.getCustomerName());
        order.setShippingAddress(orderForm.getShippingAddress());
        order.setPhoneNumber(orderForm.getPhoneNumber());
        order.setEmail(orderForm.getEmail());
        order.setNote(orderForm.getNote());
        order.setPaymentMethod(orderForm.getPaymentMethod());
        order.setInvoice(orderForm.isInvoice());

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItem cartItem : cartService.getCartItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetails.add(orderDetail);
        }

        order.setOrderDetails(orderDetails);

        try {
            orderRepository.save(order);
            orderDetailRepository.saveAll(orderDetails);
            cartService.clearCart();
            logger.info("Order for customer {} has been saved successfully", orderForm.getCustomerName());
        } catch (Exception e) {
            logger.error("Error while saving order for customer {}", orderForm.getCustomerName(), e);
            throw e;
        }
    }
    public void processOrder(Order order) {
        // Xác nhận thông tin đơn hàng
        if (order == null || order.getOrderDetails().isEmpty()) {
            throw new IllegalArgumentException("Đơn hàng không hợp lệ");
        }

        // Lưu đơn hàng vào cơ sở dữ liệu
        orderRepository.save(order);

        // Xử lý thanh toán (nếu cần)
        if (order.getTotalPrice() > 0) {
            boolean paymentSuccess = paymentService.processPayment(order);
            if (!paymentSuccess) {
                throw new PaymentProcessingException("Xử lý thanh toán thất bại");
            }
        }

    }

}
