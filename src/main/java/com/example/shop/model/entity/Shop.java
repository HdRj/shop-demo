package com.example.shop.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "shop")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = PRIVATE)
public class Shop {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(updatable = false)
    Long id;

    @Version
    @Column(updatable = true)
    Long version;

    @Column(name="shop_name")
    String name;

    @OneToMany(mappedBy = "shop",cascade=CascadeType.ALL)
    List<Article> articleList;

    @OneToMany(mappedBy = "shop")
    List<Order> orderList;

    public Shop(String name) {
        this.name = name;
        articleList = new ArrayList<>();
        orderList = new ArrayList<>();
    }

    public void addArticle(Article article){
        this.articleList.add(article);
    }
}
