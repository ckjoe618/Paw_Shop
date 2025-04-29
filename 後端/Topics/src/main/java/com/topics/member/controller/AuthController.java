package com.topics.member.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.topics.member.model.dto.AuthDto;
import com.topics.member.model.dto.MemberDto;
import com.topics.member.model.service.AuthService;
import com.topics.member.model.service.UserMemberService;
import com.topics.utils.EmailUtil;
import com.topics.utils.JwtUtil;
import com.topics.utils.ResponseUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@Autowired
	private EmailUtil emailService;

	@Autowired
	private UserMemberService userMemberService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthDto info) {
		MemberDto loginMember = authService.login(info);
		String token = JwtUtil.generateToken(loginMember.getMemberId().toString());

		Map<String, Object> response = new HashMap<>();
		response.put("token", token);
		response.put("memberId", loginMember.getMemberId());
		response.put("memberName", loginMember.getMemberName());
		response.put("gender", loginMember.getGender());
		response.put("idno", loginMember.getIdno());
		response.put("email", loginMember.getEmail());
		response.put("phone", loginMember.getPhone());
		response.put("birthDate", loginMember.getBirthDate());
		response.put("role", loginMember.getRole());
		response.put("memberPhoto", loginMember.getMemberPhoto());
		response.put("address", loginMember.getAddress());

		return ResponseUtil.success(response);
	}

//	@GetMapping("/oauth2/callback/google")
//	public ResponseEntity<?> googleCallback(@RequestParam String code) {
//		MemberDto loginMember = authService.loginByGoogle(code, clientId, clientSecret, redirectUri);
//		String token = JwtUtil.generateToken(String.valueOf(loginMember.getMemberId()));
//
//		Map<String, Object> response = new HashMap<>();
//		response.put("token", token);
//		response.put("memberId", loginMember.getMemberId());
//		response.put("memberName", loginMember.getMemberName());
//		response.put("gender", loginMember.getGender());
//		response.put("idno", loginMember.getIdno());
//		response.put("email", loginMember.getEmail());
//		response.put("phone", loginMember.getPhone());
//		response.put("birthDate", loginMember.getBirthDate());
//		response.put("role", loginMember.getRole());
//		response.put("memberPhoto", loginMember.getMemberPhoto());
//		response.put("address", loginMember.getAddress());
//
//		return ResponseUtil.success(response);
//	}
//
//	@SuppressWarnings("deprecation")
//	@GetMapping("/oauth2/authorize/google")
//	public ResponseEntity<?> loginByGoogle() {
//		String base = "https://accounts.google.com/o/oauth2/v2/auth";
//		String url = UriComponentsBuilder.fromHttpUrl(base)
//				.queryParam("client_id", clientId)
//				.queryParam("redirect_uri", redirectUri)
//				.queryParam("response_type", "code")
//				.queryParam("scope", "openid email profile")
//				.build()
//				.toUriString();
//
//		Map<String, String> response = new HashMap<>();
//		response.put("url", url);
//		return ResponseUtil.success(response);
//	}

	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> data) {
		String email = data.get("email");
		userMemberService.findMemberByEmail(email);
		String token = JwtUtil.generateResetToken(email);
		String resetUrl = "http://localhost:5173/reset-password?token=" + token;
		emailService.sendResetPasswordEmail(email, resetUrl);
		return ResponseUtil.success("重設密碼連結已寄出");
	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody AuthDto info) {
		String email = JwtUtil.getSubject(info.getToken());
		userMemberService.resetPasswordByEmail(email, info.getPassword());
		return ResponseUtil.success("密碼重設成功");
	}

}
