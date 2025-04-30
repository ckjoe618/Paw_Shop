package com.topics.discuss.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    @NotNull(message = "文章ID不可為空")
    private int articleId;

    private Integer parentCommentId;

    @NotBlank(message = "留言/回覆內容不可為空")
    private String content;
}
