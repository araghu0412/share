package com.moneymanager.anukya.exception;

import java.util.List;

public class AnukyaException extends Exception {

	private static final long serialVersionUID = 1L;
	private final List<ErrorDetails> error;
	private final String status;
	private final String traceId;

	public AnukyaException(String errorDescription, List<ErrorDetails> error, String status, String traceId) {
		super(errorDescription);
		this.error = error;
		this.status = status;
		this.traceId = traceId;
	}

	public List<ErrorDetails> getError() {
		return error;
	}

	public String getStatus() {
		return status;
	}

	public String getTraceId() {
		return traceId;
	}
}
