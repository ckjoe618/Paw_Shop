package com.topics.member.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDto {
	
	private String loginId;
	private String password;
	private String token;

}
