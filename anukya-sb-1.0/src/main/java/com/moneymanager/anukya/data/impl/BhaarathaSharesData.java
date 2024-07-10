package com.moneymanager.anukya.data.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.ErrorDetails;
import com.moneymanager.anukya.model.BhaarathaShares;
import com.moneymanager.anukya.model.BhaarathaSharesShortTermInvestment;
import com.moneymanager.anukya.utils.AnukyaConstants;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class BhaarathaSharesData {

	// Combined All Bhaaratha Shares short term investment
	public List<BhaarathaSharesShortTermInvestment> getBhaarathaSharesShortTermInvestmentList(
			File bhaarathaSharesShortTermInvestmentFile) throws AnukyaException {

		if (!bhaarathaSharesShortTermInvestmentFile.exists()) {
			return new ArrayList<>();
		}

		String jsonString;
		ObjectMapper mapper = new ObjectMapper();

		try {
			jsonString = FileUtils.readFileToString(bhaarathaSharesShortTermInvestmentFile,
					StandardCharsets.ISO_8859_1);
			return mapper.readValue(jsonString, new TypeReference<List<BhaarathaSharesShortTermInvestment>>() {
			});
		} catch (IOException e) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while reading a file -> ");
			logMessage.append("Message: " + e.getMessage());
			logMessage.append("Reason: Internal error");
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();
			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.FE_0003);
			errorDetails.setErrorMessage("Read error");
			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while getting all Bhaaratha shares short term investment directory",
					errorDetailsList, AnukyaErrorConstants.INTERNAL_SERVER_ERROR,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	public List<BhaarathaShares> getBhaarathaShares(File bhaarathaSharesFile) throws AnukyaException {

		List<BhaarathaShares> bhaarathaShares = new ArrayList<>();

		String jsonString;
		ObjectMapper mapper = new ObjectMapper();

		try {
			jsonString = FileUtils.readFileToString(bhaarathaSharesFile, StandardCharsets.UTF_8);
			bhaarathaShares = mapper.readValue(jsonString, new TypeReference<List<BhaarathaShares>>() {
			});
		} catch (IOException e) {

			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while reading a file -> ");
			logMessage.append("Message: " + e.getMessage() + " | ");
			logMessage.append("Reason: Internal error");
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();
			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.FE_0003);
			errorDetails.setErrorMessage("Read error");
			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while getting Bhaaratha shares. (Unable to read file)", errorDetailsList,
					AnukyaErrorConstants.INTERNAL_SERVER_ERROR, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		return bhaarathaShares;
	}
}
