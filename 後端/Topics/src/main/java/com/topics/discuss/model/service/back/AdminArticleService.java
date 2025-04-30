package com.topics.discuss.model.service.back;

import com.topics.discuss.model.dto.response.AdminArticleDto;
import com.topics.discuss.model.entity.ArticleBean;
import com.topics.discuss.model.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminArticleService {

	@Autowired
	private ArticleRepository articleRepo;

	public List<AdminArticleDto> getAllArticleDtos() {
		List<ArticleBean> articles = articleRepo.findAll();
		List<AdminArticleDto> result = new ArrayList<>();

		for (ArticleBean article : articles) {
			AdminArticleDto dto = new AdminArticleDto();
			dto.setArticleId(article.getArticleId());
			dto.setTitle(article.getTitle());
			dto.setContent(article.getContent());
			dto.setCategoryId(article.getCategoryId());
			dto.setMemberId(article.getMemberId());
			dto.setViewCount(article.getViewCount());
			dto.setCommentCount(article.getCommentCount());
			dto.setPinned(article.isPinned());
			dto.setFeatured(article.isFeatured());
			dto.setCreatedDate(article.getCreatedDate());
			dto.setUpdatedDate(article.getUpdatedDate());

			// 讀取會員資訊（防呆）
			try {
				var member = article.getMember();
				if (member != null) {
					dto.setMemberName(member.getMemberName());
					dto.setMemberPhoto(member.getMemberPhoto());
				}
			} catch (Exception e) {
				System.err.println("讀取 member 錯誤：" + e.getMessage());
			}

			result.add(dto);
		}
		return result;
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
