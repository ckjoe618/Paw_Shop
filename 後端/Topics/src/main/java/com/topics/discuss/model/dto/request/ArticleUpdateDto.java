package com.topics.discuss.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "ID不可為空")
    private int articleId;

    @NotBlank(message = "標題不可為空")
    private String title;

    @NotBlank(message = "內容不可為空")
    private String content;

    @NotNull(message = "分類不可為空")
    private int categoryId;

    private LocalDateTime updatedDate;
}
