package com.topics.discuss.model.service.front;

import com.topics.discuss.model.bean.ArticleBean;
import com.topics.discuss.model.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<ArticleBean> getAllArticles() {
        return articleRepository.findByDeletedFalseOrderByCreatedDateDesc();
    }
}
