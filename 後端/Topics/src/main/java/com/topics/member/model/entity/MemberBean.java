package com.topics.member.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.topics.order.model.bean.OrderBean;
import com.topics.order.model.bean.ShoppingcartItemBean;
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

@Entity
@Table(name = "member")
@Getter
@Setter
public class MemberBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memberId;
	private String memberName;
	private String gender;
	private String idno;
	private String email;
	private String phone;
	private LocalDate birthDate;
	private String memberPhoto;
	private String account;
	private String password;
	private LocalDateTime createAccountDate = LocalDateTime.now();
	private String role;
	private boolean activeStatus = true;

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<OrderBean> order;

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ShoppingcartItemBean> shoppingcartItem;

}
