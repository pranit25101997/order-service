package com.prnt.orderservice.utils;

import com.prnt.orderservice.entity.Article;
import com.prnt.orderservice.entity.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderGenerator {
    public static List<Order> generateOrders(List<Article> articles) {
        List<Order> orders = new ArrayList<>();
        Random random = new Random();

        for (Article article : articles) {
            Order order = new Order();
            order.setLogisticType(random.nextInt(2) + 1); 
            double shipmentCost = random.nextDouble() * 15 + 5; 
            BigDecimal shipmentCostRounded = BigDecimal.valueOf(shipmentCost).setScale(2, RoundingMode.HALF_UP);
            order.setShipmentCost(shipmentCostRounded);
            order.setOrderAmount(random.nextInt(8) + 3); 
            order.setArticle(article);
            orders.add(order);
        }
        return orders;
    }
}
