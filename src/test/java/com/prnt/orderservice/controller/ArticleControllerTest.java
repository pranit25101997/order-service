package com.prnt.orderservice.controller;

import com.prnt.orderservice.dto.ArticleAvailibityDto;
import com.prnt.orderservice.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class ArticleControllerTest {
    private ArticleController articleController;
    @Mock
    private ArticleService articleService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        articleController = new ArticleController(articleService);
    }
    @Test
    public void checkAvailabilityTest() {
        int articleNumber = 65;
        ArticleAvailibityDto availabilityDto = ArticleAvailibityDto.builder()
                .articleNumber(articleNumber)
                .name("Test Article")
                .price(BigDecimal.valueOf(10.0))
                .inStock(5)
                .isAvailable(true)
                .build();
        when(articleService.checkAvailability(articleNumber)).thenReturn(availabilityDto);
        ResponseEntity<ArticleAvailibityDto> response = articleController.getArticleAvailability(articleNumber);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(availabilityDto, response.getBody());

    }
}
