package com.moneymanager.anukya.exception;

public class ErrorDetails {

	private String errorCode;
	private String errorMessage;
	private String uiField;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getUiField() {
		return uiField;
	}

	public void setUiField(String uiField) {
		this.uiField = uiField;
	}
}
