package com.moneymanager.anukya.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.model.CommonResponse;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaShares;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesAnalysis;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesInvestmentResearchResponse;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesResponse;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesShortTermInvestment;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesShortTermInvestmentResponse;
import com.moneymanager.anukya.services.UnitedStatesOfAmericaSharesServices;
import com.moneymanager.anukya.utils.AnukyaConstants;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController()
@RequestMapping(value = "/post-login/united-states-of-america-shares")
@PreAuthorize("@anukyaAuthorization.validateToken(#httpRequest)")
public class UnitedStatesOfAmericaSharesController {

	@Autowired
	UnitedStatesOfAmericaSharesServices unitedStatesOfAmericaSharesServices;

	@PostMapping(value = "shares", produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonResponse addUnitedStatesOfAmericaShares(HttpServletRequest httpRequest,
			@RequestParam(value = "type") String type,
			@RequestBody UnitedStatesOfAmericaShares unitedStatesOfAmericaShares) throws AnukyaException {

		CommonResponse commonResponse = unitedStatesOfAmericaSharesServices.addShares(
				httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID), type, unitedStatesOfAmericaShares);
		commonResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return commonResponse;
	}

	@GetMapping(value = "shares", produces = MediaType.APPLICATION_JSON_VALUE)
	public UnitedStatesOfAmericaSharesResponse getUnitedStatesOfAmericaShares(HttpServletRequest httpRequest,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "isNonConsolidated", required = false) boolean isNonConsolidated,
			@RequestParam(value = "searchTerm", required = false, defaultValue = "") String searchTerm,
			@RequestParam(value = "isLongTermOnly", required = false) boolean isLongTermOnly) throws AnukyaException {

		UnitedStatesOfAmericaSharesResponse unitedStatesOfAmericaSharesResponse = unitedStatesOfAmericaSharesServices
				.getUnitedStatesOfAmericaShares(httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID), type,
						isNonConsolidated, searchTerm, isLongTermOnly);
		unitedStatesOfAmericaSharesResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return unitedStatesOfAmericaSharesResponse;
	}

	@GetMapping(value = "complete-analysis", produces = MediaType.APPLICATION_JSON_VALUE)
	public UnitedStatesOfAmericaSharesAnalysis getCompleteAnalysis(HttpServletRequest httpRequest)
			throws AnukyaException {

		UnitedStatesOfAmericaSharesAnalysis unitedStatesOfAmericaSharesAnalysis = unitedStatesOfAmericaSharesServices
				.getCompleteAnalysis(httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID));
		unitedStatesOfAmericaSharesAnalysis.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return unitedStatesOfAmericaSharesAnalysis;
	}

	@GetMapping(value = "dividend-yield-analysis", produces = MediaType.APPLICATION_JSON_VALUE)
	public UnitedStatesOfAmericaSharesAnalysis getDividebdYieldAnalysis(HttpServletRequest httpRequest)
			throws AnukyaException {

		UnitedStatesOfAmericaSharesAnalysis unitedStatesOfAmericaSharesAnalysis = unitedStatesOfAmericaSharesServices
				.getDividendYieldAnalysis(httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID));
		unitedStatesOfAmericaSharesAnalysis.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return unitedStatesOfAmericaSharesAnalysis;
	}

	@GetMapping(value = "investment-research", produces = MediaType.APPLICATION_JSON_VALUE)
	public UnitedStatesOfAmericaSharesInvestmentResearchResponse getInvestmentResearch(HttpServletRequest httpRequest)
			throws AnukyaException {

		UnitedStatesOfAmericaSharesInvestmentResearchResponse unitedStatesOfAmericaSharesInvestmentResearchResponse = unitedStatesOfAmericaSharesServices
				.getInvestmentResearch(httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID));
		unitedStatesOfAmericaSharesInvestmentResearchResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return unitedStatesOfAmericaSharesInvestmentResearchResponse;
	}

	@PostMapping(value = "short-term-investment", produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonResponse addShortTermInvestment(HttpServletRequest httpRequest,
			@RequestBody UnitedStatesOfAmericaSharesShortTermInvestment unitedStatesOfAmericaSharesShortTermInvestment)
			throws AnukyaException {

		CommonResponse commonResponse = unitedStatesOfAmericaSharesServices
				.addUnitedStatesOfAmericaSharesShortTermInvestment(
						httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID),
						unitedStatesOfAmericaSharesShortTermInvestment);
		commonResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return commonResponse;
	}

	@GetMapping(value = "short-term-investment", produces = MediaType.APPLICATION_JSON_VALUE)
	public UnitedStatesOfAmericaSharesShortTermInvestmentResponse getShortTermInvestment(HttpServletRequest httpRequest,
			@RequestParam(value = "yahooCode", required = false) String yahooCode) throws AnukyaException {

		UnitedStatesOfAmericaSharesShortTermInvestmentResponse unitedStatesOfAmericaSharesShortTermInvestmentResponse = unitedStatesOfAmericaSharesServices
				.getShortTermInvestment(httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID), yahooCode);
		unitedStatesOfAmericaSharesShortTermInvestmentResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return unitedStatesOfAmericaSharesShortTermInvestmentResponse;
	}

	@DeleteMapping(value = "short-term-investment", produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonResponse deleteShortTermInvestment(HttpServletRequest httpRequest,
			@RequestParam(value = "yahooCode", required = false) String yahooCode) throws AnukyaException {

		CommonResponse commonResponse = unitedStatesOfAmericaSharesServices
				.deleteShortTermInvestment(httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID), yahooCode);
		commonResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return commonResponse;
	}
}
