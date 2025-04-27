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
public class FavoriteArticleDto {

    private int articleId;

    private String title;

    private String memberName;

    private LocalDateTime createdDate;
}
