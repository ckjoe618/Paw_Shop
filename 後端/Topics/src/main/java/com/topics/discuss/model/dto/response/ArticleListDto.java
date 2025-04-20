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
public class ArticleListDto {

    private int articleId;
    private int memberId;
    private String title;
    private String content;
    private int categoryId;
    private String categoryName;
    private boolean pinned;
    private boolean featured;
    private int viewCount;
    private int commentCount;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
