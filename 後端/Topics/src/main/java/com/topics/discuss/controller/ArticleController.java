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

    // ✅ HTML頁面導向用（例如將來接 DataTables）
    @GetMapping
    public String articlePage() {
        return "discuss"; // resources/templates/articles.html
    }

    // ✅ RESTful API：取得所有文章
    @GetMapping("/article")
    @ResponseBody
    public List<ArticleBean> getAllArticles() {
        return articleService.getAllArticles();
    }

    // ✅ RESTful API：取得單篇文章
    @GetMapping("/article/{id}")
    @ResponseBody
    public ResponseEntity<ArticleBean> getArticle(@PathVariable Integer id) {
        ArticleBean article = articleService.getArticleById(id);
        return article != null ? ResponseEntity.ok(article) : ResponseEntity.notFound().build();
    }

    // ✅ RESTful API：新增文章
    @PostMapping("/article")
    @ResponseBody
    public ArticleBean createArticle(@RequestBody ArticleBean article) {
        return articleService.insertArticle(article);
    }

    // ✅ RESTful API：更新文章
    @PutMapping("/article/{id}")
    @ResponseBody
    public ResponseEntity<ArticleBean> updateArticle(@PathVariable Integer id, @RequestBody ArticleBean updated) {
        updated.setArticleId(id);
        ArticleBean result = articleService.updateArticle(updated);
        return ResponseEntity.ok(result);
    }

    // ✅ RESTful API：刪除文章
    @DeleteMapping("/article/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteArticle(@PathVariable Integer id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}
