package com.topics.discuss.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdateDto {

    private int articleId;
    private String title;
    private String content;
    private int categoryId;
    private LocalDateTime updatedDate;
}
