package com.prnt.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
@Entity
@Table(name = "orders", schema = "order_db")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    int id;
    @Column(name = "logistic_type", nullable = false)
    int logisticType;
    @Column(name = "shipment_cost")
    BigDecimal shipmentCost;
    int orderAmount;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id", referencedColumnName = "id", insertable = true, updatable = true)
    Article article;
}
