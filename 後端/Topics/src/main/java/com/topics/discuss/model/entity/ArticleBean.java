package com.topics.discuss.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.topics.member.model.entity.MemberBean;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "article")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int articleId;

	@Column(name = "member_id")
	private int memberId;

	private String title;

	@Column(columnDefinition = "text")
	private String content;

	private int categoryId;

	private int viewCount;

	private boolean pinned;

	private boolean featured;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(columnDefinition = "datetime2")
	@CreationTimestamp
	private LocalDateTime createdDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(columnDefinition = "datetime2")
	private LocalDateTime updatedDate;

	private boolean deleted;

	private int commentCount;

	@Column(columnDefinition = "datetime2")
	private LocalDateTime lastCommentDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", insertable = false, updatable = false)
	private MemberBean member;

}
