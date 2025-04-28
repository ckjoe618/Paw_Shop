package com.topics.member.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.BeanUtils;
import com.topics.member.model.entity.MemberBean;
import lombok.Getter;
import lombok.Setter;

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
	private String memberPhoto;
	private String role;
	private LocalDateTime createAccountDate;
	private boolean activeStatus;

	private AddressDto address;

	public MemberDto(MemberBean entity) {
		BeanUtils.copyProperties(entity, this);
	}

	public MemberDto(MemberBean entity, AddressDto address) {
		BeanUtils.copyProperties(entity, this);
		this.address = address;
	}

	public String getMemberPhoto() {
		if (memberPhoto == null || memberPhoto.isBlank()) {
			String seed = memberName != null
					? memberName
					: "member" + (memberId != null ? memberId : "0");
			return "https://api.dicebear.com/9.x/adventurer/svg?seed=" + seed;
		}
		return "http://localhost:8080" + memberPhoto;
	}

}
