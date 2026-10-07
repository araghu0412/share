package com.example.rally.exception;

import java.time.OffsetDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(QueryNotSupportedException.class)
	public ResponseEntity<ApiErrorResponse> handleQueryNotSupported(QueryNotSupportedException ex) {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
				.body(new ApiErrorResponse("QUERY_NOT_SUPPORTED", ex.getMessage(), OffsetDateTime.now()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiErrorResponse> handleBadQuery(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(new ApiErrorResponse("QUERY_NOT_SUPPORTED",
				"We cannot provide an answer for this question.", OffsetDateTime.now()));
	}
}
