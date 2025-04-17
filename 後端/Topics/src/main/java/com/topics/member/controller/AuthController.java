package com.topics.member.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.topics.member.model.dto.AuthDto;
import com.topics.member.model.dto.MemberDto;
import com.topics.member.model.service.AuthService;
import com.topics.member.utils.JwtUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> postMethodName(@RequestBody AuthDto info) {
		MemberDto loginMember = authService.login(info);
		String token = JwtUtil.generateToken(loginMember.getMemberId().toString(), 
				loginMember.getRole(),
				loginMember.getMemberName());

		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("token", token);
		response.put("memberId", loginMember.getMemberId());
		response.put("memberName", loginMember.getMemberName());

		return ResponseEntity.ok(response);
	}

}
