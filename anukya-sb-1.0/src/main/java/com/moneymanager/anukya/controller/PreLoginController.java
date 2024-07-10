package com.moneymanager.anukya.controller;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.model.BhaarathaSharesShareNameDetails;
import com.moneymanager.anukya.model.CommonResponse;
import com.moneymanager.anukya.model.CompleteUserDetails;
import com.moneymanager.anukya.model.Login;
import com.moneymanager.anukya.model.PreData;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesShareNameDetails;
import com.moneymanager.anukya.services.BhaarathaSharesServices;
import com.moneymanager.anukya.services.UnitedStatesOfAmericaSharesServices;
import com.moneymanager.anukya.services.UserServices;
import com.moneymanager.anukya.utils.AnukyaConstants;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/pre-login")
public class PreLoginController {

	@Autowired
	private UserServices userServices;

	@Autowired
	private BhaarathaSharesServices bhaarathaSharesServices;

	@Autowired
	private UnitedStatesOfAmericaSharesServices unitedStatesOfAmericaSharesServices;

	@GetMapping(value = "/user-availability/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonResponse userAvailability(@PathVariable(value = "userId") String userId) throws AnukyaException {

		CommonResponse commonResponse = userServices.userAvailability(userId);
		commonResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return commonResponse;
	}

	@PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonResponse registerUser(@RequestBody CompleteUserDetails completeUserDetails) throws AnukyaException {

		CommonResponse commonResponse = userServices.registerUser(completeUserDetails);
		commonResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return commonResponse;
	}

	@GetMapping(value = "/pre-data", produces = MediaType.APPLICATION_JSON_VALUE)
	public PreData getPreData() {

		PreData preData = userServices.getPreData();
		preData.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return preData;
	}

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public Login login(@RequestParam(value = "userEmailId", required = false) String userEmailId,
			@RequestParam(value = "password", required = false) String password) throws AnukyaException {

		Login login = userServices.login(userEmailId, password);
		login.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return login;
	}

	@GetMapping(value = "/bhaaratha-shares/share-name-details", produces = MediaType.APPLICATION_JSON_VALUE)
	public BhaarathaSharesShareNameDetails getBhaarathaSharesShareNameDetails(
			@RequestParam(value = "stockExchange", required = false) String stockExchange,
			@RequestParam(value = "scriptCode", required = false) String scriptCode) throws AnukyaException {

		BhaarathaSharesShareNameDetails bhaarathaSharesShareNameDetails = bhaarathaSharesServices
				.getShareNameDetails(stockExchange, scriptCode);
		bhaarathaSharesShareNameDetails.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return bhaarathaSharesShareNameDetails;
	}

	@GetMapping(value = "/united-states-of-america-shares/share-name-details", produces = MediaType.APPLICATION_JSON_VALUE)
	public UnitedStatesOfAmericaSharesShareNameDetails getUnitedStatesOfAmericaSharesShareNameDetails(
			@RequestParam(value = "scriptCode", required = false) String scriptCode) throws AnukyaException {

		UnitedStatesOfAmericaSharesShareNameDetails unitedStatesOfAmericaSharesShareNameDetails = unitedStatesOfAmericaSharesServices
				.getShareNameDetails(scriptCode);
		unitedStatesOfAmericaSharesShareNameDetails.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return unitedStatesOfAmericaSharesShareNameDetails;
	}
}
