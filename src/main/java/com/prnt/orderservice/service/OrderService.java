package com.prnt.orderservice.service;

import com.prnt.orderservice.dto.OrderResponseDto;
import com.prnt.orderservice.entity.Article;
import com.prnt.orderservice.entity.Order;
import com.prnt.orderservice.repo.ArticleRepository;
import com.prnt.orderservice.repo.OrderRepository;
import com.prnt.orderservice.utils.OrderGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    final OrderRepository orderRepository;
    final ArticleService articleService;
    final ArticleRepository articleRepository;
    /**
     * To get order details by providing parameters as articleNumber, orderAmount and logisticType
     * calculates subTotal, ShipmentCost and totalCost 
     * @return returns ResponseDto class fields
     * **/
    public OrderResponseDto getOrder(int articleNumber, int orderAmount, int logisticType) {
        Optional<Order> orderOpt = orderRepository.findOrderByArticleNumber(articleNumber, orderAmount, logisticType);
        if (!orderOpt.isPresent()) {
            throw new EntityNotFoundException("Cannot find order");
        }
        Order order = orderOpt.get();
        BigDecimal totalCost = order.getArticle().getPrice()
                .multiply(BigDecimal.valueOf(order.getOrderAmount()))
                .add(order.getShipmentCost());
        BigDecimal subTotalCost = order.getArticle().getPrice()
                .multiply(BigDecimal.valueOf(order.getOrderAmount()));
        return OrderResponseDto.builder()
                .articleNumber(order.getArticle().getId())
                .subTotalCost(subTotalCost)
                .shipmentCost(order.getShipmentCost())
                .totalCost(totalCost)
                .build();
    }
    /**
     * To generate Orders data, consisting of articles
     * @return persists data to the order table
     * **/
    public List<Order> createOrders () {
        List<Article> articles = articleService.createArticles();
        List<Order> orders = OrderGenerator.generateOrders(articles);
        try {
            return orderRepository.saveAll(orders);
        } catch (Exception e) {
            throw e;
        }
    }
    /**
     * To send request via Post, if user want to Post the data
     * @return save the data to the tables with provided details
     * **/
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }   
    
}
