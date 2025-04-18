package com.topics.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.topics.member.model.dto.MemberDto;
import com.topics.member.model.entity.MemberBean;
import com.topics.member.model.service.MemberService;
import com.topics.member.security.AuthHolder;
import com.topics.member.utils.JwtUtil;

@RestController
@RequestMapping("/api")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/member")
	public List<MemberDto> selectMemberAll() {
		return memberService.selectMember();
	}
	
	@PutMapping("/member/{id}")
	public ResponseEntity<?> updateMemberById(@PathVariable Integer id, @RequestBody MemberBean entity) {
		MemberDto member = AuthHolder.getMember();
		if(member==null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("尚未登入");
		}
		if(!member.getMemberId().equals(id)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("你不能修改別人的資料");
		}
		memberService.updateMember(entity);
	    return ResponseEntity.ok(entity);
	}
	

}
