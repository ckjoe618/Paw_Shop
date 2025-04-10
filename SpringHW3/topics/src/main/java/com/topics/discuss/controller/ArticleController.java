package com.topics.discuss.controller;

import com.topics.discuss.model.bean.ArticleBean;
import com.topics.discuss.model.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/discuss")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public String articlePage() {
        return "discuss";
    }

    @GetMapping("/article")
    @ResponseBody
    public List<ArticleBean> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("/article/{id}")
    @ResponseBody
    public ResponseEntity<ArticleBean> getArticle(@PathVariable Integer id) {
        ArticleBean article = articleService.getArticleById(id);
        return article != null ? ResponseEntity.ok(article) : ResponseEntity.notFound().build();
    }

    @PostMapping("/article")
    @ResponseBody
    public ArticleBean createArticle(@RequestBody ArticleBean article) {
        return articleService.insertArticle(article);
    }

    @PutMapping("/article/{id}")
    @ResponseBody
    public ResponseEntity<ArticleBean> updateArticle(@PathVariable Integer id, @RequestBody ArticleBean updated) {
        updated.setArticleId(id);
        ArticleBean result = articleService.updateArticle(updated);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/article/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteArticle(@PathVariable Integer id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}
