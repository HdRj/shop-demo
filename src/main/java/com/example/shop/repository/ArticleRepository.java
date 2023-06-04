package com.example.shop.repository;

import com.example.shop.model.entity.Article;
import com.example.shop.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByShop(Shop shop);
    List<Article> findByIdIn(List<Long> ids);
}
