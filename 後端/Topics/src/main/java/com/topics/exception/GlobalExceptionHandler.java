package com.topics.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.topics.utils.ResponseUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@ControllerAdvice
public class GlobalExceptionHandler {

	/* === 權限 === */
	// 登入失敗 > 401
	@ExceptionHandler(LoginFailException.class)
	public ResponseEntity<?> handleInsufficientAuthenticationException(Exception exception) {
		return ResponseUtil.error(HttpStatus.UNAUTHORIZED, "登入失敗，帳號或密碼錯誤");
	}

	// jwt 過期 > 401
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<?> handleJwtExpiredException(Exception exception) {
		return ResponseUtil.error(HttpStatus.UNAUTHORIZED, "jwt token 已過期，請重新登入。");
	}

	// jwt 解析錯誤 > 401
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<?> handleClaimJwtException(Exception exception) {
		return ResponseUtil.error(HttpStatus.UNAUTHORIZED, "jwt token 不合法。");
	}

	// 自訂權限不足 > 403
	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<?> handleForbiddenException(ForbiddenException ex) {
		return ResponseUtil.error(HttpStatus.FORBIDDEN, ex.getMessage());
	}

	// 查無資料 > 404
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
		return ResponseUtil.error(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	// 其他錯誤 > 500
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleAllExceptions(Exception ex) {
		ex.printStackTrace();
		return ResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, "伺服器錯誤，請稍後再試");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
		return ResponseUtil.error(HttpStatus.BAD_REQUEST, "參數驗證錯誤");
	}

	@ExceptionHandler(EmailSendException.class)
	public ResponseEntity<?> handleEmailSendException(EmailSendException ex) {
		return ResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
	}

	@ExceptionHandler(PasswordErrorException.class)
	public ResponseEntity<?> handlePasswordErrorException(PasswordErrorException ex) {
		return ResponseUtil.error(HttpStatus.BAD_REQUEST, ex.getMessage());
	}
	
	@ExceptionHandler(RegisterException.class)
	public ResponseEntity<?> handleRegisterException(RegisterException ex) {
		return ResponseUtil.error(HttpStatus.BAD_REQUEST, ex.getMessage());
	}
}
