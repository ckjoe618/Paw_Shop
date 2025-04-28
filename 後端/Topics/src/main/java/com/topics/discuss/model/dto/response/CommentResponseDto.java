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
public class CommentResponseDto {

    private int commentId;

    private int memberId;

    private String memberName;

    private String memberPhoto;

    private String content;

    private Integer floor;

    private Integer parentCommentId;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
}
