package com.example.bookshopapi.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private int item_id;
    private String name;
    private String price;
    private int quantity;
    private int product_id;
    private String sub_total;
    private String added_on;
    private String discounted_price;
}
