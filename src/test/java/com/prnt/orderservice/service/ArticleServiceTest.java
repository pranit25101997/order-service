package com.prnt.orderservice.service;

import com.prnt.orderservice.dto.ArticleAvailibityDto;
import com.prnt.orderservice.entity.Article;
import com.prnt.orderservice.repo.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
public class ArticleServiceTest {
    @Mock
    private ArticleRepository articleRepository;
    @InjectMocks
    private ArticleService articleService;
    @Test
    public void checkAvailabilityTest_Exists() {
        long articleNumber = 1;
        Article article = new Article();
        article.setId((int)articleNumber);
        article.setInStock(102);
        Mockito.when(articleRepository.findById((long) articleNumber)).thenReturn(Optional.of(article));
        ArticleAvailibityDto availabilityDto = articleService.checkAvailability(1);
        
        Assertions.assertTrue(availabilityDto.isAvailable());
        Assertions.assertEquals(articleNumber, availabilityDto.getArticleNumber());
        Assertions.assertEquals(article.getInStock(), availabilityDto.getInStock());
    }
    @Test
    public void saveArticleTest() {
        Article article = new Article();
        article.setId(2);
        article.setPrice(new BigDecimal(41.2));
        when(articleRepository.save(any(Article.class))).thenReturn(article);
        Article savedArticle = articleService.saveArticle(article);
        verify(articleRepository).save(article);
      
        Assertions.assertNotNull(savedArticle);
        Assertions.assertEquals(article.getId(), savedArticle.getId());
        Assertions.assertEquals(article.getPrice(), savedArticle.getPrice());
    }
    @Test
    public void checkAvailabilityTest_NotExists() {
        int articleNumber = 56;
        when(articleRepository.findById((long) articleNumber)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            articleService.checkAvailability(articleNumber);
        });
    }
}