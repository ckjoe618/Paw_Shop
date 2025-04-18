package com.topics.member.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.topics.member.security.AuthHolder;

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
		MemberDto memberDto = AuthHolder.getMember();
		if (memberDto == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("尚未登入");
		}
		if (!memberDto.getRole().equals("ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("沒有權限");
		}
		MemberBean memberBean = memberService.selectMembeEntityrById(id);
		entity.setAccount(memberBean.getAccount());
		entity.setPassword(memberBean.getPassword());
		memberService.updateMember(entity);
		return ResponseEntity.ok(entity);
	}

	@DeleteMapping("/member/{id}")
	public ResponseEntity<?> deleteMemberById(@PathVariable Integer id) {
		MemberDto member = AuthHolder.getMember();
		if (member == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("尚未登入");
		}
		if (!member.getRole().equals("ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("沒有權限");
		}
		try {
			memberService.deleteMemberById(id);
			return ResponseEntity.ok("會員刪除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("會員停用失敗");
		}
	}
}
