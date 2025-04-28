package com.topics.utils;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

	public static ResponseEntity<Map<String, Object>> success(Object data) {
		return ResponseEntity.ok(Map.of(
				"success", true,
				"data", data));
	}

	public static ResponseEntity<Map<String, Object>> created(Object data) {
		return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
				"success", true,
				"data", data));
	}

	public static ResponseEntity<Map<String, Object>> message(String msg) {
		return ResponseEntity.ok(Map.of(
				"success", true,
				"message", msg));
	}

	public static ResponseEntity<Map<String, Object>> error(HttpStatus status, String msg) {
		return ResponseEntity.status(status).body(Map.of(
				"success", false,
				"message", msg));
	}
}
