package com.topics.discuss.model.repository;

import com.topics.discuss.model.dto.response.FavoriteArticleDto;
import com.topics.discuss.model.entity.FavoriteBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteBean, Integer> {

    // 查詢某會員是否已收藏某文章（用來避免重複收藏）
    Optional<FavoriteBean> findByMemberIdAndArticleId(int memberId, int articleId);

    // 查詢某會員所有收藏（前台個人頁面）
    List<FavoriteBean> findByMemberIdOrderByCreatedDateDesc(int memberId);

    // 查詢某文章的收藏次數（後台統計使用）
    int countByArticleId(int articleId);

    // 刪除收藏紀錄（取消收藏）
    void deleteByMemberIdAndArticleId(int memberId, int articleId);

    @Query("""
    SELECT new com.topics.discuss.model.dto.response.FavoriteArticleDto(
        a.articleId, a.title, m.memberName, f.createdDate
    )
    FROM FavoriteBean f
    JOIN ArticleBean a ON f.articleId = a.articleId
    JOIN MemberBean m ON f.memberId = m.memberId
    WHERE f.memberId = :memberId
    ORDER BY f.createdDate DESC
""")
    List<FavoriteArticleDto> findFavoriteArticlesByMemberId(int memberId);

}
