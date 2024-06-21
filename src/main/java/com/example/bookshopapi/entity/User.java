package com.example.bookshopapi.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "name", columnDefinition = "VARCHAR(255)")
    private String name;

    @NotNull
    @Column(name = "email", columnDefinition = "VARCHAR(255)")
    private String email;

    @NotNull
    @Column(name = "password", columnDefinition = "VARCHAR(255)")
    private String password;

    @Column(name = "address", columnDefinition = "VARCHAR(100)")
    private String address;

    @Column(name = "mob_phone", columnDefinition = "VARCHAR(12)")
    private String mobPhone;

    @Column(name = "date_of_birth", columnDefinition = "VARCHAR(30)")
//    @Temporal(TemporalType.DATE) // Xác định kiểu thời gian
    private LocalDate dateOfBirth;

    @Column(name = "gender", columnDefinition = "VARCHAR(10)")
    private String gender;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
//    @OneToMany(mappedBy = "customer")
//    @JsonIgnore
//    private List<Order> orders;
//    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private WishList wishList;
//    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Cart cart;
//    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<Rating> ratings;
}
