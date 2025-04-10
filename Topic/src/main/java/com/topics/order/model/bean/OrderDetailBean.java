package com.topics.order.model.bean;

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
@Table(name = "order_detail")
public class OrderDetailBean {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderDetailId;
	private Integer productId;
	private Integer quantity;
	private Integer unitPrice;
	private Integer subtotal;
	private Integer rating;
	private String comment;
	private String status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId")
	private OrderBean order;

}
