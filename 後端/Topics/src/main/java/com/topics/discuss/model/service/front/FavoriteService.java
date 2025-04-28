package com.topics.discuss.model.service.front;

import com.topics.discuss.model.dto.request.FavoriteAddDto;
import com.topics.discuss.model.dto.response.FavoriteArticleDto;
import com.topics.discuss.model.entity.FavoriteBean;
import com.topics.discuss.model.repository.FavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    // 加入收藏
    public void addFavorite(FavoriteAddDto favoriteAddDto) {
        Optional<FavoriteBean> optional = favoriteRepository.findByMemberIdAndArticleId(favoriteAddDto.getMemberId(), favoriteAddDto.getArticleId());

        if (optional.isPresent()) {
            throw new RuntimeException("已收藏過此文章");
        }

        FavoriteBean favoriteBean = new FavoriteBean();
        favoriteBean.setMemberId(favoriteAddDto.getMemberId());
        favoriteBean.setArticleId(favoriteAddDto.getArticleId());

        favoriteRepository.save(favoriteBean);
    }

    // 移除收藏
    @Transactional
    public void removeFavorite(FavoriteAddDto favoriteAddDto) {
        Optional<FavoriteBean> optional = favoriteRepository.findByMemberIdAndArticleId(favoriteAddDto.getMemberId(), favoriteAddDto.getArticleId());

        if (optional.isEmpty()) {
            throw new RuntimeException("尚未收藏過此文章");
        }

        favoriteRepository.deleteByMemberIdAndArticleId(favoriteAddDto.getMemberId(), favoriteAddDto.getArticleId());
    }

    // 查詢是否收藏
    public boolean isFavorite(FavoriteAddDto favoriteAddDto) {
        return favoriteRepository.findByMemberIdAndArticleId(favoriteAddDto.getMemberId(), favoriteAddDto.getArticleId()).isPresent();
    }

    // 統計收藏數
    public int countFavorites(int articleId) {
        return favoriteRepository.countByArticleId(articleId);
    }

    // 查詢會員全部收藏
    public List<FavoriteArticleDto> getFavoriteArticlesByMemberId(int memberId) {
        return favoriteRepository.findFavoriteArticlesByMemberId(memberId);
    }
}
