package com.moneymanager.anukya.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.model.BhaarathaShares;
import com.moneymanager.anukya.model.BhaarathaSharesAnalysis;
import com.moneymanager.anukya.model.BhaarathaSharesInvestmentResearchResponse;
import com.moneymanager.anukya.model.BhaarathaSharesOneShareDetailsResponse;
import com.moneymanager.anukya.model.BhaarathaSharesResponse;
import com.moneymanager.anukya.model.BhaarathaSharesShortTermInvestment;
import com.moneymanager.anukya.model.BhaarathaSharesShortTermInvestmentResponse;
import com.moneymanager.anukya.model.CommonResponse;
import com.moneymanager.anukya.services.BhaarathaSharesServices;
import com.moneymanager.anukya.utils.AnukyaConstants;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController()
@RequestMapping(value = "/post-login/bhaaratha-shares")
@PreAuthorize("@anukyaAuthorization.validateToken(#httpRequest)")
public class BhaarathaSharesController {

	@Autowired
	private BhaarathaSharesServices bhaarathaSharesServices;

	@PostMapping(value = "short-term-investment", produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonResponse addShortTermInvestment(HttpServletRequest httpRequest,
			@RequestBody BhaarathaSharesShortTermInvestment bhaarathaSharesShortTermInvestment) throws AnukyaException {

		CommonResponse commonResponse = bhaarathaSharesServices.addBhaarathaSharesShortTermInvestment(
				httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID), bhaarathaSharesShortTermInvestment);
		commonResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return commonResponse;
	}

	@GetMapping(value = "short-term-investment", produces = MediaType.APPLICATION_JSON_VALUE)
	public BhaarathaSharesShortTermInvestmentResponse getShortTermInvestment(HttpServletRequest httpRequest,
			@RequestParam(value = "bseCode", required = false) String bseCode) throws AnukyaException {

		BhaarathaSharesShortTermInvestmentResponse bhaarathaSharesShortTermInvestmentResponse = bhaarathaSharesServices
				.getShortTermInvestment(httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID), bseCode);
		bhaarathaSharesShortTermInvestmentResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return bhaarathaSharesShortTermInvestmentResponse;
	}

	@DeleteMapping(value = "short-term-investment", produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonResponse deleteShortTermInvestment(HttpServletRequest httpRequest,
			@RequestParam(value = "bseCode", required = false) String bseCode) throws AnukyaException {

		CommonResponse commonResponse = bhaarathaSharesServices
				.deleteShortTermInvestment(httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID), bseCode);
		commonResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return commonResponse;
	}

	@PostMapping(value = "shares", produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonResponse addBhaarathaShares(HttpServletRequest httpRequest, @RequestParam(value = "type") String type,
			@RequestBody BhaarathaShares bhaarathaShares) throws AnukyaException {

		CommonResponse commonResponse = bhaarathaSharesServices.addBhaarathaShares(
				httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID), type, bhaarathaShares);
		commonResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return commonResponse;
	}

	@GetMapping(value = "shares", produces = MediaType.APPLICATION_JSON_VALUE)
	public BhaarathaSharesResponse getBhaarathaShares(HttpServletRequest httpRequest,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "isNonConsolidated", required = false) boolean isNonConsolidated,
			@RequestParam(value = "searchTerm", required = false, defaultValue = "") String searchTerm,
			@RequestParam(value = "isLongTermOnly", required = false) boolean isLongTermOnly) throws AnukyaException {

		BhaarathaSharesResponse bhaarathaSharesResponse = bhaarathaSharesServices.getBhaarathaShares(
				httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID), type, isNonConsolidated, searchTerm,
				isLongTermOnly);
		bhaarathaSharesResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return bhaarathaSharesResponse;
	}

	@GetMapping(value = "one-share-details", produces = MediaType.APPLICATION_JSON_VALUE)
	public BhaarathaSharesOneShareDetailsResponse getOneShareDetails(HttpServletRequest httpRequest,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "bseCode", required = false) String bseCode,
			@RequestParam(value = "nseCode", required = false) String nseCode,
			@RequestParam(value = "moneycontrolCode", required = false) String moneycontrolCode,
			@RequestParam(value = "yahooBseCode", required = false) String yahooBseCode,
			@RequestParam(value = "yahooNseCode", required = false) String yahooNseCode) throws AnukyaException {

		BhaarathaSharesOneShareDetailsResponse bhaarathaSharesOneShareDetailsResponse = bhaarathaSharesServices
				.getOneShareDetails(httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID), type, bseCode,
						nseCode, moneycontrolCode, yahooBseCode, yahooNseCode);
		bhaarathaSharesOneShareDetailsResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return bhaarathaSharesOneShareDetailsResponse;
	}

	@GetMapping(value = "complete-analysis", produces = MediaType.APPLICATION_JSON_VALUE)
	public BhaarathaSharesAnalysis getCompleteAnalysis(HttpServletRequest httpRequest) throws AnukyaException {

		BhaarathaSharesAnalysis bhaarathaSharesAnalysis = bhaarathaSharesServices
				.getCompleteAnalysis(httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID));
		bhaarathaSharesAnalysis.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return bhaarathaSharesAnalysis;
	}

	@GetMapping(value = "download-bulk-upload-document")
	public ResponseEntity<InputStreamResource> downloadBulkUploadDocument(HttpServletRequest httpRequest)
			throws IOException, AnukyaException {

		return bhaarathaSharesServices.downloadBulkUploadDocument();
	}

	@PostMapping(value = "bulk-upload-document", produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonResponse bulkUpload(HttpServletRequest httpRequest,
			@RequestParam(name = "file") MultipartFile bulkUploadFile) throws IOException, AnukyaException {

		CommonResponse commonResponse = bhaarathaSharesServices
				.bulkUpload(httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID), bulkUploadFile);
		commonResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return commonResponse;
	}

	@GetMapping(value = "dividend-yield-analysis", produces = MediaType.APPLICATION_JSON_VALUE)
	public BhaarathaSharesAnalysis getDividebdYieldAnalysis(HttpServletRequest httpRequest)
			throws AnukyaException, IOException {

		BhaarathaSharesAnalysis bhaarathaSharesAnalysis = bhaarathaSharesServices
				.getDividendYieldAnalysis(httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID));
		bhaarathaSharesAnalysis.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return bhaarathaSharesAnalysis;
	}

	@GetMapping(value = "investment-research", produces = MediaType.APPLICATION_JSON_VALUE)
	public BhaarathaSharesInvestmentResearchResponse getInvestmentResearch(HttpServletRequest httpRequest)
			throws AnukyaException {

		BhaarathaSharesInvestmentResearchResponse bhaarathaSharesInvestmentResearchResponse = bhaarathaSharesServices
				.getInvestmentResearch(httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID));
		bhaarathaSharesInvestmentResearchResponse.setTraceId(ThreadContext.get(AnukyaConstants.TRACE_ID));

		return bhaarathaSharesInvestmentResearchResponse;
	}
}
