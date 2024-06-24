package com.example.bookshopapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "create_on")
    private Date createOn;

    @Column(name = "shipped_on")
    private Date shippedOn;

    @Column(name = "address", columnDefinition = "VARCHAR(255)")
    private String address;

    @Column(name = "receiver_name", columnDefinition = "VARCHAR(100)")
    private String receiverName;

    @Column(name = "receiver_phone", columnDefinition = "VARCHAR(10)")
    private String receiverPhone;

    @Column(name = "isRating")
    private int isRating;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "shipping_id")
    private Shipping shipping;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

//    @OneToMany(mappedBy = "order")
//    @JsonIgnore
//    List<OrderDetail> orderDetails;
}
