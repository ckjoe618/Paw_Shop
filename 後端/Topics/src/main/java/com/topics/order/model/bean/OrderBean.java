package com.topics.order.model.bean;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.topics.member.model.bean.MemberBean;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberId")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private MemberBean member;

	public OrderBean() {
		super();
		trackingNum = "";
	}

}
