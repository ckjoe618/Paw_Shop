package com.topics.discuss.model.entity;

import com.topics.member.model.entity.MemberBean;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    private int articleId;

    @Column(name = "member_id")
    private int memberId;

    private Integer parentCommentId;

    private String content;

    @CreationTimestamp
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private Integer floor;

    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private MemberBean member;
}
