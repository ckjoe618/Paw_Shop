package com.topics.discuss.model.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity @Table(name = "article_tag_mapping")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTagMappingBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleId;
    private int tagId;
}
