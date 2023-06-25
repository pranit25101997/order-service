package com.prnt.orderservice.utils;

import com.prnt.orderservice.entity.Article;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ArticleTestDataGenerator {
    public static List<Article> generateTestData() {
        List<Article> articles = new ArrayList<>();
        
        for (int i = 1; i <= 10; i++) {
            Article article = new Article();
            article.setId(i);
            article.setName("Article " + i);
            article.setPrice(BigDecimal.valueOf(10.99 * i));
            article.setInStock(10 + i);
            articles.add(article);
        }
        return articles;
    }
}
