package com.topics.discuss.model.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity @Table(name = "article_tag")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTagBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagId;
    private String name;
}
