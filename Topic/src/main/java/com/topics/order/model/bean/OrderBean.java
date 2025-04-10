package com.topics.order.model.bean;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderBean {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
	private Integer memberId;
	private Integer priceTotal;
	private LocalDateTime transactionTime;
	private String paymentMethod;
	private String paymentStatus;
	private String orderStatus;
	private String pickupMethod;
	private String trackingNum;
	private LocalDateTime updateTime;
	
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<OrderDetailBean> orderDetails;
	
	public OrderBean() {
		super();
		trackingNum = "";
	}

}
