package com.topics.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.topics.member.model.dto.MemberDto;
import com.topics.member.model.entity.MemberBean;
import com.topics.member.model.service.MemberService;
import com.topics.member.utils.ResponseUtil;
import com.topics.member.utils.SecurityUtil;

@RestController
@RequestMapping("/api/user")
public class UserMemberController {
	
	@Autowired
	private MemberService memberService;
	
	@PutMapping("/member/{id}")
	public ResponseEntity<?> updateMemberById(@PathVariable Integer id, @RequestBody MemberBean entity) {
		SecurityUtil.checkAdminPermission();
		MemberDto member = memberService.updateMemberById(id, entity);
		return ResponseUtil.success(member);
	}
	
	@PostMapping("/member")
	public ResponseEntity<?> insertMember(@RequestBody MemberBean entity) {
		MemberDto member = memberService.insertMember(entity);
		return ResponseUtil.created(member);
	}

}
