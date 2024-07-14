package com.example.bookshopapi.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "uuid-char")
    private UUID id;

    @NotNull
    @Column(name = "name", columnDefinition = "VARCHAR(100)")
    private String name;

    @NotNull
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull
    @Column(name = "discounted_price", precision = 10, scale = 2)
    private BigDecimal discountedPrice;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "quantity_sold")
    private int quantitySold;

    @Column(name = "image", columnDefinition = "VARCHAR(255)")
    private String image;

    @Column(name = "thumbnail", columnDefinition = "VARCHAR(255)")
    private String thumbnail;

    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "updated_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "banner_url")
    private String banner;

    @Column(name = "status", nullable = false, columnDefinition = "INT DEFAULT 1")
    private int status = 1;

    //    List<CartItem> cartItems;
//    @Column(name = "purchase_status")
//    private int purchaseStatus;
//    @Column(name = "display")
//    private int display;
//    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<Rating> ratings;
//    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<OrderDetail> orderDetails;
//    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<CartItem> cartItems;
//    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<Wishlistitem> wishlistitems;
}
