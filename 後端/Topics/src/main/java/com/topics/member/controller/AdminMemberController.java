package com.topics.member.controller;

import java.util.List;
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
import com.topics.member.model.service.AdminMemberService;
import com.topics.utils.ResponseUtil;
import com.topics.utils.SecurityUtil;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/admin")
public class AdminMemberController {

	@Autowired
	private AdminMemberService adminMemberService;

	@PostMapping("/member")
	public ResponseEntity<?> insertMember(@RequestBody MemberBean entity) {
		SecurityUtil.checkAdminPermission();
		MemberDto member = adminMemberService.insertMember(entity);
		return ResponseUtil.created(member);
	}

	@PutMapping("/member/{id}")
	public ResponseEntity<?> updateMemberById(@PathVariable Integer id, @RequestBody MemberBean entity) {
		SecurityUtil.checkAdminPermission();
		MemberDto member = adminMemberService.updateMemberById(id, entity);
		return ResponseUtil.success(member);
	}

	@DeleteMapping("/member/{id}")
	public ResponseEntity<?> deleteMemberById(@PathVariable Integer id) {
		SecurityUtil.checkAdminPermission();
		adminMemberService.deleteMemberById(id);
		return ResponseUtil.message("會員刪除成功");
	}

	@GetMapping("/member")
	public ResponseEntity<?> findMemberAll() {
		SecurityUtil.checkAdminPermission();
		List<MemberDto> memberList = adminMemberService.findMemberAll();
		return ResponseUtil.success(memberList);
	}

}
