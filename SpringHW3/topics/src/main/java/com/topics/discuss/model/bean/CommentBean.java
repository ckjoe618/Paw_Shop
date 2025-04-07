package com.topics.discuss.model.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

//@Entity @Table(name = "comment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;
    private int articleId;
    private int memberId;
    private Integer parentCommentId;
    private String content;
    private LocalDateTime createdDate;
    private boolean isDeletedStatus;
    private  ArticleBean article;
//    private MemberBean member;
}
