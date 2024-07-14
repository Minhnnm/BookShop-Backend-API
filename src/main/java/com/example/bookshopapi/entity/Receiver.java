package com.example.bookshopapi.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "receiver")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Receiver {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "receiver_name")
    private String receiverName;
    @Column(name = "receiver_phone", columnDefinition = "VARCHAR(10)")
    private String receiverPhone;
    @Column(name = "address")
    private String address;
    @Column(name = "is_default")
    private int isDefault;
    @Column(name = "is_selected")
    private int isSelected;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
