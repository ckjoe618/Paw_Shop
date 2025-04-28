package com.topics.discuss.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateDto {

    @NotNull(message = "ID不可為空")
    private Integer commentId;

    @NotNull(message = "會員不可為空")
    private Integer memberId;

    @NotBlank(message = "內容不可為空")
    private String content;
}
