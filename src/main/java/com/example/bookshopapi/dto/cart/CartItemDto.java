package com.example.bookshopapi.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private UUID item_id;
    private String name;
    private int quantity;
    private int quantity_sold;
    private int quantity_in_cart;
    private String price;
    private String discounted_price;
    private UUID product_id;
    private String sub_total;
    private String added_on;
    private String update_on;
    private String image;
}
