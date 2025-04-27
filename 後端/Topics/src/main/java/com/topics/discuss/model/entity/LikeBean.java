package com.topics.discuss.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "article_like")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int likeId;

    private int articleId;

    private int memberId;

    @CreationTimestamp
    private LocalDateTime createdDate;
}
