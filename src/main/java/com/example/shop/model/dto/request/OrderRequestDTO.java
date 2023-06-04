package com.example.shop.model.dto.request;

import java.util.List;

public record OrderRequestDTO(
        String name,
        String email,
        String phone,
        String address,
        List<OrderItemDTO> items
) {
}
