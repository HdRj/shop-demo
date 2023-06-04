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
@Table(name = "order_item")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = PRIVATE)
public class OrderItem {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(updatable = false)
    Long id;

    @Version
    @Column(updatable = true)
    Long version;

    @Column(name = "price")
    BigDecimal price;

    @Column(name = "count_articles")
    Integer count;

    @Column(name = "sum_item")
    BigDecimal sum;

    @ManyToOne
    @JoinColumn(name = "article_id")
    Article article;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    public OrderItem(BigDecimal price, Integer count, BigDecimal sum, Article article, Order order) {
        this.price = price;
        this.count = count;
        this.sum = sum;
        this.article = article;
        this.order = order;
    }
}
