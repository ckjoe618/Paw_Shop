package com.topics.order.model.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_detail")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class OrderDetailBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderDetailId;
	private Integer quantity;
	private Integer unitPrice;
	private Integer subtotal;
	private Integer rating;
	private String comment;
	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId")
	@JsonIgnore
	@JsonBackReference
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private OrderBean order;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //要序列化product
	private ProductBean product;
	
	public OrderDetailBean() {
		super();
		status = "active";
	}

}
