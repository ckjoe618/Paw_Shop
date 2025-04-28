package com.topics.discuss.model.service.front;

import com.topics.discuss.model.dto.request.LikeAddDto;
import com.topics.discuss.model.entity.LikeBean;
import com.topics.discuss.model.repository.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    // 按讚
    @Transactional
    public void addLike(LikeAddDto likeAddDto) {
        if (isLiked(likeAddDto)) {
            throw new RuntimeException("已經按過讚了");
        }

        LikeBean likeBean = new LikeBean();
        likeBean.setMemberId(likeAddDto.getMemberId());
        likeBean.setArticleId(likeAddDto.getArticleId());
        likeRepository.save(likeBean);
    }

    // 收回讚
    @Transactional
    public void removeLike(LikeAddDto likeAddDto) {
        if (!isLiked(likeAddDto)) {
            throw new RuntimeException("尚未按讚，無法取消");
        }

        likeRepository.deleteByMemberIdAndArticleId(
                likeAddDto.getMemberId(), likeAddDto.getArticleId()
        );
    }

    // 查詢是否按過
    public boolean isLiked(LikeAddDto likeAddDto) {
        return likeRepository.findByMemberIdAndArticleId(
                likeAddDto.getMemberId(), likeAddDto.getArticleId()
        ).isPresent();
    }

    // 查詢文章總讚數
    public int countLikes(int articleId) {
        return likeRepository.countByArticleId(articleId);
    }

}
