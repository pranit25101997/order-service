package com.prnt.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Entity
@Table(name = "article", schema = "order_db")
@NoArgsConstructor
@AllArgsConstructor

public class Article {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "price")
    BigDecimal price;

    int inStock;

//    @Getter(value = AccessLevel.NONE)
    @OneToOne(
            mappedBy = "article",
            cascade = CascadeType.ALL,
            orphanRemoval = false,
            fetch = FetchType.EAGER
    )
    Order order;
}
