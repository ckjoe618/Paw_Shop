package com.topics.discuss.model.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

//@Entity @Table(name = "article_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleProductBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleProductId;
    private int articleId;
    private int productId;
    private LocalDateTime createdDate;
}
