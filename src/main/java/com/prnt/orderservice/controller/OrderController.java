package com.prnt.orderservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.prnt.orderservice.dto.OrderResponseDto;
import com.prnt.orderservice.entity.Article;
import com.prnt.orderservice.entity.Order;
import com.prnt.orderservice.service.ArticleService;
import com.prnt.orderservice.service.OrderService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@RestController
@RequestMapping("/order/")
@Slf4j
public class OrderController {

    final OrderService orderService;
    final ArticleService articleService;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    /**
     * To create random order data in memory database
     * @return status 200: and list of created orders
     * **/
    @GetMapping("/init")
    public ResponseEntity<Object> createOrder() {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrders());
    }

    /**
     * To get specific order
     * @param articleNumber
     * @param orderAmount
     * @param logisticType
     * @return status code 200, and an object OrderResponseDto
     * @return status code 404 if no order found
     * **/
    @GetMapping("{articleNumber}/{orderAmount}/{logisticType}")
    public ResponseEntity<OrderResponseDto> getOrderByOrderId(@PathVariable int articleNumber,
                                                              @PathVariable int orderAmount,
                                                              @PathVariable int logisticType) {
        try {
            OrderResponseDto orderResponseDto = orderService.getOrder(articleNumber, orderAmount, logisticType);
            return ResponseEntity.ok(orderResponseDto);
        } catch (EntityNotFoundException e) {
            log.info("{}",e);
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * To send an order details via API end-point
     * @RequestBody order
     * @Save Persists data in H2 database for both tables article and orders
     * @return returns JSON response
     * **/
    @PostMapping("save")
    public ResponseEntity<Order> saveShipment(@RequestBody Order order) {
    	try{
    		
    		Article article = Article.builder()
                .id(order.getArticle().getId())
                .name(order.getArticle().getName())
                .price(order.getArticle().getPrice())
                .inStock(order.getArticle().getInStock())
                .build();
    	Article savedArticle = articleService.saveArticle(article);
    	Order orderData = Order.builder()
                .id(order.getId())
                .logisticType(order.getLogisticType())
                .shipmentCost(order.getShipmentCost())
                .orderAmount(order.getOrderAmount())
                .article(savedArticle)
                .build();
        Order savedShipment = orderService.saveOrder(orderData);
        return ResponseEntity.ok(savedShipment);
    	}catch(Exception e) {
    		log.info("{}",e);
    		return ResponseEntity.notFound().build();
    	}
    }
   
}
