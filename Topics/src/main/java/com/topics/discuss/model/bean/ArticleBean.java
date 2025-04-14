package com.topics.discuss.model.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
	@Column(name = "article_id")
	private int articleId;

	@Column(name = "member_id")
	private int memberId;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "category_id")
	private int categoryId;

	@Column(name = "view_count_num")
	private int viewCountNum;

	@Column(name = "is_pinned_status")
	private boolean isPinnedStatus;

	@Column(name = "is_featured_status")
	private boolean isFeaturedStatus;

	@Column(name = "created_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate;

	@Column(name = "updated_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedDate;

	@Column(name = "is_deleted_status")
	private boolean isDeletedStatus;

}
