package com.topics.order.model.bean;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.topics.member.model.entity.MemberBean;
import com.topics.product.model.bean.ProductBean;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shoppingcart_item")
public class ShoppingcartItemBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cartItemId;
	private Integer quantity;
	private LocalDateTime createdTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberId")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private MemberBean member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private ProductBean product;

}
