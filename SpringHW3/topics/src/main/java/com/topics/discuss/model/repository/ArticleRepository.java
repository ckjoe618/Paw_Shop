package com.topics.discuss.model.repository;

import com.topics.discuss.model.bean.ArticleBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleBean, Integer> {
}
