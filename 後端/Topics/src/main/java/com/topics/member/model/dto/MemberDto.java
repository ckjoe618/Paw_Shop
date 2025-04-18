package com.topics.member.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.BeanUtils;
import com.topics.member.model.entity.MemberBean;
import lombok.Getter;
import lombok.Setter;
//import java.util.Base64;

@Getter
@Setter
public class MemberDto {

	private Integer memberId;
	private String memberName;
	private String gender;
	private String idno;
	private String email;
	private String phone;
	private LocalDate birthDate;
	private String photo;
	private String role;
	private LocalDateTime createAccountDate;
	private boolean activeStatus;

	public MemberDto(MemberBean entity) {
		BeanUtils.copyProperties(entity, this); // 將 Member 轉型成 MemberDto 
		//this.phone = "data:image/png;base64," + Base64.getEncoder().encodeToString(entity.getMemberPhoto()); // 將 base64 編碼成 String
	}

}
