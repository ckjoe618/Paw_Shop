package com.topics.discuss.controller.front;

import com.topics.discuss.model.entity.ArticleBean;
import com.topics.discuss.model.dto.request.ArticleRequestDto;
import com.topics.discuss.model.dto.response.ArticleDetailDto;
import com.topics.discuss.model.dto.response.ArticleListDto;
import com.topics.discuss.model.service.front.ArticleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private   final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // 查詢全部文章
    @GetMapping
    public ResponseEntity<List<ArticleListDto>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticlesWithCategoryName());
    }

    // 查詢單筆文章
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDetailDto> getArticleDetail(@PathVariable int id) {
        ArticleDetailDto dto = articleService.getArticleDetailById(id);
        return ResponseEntity.ok(dto);
    }

    // 新增文章
    @PostMapping
    public ResponseEntity<ArticleBean> addArticle(@Valid @RequestBody ArticleRequestDto articleRequestDto) {
        ArticleBean saved = articleService.addArticle(articleRequestDto);
        return ResponseEntity.status(201).body(saved);
    }
}
