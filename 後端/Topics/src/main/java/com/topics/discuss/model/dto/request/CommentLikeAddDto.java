package com.topics.discuss.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeAddDto {

    @NotNull(message = "留言ID不可為空")
    private Integer commentId;

    @NotNull(message = "會員ID不可為空")
    private Integer memberId;
}
