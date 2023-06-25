package com.prnt.orderservice.service;

import com.prnt.orderservice.dto.ArticleAvailibityDto;
import com.prnt.orderservice.entity.Article;
import com.prnt.orderservice.repo.ArticleRepository;
import com.prnt.orderservice.utils.ArticleTestDataGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    final ArticleRepository articleRepository;
    /**
     * To generate Articles data
     * @return persists data to the article table
     * **/
    public List<Article> createArticles() {
        List<Article> articles = ArticleTestDataGenerator.generateTestData();
        try {
            return articleRepository.saveAll(articles);
        } catch (Exception e) {
            throw e;
        }
    }
    /**
     * To check if Article, is available or not
     * @return returns article details with its availability and in-stock details
     * **/
    public ArticleAvailibityDto checkAvailability(int articleNumber) {
        Optional<Article> articleOpt = articleRepository.findById((long) articleNumber);
        if(!articleOpt.isPresent()) {
            throw new EntityNotFoundException("Article not found");
        }
        Article article = articleOpt.get();
        return ArticleAvailibityDto.builder()
                .isAvailable(article.getInStock()>0)
                .price(article.getPrice())
                .name(article.getName())
                .articleNumber(articleNumber)
                .inStock(article.getInStock())
                .build();
    }
    /**
     * To send request via Post, if user want to Post the data
     * @return save the data to the tables with provided details
     * **/
    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }
   

}
