package com.example.shop.model.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "article")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = PRIVATE)
public class Article {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(updatable = false)
    Long id;

    @Version
    @Column(updatable = true)
    Long version;

    @Column(name="article_name")
    String name;

    @Column(name="price")
    BigDecimal price;

    @Column(name="image_link")
    String imageLink;

    @ManyToOne
    @JoinColumn(name="shop_id", nullable=false)
    Shop shop;

    public Article(String name, BigDecimal price, String imageLink, Shop shop) {
        this.name = name;
        this.price = price;
        this.imageLink = imageLink;
        this.shop = shop;
    }
}
