package com.topics.discuss.controller.front;

import com.topics.discuss.model.dto.response.ArticleCategoryDto;
import com.topics.discuss.model.service.front.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/article-category")
public class ArticleCategoryController {

    private final ArticleService articleService;

    public ArticleCategoryController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<List<ArticleCategoryDto>> getAllCategories() {
        return ResponseEntity.ok(articleService.getAllCategories());
    }
}
