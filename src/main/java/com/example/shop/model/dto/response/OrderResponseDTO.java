package com.example.shop.model.dto.response;

import com.example.shop.enums.OrderStatus;

public record OrderResponseDTO (
        String date,
        String totalPrice,
        Integer count,
        String address,
        OrderStatus status
){

}
