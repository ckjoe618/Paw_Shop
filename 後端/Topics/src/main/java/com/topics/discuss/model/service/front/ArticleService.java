package com.topics.discuss.model.service.front;

import com.topics.discuss.model.dto.request.ArticleUpdateDto;
import com.topics.discuss.model.entity.ArticleBean;
import com.topics.discuss.model.entity.ArticleCategory;
import com.topics.discuss.model.dto.request.ArticleRequestDto;
import com.topics.discuss.model.dto.response.ArticleCategoryDto;
import com.topics.discuss.model.dto.response.ArticleDetailDto;
import com.topics.discuss.model.dto.response.ArticleListDto;
import com.topics.discuss.model.repository.ArticleCategoryRepository;
import com.topics.discuss.model.repository.ArticleRepository;
import com.topics.member.model.entity.MemberBean;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleCategoryRepository articleCategoryRepository;

    public ArticleService(ArticleRepository articleRepository, ArticleCategoryRepository articleCategoryRepository) {
        this.articleRepository = articleRepository;
        this.articleCategoryRepository = articleCategoryRepository;
    }
// 查全部文章
//    public List<ArticleBean> getAllArticles() {
//        return articleRepository.findByDeletedFalseOrderByCreatedDateDesc();
//    }

    // 查全部文章(含分類)
    public List<ArticleListDto> getAllArticlesWithCategoryName() {
        List<ArticleBean> articles = articleRepository.findByDeletedFalseOrderByCreatedDateDesc();

        List<ArticleListDto> result = new ArrayList<>();

        for (ArticleBean article : articles) {
            //查分類名稱
            String categoryName = articleCategoryRepository
                    .findById(article.getCategoryId())
                    .map(ArticleCategory::getCategoryName)
                    .orElse("未知分類");

            ArticleListDto dto = new ArticleListDto();

            dto.setArticleId(article.getArticleId());
            dto.setMemberId(article.getMemberId());
            dto.setTitle(article.getTitle());
            dto.setContent(article.getContent());
            dto.setCategoryId(article.getCategoryId());
            dto.setCategoryName(categoryName);
            dto.setViewCount(article.getViewCount());
            dto.setCommentCount(article.getCommentCount());
            dto.setCreatedDate(article.getCreatedDate());
            dto.setUpdatedDate(article.getUpdatedDate());
            dto.setPinned(article.isPinned());
            dto.setFeatured(article.isFeatured());

            result.add(dto);
        }
        return result;
    }

    // 查詢所有分類
    public List<ArticleCategoryDto> getAllCategories() {
        List<ArticleCategory> categories = articleCategoryRepository.findAll();

        List<ArticleCategoryDto> result = new ArrayList<>();

        for (ArticleCategory category : categories) {
            ArticleCategoryDto dto = new ArticleCategoryDto();
            dto.setCategoryId(category.getCategoryId());
            dto.setCategoryName(category.getCategoryName());
            result.add(dto);
        }
        return result;
    }

    // 查詢單筆文章詳情
    public ArticleDetailDto getArticleDetailById(int articleId) {
        Optional<ArticleBean> optional = articleRepository.findById(articleId);

        if (optional.isEmpty() || optional.get().isDeleted()) {
            throw new RuntimeException("文章不存在");
        }

        ArticleBean article = optional.get();

        return toDetailDto(article);
    }

    // 新增文章
    public ArticleBean addArticle(@Valid ArticleRequestDto articleRequestDto) {
        ArticleBean article = new ArticleBean();

        article.setTitle(articleRequestDto.getTitle());
        article.setContent(articleRequestDto.getContent());
        article.setCategoryId(articleRequestDto.getCategoryId());
        article.setMemberId(articleRequestDto.getMemberId());

        article.setViewCount(0);
        article.setDeleted(false);
        article.setPinned(false);
        article.setFeatured(false);
        article.setCommentCount(0);
        article.setLastCommentDate(null);

        return articleRepository.save(article);
    }

    // 編輯文章
    public ArticleDetailDto updateArticle(ArticleUpdateDto articleUpdateDto) {
        ArticleBean article = articleRepository.findById(articleUpdateDto.getArticleId())
                .orElseThrow(() -> new RuntimeException("找不到該文章"));

        if (article.isDeleted()) {
            throw new RuntimeException("該文章已刪除");
        }

        article.setTitle(articleUpdateDto.getTitle());
        article.setContent(articleUpdateDto.getContent());
        article.setCategoryId(articleUpdateDto.getCategoryId());
        article.setUpdatedDate(LocalDateTime.now());

        return toDetailDto(articleRepository.save(article));
    }

    // 刪除文章
    public void deleteArticle(int articleId, int memberId) {
        ArticleBean article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("找不到該文章"));

        if (article.getMemberId() != memberId) {
            throw new RuntimeException("沒有權限");
        }

        if (article.isDeleted()) {
            throw new RuntimeException("文章已刪除");
        }

        article.setDeleted(true);
        article.setUpdatedDate(LocalDateTime.now());

        articleRepository.save(article);
    }

    // 共用方法
    private ArticleDetailDto toDetailDto(ArticleBean article) {
        ArticleDetailDto dto = new ArticleDetailDto();

        dto.setArticleId(article.getArticleId());
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        dto.setCategoryId(article.getCategoryId());
        dto.setMemberId(article.getMemberId());
        dto.setCreatedDate(article.getCreatedDate());
        dto.setUpdatedDate(article.getUpdatedDate());
        dto.setViewCount(article.getViewCount());
        dto.setCommentCount(article.getCommentCount());
        dto.setPinned(article.isPinned());
        dto.setFeatured(article.isFeatured());

        // 額外查分類名稱
        String categoryName = articleCategoryRepository
                .findById(article.getCategoryId())
                .map(ArticleCategory::getCategoryName)
                .orElse("未知分類");
        dto.setCategoryName(categoryName);

        // 額外查會員資訊
        MemberBean member = article.getMember();
        String memberName = (member != null) ? member.getMemberName() : "未知會員";
        String memberPhoto = (member != null && member.getMemberPhoto() != null)
                ? new String(member.getMemberPhoto(), StandardCharsets.UTF_8)
                : null;
        dto.setMemberName(memberName);
        dto.setMemberPhoto(memberPhoto);

        return dto;
    }

}
