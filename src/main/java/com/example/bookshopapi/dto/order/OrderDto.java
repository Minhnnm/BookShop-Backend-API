package com.example.bookshopapi.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private UUID order_id;
    private String receiver_name;
    private String receiver_phone;
    private String address;
    private String created_on;
    private String shipped_on;
    private String shipping_type;
    private BigDecimal shipping_cost;
    private String total_quantity;
    private String order_amount;
    private String merchandise_subtotal;
    private String order_status;
    private int is_rating;
}
