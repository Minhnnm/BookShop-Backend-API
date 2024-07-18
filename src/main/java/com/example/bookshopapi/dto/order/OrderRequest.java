package com.example.bookshopapi.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private List<UUID> idCartItem;
    private int shippingId;
    private int paymentId;
    private int receiverId;
}
