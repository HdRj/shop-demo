package com.example.shop.model.entity;


import com.example.shop.enums.OrderStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = PRIVATE)
public class Order {
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(updatable = false)
    Long id;

    @Version
    @Column(updatable = true)
    Long version;

    @Column(name="date_order")
    @Temporal(TemporalType.TIMESTAMP)
    Calendar date;

    @Column(name="total_price")
    BigDecimal totalPrice;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @Column(name="address")
    String address;

    @ManyToOne
    @JoinColumn(name="shop_id")
    Shop shop;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    Customer customer;

    @OneToMany(mappedBy = "order", cascade=CascadeType.ALL)
    List<OrderItem> orderItemList;

    public Order(Calendar date, OrderStatus status, String address, Shop shop, Customer customer) {
        this.date = date;
        this.status = status;
        this.address = address;
        this.shop = shop;
        this.customer = customer;
        this.orderItemList=new ArrayList<>();
    }

    public void addOrderItem(OrderItem orderItem){
        this.orderItemList.add(orderItem);
    }

    public void setTotalPrice(BigDecimal totalPrice){
        this.totalPrice = totalPrice;
    }
}
