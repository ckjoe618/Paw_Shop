package com.topics.discuss.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminArticleDto {

    private Integer articleId;
    private String title;
    private String content;
    private Integer categoryId;
    private Integer memberId;
    private String memberName;
    private String memberPhoto;
    private Integer viewCount;
    private Integer commentCount;
    private boolean pinned;
    private boolean featured;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
