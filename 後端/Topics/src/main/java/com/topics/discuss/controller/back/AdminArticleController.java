package com.topics.discuss.controller.back;

import com.topics.discuss.model.dto.response.AdminArticleDto;
import com.topics.discuss.model.entity.ArticleBean;
import com.topics.discuss.model.service.back.AdminArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/discuss")
public class AdminArticleController {

    @Autowired
    private AdminArticleService adminArticleService;

    // HTML頁面導向用（例如將來接 DataTables）
    @GetMapping
    public String articlePage() {
        return "discuss"; // resources/templates/articles.html
    }

    // RESTful API：取得所有文章
    @GetMapping("/article")
    @ResponseBody
    public ResponseEntity<List<AdminArticleDto>> getAllArticles() {
        return ResponseEntity.ok(adminArticleService.getAllArticleDtos());
    }

    // RESTful API：取得單篇文章
    @GetMapping("/article/{id}")
    @ResponseBody
    public ResponseEntity<ArticleBean> getArticle(@PathVariable Integer id) {
        ArticleBean article = adminArticleService.getArticleById(id);
        return article != null ? ResponseEntity.ok(article) : ResponseEntity.notFound().build();
    }

    // RESTful API：新增文章
    @PostMapping("/article")
    @ResponseBody
    public ArticleBean createArticle(@RequestBody ArticleBean article) {
        return adminArticleService.insertArticle(article);
    }

    // RESTful API：更新文章
    @PutMapping("/article/{id}")
    @ResponseBody
    public ResponseEntity<ArticleBean> updateArticle(@PathVariable Integer id, @RequestBody ArticleBean updated) {
        updated.setArticleId(id);
        ArticleBean result = adminArticleService.updateArticle(updated);
        return ResponseEntity.ok(result);
    }

    // RESTful API：刪除文章
    @DeleteMapping("/admin/{articleId}")
    public ResponseEntity<Void> adminDeleteArticle(@PathVariable int articleId) {
        adminArticleService.adminDeleteArticle(articleId); // 呼叫新的 service 方法
        return ResponseEntity.noContent().build();
    }
}
