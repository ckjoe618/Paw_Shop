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
public class ArticleDetailDto {

    private int articleId;

    private String title;

    private String content;

    private int categoryId;

    private String categoryName;

    private int memberId;

    private String memberName;

    private String memberPhoto;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private int viewCount;

    private int commentCount;

    private boolean pinned;

    private boolean featured;
}
