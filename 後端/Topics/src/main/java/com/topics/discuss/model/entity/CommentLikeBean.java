package com.topics.discuss.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment_like")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int likeId;

    private int commentId;

    private int memberId;

    @CreationTimestamp
    private LocalDateTime createdDate;
}
