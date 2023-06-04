package com.example.shop.model.dto.response;

import java.math.BigDecimal;

public record ArticleResponseDTO(
        Long id,
        String name,
        BigDecimal price,
        String imageLink
) {
}
