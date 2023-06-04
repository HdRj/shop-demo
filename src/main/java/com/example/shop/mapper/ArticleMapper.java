package com.example.shop.mapper;

import com.example.shop.model.dto.response.ArticleResponseDTO;
import com.example.shop.model.entity.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

    public ArticleResponseDTO toResponseDto(Article entity){
        return new ArticleResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getImageLink()
        );
    }
}
