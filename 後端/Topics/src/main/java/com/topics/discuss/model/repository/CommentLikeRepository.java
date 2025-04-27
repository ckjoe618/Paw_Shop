package com.topics.discuss.model.repository;

import com.topics.discuss.model.entity.CommentLikeBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLikeBean, Integer> {

    // 查詢某留言是否已經被某會員按過讚
    Optional<CommentLikeBean> findByMemberIdAndCommentId(int memberId, int commentId);

    // 刪除某會員對某留言的讚（取消讚）
    void deleteByMemberIdAndCommentId(int memberId, int commentId);

    // 統計某留言的總按讚數
    int countByCommentId(int commentId);
}
