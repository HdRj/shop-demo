package com.example.shop.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "customer")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = PRIVATE)
public class Customer {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(updatable = false)
    Long id;

    @Version
    @Column(updatable = true)
    Long version;

    @Column(name="email", unique = true)
    String email;

    @Column(name="phone", unique = true)
    String phone;

    @Column(name = "customer_name")
    String name;

    @OneToMany(mappedBy = "customer", cascade=CascadeType.ALL)
    List<Order> orderList;

    public Customer(String email, String phone, String name) {
        this.email = email;
        this.phone = phone;
        this.name = name;
    }

    public void addOrder(Order order){
        this.orderList.add(order);
    }
}
