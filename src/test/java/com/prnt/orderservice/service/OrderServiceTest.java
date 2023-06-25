package com.prnt.orderservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.prnt.orderservice.dto.OrderResponseDto;
import com.prnt.orderservice.entity.Article;
import com.prnt.orderservice.entity.Order;
import com.prnt.orderservice.repo.ArticleRepository;
import com.prnt.orderservice.repo.OrderRepository;
import jakarta.persistence.EntityNotFoundException;

public class OrderServiceTest {

    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ArticleService articleService;
    @Mock
    private ArticleRepository articleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository, articleService, articleRepository);
    }

    @Test
    public void getOrder_Exists() {
        int articleNumber = 123;
        int orderAmount = 2;
        int logisticType = 1;
        Article article = new Article();
        article.setId(1);
        article.setPrice(BigDecimal.valueOf(10.0));

        Order order = new Order();
        order.setId(1);
        order.setArticle(article);
        order.setOrderAmount(orderAmount);
        order.setShipmentCost(BigDecimal.valueOf(5.0));

        when(orderRepository.findOrderByArticleNumber(articleNumber, orderAmount, logisticType)).thenReturn(Optional.of(order));

        OrderResponseDto expectedResponse = OrderResponseDto.builder()
                .articleNumber(article.getId())
                .subTotalCost(article.getPrice().multiply(BigDecimal.valueOf(orderAmount)))
                .shipmentCost(order.getShipmentCost())
                .totalCost(article.getPrice().multiply(BigDecimal.valueOf(orderAmount)).add(order.getShipmentCost()))
                .build();
        
        OrderResponseDto response = orderService.getOrder(articleNumber, orderAmount, logisticType);     
        assertEquals(expectedResponse.getArticleNumber(), response.getArticleNumber());
   }
    @Test
    public void saveOrderTest() {
   
        Order order = new Order();
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);
        Order savedOrder = orderService.saveOrder(order);
        assertEquals(order, savedOrder);

    }
    @Test
    public void getOrder_NotExists() {
        Mockito.when(orderRepository.findOrderByArticleNumber(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(Optional.empty());
 
        assertThrows(EntityNotFoundException.class, () -> {
            orderService.getOrder(19, 7, 1);
        });
    }

    
}

    
