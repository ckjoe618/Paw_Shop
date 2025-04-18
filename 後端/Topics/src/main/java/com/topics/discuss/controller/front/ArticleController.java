package com.topics.discuss.controller.front;

import com.topics.discuss.model.bean.ArticleBean;
import com.topics.discuss.model.service.front.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private   final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<List<ArticleBean>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }
}
