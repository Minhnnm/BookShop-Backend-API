package com.example.bookshopapi.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "wish_list_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishListItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private WishList wishList;
}
