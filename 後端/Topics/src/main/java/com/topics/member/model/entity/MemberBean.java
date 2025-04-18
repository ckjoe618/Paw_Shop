package com.topics.member.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private byte[] memberPhoto;
	private String account;
	private String password;
	private LocalDateTime createAccountDate = LocalDateTime.now();
	private String role;
	private boolean activeStatus = true;

}
