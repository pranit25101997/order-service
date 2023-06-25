package com.prnt.orderservice.controller;

import com.prnt.orderservice.dto.ArticleAvailibityDto;
import com.prnt.orderservice.service.ArticleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("article/")
public class ArticleController {
    final ArticleService articleService;
    /**
     * To check the availability of an article in stock
     * @param articleNumber
     * @return 200 if order found and ArticleAvailibityDto object with availability true or false
     * @return 404 if not found
     * **/
    @GetMapping("availability/{articleNumber}")
    public ResponseEntity<ArticleAvailibityDto> getArticleAvailability(@PathVariable int articleNumber) {
        try {
            return ResponseEntity.ok(articleService.checkAvailability(articleNumber));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
