package com.topics.discuss.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "article_favorite")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int favoriteId;

    private int articleId;

    private int memberId;

    @Column(columnDefinition = "datetime2")
    @CreationTimestamp
    private LocalDateTime createdDate;

    // 之後再加入關聯
}
