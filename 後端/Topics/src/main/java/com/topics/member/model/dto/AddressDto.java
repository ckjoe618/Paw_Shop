package com.topics.member.model.dto;

import org.springframework.beans.BeanUtils;
import com.topics.member.model.entity.AddressBean;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {

	private Integer addressId;
	private Integer memberId;
	private String recipientName;
	private String phone;
	private Integer zipcode;
	private String city;
	private String district;
	private String addressDetail;

	public AddressDto(AddressBean entity) {
		if (entity != null) {
			BeanUtils.copyProperties(entity, this);
			this.memberId = entity.getMember().getMemberId();
		}
	}
	
}
