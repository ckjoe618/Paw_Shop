package com.topics.discuss.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentGroupDto {

    private int commentId;
    private int memberId;
    private String memberName;
    private String memberPhoto;
    private String content;
    private Integer floor;
    private Integer parentCommentId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // 回覆（樓中樓）
    private List<CommentGroupDto> replies;
}
