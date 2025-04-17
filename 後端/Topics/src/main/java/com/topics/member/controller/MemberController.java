package com.topics.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.topics.member.model.dto.MemberDto;
import com.topics.member.model.service.MemberService;

@RestController
@RequestMapping("/api")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/member")
	public List<MemberDto> getMethodName() {
		return memberService.selectMember();
	}
	

}
