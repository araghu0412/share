package com.example.rally.exception;

public class QueryNotSupportedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public QueryNotSupportedException(String message) {
		super(message);
	}

	public QueryNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}
}
