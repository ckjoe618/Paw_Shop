package com.topics.discuss.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleRequestDto {

    @NotBlank(message = "文章標題不能為空")
    private String title;

    @NotBlank(message = "文章內容不能為空")
    private String content;

    @NotNull
    private int categoryId;
    private int memberId;
}
