package com.topics.member.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.topics.member.model.dto.MemberDto;
import com.topics.member.model.entity.MemberBean;
import com.topics.member.model.service.MemberService;
import com.topics.member.utils.ResponseUtil;
import com.topics.member.utils.SecurityUtil;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@GetMapping("/member")
	public ResponseEntity<Map<String, Object>> findMemberAll() {
		List<MemberDto> member = memberService.findMember();
		return ResponseUtil.success(member);
	}

	@PutMapping("/member/{id}")
	public ResponseEntity<?> updateMemberById(@PathVariable Integer id, @RequestBody MemberBean entity) {
		SecurityUtil.checkAdminPermission();
		MemberDto member = memberService.updateMemberById(id, entity);
		return ResponseUtil.success(member);
	}

	@DeleteMapping("/member/{id}")
	public ResponseEntity<?> deleteMemberById(@PathVariable Integer id) {
		SecurityUtil.checkAdminPermission();
		memberService.deleteMemberById(id);
		return ResponseUtil.message("會員刪除成功");
	}

	@PostMapping("/member")
	public ResponseEntity<?> insertMember(@RequestBody MemberBean entity) {
		MemberDto member = memberService.insertMember(entity);
		return ResponseUtil.created(member);
	}

}
