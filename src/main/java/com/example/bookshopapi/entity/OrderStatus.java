package com.example.bookshopapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "order_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "status", columnDefinition = "VARCHAR(100)")
    private String status;

//    @OneToMany(mappedBy = "orderStatus", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<Order> orders;
}
