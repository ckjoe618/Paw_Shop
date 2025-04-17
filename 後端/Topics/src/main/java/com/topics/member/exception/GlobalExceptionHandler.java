package com.topics.member.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@ControllerAdvice
public class GlobalExceptionHandler {

	/* === 權限 === */
	// 登入失敗 > 401
	@ExceptionHandler(LoginFailException.class)
	public ResponseEntity<?> handleInsufficientAuthenticationException(Exception exception) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(Map.of("success", false, "message", "登入失敗，帳號或密碼錯誤"));
	}

	// jwt 過期 > 401
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<?> handleJwtExpiredException(Exception exception) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(Map.of("success", false, "message", "jwt token 已過期，請重新登入。"));
	}

	// jwt 解析錯誤 > 401
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<?> handleClaimJwtException(Exception exception) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(Map.of("success", false, "message", "jwt token 不合法。"));
	}
}
