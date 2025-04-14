package com.topics.member.model.bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "member")
@Setter
@Getter
@NoArgsConstructor
public class MemberBean {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memberId;

	@Column(name = "member_name")
	private String name;

	@Column(name = "gender")
	private String gender;

	@Column(name = "idno")
	private String idno;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "birth_date")
	private LocalDate birthday;

	@Column(name = "account")
	private String account;

	@Column(name = "password")
	private String password;

	@Column(name = "create_account_date")
	private LocalDateTime createAccount = LocalDateTime.now();

	@Column(name = "active_status")
	private boolean isActive = true;

	@Override
	public String toString() {
		return "MemberBean [memberId=" + memberId + ", name=" + name + ", gender=" + gender + ", idno=" + idno
				+ ", email=" + email + ", phone=" + phone + ", birthday=" + birthday + ", account=" + account
				+ ", password=" + password + ", createAccount=" + createAccount + ", isActive=" + isActive + "]";
	}
	
}
