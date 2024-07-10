package com.moneymanager.anukya.exception;

import java.util.List;

public class AnukyaFault {

	private List<ErrorDetails> error;
	private String errorDescription;
	private String status;
	private String traceId;

	public AnukyaFault(List<ErrorDetails> error, String errorDescription, String status, String traceId) {
		this.error = error;
		this.errorDescription = errorDescription;
		this.status = status;
		this.traceId = traceId;
	}

	public List<ErrorDetails> getError() {
		return error;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public String getStatus() {
		return status;
	}

	public String getTraceId() {
		return traceId;
	}

}
