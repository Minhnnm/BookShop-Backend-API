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
    @JsonProperty("id")
    private UUID order_id;
    @JsonProperty("total_amount")
    private String merchandise_subtotal;
    private String total_quantity;
    private String created_on;
    private String shipped_on;
    private int user_id;
    private String address;
    private String receiver_name;
    private String receiver_phone;
    private int shipping_id;
    private String shipping_type;
    private int shipping_cost;
    private String order_status;
    private String order_total;
    private int is_rating;
}
