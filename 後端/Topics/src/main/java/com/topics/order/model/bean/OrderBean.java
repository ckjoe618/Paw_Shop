package com.topics.order.model.bean;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.topics.member.model.entity.MemberBean;
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
	private String recipientName;
	private String recipientAddress;
	private String recipientPhone;
	private Integer shippingFee;
	private LocalDateTime updateTime;
	//綠界
	private String merchantTradeNo; // TSxxxxxx，自己系統產出的訂單編號
	private String ecpayTradeNo; // 綠界交易編號

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//@JsonIgnore
	@JsonProperty("orderDetails")
	private List<OrderDetailBean> orderDetails;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberId")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private MemberBean member;

	public OrderBean() {
		super();
		trackingNum = "";
	}

}
