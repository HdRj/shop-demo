package com.example.shop.model.dto.request;

public record OrderItemDTO(
        Long articleId,
        Integer count
) {
}
