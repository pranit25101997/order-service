package com.prnt.orderservice.controller;

import com.prnt.orderservice.dto.OrderResponseDto;
import com.prnt.orderservice.entity.Article;
import com.prnt.orderservice.entity.Order;
import com.prnt.orderservice.service.ArticleService;
import com.prnt.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;

public class OrderControllerTest {

    private OrderController orderController;
    @Mock
    private OrderService orderService;
    @Mock
    private ArticleService articleService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderController = new OrderController(orderService, articleService);
    }

    @Test
    public void getOrderByOrderIdTest() {
        int articleNumber = 123;
        int orderAmount = 2;
        int logisticType = 1;

        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .articleNumber(1)
                .subTotalCost(BigDecimal.valueOf(20.0))
                .shipmentCost(BigDecimal.valueOf(5.0))
                .totalCost(BigDecimal.valueOf(25.0))
                .build();

        when(orderService.getOrder(articleNumber, orderAmount, logisticType)).thenReturn(orderResponseDto);

        ResponseEntity<OrderResponseDto> response = orderController.getOrderByOrderId(articleNumber, orderAmount, logisticType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderResponseDto, response.getBody());
    }
    @Test
    public void saveShipmentTest() {
        Order order = new Order();
        order.setId(1);
        order.setLogisticType(1);
        order.setShipmentCost(BigDecimal.valueOf(5.0));
        order.setOrderAmount(2);
        
        Article article = new Article();
        article.setId(1);
        article.setName("Test Article");
        article.setPrice(BigDecimal.valueOf(10.0));
        article.setInStock(10);
        order.setArticle(article); 

        when(articleService.saveArticle(any(Article.class))).thenReturn(article);
        when(orderService.saveOrder(any(Order.class))).thenReturn(order);
        ResponseEntity<Order> response = orderController.saveShipment(order);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }
}

