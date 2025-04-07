package com.topics.discuss.model.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Table(name = "article")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleId;
    private int memberId;
    private String title;
    private String content;
    private int categoryId;
    private int viewCountNum;
    private boolean isPinnedStatus;
    private boolean isFeaturedStatus;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean isDeletedStatus;
}

