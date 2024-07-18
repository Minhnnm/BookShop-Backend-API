package com.example.bookshopapi.config.init;

import com.example.bookshopapi.entity.OrderStatus;
import com.example.bookshopapi.entity.Payment;
import com.example.bookshopapi.entity.Role;
import com.example.bookshopapi.entity.Shipping;
import com.example.bookshopapi.repository.OrderStatusRepository;
import com.example.bookshopapi.repository.PaymentRepository;
import com.example.bookshopapi.repository.RoleRepository;
import com.example.bookshopapi.repository.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Configuration
public class DataInitializer {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ShippingRepository shippingRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Bean
    public ApplicationRunner initializer() {
        return args -> {
            if (roleRepository.count() == 0) {
                Role role = new Role(1, "ADMIN");
                Role role2 = new Role(2, "USER");
                roleRepository.saveAll(Arrays.asList(role, role2));
            }
            if (shippingRepository.count() == 0) {
                Shipping shipping = new Shipping(1, "Hỏa tốc", 50000);
                Shipping shipping2 = new Shipping(2, "Nhanh", 30000);
                Shipping shipping3 = new Shipping(3, "Tiết kiệm", 10000);
                shippingRepository.saveAll(Arrays.asList(shipping, shipping2, shipping3));
            }
            if (paymentRepository.count() == 0) {
                Payment payment = new Payment(1, "Thanh toán bằng tiền mặt");
                Payment payment2 = new Payment(2, "Thanh toán bằng Zalopay");
                paymentRepository.saveAll(Arrays.asList(payment, payment2));
            }
            if (orderStatusRepository.count() == 0) {
                OrderStatus preparing = new OrderStatus(1, "Đang chuẩn bị");
                OrderStatus delivering = new OrderStatus(2, "Đang giao hàng");
                OrderStatus delivered = new OrderStatus(3, "Đã giao hàng");
                OrderStatus canceled = new OrderStatus(4, "Đã hủy");
                orderStatusRepository.saveAll(Arrays.asList(preparing, delivering, delivered, canceled));
            }
        };
    }
}
