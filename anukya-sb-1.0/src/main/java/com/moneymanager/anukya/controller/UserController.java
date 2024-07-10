package com.moneymanager.anukya.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.model.CommonResponse;
import com.moneymanager.anukya.model.TokenDetails;
import com.moneymanager.anukya.model.UpdateCountriesAndServicesResponse;
import com.moneymanager.anukya.model.UserOptedCountriesAndServices;
import com.moneymanager.anukya.services.UserServices;
import com.moneymanager.anukya.utils.AnukyaConstants;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController()
@RequestMapping(value = "/post-login/users")
@PreAuthorize("@anukyaAuthorization.validateToken(#httpRequest)")
public class UserController {

	@Autowired
	private UserServices userServices;

	@GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonResponse logout(HttpServletRequest httpRequest) throws AnukyaException {

		CommonResponse commonResponse = userServices
				.logout(httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID));
		commonResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return commonResponse;
	}

	@PostMapping(value = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
	public TokenDetails refreshToken(HttpServletRequest httpRequest,
			@RequestParam(value = "pin", required = false) String pin,
			@RequestParam(value = "refreshToken", required = false) String refreshToken) throws AnukyaException {

		TokenDetails tokenDetails = userServices
				.refreshToken(httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID), pin, refreshToken);
		tokenDetails.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return tokenDetails;
	}

	@PostMapping(value = "update-password", produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonResponse updatePassword(HttpServletRequest httpRequest,
			@RequestParam(value = "oldPassword", required = false) String oldPassword,
			@RequestParam(value = "newPassword", required = false) String newPassword,
			@RequestParam(value = "confirmNewPassword", required = false) String confirmNewPassword)
			throws AnukyaException {

		CommonResponse commonResponse = userServices.updatePassword(
				httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID), oldPassword, newPassword,
				confirmNewPassword);
		commonResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return commonResponse;
	}

	@PostMapping(value = "update-pin", produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonResponse updatePin(HttpServletRequest httpRequest,
			@RequestParam(value = "oldPin", required = false) String oldPin,
			@RequestParam(value = "newPin", required = false) String newPin,
			@RequestParam(value = "confirmNewPin", required = false) String confirmNewPin) throws AnukyaException {

		CommonResponse commonResponse = userServices.updatePin(
				httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID), oldPin, newPin, confirmNewPin);
		commonResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return commonResponse;
	}

	@PostMapping(value = "update-countries-and-services", produces = MediaType.APPLICATION_JSON_VALUE)
	public UpdateCountriesAndServicesResponse updateCountriesAndServices(HttpServletRequest httpRequest,
			@RequestBody UserOptedCountriesAndServices userOptedCountriesAndServices) throws AnukyaException {

		UpdateCountriesAndServicesResponse updateCountriesAndServicesResponse = userServices.updateCountriesAndServices(
				httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID), userOptedCountriesAndServices);
		updateCountriesAndServicesResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return updateCountriesAndServicesResponse;
	}
}
