package com.topics.discuss.model.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

//@Entity @Table(name = "report")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportId;
    private int reportType;
    private Integer articleId;
    private Integer commentId;
    private int memberId;
    private String reason;
    private LocalDateTime createdDate;
    private int status;
    private  CommentBean comment;
    private  ArticleBean article;
//    private  MemberBean member;
}
