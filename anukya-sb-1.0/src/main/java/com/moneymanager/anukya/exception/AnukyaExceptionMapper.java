package com.moneymanager.anukya.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AnukyaExceptionMapper {

	@ExceptionHandler({ AnukyaException.class })
	@ResponseBody
	@RequestMapping(produces = "application/json")
	public ResponseEntity<Object> toResponse(AnukyaException objAnukyaException) {
		List<ErrorDetails> error = objAnukyaException.getError();
		String errorDescription = objAnukyaException.getMessage();
		String status = objAnukyaException.getStatus();
		String traceId = objAnukyaException.getTraceId();

		AnukyaFault fault = new AnukyaFault(error, errorDescription, status, traceId);

		HttpStatus httpStatus;

		if (status.equals(AnukyaErrorConstants.BAD_REQUEST)) {
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if (status.equals(AnukyaErrorConstants.UNAUTHORIZED)) {
			httpStatus = HttpStatus.UNAUTHORIZED;
		} else if (status.equals(AnukyaErrorConstants.NO_CONTENT)) {
			httpStatus = HttpStatus.NO_CONTENT;
		} else {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(fault, httpStatus);
	}
}
