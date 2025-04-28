package com.topics.discuss.model.repository;

import com.topics.discuss.model.entity.CommentBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentBean, Integer> {

    // 查全部留言
    List<CommentBean> findByArticleIdAndDeletedFalseOrderByCreatedDateAsc(int articleId);

    // 查主文章留言(1-1,1-2)
    List<CommentBean> findByArticleIdAndParentCommentIdAndDeletedFalseOrderByCreatedDateAsc(int articleId, int parentCommentId);

    // 查文章回復(2樓3樓)
    List<CommentBean> findByArticleIdAndParentCommentIdIsNullAndDeletedFalseOrderByFloorAsc(int articleId);

    // 查子樓層留言(2-1,2-2...)
    List<CommentBean> findByParentCommentIdAndDeletedFalseOrderByCreatedDateAsc(Integer parentCommentId);

    // 查最大樓層(新增下一樓用)
    CommentBean findFirstByArticleIdAndParentCommentIdIsNullAndDeletedFalseOrderByFloorDesc(int articleId);
}
