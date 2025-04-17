package com.topics.discuss.model.service.back;

import com.topics.discuss.model.bean.ArticleBean;
import com.topics.discuss.model.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminArticleService {

	@Autowired
	private ArticleRepository articleRepo;

	public List<ArticleBean> getAllArticles() {
		return articleRepo.findAll();
	}

	public ArticleBean getArticleById(Integer id) {
		return articleRepo.findById(id).orElse(null);
	}

	public ArticleBean insertArticle(ArticleBean article) {
		return articleRepo.save(article);
	}

	public ArticleBean updateArticle(ArticleBean updated) {
		// 先從資料庫查出舊資料
		ArticleBean existing = articleRepo.findById(updated.getArticleId()).orElse(null);
		if (existing == null)
			return null;

		// 選擇性覆蓋欄位（不要覆蓋 createdDate）
		existing.setTitle(updated.getTitle());
		existing.setContent(updated.getContent());
		existing.setCategoryId(updated.getCategoryId());
		existing.setViewCount(updated.getViewCount());
		existing.setPinned(updated.isPinned());
		existing.setFeatured(updated.isFeatured());
		existing.setUpdatedDate(LocalDateTime.now()); // 更新時間自己補上

		return articleRepo.save(existing); // 儲存更新後的資料
	}

	public void deleteArticle(Integer id) {
		articleRepo.deleteById(id);
	}
}
