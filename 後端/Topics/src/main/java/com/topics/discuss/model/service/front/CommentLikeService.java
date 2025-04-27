package com.topics.discuss.model.service.front;

import com.topics.discuss.model.dto.request.CommentLikeAddDto;
import com.topics.discuss.model.entity.CommentLikeBean;
import com.topics.discuss.model.repository.CommentLikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    public CommentLikeService(CommentLikeRepository commentLikeRepository) {
        this.commentLikeRepository = commentLikeRepository;
    }

    // 按讚
    @Transactional
    public void addCommentLike(CommentLikeAddDto commentLikeAddDto) {
        if (isCommentLiked(commentLikeAddDto)) {
            throw new RuntimeException("已經按過讚了");
        }

        CommentLikeBean commentLikeBean = new CommentLikeBean();
        commentLikeBean.setCommentId(commentLikeAddDto.getCommentId());
        commentLikeBean.setMemberId(commentLikeAddDto.getMemberId());

        commentLikeRepository.save(commentLikeBean);
    }

    // 收回讚
    @Transactional
    public void removeCommentLike(CommentLikeAddDto commentLikeAddDto) {
        if (!isCommentLiked(commentLikeAddDto)) {
            throw new RuntimeException("尚未按讚，無法取消");
        }

        commentLikeRepository.deleteByMemberIdAndCommentId(
                commentLikeAddDto.getMemberId(), commentLikeAddDto.getCommentId()
        );
    }

    // 查詢是否按讚
    public boolean isCommentLiked(CommentLikeAddDto commentLikeAddDto) {
        return commentLikeRepository
                .findByMemberIdAndCommentId(commentLikeAddDto.getMemberId(), commentLikeAddDto.getCommentId())
                .isPresent();
    }

    // 查詢留言按讚數
    public int getCommentLikeCount(int commentId) {
        return commentLikeRepository.countByCommentId(commentId);
    }
}
