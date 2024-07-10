package com.moneymanager.anukya.services;

import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.model.BhaarathaShares;
import com.moneymanager.anukya.model.BhaarathaSharesAnalysis;
import com.moneymanager.anukya.model.BhaarathaSharesInvestmentResearchResponse;
import com.moneymanager.anukya.model.BhaarathaSharesOneShareDetailsResponse;
import com.moneymanager.anukya.model.BhaarathaSharesResponse;
import com.moneymanager.anukya.model.BhaarathaSharesShareNameDetails;
import com.moneymanager.anukya.model.BhaarathaSharesShortTermInvestment;
import com.moneymanager.anukya.model.BhaarathaSharesShortTermInvestmentResponse;
import com.moneymanager.anukya.model.CommonResponse;

public interface BhaarathaSharesServices {

	BhaarathaSharesShareNameDetails getShareNameDetails(String stockExchange, String scriptCode) throws AnukyaException;

	CommonResponse addBhaarathaSharesShortTermInvestment(String userEmailId,
			BhaarathaSharesShortTermInvestment bhaarathaSharesShortTermInvestment) throws AnukyaException;

	BhaarathaSharesShortTermInvestmentResponse getShortTermInvestment(String userEmailId, String bseCode)
			throws AnukyaException;

	CommonResponse deleteShortTermInvestment(String userEmailId, String bseCode) throws AnukyaException;

	CommonResponse addBhaarathaShares(String header, String type, BhaarathaShares bhaarathaShares)
			throws AnukyaException;

	BhaarathaSharesResponse getBhaarathaShares(String header, String type, boolean isNonConsolidated, String searchTerm,
			boolean isLongTermOnly) throws AnukyaException;

	BhaarathaSharesOneShareDetailsResponse getOneShareDetails(String userEmailId, String type, String bseCode,
			String nseCode, String moneycontrolCode, String yahooBseCode, String yahooNseCode) throws AnukyaException;

	BhaarathaSharesAnalysis getCompleteAnalysis(String userEmailId) throws AnukyaException;

	ResponseEntity<InputStreamResource> downloadBulkUploadDocument() throws IOException, AnukyaException;

	CommonResponse bulkUpload(String userEmailId, MultipartFile bulkUploadFile) throws IOException, AnukyaException;

	BhaarathaSharesAnalysis getDividendYieldAnalysis(String userEmailId) throws AnukyaException, IOException;

	BhaarathaSharesInvestmentResearchResponse getInvestmentResearch(String userEmailId) throws AnukyaException;

}
