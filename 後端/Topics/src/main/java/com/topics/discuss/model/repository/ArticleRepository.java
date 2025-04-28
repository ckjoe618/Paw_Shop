package com.topics.discuss.model.repository;

import com.topics.discuss.model.dto.response.ArticlePreviewDto;
import com.topics.discuss.model.entity.ArticleBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleBean, Integer> {

    // 後台
    List<ArticleBean> findByDeletedFalseOrderByCreatedDateDesc();

    // 前台顯示全部文章摘要(時間排序)
    @Query("SELECT new com.topics.discuss.model.dto.response.ArticlePreviewDto(" +
            "a.articleId, a.title, m.memberName, c.categoryName, a.createdDate, a.updatedDate, " +
            "a.commentCount, a.viewCount) " +
            "FROM ArticleBean a " +
            "JOIN MemberBean m ON a.memberId = m.memberId " +
            "JOIN ArticleCategory c ON a.categoryId = c.categoryId " +
            "WHERE a.deleted = false " +
            "ORDER BY a.createdDate DESC")
    List<ArticlePreviewDto> findAllArticlePreviewsOrderByCreatedDate();

    // 前台顯示全部文章摘要（依瀏覽數排序）
    @Query("SELECT new com.topics.discuss.model.dto.response.ArticlePreviewDto(" +
            "a.articleId, a.title, m.memberName, c.categoryName, a.createdDate, a.updatedDate, " +
            "a.commentCount, a.viewCount) " +
            "FROM ArticleBean a " +
            "JOIN MemberBean m ON a.memberId = m.memberId " +
            "JOIN ArticleCategory c ON a.categoryId = c.categoryId " +
            "WHERE a.deleted = false " +
            "ORDER BY a.viewCount DESC")
    List<ArticlePreviewDto> findAllArticlePreviewsOrderByViewCount();

}
