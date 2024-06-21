package com.example.bookshopapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "payment_method", columnDefinition = "VARCHAR(100)")
    private String paymentMethod;

//    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<Order> orders;
}
