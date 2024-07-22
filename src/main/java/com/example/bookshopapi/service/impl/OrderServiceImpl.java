package com.example.bookshopapi.service.impl;

import com.example.bookshopapi.dto.MessageDto;
import com.example.bookshopapi.dto.order.OrderDto;
import com.example.bookshopapi.dto.order.OrderRequest;
import com.example.bookshopapi.entity.*;
import com.example.bookshopapi.exception.BadRequestException;
import com.example.bookshopapi.exception.NotFoundException;
import com.example.bookshopapi.mapper.OrderMapper;
import com.example.bookshopapi.repository.*;
import com.example.bookshopapi.service.EmailService;
import com.example.bookshopapi.service.OrderDetailService;
import com.example.bookshopapi.service.OrderService;
import com.example.bookshopapi.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CurrentUserUtil currentUserUtil;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;
    @Autowired
    private ReceiverRepository receiverRepository;
    @Autowired
    private ShippingRepository shippingRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderMapper orderMapper;
    private final Executor asyncExecutor;

    @Override
    public List<OrderDto> getOrderByUser() {
        User currenUser = currentUserUtil.getCurrentUser();
        List<Order> orders = orderRepository.findByUserId(currenUser.getId());
        return orderMapper.toDtos(orders);
    }

    @Override
    public MessageDto createOrder(OrderRequest orderRequest) {
        User user = currentUserUtil.getCurrentUser();
        if (orderRequest.getIdCartItem().size() == 0) {
            throw new BadRequestException("Please select product to pay!");
        }
        List<CartItem> cartItems = new ArrayList<>();
        BigDecimal subTotal = new BigDecimal("0");
        for (UUID idCartItem : orderRequest.getIdCartItem()) {
            Optional<CartItem> cartItem = cartItemRepository.findById(idCartItem);
            if (cartItem.isPresent()) {
                subTotal = subTotal.add(cartItem.get().getProduct().getDiscountedPrice().
                        multiply(new BigDecimal(cartItem.get().getQuantity())));
                cartItems.add(cartItem.get());
                Product product = productRepository.findById(cartItem.get().getProduct().getId()).orElseThrow(
                        () -> new NotFoundException("Can not find product with id: " + cartItem.get().getProduct().getId())
                );
                product.setQuantitySold(product.getQuantitySold() + cartItem.get().getQuantity());
                productRepository.save(product);
            }
        }
        Receiver receiver = receiverRepository.findById(orderRequest.getReceiverId()).orElseThrow(
                () -> new NotFoundException("Can not find receiver with id: " + orderRequest.getReceiverId())
        );
        Shipping shipping = shippingRepository.findById(orderRequest.getShippingId()).orElseThrow(
                () -> new NotFoundException("Can not find shipping method with id: " + orderRequest.getShippingId())
        );
        Payment payment = paymentRepository.findById(orderRequest.getPaymentId()).orElseThrow(
                () -> new NotFoundException("Can not find payment method with id: " + orderRequest.getPaymentId())
        );
        Order order = new Order();
        OrderStatus orderStatus = orderStatusRepository.getById(1);
        order.setCreateOn(LocalDateTime.now());
        order.setAddress(receiver.getAddress());
        order.setReceiverName(receiver.getReceiverName());
        order.setReceiverPhone(receiver.getReceiverPhone());
        order.setShipping(shipping);
        order.setTotalAmount(subTotal);
        order.setOrderStatus(orderStatus);
        order.setUser(user);
        order.setIsRating(0);
        order.setPayment(payment);
        orderRepository.save(order);
        orderDetailService.saveOrderDetail(order, cartItems);
        cartItemRepository.deleteAll(cartItems);
        CompletableFuture.runAsync(() -> {
            emailService.sendMailOrder(receiver, user, order, cartItems);
        }, asyncExecutor);
        return new MessageDto("Đặt hàng thành công");
    }
}
