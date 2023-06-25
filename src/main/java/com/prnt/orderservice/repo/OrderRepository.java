package com.prnt.orderservice.repo;

import com.prnt.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT DISTINCT o FROM Order o JOIN o.article a WHERE a.id = :articleNumber AND o.orderAmount = :orderAmount AND o.logisticType = :logisticType")
    Optional<Order> findOrderByArticleNumber(int articleNumber, int orderAmount, int logisticType);
}
