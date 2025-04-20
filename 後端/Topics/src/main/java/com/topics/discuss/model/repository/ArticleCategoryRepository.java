package com.topics.discuss.model.repository;

import com.topics.discuss.model.bean.ArticleCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCategoryRepository extends JpaRepository<ArticleCategory, Integer> {
}
