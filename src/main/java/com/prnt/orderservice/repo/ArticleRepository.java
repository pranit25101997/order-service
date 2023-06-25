package com.prnt.orderservice.repo;

import com.prnt.orderservice.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
