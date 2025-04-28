package com.topics.discuss.model.repository;

import com.topics.discuss.model.entity.LikeBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeBean, Integer> {

    // 查詢某會員是否有對某文章按讚過（避免重複按讚）
    Optional<LikeBean> findByMemberIdAndArticleId(int memberId, int articleId);

    // 查詢某文章的總按讚數
    int countByArticleId(int articleId);

    // 移除讚
    void deleteByMemberIdAndArticleId(int memberId, int articleId);
}
