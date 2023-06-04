package com.example.shop.mapper;

import com.example.shop.model.dto.response.ShopResponseDTO;
import com.example.shop.model.entity.Shop;
import org.springframework.stereotype.Component;

@Component
public class ShopMapper {

    public ShopResponseDTO toResponseDto(Shop entity){
        return new ShopResponseDTO(
                entity.getId(),
                entity.getName()
        );
    }
}
