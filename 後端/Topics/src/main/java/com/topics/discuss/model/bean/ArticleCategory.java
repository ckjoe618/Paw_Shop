package com.topics.discuss.model.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "article_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    private String categoryName;
}
