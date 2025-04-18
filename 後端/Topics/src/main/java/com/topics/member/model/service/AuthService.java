package com.topics.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.topics.member.exception.LoginFailException;
import com.topics.member.model.dto.AuthDto;
import com.topics.member.model.dto.MemberDto;
import com.topics.member.model.entity.MemberBean;
import com.topics.member.model.repository.MemberRepository;

@Service
public class AuthService {

	@Autowired
	private MemberRepository memberRepository;

	public MemberDto login(AuthDto info) {
		MemberBean member = memberRepository.findByAccount(info.getAccount());
		if (member == null 
				|| !member.getAccount().equals(info.getAccount())
				|| !member.getPassword().equals(info.getPassword())) {
			throw new LoginFailException("登入失敗，帳號或密碼錯誤");
		}
		return new MemberDto(member);
	}

}
