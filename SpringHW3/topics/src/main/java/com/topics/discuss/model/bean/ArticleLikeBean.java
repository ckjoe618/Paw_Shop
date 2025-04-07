package com.topics.discuss.model.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

//@Entity @Table(name = "article_like")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleLikeBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int likeId;
    private int articleId;
    private int memberId;
    private LocalDateTime createdDate;
}
