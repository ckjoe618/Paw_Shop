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
public class CommentDeleteDto {

    @NotNull(message = "ID不可為空")
    private Integer commentId;

    @NotNull(message = "會員不可為空")
    private Integer memberId;
}
