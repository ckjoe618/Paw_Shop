package com.topics.discuss.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePreviewDto {

    private int articleId;

    private String title;

    private String memberName;

    private String categoryName;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private int commentCount;

    private int viewCount;
}
