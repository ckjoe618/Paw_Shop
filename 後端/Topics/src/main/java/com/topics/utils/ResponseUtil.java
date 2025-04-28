package com.topics.utils;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

	public static ResponseEntity<?> success(Object data) {
		return ResponseEntity.ok(Map.of(
				"data", data));
	}

	public static ResponseEntity<?> created(Object data) {
		return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
				"data", data));
	}

	public static ResponseEntity<?> message(String msg) {
		return ResponseEntity.ok(Map.of(
				"message", msg));
	}

	public static ResponseEntity<?> error(HttpStatus status, String msg) {
		return ResponseEntity.status(status).body(Map.of(
				"message", msg));
	}
}
