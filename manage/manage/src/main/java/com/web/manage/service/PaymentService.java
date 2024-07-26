package com.web.manage.service;

import com.web.manage.model.Order;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public boolean processPayment(Order order) {
        // Logic xử lý thanh toán
        // Trả về true nếu thanh toán thành công, ngược lại trả về false
        return true; // Ví dụ
    }
}
