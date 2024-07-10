package com.moneymanager.anukya.services.impl;

import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.moneymanager.anukya.data.impl.UnitedStatesOfAmericaSharesData;
import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.handler.UnitedStatesOfAmericaSharesExceptionHandler;
import com.moneymanager.anukya.model.CommonResponse;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaShares;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesAnalysis;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesCalculationDetails;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesCategory;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesInvestmentResearch;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesInvestmentResearchResponse;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesLastTradingPrice;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesOneCategoryDetails;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesOneSectorDetails;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesOneShareDetails;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesResponse;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesSector;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesShareNameDetails;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesShortTermInvestment;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesShortTermInvestmentResponse;
import com.moneymanager.anukya.servicecall.UnitedStatesOfAmericaSharesServiceCall;
import com.moneymanager.anukya.services.UnitedStatesOfAmericaSharesServices;
import com.moneymanager.anukya.unitedstatesofamerica.eq.AbstractUnitedStatesOfAmericaShares;
import com.moneymanager.anukya.unitedstatesofamerica.factories.UnitedStatesOfAmericaSharesFactory;
import com.moneymanager.anukya.utils.AnukyaConstants;
import com.moneymanager.anukya.utils.AnukyaDataUtils;
import com.moneymanager.anukya.utils.AnukyaUtils;
import com.moneymanager.anukya.utils.UnitedStatesOfAmericaExpenditureUtils;
import com.moneymanager.anukya.utils.UnitedStatesOfAmericaSharesUtils;

@Service
public class UnitedStatesOfAmericaServicesImpl implements UnitedStatesOfAmericaSharesServices {

	@Value("${DATABASE.BASE.LOCATION}")
	private String databaseBaseLocation;

	@Value("${UNITED.STATES.OF.AMERICA.SHARES.DIVIDEND.YIELD.ANALYSIS.VALUE}")
	private String unitedStatesOfAmericaSharesDividendYieldAnalysisValue;

	@Autowired
	private UnitedStatesOfAmericaSharesExceptionHandler unitedStatesOfAmericaSharesExceptionHandler;

	@Autowired
	private UnitedStatesOfAmericaSharesServiceCall unitedStatesOfAmericaSharesServiceCall;

	@Autowired
	private UnitedStatesOfAmericaSharesFactory unitedStatesOfAmericaSharesFactory;

	@Autowired
	private UnitedStatesOfAmericaSharesData unitedStatesOfAmericaSharesData;

	@Autowired
	private UnitedStatesOfAmericaExpenditureUtils unitedStatesOfAmericaExpenditureUtils;

	@Autowired
	private AnukyaUtils anukyaUtils;

	@Autowired
	private AnukyaDataUtils anukyaDataUtils;

	@Autowired
	UnitedStatesOfAmericaSharesUtils unitedStatesOfAmericaSharesUtils;

	@Override
	public UnitedStatesOfAmericaSharesShareNameDetails getShareNameDetails(String scriptCode) throws AnukyaException {

		unitedStatesOfAmericaSharesExceptionHandler.shareNameDetailsException(scriptCode);

		return unitedStatesOfAmericaSharesServiceCall.getUnitedStatesOfAmericaSharesShareNameDetails(scriptCode);
	}

	@Override
	public CommonResponse addShares(String userEmailId, String type,
			UnitedStatesOfAmericaShares unitedStatesOfAmericaShares) throws AnukyaException {

		if (type.equalsIgnoreCase(AnukyaConstants.BOUGHT_CONSTANT)) {
			unitedStatesOfAmericaSharesExceptionHandler.boughtSharesException(unitedStatesOfAmericaShares);
		} else if (type.equalsIgnoreCase(AnukyaConstants.SOLD_CONSTANT)) {
			unitedStatesOfAmericaSharesExceptionHandler.soldSharesException(unitedStatesOfAmericaShares);
		}

		AbstractUnitedStatesOfAmericaShares abstractUnitedStatesOfAmericaShares = unitedStatesOfAmericaSharesFactory
				.getUnitedStatesOfAmericaShares(type);
		abstractUnitedStatesOfAmericaShares.userEmailId = userEmailId;
		return abstractUnitedStatesOfAmericaShares.addShares(unitedStatesOfAmericaShares);
	}

	@Override
	public UnitedStatesOfAmericaSharesResponse getUnitedStatesOfAmericaShares(String userEmailId, String type,
			boolean isNonConsolidated, String searchTerm, boolean isLongTermOnly) throws AnukyaException {

		AbstractUnitedStatesOfAmericaShares abstractUnitedStatesOfAmericaShares = unitedStatesOfAmericaSharesFactory
				.getUnitedStatesOfAmericaShares(type);
		abstractUnitedStatesOfAmericaShares.userEmailId = userEmailId;
		return abstractUnitedStatesOfAmericaShares.getUnitedStatesOfAmericaShares(isNonConsolidated, searchTerm,
				isLongTermOnly);
	}

	@Override
	public UnitedStatesOfAmericaSharesAnalysis getCompleteAnalysis(String userEmailId) throws AnukyaException {

		File consolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_PURCHASE_MAIN_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		List<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(consolidatedFile);

		if (unitedStatesOfAmericaSharesList.isEmpty()) {
			throw new AnukyaException("No content for UnitedStatesOfAmerica Shares analysis", null,
					AnukyaErrorConstants.NO_CONTENT, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		return getAnalysis(unitedStatesOfAmericaSharesList);
	}

	@Override
	public UnitedStatesOfAmericaSharesAnalysis getDividendYieldAnalysis(String userEmailId) throws AnukyaException {

		File consolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_PURCHASE_MAIN_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		List<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(consolidatedFile);

		if (unitedStatesOfAmericaSharesList.isEmpty()) {
			throw new AnukyaException("No content for United States of America Shares analysis", null,
					AnukyaErrorConstants.NO_CONTENT, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		List<UnitedStatesOfAmericaShares> dividendYieldUnitedStatesOfAmericaSharesList = new ArrayList<>();

		for (UnitedStatesOfAmericaShares unitedStatesOfAmericaShares : unitedStatesOfAmericaSharesList) {

			UnitedStatesOfAmericaSharesLastTradingPrice unitedStatesOfAmericaSharesLastTradingPrice = unitedStatesOfAmericaSharesServiceCall
					.getUnitedStatesOfAmericaSharesLastTradingPrice(unitedStatesOfAmericaShares.getYahooCode());

			BigDecimal ttmDividendYield = getTtmDividendYield(unitedStatesOfAmericaShares.getYahooCode(),
					unitedStatesOfAmericaSharesLastTradingPrice.getLastTradingPrice());

			BigDecimal dividendYieldAnalysisValueBigDecimal = new BigDecimal(
					unitedStatesOfAmericaSharesDividendYieldAnalysisValue);

			if (ttmDividendYield.compareTo(dividendYieldAnalysisValueBigDecimal) > 0) {
				dividendYieldUnitedStatesOfAmericaSharesList.add(unitedStatesOfAmericaShares);
			}
		}

		return getAnalysis(dividendYieldUnitedStatesOfAmericaSharesList);
	}

	@Override
	public UnitedStatesOfAmericaSharesInvestmentResearchResponse getInvestmentResearch(String userEmailId)
			throws AnukyaException {

		UnitedStatesOfAmericaSharesInvestmentResearchResponse unitedStatesOfAmericaSharesInvestmentResearchResponse = new UnitedStatesOfAmericaSharesInvestmentResearchResponse();
		File consolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_PURCHASE_MAIN_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		List<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(consolidatedFile);

		if (unitedStatesOfAmericaSharesList.isEmpty()) {
			throw new AnukyaException("No content for UnitedStatesOfAmerica Shares analysis", null,
					AnukyaErrorConstants.NO_CONTENT, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		for (UnitedStatesOfAmericaShares unitedStatesOfAmericaShares : unitedStatesOfAmericaSharesList) {

			UnitedStatesOfAmericaSharesInvestmentResearch unitedStatesOfAmericaSharesInvestmentResearch = new UnitedStatesOfAmericaSharesInvestmentResearch();
			unitedStatesOfAmericaSharesInvestmentResearch.setScriptCode(unitedStatesOfAmericaShares.getYahooCode());
			unitedStatesOfAmericaSharesInvestmentResearch.setScriptName(unitedStatesOfAmericaShares.getScriptName());

			// Price Range
			UnitedStatesOfAmericaSharesOneShareDetails unitedStatesOfAmericaSharesOneShareDetails = unitedStatesOfAmericaSharesServiceCall
					.getOneShareDetails(unitedStatesOfAmericaShares.getYahooCode());

			BigDecimal dailyRangeBigDecimal = anukyaUtils.calculateHighLow(
					new BigDecimal(unitedStatesOfAmericaSharesOneShareDetails.getDailyLow()),
					new BigDecimal(unitedStatesOfAmericaSharesOneShareDetails.getDailyHigh()),
					new BigDecimal(unitedStatesOfAmericaSharesOneShareDetails.getCurrentMarketPrice()));
			BigDecimal fiftyTwoWeekRangeBigDecimal = anukyaUtils.calculateHighLow(
					new BigDecimal(unitedStatesOfAmericaSharesOneShareDetails.getFiftyTwoWeekLow()),
					new BigDecimal(unitedStatesOfAmericaSharesOneShareDetails.getFiftyTwoWeekHigh()),
					new BigDecimal(unitedStatesOfAmericaSharesOneShareDetails.getCurrentMarketPrice()));

			unitedStatesOfAmericaSharesInvestmentResearch
					.setDailyRange(String.format(AnukyaConstants.FLOAT_2_DECIMAL, dailyRangeBigDecimal));
			unitedStatesOfAmericaSharesInvestmentResearch
					.setFiftyTwoWeekRange(String.format(AnukyaConstants.FLOAT_2_DECIMAL, fiftyTwoWeekRangeBigDecimal));

			// TTM Dividend Yield
			BigDecimal ttmDividendYield = getTtmDividendYield(unitedStatesOfAmericaShares.getYahooCode(),
					unitedStatesOfAmericaSharesOneShareDetails.getCurrentMarketPrice());

			unitedStatesOfAmericaSharesInvestmentResearch
					.setTtmDividendYield(String.format(AnukyaConstants.FLOAT_2_DECIMAL, ttmDividendYield));

			unitedStatesOfAmericaSharesInvestmentResearchResponse.getUnitedStatesOfAmericaSharesInvestmentResearchList()
					.add(unitedStatesOfAmericaSharesInvestmentResearch);

		}

		return unitedStatesOfAmericaSharesInvestmentResearchResponse;
	}

	@Override
	public CommonResponse addUnitedStatesOfAmericaSharesShortTermInvestment(String userEmailId,
			UnitedStatesOfAmericaSharesShortTermInvestment unitedStatesOfAmericaSharesShortTermInvestment)
			throws AnukyaException {

		unitedStatesOfAmericaSharesExceptionHandler
				.addShortTermInvestmentException(unitedStatesOfAmericaSharesShortTermInvestment);

		File updateDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_USA_EQ_UPDATE_DIRECTORY);
		File mainBackUpDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_USA_EQ_MAIN_BACKUP_DIRECTORY);
		File mainDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_USA_EQ_MAIN_DIRECTORY);

		// Delete update directory and main backup directory
		anukyaDataUtils.deleteDirectory(updateDirectory);
		anukyaDataUtils.deleteDirectory(mainBackUpDirectory);

		// Copy main directory to update directory and main back up directory
		anukyaDataUtils.copyDirectory(mainDirectory, updateDirectory);
		anukyaDataUtils.copyDirectory(mainDirectory, mainBackUpDirectory);

		String dateInddMmmYyyy = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		// Update combined United States of America Shares short term investment object
		List<UnitedStatesOfAmericaSharesShortTermInvestment> unitedStatesOfAmericaSharesShortTermInvestmentList = updateUnitedStatesOfAmericaSharesShortTermInvestment(
				userEmailId, dateInddMmmYyyy, unitedStatesOfAmericaSharesShortTermInvestment);

		// Update individual United States of America Shares short term investment
		// object
		List<UnitedStatesOfAmericaSharesShortTermInvestment> individualUnitedStatesOfAmericaSharesShortTermInvestmentList = updateIndividualUnitedStatesOfAmericaSharesShortTermInvestment(
				userEmailId, dateInddMmmYyyy, unitedStatesOfAmericaSharesShortTermInvestment);

		// Save the updated combined United States of America shares short term
		// investment
		File shortTremInvestmentFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_USA_EQ_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_JSON);

		anukyaDataUtils.createOrUpdateFile(shortTremInvestmentFile, unitedStatesOfAmericaSharesShortTermInvestmentList);

		// Save the updated individual United States of America Shares short term
		// investment
		File individualShortTremInvestmentFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_USA_EQ_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ unitedStatesOfAmericaSharesShortTermInvestment.getYahooCode() + AnukyaConstants.JSON_EXTENSION);

		anukyaDataUtils.createOrUpdateFile(individualShortTremInvestmentFile,
				individualUnitedStatesOfAmericaSharesShortTermInvestmentList);

		// Delete main directory
		anukyaDataUtils.deleteDirectory(mainDirectory);

		// Copy from update directory to main directory
		anukyaDataUtils.copyDirectory(updateDirectory, mainDirectory);

		// Delete the update directory
		anukyaDataUtils.deleteDirectory(updateDirectory);

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setStatus(true);
		commonResponse.setMessage("United States of America Shares Short term investment added successfully");

		return commonResponse;
	}

	@Override
	public UnitedStatesOfAmericaSharesShortTermInvestmentResponse getShortTermInvestment(String userEmailId,
			String yahooCode) throws AnukyaException {

		List<UnitedStatesOfAmericaSharesShortTermInvestment> unitedStatesOfAmericaSharesShortTermInvestmentList;

		File shortTermInvestmentFile;

		if (yahooCode == null || yahooCode.isEmpty()) {
			shortTermInvestmentFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
					+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
					+ AnukyaConstants.SHORT_TERM_INVESTMENT_USA_EQ_MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
					+ AnukyaConstants.SHORT_TERM_INVESTMENT_JSON);
		} else {
			shortTermInvestmentFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
					+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
					+ AnukyaConstants.SHORT_TERM_INVESTMENT_USA_EQ_MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
					+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH + yahooCode
					+ AnukyaConstants.JSON_EXTENSION);
		}

		unitedStatesOfAmericaSharesShortTermInvestmentList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaSharesShortTermInvestmentList(shortTermInvestmentFile);

		if (unitedStatesOfAmericaSharesShortTermInvestmentList.isEmpty()) {

			throw new AnukyaException("No content for United States of America Shares Short term investment", null,
					AnukyaErrorConstants.NO_CONTENT, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		return calculateShortTermInvestment(unitedStatesOfAmericaSharesShortTermInvestmentList);
	}

	@Override
	public CommonResponse deleteShortTermInvestment(String userEmailId, String yahooCode) throws AnukyaException {

		unitedStatesOfAmericaSharesExceptionHandler.deleteShortTermInvestmentException(yahooCode);

		// Check if short term investment exists
		File individualShortTermInvestmentFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_USA_EQ_MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH + yahooCode
				+ AnukyaConstants.JSON_EXTENSION);

		if (!anukyaDataUtils.isFileExists(individualShortTermInvestmentFile)) {
			throw new AnukyaException("UnitedStatesOfAmerica Shares Short term investment does not exist", null,
					AnukyaErrorConstants.NO_CONTENT, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		File updateDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_USA_EQ_UPDATE_DIRECTORY);
		File mainBackUpDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_USA_EQ_MAIN_BACKUP_DIRECTORY);
		File mainDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_USA_EQ_MAIN_DIRECTORY);

		// Delete update directory and main backup directory
		anukyaDataUtils.deleteDirectory(updateDirectory);
		anukyaDataUtils.deleteDirectory(mainBackUpDirectory);

		// Copy main directory to update directory and backup directory
		anukyaDataUtils.copyDirectory(mainDirectory, updateDirectory);
		anukyaDataUtils.copyDirectory(mainDirectory, mainBackUpDirectory);

		// Delete individual file
		individualShortTermInvestmentFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_USA_EQ_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH + yahooCode
				+ AnukyaConstants.JSON_EXTENSION);
		anukyaDataUtils.deleteFile(individualShortTermInvestmentFile);

		// Get existing combined short term investment and remove
		File shortTermInvestmentFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_USA_EQ_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_JSON);
		List<UnitedStatesOfAmericaSharesShortTermInvestment> unitedStatesOfAmericaSharesShortTermInvestmentList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaSharesShortTermInvestmentList(shortTermInvestmentFile);

		List<UnitedStatesOfAmericaSharesShortTermInvestment> updatedUnitedStatesOfAmericaSharesShortTermInvestmentList = new ArrayList<>();

		String shareName = "";
		for (UnitedStatesOfAmericaSharesShortTermInvestment unitedStatesOfAmericaSharesShortTermInvestment : unitedStatesOfAmericaSharesShortTermInvestmentList) {
			if (unitedStatesOfAmericaSharesShortTermInvestment.getYahooCode().equalsIgnoreCase(yahooCode)) {
				shareName = unitedStatesOfAmericaSharesShortTermInvestment.getScriptName();
			} else {
				updatedUnitedStatesOfAmericaSharesShortTermInvestmentList
						.add(unitedStatesOfAmericaSharesShortTermInvestment);
			}
		}

		// Removing existing combined short term investment
		anukyaDataUtils.deleteFile(shortTermInvestmentFile);

		// Add the combined short term investment after removing
		anukyaDataUtils.createOrUpdateFile(shortTermInvestmentFile,
				updatedUnitedStatesOfAmericaSharesShortTermInvestmentList);

		// Delete main directory
		anukyaDataUtils.deleteDirectory(mainDirectory);

		// Copy from update directory to main directory
		anukyaDataUtils.copyDirectory(updateDirectory, mainDirectory);

		// Delete the update directory and main backup directory
		anukyaDataUtils.deleteDirectory(updateDirectory);

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setStatus(true);
		commonResponse.setMessage("United States of America Shares Short term investment deleted successfully for "
				+ shareName + " [Yahoo code " + yahooCode + "]");

		return commonResponse;
	}

	// Private methods
	private UnitedStatesOfAmericaSharesAnalysis getAnalysis(
			List<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesList) throws AnukyaException {

		Map<String, UnitedStatesOfAmericaSharesLastTradingPrice> latestTradingDetailsMap = new HashMap<>();
		Map<String, BigDecimal> totalSoldQuantityMap = new HashMap<>();
		Map<String, BigDecimal> totalSoldGrossTotalMap = new HashMap<>();

		Map<String, BigDecimal> sectorNumberOfScriptsMap = new HashMap<>();
		Map<String, BigDecimal> sectorNumberOfScriptsLongTermOnlyMap = new HashMap<>();

		Map<String, BigDecimal> categoryNumberOfScriptsMap = new HashMap<>();
		Map<String, BigDecimal> categoryNumberOfScriptsLongTermOnlyMap = new HashMap<>();

		for (UnitedStatesOfAmericaShares unitedStatesOfAmericaShare : unitedStatesOfAmericaSharesList) {

			updateNumberOfScripts(sectorNumberOfScriptsMap, sectorNumberOfScriptsLongTermOnlyMap,
					unitedStatesOfAmericaShare.getSector(), unitedStatesOfAmericaShare.isShortTermInvestment());

			updateNumberOfScripts(categoryNumberOfScriptsMap, categoryNumberOfScriptsLongTermOnlyMap,
					unitedStatesOfAmericaShare.getCategory(), unitedStatesOfAmericaShare.isShortTermInvestment());

			BigDecimal totalQuantity = new BigDecimal(unitedStatesOfAmericaShare.getPurchaseQuantity());
			totalSoldQuantityMap.put(unitedStatesOfAmericaShare.getYahooCode(), totalQuantity);

			latestTradingDetailsMap.put(unitedStatesOfAmericaShare.getYahooCode(),
					unitedStatesOfAmericaSharesServiceCall
							.getUnitedStatesOfAmericaSharesLastTradingPrice(unitedStatesOfAmericaShare.getYahooCode()));

			BigDecimal totalGrossTotal = totalQuantity.multiply(new BigDecimal(
					latestTradingDetailsMap.get(unitedStatesOfAmericaShare.getYahooCode()).getLastTradingPrice()));
			totalSoldGrossTotalMap.put(unitedStatesOfAmericaShare.getYahooCode(), totalGrossTotal);

		}

		UnitedStatesOfAmericaSharesCalculationDetails unitedStatesOfAmericaSharesCalculationDetails = new UnitedStatesOfAmericaSharesCalculationDetails();

		unitedStatesOfAmericaSharesCalculationDetails.setLatestTradingDetailsMap(latestTradingDetailsMap);
		unitedStatesOfAmericaSharesCalculationDetails.setTotalSoldQuantityMap(totalSoldQuantityMap);
		unitedStatesOfAmericaSharesCalculationDetails.setTotalSoldGrossTotalMap(totalSoldGrossTotalMap);

		Map<String, UnitedStatesOfAmericaSharesSector> unitedStatesOfAmericaSharesSectorMap = new LinkedHashMap<>();
		unitedStatesOfAmericaSharesSectorMap.put(AnukyaConstants.TOTAL_CONSTANT,
				new UnitedStatesOfAmericaSharesSector());
		Map<String, UnitedStatesOfAmericaSharesSector> unitedStatesOfAmericaSharesSectorLongTermOnlyMap = new LinkedHashMap<>();
		unitedStatesOfAmericaSharesSectorLongTermOnlyMap.put(AnukyaConstants.TOTAL_CONSTANT,
				new UnitedStatesOfAmericaSharesSector());

		Map<String, UnitedStatesOfAmericaSharesCategory> unitedStatesOfAmericaSharesCategoryMap = new LinkedHashMap<>();
		unitedStatesOfAmericaSharesCategoryMap.put(AnukyaConstants.TOTAL_CONSTANT,
				new UnitedStatesOfAmericaSharesCategory());
		Map<String, UnitedStatesOfAmericaSharesCategory> unitedStatesOfAmericaSharesCategoryLongTermOnlyMap = new LinkedHashMap<>();
		unitedStatesOfAmericaSharesCategoryLongTermOnlyMap.put(AnukyaConstants.TOTAL_CONSTANT,
				new UnitedStatesOfAmericaSharesCategory());

		Map<String, Map<String, UnitedStatesOfAmericaSharesOneSectorDetails>> unitedStatesOfAmericaSharesOneSectorDetailsMap = new LinkedHashMap<>();
		Map<String, Map<String, UnitedStatesOfAmericaSharesOneSectorDetails>> unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap = new LinkedHashMap<>();

		Map<String, Map<String, UnitedStatesOfAmericaSharesOneCategoryDetails>> unitedStatesOfAmericaSharesOneCategoryDetailsMap = new LinkedHashMap<>();
		Map<String, Map<String, UnitedStatesOfAmericaSharesOneCategoryDetails>> unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap = new LinkedHashMap<>();

		for (UnitedStatesOfAmericaShares unitedStatesOfAmericaShare : unitedStatesOfAmericaSharesList) {

			buildUnitedStatesOfAmericaSharesSector(unitedStatesOfAmericaShare,
					unitedStatesOfAmericaSharesCalculationDetails, unitedStatesOfAmericaSharesSectorMap);
			buildUnitedStatesOfAmericaSharesCategory(unitedStatesOfAmericaShare,
					unitedStatesOfAmericaSharesCalculationDetails, unitedStatesOfAmericaSharesCategoryMap);

			buildUnitedStatesOfAmericaSharesOneSectorDetails(unitedStatesOfAmericaShare,
					unitedStatesOfAmericaSharesCalculationDetails, unitedStatesOfAmericaSharesOneSectorDetailsMap);
			buildUnitedStatesOfAmericaSharesOneCategoryDetails(unitedStatesOfAmericaShare,
					unitedStatesOfAmericaSharesCalculationDetails, unitedStatesOfAmericaSharesOneCategoryDetailsMap);

			if (!unitedStatesOfAmericaShare.isShortTermInvestment()) {
				buildUnitedStatesOfAmericaSharesSector(unitedStatesOfAmericaShare,
						unitedStatesOfAmericaSharesCalculationDetails,
						unitedStatesOfAmericaSharesSectorLongTermOnlyMap);
				buildUnitedStatesOfAmericaSharesCategory(unitedStatesOfAmericaShare,
						unitedStatesOfAmericaSharesCalculationDetails,
						unitedStatesOfAmericaSharesCategoryLongTermOnlyMap);

				buildUnitedStatesOfAmericaSharesOneSectorDetails(unitedStatesOfAmericaShare,
						unitedStatesOfAmericaSharesCalculationDetails,
						unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap);
				buildUnitedStatesOfAmericaSharesOneCategoryDetails(unitedStatesOfAmericaShare,
						unitedStatesOfAmericaSharesCalculationDetails,
						unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap);
			}
		}

		BigDecimal totalSectorInvestedValue = new BigDecimal(
				unitedStatesOfAmericaSharesSectorMap.get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue());
		BigDecimal totalSectorCurrentValue = new BigDecimal(
				unitedStatesOfAmericaSharesSectorMap.get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue());

		BigDecimal totalSectorInvestedValueLongTermOnly = new BigDecimal(
				unitedStatesOfAmericaSharesSectorLongTermOnlyMap.get(AnukyaConstants.TOTAL_CONSTANT)
						.getInvestedValue());
		BigDecimal totalSectorCurrentValueLongTermOnly = new BigDecimal(
				unitedStatesOfAmericaSharesSectorLongTermOnlyMap.get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue());

		for (Map.Entry<String, UnitedStatesOfAmericaSharesSector> entry : unitedStatesOfAmericaSharesSectorMap
				.entrySet()) {
			if (!entry.getKey().equalsIgnoreCase(AnukyaConstants.TOTAL_CONSTANT)) {
				BigDecimal investedValuePercentage = new BigDecimal(entry.getValue().getInvestedValue())
						.divide(totalSectorInvestedValue, MathContext.DECIMAL128)
						.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
				entry.getValue().setInvestedValuePercentage(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, investedValuePercentage));

				BigDecimal currentValuePercentage = new BigDecimal(entry.getValue().getCurrentValue())
						.divide(totalSectorCurrentValue, MathContext.DECIMAL128)
						.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
				entry.getValue().setCurrentValuePercentage(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, currentValuePercentage));

				entry.getValue().setNumberOfScripts(String.valueOf(sectorNumberOfScriptsMap.get(entry.getKey())));
			}
		}

		for (Map.Entry<String, UnitedStatesOfAmericaSharesSector> entry : unitedStatesOfAmericaSharesSectorLongTermOnlyMap
				.entrySet()) {
			if (!entry.getKey().equalsIgnoreCase(AnukyaConstants.TOTAL_CONSTANT)) {
				BigDecimal investedValuePercentage = new BigDecimal(entry.getValue().getInvestedValue())
						.divide(totalSectorInvestedValueLongTermOnly, MathContext.DECIMAL128)
						.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
				entry.getValue().setInvestedValuePercentage(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, investedValuePercentage));

				BigDecimal currentValuePercentage = new BigDecimal(entry.getValue().getCurrentValue())
						.divide(totalSectorCurrentValueLongTermOnly, MathContext.DECIMAL128)
						.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
				entry.getValue().setCurrentValuePercentage(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, currentValuePercentage));

				entry.getValue()
						.setNumberOfScripts(String.valueOf(sectorNumberOfScriptsLongTermOnlyMap.get(entry.getKey())));
			}
		}

		unitedStatesOfAmericaSharesSectorMap.get(AnukyaConstants.TOTAL_CONSTANT)
				.setNumberOfScripts(String.valueOf(sectorNumberOfScriptsMap.get(AnukyaConstants.TOTAL_CONSTANT)));
		unitedStatesOfAmericaSharesSectorLongTermOnlyMap.get(AnukyaConstants.TOTAL_CONSTANT).setNumberOfScripts(
				String.valueOf(sectorNumberOfScriptsLongTermOnlyMap.get(AnukyaConstants.TOTAL_CONSTANT)));

		BigDecimal totalCategoryInvestedValue = new BigDecimal(
				unitedStatesOfAmericaSharesCategoryMap.get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue());
		BigDecimal totalCategoryCurrentValue = new BigDecimal(
				unitedStatesOfAmericaSharesCategoryMap.get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue());

		BigDecimal totalCategoryInvestedValueLongTermOnly = new BigDecimal(
				unitedStatesOfAmericaSharesCategoryLongTermOnlyMap.get(AnukyaConstants.TOTAL_CONSTANT)
						.getInvestedValue());
		BigDecimal totalCategoryCurrentValueLongTermOnly = new BigDecimal(
				unitedStatesOfAmericaSharesCategoryLongTermOnlyMap.get(AnukyaConstants.TOTAL_CONSTANT)
						.getCurrentValue());

		for (Map.Entry<String, UnitedStatesOfAmericaSharesCategory> entry : unitedStatesOfAmericaSharesCategoryMap
				.entrySet()) {
			if (!entry.getKey().equalsIgnoreCase(AnukyaConstants.TOTAL_CONSTANT)) {
				BigDecimal investedValuePercentage = new BigDecimal(entry.getValue().getInvestedValue())
						.divide(totalCategoryInvestedValue, MathContext.DECIMAL128)
						.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
				entry.getValue().setInvestedValuePercentage(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, investedValuePercentage));

				BigDecimal currentValuePercentage = new BigDecimal(entry.getValue().getCurrentValue())
						.divide(totalCategoryCurrentValue, MathContext.DECIMAL128)
						.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
				entry.getValue().setCurrentValuePercentage(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, currentValuePercentage));

				entry.getValue().setNumberOfScripts(String.valueOf(categoryNumberOfScriptsMap.get(entry.getKey())));
			}
		}

		for (Map.Entry<String, UnitedStatesOfAmericaSharesCategory> entry : unitedStatesOfAmericaSharesCategoryLongTermOnlyMap
				.entrySet()) {
			if (!entry.getKey().equalsIgnoreCase(AnukyaConstants.TOTAL_CONSTANT)) {
				BigDecimal investedValuePercentage = new BigDecimal(entry.getValue().getInvestedValue())
						.divide(totalCategoryInvestedValueLongTermOnly, MathContext.DECIMAL128)
						.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
				entry.getValue().setInvestedValuePercentage(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, investedValuePercentage));

				BigDecimal currentValuePercentage = new BigDecimal(entry.getValue().getCurrentValue())
						.divide(totalCategoryCurrentValueLongTermOnly, MathContext.DECIMAL128)
						.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
				entry.getValue().setCurrentValuePercentage(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, currentValuePercentage));

				entry.getValue()
						.setNumberOfScripts(String.valueOf(categoryNumberOfScriptsLongTermOnlyMap.get(entry.getKey())));
			}
		}

		unitedStatesOfAmericaSharesCategoryMap.get(AnukyaConstants.TOTAL_CONSTANT)
				.setNumberOfScripts(String.valueOf(categoryNumberOfScriptsMap.get(AnukyaConstants.TOTAL_CONSTANT)));
		unitedStatesOfAmericaSharesCategoryLongTermOnlyMap.get(AnukyaConstants.TOTAL_CONSTANT).setNumberOfScripts(
				String.valueOf(categoryNumberOfScriptsLongTermOnlyMap.get(AnukyaConstants.TOTAL_CONSTANT)));

		for (Map.Entry<String, Map<String, UnitedStatesOfAmericaSharesOneSectorDetails>> oneSectorDetailsMap : unitedStatesOfAmericaSharesOneSectorDetailsMap
				.entrySet()) {

			BigDecimal totalOneSectorInvestedValue = new BigDecimal(
					oneSectorDetailsMap.getValue().get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue());
			BigDecimal totalOneSectorCurrentValue = new BigDecimal(
					oneSectorDetailsMap.getValue().get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue());

			for (Map.Entry<String, UnitedStatesOfAmericaSharesOneSectorDetails> sectorMap : oneSectorDetailsMap
					.getValue().entrySet()) {
				if (!sectorMap.getKey().equalsIgnoreCase(AnukyaConstants.TOTAL_CONSTANT)) {
					BigDecimal investedValuePercentage = new BigDecimal(sectorMap.getValue().getInvestedValue())
							.divide(totalOneSectorInvestedValue, MathContext.DECIMAL128)
							.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
					sectorMap.getValue().setInvestedValuePercentage(
							String.format(AnukyaConstants.FLOAT_2_DECIMAL, investedValuePercentage));

					BigDecimal currentValuePercentage = new BigDecimal(sectorMap.getValue().getCurrentValue())
							.divide(totalOneSectorCurrentValue, MathContext.DECIMAL128)
							.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
					sectorMap.getValue().setCurrentValuePercentage(
							String.format(AnukyaConstants.FLOAT_2_DECIMAL, currentValuePercentage));
				}
			}
		}

		for (Map.Entry<String, Map<String, UnitedStatesOfAmericaSharesOneSectorDetails>> oneSectorDetailsMap : unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap
				.entrySet()) {

			BigDecimal totalOneSectorInvestedValue = new BigDecimal(
					oneSectorDetailsMap.getValue().get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue());
			BigDecimal totalOneSectorCurrentValue = new BigDecimal(
					oneSectorDetailsMap.getValue().get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue());

			for (Map.Entry<String, UnitedStatesOfAmericaSharesOneSectorDetails> sectorMap : oneSectorDetailsMap
					.getValue().entrySet()) {
				if (!sectorMap.getKey().equalsIgnoreCase(AnukyaConstants.TOTAL_CONSTANT)) {
					BigDecimal investedValuePercentage = new BigDecimal(sectorMap.getValue().getInvestedValue())
							.divide(totalOneSectorInvestedValue, MathContext.DECIMAL128)
							.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
					sectorMap.getValue().setInvestedValuePercentage(
							String.format(AnukyaConstants.FLOAT_2_DECIMAL, investedValuePercentage));

					BigDecimal currentValuePercentage = new BigDecimal(sectorMap.getValue().getCurrentValue())
							.divide(totalOneSectorCurrentValue, MathContext.DECIMAL128)
							.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
					sectorMap.getValue().setCurrentValuePercentage(
							String.format(AnukyaConstants.FLOAT_2_DECIMAL, currentValuePercentage));
				}
			}
		}

		for (Map.Entry<String, Map<String, UnitedStatesOfAmericaSharesOneCategoryDetails>> oneCategoryDetailsMap : unitedStatesOfAmericaSharesOneCategoryDetailsMap
				.entrySet()) {

			BigDecimal totalOneCategoryInvestedValue = new BigDecimal(
					oneCategoryDetailsMap.getValue().get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue());
			BigDecimal totalOneCategoryCurrentValue = new BigDecimal(
					oneCategoryDetailsMap.getValue().get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue());

			for (Map.Entry<String, UnitedStatesOfAmericaSharesOneCategoryDetails> categoryMap : oneCategoryDetailsMap
					.getValue().entrySet()) {
				if (!categoryMap.getKey().equalsIgnoreCase(AnukyaConstants.TOTAL_CONSTANT)) {
					BigDecimal investedValuePercentage = new BigDecimal(categoryMap.getValue().getInvestedValue())
							.divide(totalOneCategoryInvestedValue, MathContext.DECIMAL128)
							.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
					categoryMap.getValue().setInvestedValuePercentage(
							String.format(AnukyaConstants.FLOAT_2_DECIMAL, investedValuePercentage));

					BigDecimal currentValuePercentage = new BigDecimal(categoryMap.getValue().getCurrentValue())
							.divide(totalOneCategoryCurrentValue, MathContext.DECIMAL128)
							.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
					categoryMap.getValue().setCurrentValuePercentage(
							String.format(AnukyaConstants.FLOAT_2_DECIMAL, currentValuePercentage));
				}
			}
		}

		for (Map.Entry<String, Map<String, UnitedStatesOfAmericaSharesOneCategoryDetails>> oneCategoryDetailsMap : unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap
				.entrySet()) {

			BigDecimal totalOneCategoryInvestedValue = new BigDecimal(
					oneCategoryDetailsMap.getValue().get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue());
			BigDecimal totalOneCategoryCurrentValue = new BigDecimal(
					oneCategoryDetailsMap.getValue().get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue());

			for (Map.Entry<String, UnitedStatesOfAmericaSharesOneCategoryDetails> categoryMap : oneCategoryDetailsMap
					.getValue().entrySet()) {
				if (!categoryMap.getKey().equalsIgnoreCase(AnukyaConstants.TOTAL_CONSTANT)) {
					BigDecimal investedValuePercentage = new BigDecimal(categoryMap.getValue().getInvestedValue())
							.divide(totalOneCategoryInvestedValue, MathContext.DECIMAL128)
							.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
					categoryMap.getValue().setInvestedValuePercentage(
							String.format(AnukyaConstants.FLOAT_2_DECIMAL, investedValuePercentage));

					BigDecimal currentValuePercentage = new BigDecimal(categoryMap.getValue().getCurrentValue())
							.divide(totalOneCategoryCurrentValue, MathContext.DECIMAL128)
							.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
					categoryMap.getValue().setCurrentValuePercentage(
							String.format(AnukyaConstants.FLOAT_2_DECIMAL, currentValuePercentage));
				}
			}
		}

		UnitedStatesOfAmericaSharesAnalysis unitedStatesOfAmericaSharesAnalysis = new UnitedStatesOfAmericaSharesAnalysis();

		unitedStatesOfAmericaSharesAnalysis
				.setUnitedStatesOfAmericaSharesSectorMap(unitedStatesOfAmericaSharesSectorMap);
		unitedStatesOfAmericaSharesAnalysis
				.setUnitedStatesOfAmericaSharesSectorLongTermOnlyMap(unitedStatesOfAmericaSharesSectorLongTermOnlyMap);

		unitedStatesOfAmericaSharesAnalysis
				.setUnitedStatesOfAmericaSharesCategoryMap(unitedStatesOfAmericaSharesCategoryMap);
		unitedStatesOfAmericaSharesAnalysis.setUnitedStatesOfAmericaSharesCategoryLongTermOnlyMap(
				unitedStatesOfAmericaSharesCategoryLongTermOnlyMap);

		unitedStatesOfAmericaSharesAnalysis
				.setUnitedStatesOfAmericaSharesOneSectorDetailsMap(unitedStatesOfAmericaSharesOneSectorDetailsMap);
		unitedStatesOfAmericaSharesAnalysis.setUnitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap(
				unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap);

		unitedStatesOfAmericaSharesAnalysis
				.setUnitedStatesOfAmericaSharesOneCategoryDetailsMap(unitedStatesOfAmericaSharesOneCategoryDetailsMap);
		unitedStatesOfAmericaSharesAnalysis.setUnitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap(
				unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap);

		return unitedStatesOfAmericaSharesAnalysis;
	}

	private void updateNumberOfScripts(Map<String, BigDecimal> numberOfScriptsMap,
			Map<String, BigDecimal> numberOfScriptsLongTermOnlyMap, String key, boolean isShortTermInvestment) {

		BigDecimal existingNumberOfScripts = numberOfScriptsMap.get(key) != null ? numberOfScriptsMap.get(key)
				: new BigDecimal(AnukyaConstants.NUMBER_0);
		BigDecimal numberOfScripts = existingNumberOfScripts.add(new BigDecimal(AnukyaConstants.NUMBER_1));
		numberOfScriptsMap.put(key, numberOfScripts);

		BigDecimal totalExistingNumberOfScripts = numberOfScriptsMap.get(AnukyaConstants.TOTAL_CONSTANT) != null
				? numberOfScriptsMap.get(AnukyaConstants.TOTAL_CONSTANT)
				: new BigDecimal(AnukyaConstants.NUMBER_0);
		BigDecimal totalNumberOfScripts = totalExistingNumberOfScripts.add(new BigDecimal(AnukyaConstants.NUMBER_1));
		numberOfScriptsMap.put(AnukyaConstants.TOTAL_CONSTANT, totalNumberOfScripts);
		if (!isShortTermInvestment) {

			BigDecimal existingNumberOfScriptsLongTermOnly = numberOfScriptsLongTermOnlyMap.get(key) != null
					? numberOfScriptsLongTermOnlyMap.get(key)
					: new BigDecimal(AnukyaConstants.NUMBER_0);
			BigDecimal numberOfScriptsLongTermOnly = existingNumberOfScriptsLongTermOnly
					.add(new BigDecimal(AnukyaConstants.NUMBER_1));
			numberOfScriptsLongTermOnlyMap.put(key, numberOfScriptsLongTermOnly);

			BigDecimal totalExistingNumberOfScriptsLongTermOnly = numberOfScriptsLongTermOnlyMap
					.get(AnukyaConstants.TOTAL_CONSTANT) != null
							? numberOfScriptsLongTermOnlyMap.get(AnukyaConstants.TOTAL_CONSTANT)
							: new BigDecimal(AnukyaConstants.NUMBER_0);
			BigDecimal totalNumberOfScriptsLongTermOnly = totalExistingNumberOfScriptsLongTermOnly
					.add(new BigDecimal(AnukyaConstants.NUMBER_1));
			numberOfScriptsLongTermOnlyMap.put(AnukyaConstants.TOTAL_CONSTANT, totalNumberOfScriptsLongTermOnly);
		}
	}

	private void buildUnitedStatesOfAmericaSharesSector(UnitedStatesOfAmericaShares unitedStatesOfAmericaShare,
			UnitedStatesOfAmericaSharesCalculationDetails unitedStatesOfAmericaSharesCalculationDetails,
			Map<String, UnitedStatesOfAmericaSharesSector> unitedStatesOfAmericaSharesSectorMap) {

		if (!unitedStatesOfAmericaSharesSectorMap.containsKey(unitedStatesOfAmericaShare.getSector())) {
			unitedStatesOfAmericaSharesSectorMap.put(unitedStatesOfAmericaShare.getSector(),
					new UnitedStatesOfAmericaSharesSector());
		}

		BigDecimal purchaseQuantity = new BigDecimal(unitedStatesOfAmericaShare.getPurchaseQuantity());

		BigDecimal purchaseTotal = new BigDecimal(unitedStatesOfAmericaShare.getPurchaseTotal());

		BigDecimal investedValue = new BigDecimal(
				unitedStatesOfAmericaSharesSectorMap.get(unitedStatesOfAmericaShare.getSector()).getInvestedValue())
						.add(purchaseTotal);

		BigDecimal totalInvestedValue = new BigDecimal(
				unitedStatesOfAmericaSharesSectorMap.get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue())
						.add(purchaseTotal);

		BigDecimal sellQuantity = purchaseQuantity;

		BigDecimal sellPrice = new BigDecimal(unitedStatesOfAmericaSharesCalculationDetails.getLatestTradingDetailsMap()
				.get(unitedStatesOfAmericaShare.getYahooCode()).getLastTradingPrice());

		BigDecimal sellGrossTotal = sellQuantity.multiply(sellPrice);

		BigDecimal sellCommission = unitedStatesOfAmericaExpenditureUtils.calculateCommission();

		BigDecimal sellTransactionFees = unitedStatesOfAmericaExpenditureUtils.calculateTransactionFees(1L);

		BigDecimal sellOtherFees = unitedStatesOfAmericaExpenditureUtils.getOtherFees();

		BigDecimal sellTotal = sellGrossTotal.subtract(sellCommission).subtract(sellTransactionFees)
				.subtract(sellOtherFees);

		BigDecimal currentValue = new BigDecimal(
				unitedStatesOfAmericaSharesSectorMap.get(unitedStatesOfAmericaShare.getSector()).getCurrentValue())
						.add(sellTotal);
		BigDecimal totalCurrentValue = new BigDecimal(
				unitedStatesOfAmericaSharesSectorMap.get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue())
						.add(sellTotal);

		BigDecimal profitLoss = currentValue.subtract(investedValue);
		BigDecimal profitLossPercentage = currentValue.divide(investedValue, MathContext.DECIMAL128)
				.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
				.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));

		BigDecimal totalProfitLoss = totalCurrentValue.subtract(totalInvestedValue);

		BigDecimal totalProfitLossPercentage = totalCurrentValue.divide(totalInvestedValue, MathContext.DECIMAL128)
				.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
				.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));

		UnitedStatesOfAmericaSharesSector unitedStatesOfAmericaSharesSector = new UnitedStatesOfAmericaSharesSector();
		unitedStatesOfAmericaSharesSector.setSectorName(unitedStatesOfAmericaShare.getSector());
		unitedStatesOfAmericaSharesSector
				.setInvestedValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, investedValue));
		unitedStatesOfAmericaSharesSector.setCurrentValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, currentValue));
		unitedStatesOfAmericaSharesSector.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLoss));
		unitedStatesOfAmericaSharesSector
				.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLossPercentage));

		unitedStatesOfAmericaSharesSectorMap.put(unitedStatesOfAmericaShare.getSector(),
				unitedStatesOfAmericaSharesSector);

		UnitedStatesOfAmericaSharesSector unitedStatesOfAmericaSharesSectorTotal = new UnitedStatesOfAmericaSharesSector();
		unitedStatesOfAmericaSharesSectorTotal.setSectorName(AnukyaConstants.TOTAL_CONSTANT);
		unitedStatesOfAmericaSharesSectorTotal
				.setInvestedValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalInvestedValue));
		unitedStatesOfAmericaSharesSectorTotal.setInvestedValuePercentage(
				String.format(AnukyaConstants.FLOAT_2_DECIMAL, new BigDecimal(AnukyaConstants.NUMBER_100)));
		unitedStatesOfAmericaSharesSectorTotal
				.setCurrentValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalCurrentValue));
		unitedStatesOfAmericaSharesSectorTotal.setCurrentValuePercentage(
				String.format(AnukyaConstants.FLOAT_2_DECIMAL, new BigDecimal(AnukyaConstants.NUMBER_100)));
		unitedStatesOfAmericaSharesSectorTotal
				.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLoss));
		unitedStatesOfAmericaSharesSectorTotal
				.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLossPercentage));

		unitedStatesOfAmericaSharesSectorMap.put(AnukyaConstants.TOTAL_CONSTANT,
				unitedStatesOfAmericaSharesSectorTotal);
	}

	private void buildUnitedStatesOfAmericaSharesCategory(UnitedStatesOfAmericaShares unitedStatesOfAmericaShare,
			UnitedStatesOfAmericaSharesCalculationDetails unitedStatesOfAmericaSharesCalculationDetails,
			Map<String, UnitedStatesOfAmericaSharesCategory> unitedStatesOfAmericaSharesCategoryMap) {

		if (!unitedStatesOfAmericaSharesCategoryMap.containsKey(unitedStatesOfAmericaShare.getCategory())) {
			unitedStatesOfAmericaSharesCategoryMap.put(unitedStatesOfAmericaShare.getCategory(),
					new UnitedStatesOfAmericaSharesCategory());
		}

		BigDecimal purchaseQuantity = new BigDecimal(unitedStatesOfAmericaShare.getPurchaseQuantity());

		BigDecimal purchaseTotal = new BigDecimal(unitedStatesOfAmericaShare.getPurchaseTotal());

		BigDecimal investedValue = new BigDecimal(
				unitedStatesOfAmericaSharesCategoryMap.get(unitedStatesOfAmericaShare.getCategory()).getInvestedValue())
						.add(purchaseTotal);

		BigDecimal totalInvestedValue = new BigDecimal(
				unitedStatesOfAmericaSharesCategoryMap.get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue())
						.add(purchaseTotal);

		BigDecimal sellQuantity = purchaseQuantity;

		BigDecimal sellPrice = new BigDecimal(unitedStatesOfAmericaSharesCalculationDetails.getLatestTradingDetailsMap()
				.get(unitedStatesOfAmericaShare.getYahooCode()).getLastTradingPrice());

		BigDecimal sellGrossTotal = sellQuantity.multiply(sellPrice);

		BigDecimal sellCommission = unitedStatesOfAmericaExpenditureUtils.calculateCommission();

		BigDecimal sellTransactionFees = unitedStatesOfAmericaExpenditureUtils.calculateTransactionFees(1L);

		BigDecimal sellOtherFees = unitedStatesOfAmericaExpenditureUtils.getOtherFees();

		BigDecimal sellTotal = sellGrossTotal.subtract(sellCommission).subtract(sellTransactionFees)
				.subtract(sellOtherFees);

		BigDecimal currentValue = new BigDecimal(
				unitedStatesOfAmericaSharesCategoryMap.get(unitedStatesOfAmericaShare.getCategory()).getCurrentValue())
						.add(sellTotal);
		BigDecimal totalCurrentValue = new BigDecimal(
				unitedStatesOfAmericaSharesCategoryMap.get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue())
						.add(sellTotal);

		BigDecimal profitLoss = currentValue.subtract(investedValue);
		BigDecimal profitLossPercentage = currentValue.divide(investedValue, MathContext.DECIMAL128)
				.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
				.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));

		BigDecimal totalProfitLoss = totalCurrentValue.subtract(totalInvestedValue);

		BigDecimal totalProfitLossPercentage = totalCurrentValue.divide(totalInvestedValue, MathContext.DECIMAL128)
				.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
				.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));

		UnitedStatesOfAmericaSharesCategory unitedStatesOfAmericaSharesCategory = new UnitedStatesOfAmericaSharesCategory();
		unitedStatesOfAmericaSharesCategory.setCategoryName(unitedStatesOfAmericaShare.getCategory());
		unitedStatesOfAmericaSharesCategory
				.setInvestedValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, investedValue));
		unitedStatesOfAmericaSharesCategory
				.setCurrentValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, currentValue));
		unitedStatesOfAmericaSharesCategory.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLoss));
		unitedStatesOfAmericaSharesCategory
				.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLossPercentage));

		unitedStatesOfAmericaSharesCategoryMap.put(unitedStatesOfAmericaShare.getCategory(),
				unitedStatesOfAmericaSharesCategory);

		UnitedStatesOfAmericaSharesCategory unitedStatesOfAmericaSharesCategoryTotal = new UnitedStatesOfAmericaSharesCategory();
		unitedStatesOfAmericaSharesCategoryTotal.setCategoryName(AnukyaConstants.TOTAL_CONSTANT);
		unitedStatesOfAmericaSharesCategoryTotal
				.setInvestedValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalInvestedValue));
		unitedStatesOfAmericaSharesCategoryTotal.setInvestedValuePercentage(
				String.format(AnukyaConstants.FLOAT_2_DECIMAL, new BigDecimal(AnukyaConstants.NUMBER_100)));
		unitedStatesOfAmericaSharesCategoryTotal
				.setCurrentValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalCurrentValue));
		unitedStatesOfAmericaSharesCategoryTotal.setCurrentValuePercentage(
				String.format(AnukyaConstants.FLOAT_2_DECIMAL, new BigDecimal(AnukyaConstants.NUMBER_100)));
		unitedStatesOfAmericaSharesCategoryTotal
				.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLoss));
		unitedStatesOfAmericaSharesCategoryTotal
				.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLossPercentage));

		unitedStatesOfAmericaSharesCategoryMap.put(AnukyaConstants.TOTAL_CONSTANT,
				unitedStatesOfAmericaSharesCategoryTotal);
	}

	private void buildUnitedStatesOfAmericaSharesOneSectorDetails(
			UnitedStatesOfAmericaShares unitedStatesOfAmericaShare,
			UnitedStatesOfAmericaSharesCalculationDetails unitedStatesOfAmericaSharesCalculationDetails,
			Map<String, Map<String, UnitedStatesOfAmericaSharesOneSectorDetails>> unitedStatesOfAmericaSharesOneSectorDetailsMap) {

		if (unitedStatesOfAmericaSharesOneSectorDetailsMap.isEmpty()) {
			Map<String, UnitedStatesOfAmericaSharesOneSectorDetails> oneSectorDetailsMap = new LinkedHashMap<>();
			oneSectorDetailsMap.put(AnukyaConstants.TOTAL_CONSTANT, new UnitedStatesOfAmericaSharesOneSectorDetails());
		}

		if (!unitedStatesOfAmericaSharesOneSectorDetailsMap.containsKey(unitedStatesOfAmericaShare.getSector())) {
			Map<String, UnitedStatesOfAmericaSharesOneSectorDetails> oneSectorDetailsMap = new LinkedHashMap<>();
			oneSectorDetailsMap.put(AnukyaConstants.TOTAL_CONSTANT, new UnitedStatesOfAmericaSharesOneSectorDetails());
			unitedStatesOfAmericaSharesOneSectorDetailsMap.put(unitedStatesOfAmericaShare.getSector(),
					oneSectorDetailsMap);
		}

		if (!unitedStatesOfAmericaSharesOneSectorDetailsMap.get(unitedStatesOfAmericaShare.getSector())
				.containsKey(unitedStatesOfAmericaShare.getYahooCode())) {
			unitedStatesOfAmericaSharesOneSectorDetailsMap.get(unitedStatesOfAmericaShare.getSector())
					.put(unitedStatesOfAmericaShare.getYahooCode(), new UnitedStatesOfAmericaSharesOneSectorDetails());
		}

		BigDecimal purchaseQuantity = new BigDecimal(unitedStatesOfAmericaShare.getPurchaseQuantity());

		BigDecimal purchaseTotal = new BigDecimal(unitedStatesOfAmericaShare.getPurchaseTotal());

		BigDecimal investedValue = new BigDecimal(
				unitedStatesOfAmericaSharesOneSectorDetailsMap.get(unitedStatesOfAmericaShare.getSector())
						.get(unitedStatesOfAmericaShare.getYahooCode()).getInvestedValue()).add(purchaseTotal);
		BigDecimal totalInvestedValue = new BigDecimal(unitedStatesOfAmericaSharesOneSectorDetailsMap
				.get(unitedStatesOfAmericaShare.getSector()).get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue())
						.add(purchaseTotal);

		BigDecimal sellQuantity = purchaseQuantity;

		BigDecimal sellPrice = new BigDecimal(unitedStatesOfAmericaSharesCalculationDetails.getLatestTradingDetailsMap()
				.get(unitedStatesOfAmericaShare.getYahooCode()).getLastTradingPrice());

		BigDecimal sellGrossTotal = sellQuantity.multiply(sellPrice);

		BigDecimal sellCommission = unitedStatesOfAmericaExpenditureUtils.calculateCommission();

		BigDecimal sellTransactionFees = unitedStatesOfAmericaExpenditureUtils.calculateTransactionFees(1L);

		BigDecimal sellOtherFees = unitedStatesOfAmericaExpenditureUtils.getOtherFees();

		BigDecimal sellTotal = sellGrossTotal.subtract(sellCommission).subtract(sellTransactionFees)
				.subtract(sellOtherFees);

		BigDecimal currentValue = new BigDecimal(
				unitedStatesOfAmericaSharesOneSectorDetailsMap.get(unitedStatesOfAmericaShare.getSector())
						.get(unitedStatesOfAmericaShare.getYahooCode()).getCurrentValue()).add(sellTotal);
		BigDecimal totalCurrentValue = new BigDecimal(unitedStatesOfAmericaSharesOneSectorDetailsMap
				.get(unitedStatesOfAmericaShare.getSector()).get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue())
						.add(sellTotal);

		BigDecimal profitLoss = currentValue.subtract(investedValue);
		BigDecimal profitLossPercentage = currentValue.divide(investedValue, MathContext.DECIMAL128)
				.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
				.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));

		BigDecimal totalProfitLoss = totalCurrentValue.subtract(totalInvestedValue);
		BigDecimal totalProfitLossPercentage = totalCurrentValue.divide(totalInvestedValue, MathContext.DECIMAL128)
				.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
				.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));

		UnitedStatesOfAmericaSharesOneSectorDetails unitedStatesOfAmericaSharesOneSectorDetails = new UnitedStatesOfAmericaSharesOneSectorDetails();
		unitedStatesOfAmericaSharesOneSectorDetails.setScriptName(unitedStatesOfAmericaShare.getScriptName());
		unitedStatesOfAmericaSharesOneSectorDetails.setScriptCode(unitedStatesOfAmericaShare.getYahooCode());
		unitedStatesOfAmericaSharesOneSectorDetails
				.setInvestedValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, investedValue));
		unitedStatesOfAmericaSharesOneSectorDetails
				.setCurrentValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, currentValue));
		unitedStatesOfAmericaSharesOneSectorDetails
				.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLoss));
		unitedStatesOfAmericaSharesOneSectorDetails
				.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLossPercentage));

		unitedStatesOfAmericaSharesOneSectorDetailsMap.get(unitedStatesOfAmericaShare.getSector())
				.put(unitedStatesOfAmericaShare.getYahooCode(), unitedStatesOfAmericaSharesOneSectorDetails);

		UnitedStatesOfAmericaSharesOneSectorDetails unitedStatesOfAmericaSharesOneSectorDetailsTotal = new UnitedStatesOfAmericaSharesOneSectorDetails();
		unitedStatesOfAmericaSharesOneSectorDetailsTotal
				.setInvestedValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalInvestedValue));
		unitedStatesOfAmericaSharesOneSectorDetailsTotal.setInvestedValuePercentage(
				String.format(AnukyaConstants.FLOAT_2_DECIMAL, new BigDecimal(AnukyaConstants.NUMBER_100)));
		unitedStatesOfAmericaSharesOneSectorDetailsTotal
				.setCurrentValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalCurrentValue));
		unitedStatesOfAmericaSharesOneSectorDetailsTotal.setCurrentValuePercentage(
				String.format(AnukyaConstants.FLOAT_2_DECIMAL, new BigDecimal(AnukyaConstants.NUMBER_100)));
		unitedStatesOfAmericaSharesOneSectorDetailsTotal
				.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLoss));
		unitedStatesOfAmericaSharesOneSectorDetailsTotal
				.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLossPercentage));

		unitedStatesOfAmericaSharesOneSectorDetailsMap.get(unitedStatesOfAmericaShare.getSector())
				.put(AnukyaConstants.TOTAL_CONSTANT, unitedStatesOfAmericaSharesOneSectorDetailsTotal);
	}

	private void buildUnitedStatesOfAmericaSharesOneCategoryDetails(
			UnitedStatesOfAmericaShares unitedStatesOfAmericaShare,
			UnitedStatesOfAmericaSharesCalculationDetails unitedStatesOfAmericaSharesCalculationDetails,
			Map<String, Map<String, UnitedStatesOfAmericaSharesOneCategoryDetails>> unitedStatesOfAmericaSharesOneCategoryDetailsMap) {

		if (unitedStatesOfAmericaSharesOneCategoryDetailsMap.isEmpty()) {
			Map<String, UnitedStatesOfAmericaSharesOneCategoryDetails> oneCategoryDetailsMap = new LinkedHashMap<>();
			oneCategoryDetailsMap.put(AnukyaConstants.TOTAL_CONSTANT,
					new UnitedStatesOfAmericaSharesOneCategoryDetails());
		}

		if (!unitedStatesOfAmericaSharesOneCategoryDetailsMap.containsKey(unitedStatesOfAmericaShare.getCategory())) {
			Map<String, UnitedStatesOfAmericaSharesOneCategoryDetails> oneCategoryDetailsMap = new LinkedHashMap<>();
			oneCategoryDetailsMap.put(AnukyaConstants.TOTAL_CONSTANT,
					new UnitedStatesOfAmericaSharesOneCategoryDetails());
			unitedStatesOfAmericaSharesOneCategoryDetailsMap.put(unitedStatesOfAmericaShare.getCategory(),
					oneCategoryDetailsMap);
		}

		if (!unitedStatesOfAmericaSharesOneCategoryDetailsMap.get(unitedStatesOfAmericaShare.getCategory())
				.containsKey(unitedStatesOfAmericaShare.getYahooCode())) {
			unitedStatesOfAmericaSharesOneCategoryDetailsMap.get(unitedStatesOfAmericaShare.getCategory()).put(
					unitedStatesOfAmericaShare.getYahooCode(), new UnitedStatesOfAmericaSharesOneCategoryDetails());
		}

		BigDecimal purchaseQuantity = new BigDecimal(unitedStatesOfAmericaShare.getPurchaseQuantity());

		BigDecimal purchaseTotal = new BigDecimal(unitedStatesOfAmericaShare.getPurchaseTotal());

		BigDecimal investedValue = new BigDecimal(
				unitedStatesOfAmericaSharesOneCategoryDetailsMap.get(unitedStatesOfAmericaShare.getCategory())
						.get(unitedStatesOfAmericaShare.getYahooCode()).getInvestedValue()).add(purchaseTotal);
		BigDecimal totalInvestedValue = new BigDecimal(unitedStatesOfAmericaSharesOneCategoryDetailsMap
				.get(unitedStatesOfAmericaShare.getCategory()).get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue())
						.add(purchaseTotal);

		BigDecimal sellQuantity = purchaseQuantity;

		BigDecimal sellPrice = new BigDecimal(unitedStatesOfAmericaSharesCalculationDetails.getLatestTradingDetailsMap()
				.get(unitedStatesOfAmericaShare.getYahooCode()).getLastTradingPrice());

		BigDecimal sellGrossTotal = sellQuantity.multiply(sellPrice);

		BigDecimal sellCommission = unitedStatesOfAmericaExpenditureUtils.calculateCommission();

		BigDecimal sellTransactionFees = unitedStatesOfAmericaExpenditureUtils.calculateTransactionFees(1L);

		BigDecimal sellOtherFees = unitedStatesOfAmericaExpenditureUtils.getOtherFees();

		BigDecimal sellTotal = sellGrossTotal.subtract(sellCommission).subtract(sellTransactionFees)
				.subtract(sellOtherFees);

		BigDecimal currentValue = new BigDecimal(
				unitedStatesOfAmericaSharesOneCategoryDetailsMap.get(unitedStatesOfAmericaShare.getCategory())
						.get(unitedStatesOfAmericaShare.getYahooCode()).getCurrentValue()).add(sellTotal);
		BigDecimal totalCurrentValue = new BigDecimal(unitedStatesOfAmericaSharesOneCategoryDetailsMap
				.get(unitedStatesOfAmericaShare.getCategory()).get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue())
						.add(sellTotal);

		BigDecimal profitLoss = currentValue.subtract(investedValue);
		BigDecimal profitLossPercentage = currentValue.divide(investedValue, MathContext.DECIMAL128)
				.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
				.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));

		BigDecimal totalProfitLoss = totalCurrentValue.subtract(totalInvestedValue);
		BigDecimal totalProfitLossPercentage = totalCurrentValue.divide(totalInvestedValue, MathContext.DECIMAL128)
				.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
				.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));

		UnitedStatesOfAmericaSharesOneCategoryDetails unitedStatesOfAmericaSharesOneCategoryDetails = new UnitedStatesOfAmericaSharesOneCategoryDetails();
		unitedStatesOfAmericaSharesOneCategoryDetails.setScriptName(unitedStatesOfAmericaShare.getScriptName());
		unitedStatesOfAmericaSharesOneCategoryDetails.setScriptCode(unitedStatesOfAmericaShare.getYahooCode());
		unitedStatesOfAmericaSharesOneCategoryDetails
				.setInvestedValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, investedValue));
		unitedStatesOfAmericaSharesOneCategoryDetails
				.setCurrentValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, currentValue));
		unitedStatesOfAmericaSharesOneCategoryDetails
				.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLoss));
		unitedStatesOfAmericaSharesOneCategoryDetails
				.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLossPercentage));

		unitedStatesOfAmericaSharesOneCategoryDetailsMap.get(unitedStatesOfAmericaShare.getCategory())
				.put(unitedStatesOfAmericaShare.getYahooCode(), unitedStatesOfAmericaSharesOneCategoryDetails);

		UnitedStatesOfAmericaSharesOneCategoryDetails unitedStatesOfAmericaSharesOneCategoryDetailsTotal = new UnitedStatesOfAmericaSharesOneCategoryDetails();
		unitedStatesOfAmericaSharesOneCategoryDetailsTotal
				.setInvestedValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalInvestedValue));
		unitedStatesOfAmericaSharesOneCategoryDetailsTotal.setInvestedValuePercentage(
				String.format(AnukyaConstants.FLOAT_2_DECIMAL, new BigDecimal(AnukyaConstants.NUMBER_100)));
		unitedStatesOfAmericaSharesOneCategoryDetailsTotal
				.setCurrentValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalCurrentValue));
		unitedStatesOfAmericaSharesOneCategoryDetailsTotal.setCurrentValuePercentage(
				String.format(AnukyaConstants.FLOAT_2_DECIMAL, new BigDecimal(AnukyaConstants.NUMBER_100)));
		unitedStatesOfAmericaSharesOneCategoryDetailsTotal
				.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLoss));
		unitedStatesOfAmericaSharesOneCategoryDetailsTotal
				.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLossPercentage));

		unitedStatesOfAmericaSharesOneCategoryDetailsMap.get(unitedStatesOfAmericaShare.getCategory())
				.put(AnukyaConstants.TOTAL_CONSTANT, unitedStatesOfAmericaSharesOneCategoryDetailsTotal);

	}

	private BigDecimal getTtmDividendYield(String scriptCode, String latestTradingPrice) throws AnukyaException {

		BigDecimal ttmDividend = unitedStatesOfAmericaSharesServiceCall.getTtmDividend(scriptCode);

		return ttmDividend.divide(new BigDecimal(latestTradingPrice), MathContext.DECIMAL128)
				.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
	}

	private List<UnitedStatesOfAmericaSharesShortTermInvestment> updateUnitedStatesOfAmericaSharesShortTermInvestment(
			String userEmailId, String dateInddMmmYyyy,
			UnitedStatesOfAmericaSharesShortTermInvestment unitedStatesOfAmericaSharesShortTermInvestment)
			throws AnukyaException {

		File shortTermInvestmentFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_USA_EQ_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_JSON);

		List<UnitedStatesOfAmericaSharesShortTermInvestment> unitedStatesOfAmericaSharesShortTermInvestmentList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaSharesShortTermInvestmentList(shortTermInvestmentFile);

		for (UnitedStatesOfAmericaSharesShortTermInvestment existingUnitedStatesOfAmericaSharesShortTermInvestment : unitedStatesOfAmericaSharesShortTermInvestmentList) {
			if (existingUnitedStatesOfAmericaSharesShortTermInvestment.getYahooCode()
					.equals(unitedStatesOfAmericaSharesShortTermInvestment.getYahooCode())) {

				BigDecimal existingPurchaseQuantity = new BigDecimal(
						existingUnitedStatesOfAmericaSharesShortTermInvestment.getPurchaseQuantity());
				BigDecimal boughtPurchaseQuantity = new BigDecimal(
						unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseQuantity());
				BigDecimal purchaseQuantity = existingPurchaseQuantity.add(boughtPurchaseQuantity);
				existingUnitedStatesOfAmericaSharesShortTermInvestment
						.setPurchaseQuantity(String.valueOf(purchaseQuantity));

				BigDecimal existingPurchasePrice = new BigDecimal(
						existingUnitedStatesOfAmericaSharesShortTermInvestment.getPurchasePrice());
				BigDecimal boughtPurchasePrice = new BigDecimal(
						unitedStatesOfAmericaSharesShortTermInvestment.getPurchasePrice());
				BigDecimal purchasePrice = existingPurchaseQuantity.multiply(existingPurchasePrice)
						.add(boughtPurchaseQuantity.multiply(boughtPurchasePrice))
						.divide(purchaseQuantity, MathContext.DECIMAL128);
				existingUnitedStatesOfAmericaSharesShortTermInvestment.setPurchasePrice(String.valueOf(purchasePrice));

				BigDecimal existingPurchaseGrossTotal = new BigDecimal(
						existingUnitedStatesOfAmericaSharesShortTermInvestment.getPurchaseGrossTotal());
				BigDecimal boughtPurchaseGrossTotal = boughtPurchaseQuantity.multiply(boughtPurchasePrice);
				BigDecimal purchaseGrossTotal = existingPurchaseGrossTotal.add(boughtPurchaseGrossTotal);
				existingUnitedStatesOfAmericaSharesShortTermInvestment
						.setPurchaseGrossTotal(String.valueOf(purchaseGrossTotal));

				BigDecimal existingPurchaseCommission = new BigDecimal(
						existingUnitedStatesOfAmericaSharesShortTermInvestment.getPurchaseCommission());
				BigDecimal boughtPurchaseCommission = new BigDecimal(
						unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseCommission());
				BigDecimal purchaseCommission = existingPurchaseCommission.add(boughtPurchaseCommission);
				existingUnitedStatesOfAmericaSharesShortTermInvestment
						.setPurchaseCommission(String.valueOf(purchaseCommission));

				BigDecimal existingPurchaseTransactionFees = new BigDecimal(
						existingUnitedStatesOfAmericaSharesShortTermInvestment.getPurchaseTransactionFees());
				BigDecimal boughtPurchaseTransactionFees = new BigDecimal(
						unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseTransactionFees());
				BigDecimal purchaseTransactionFees = existingPurchaseTransactionFees.add(boughtPurchaseTransactionFees);
				existingUnitedStatesOfAmericaSharesShortTermInvestment
						.setPurchaseTransactionFees(String.valueOf(purchaseTransactionFees));

				BigDecimal existingPurchaseOtherFees = new BigDecimal(
						existingUnitedStatesOfAmericaSharesShortTermInvestment.getPurchaseOtherFees());
				BigDecimal boughtPurchaseOtherFees = new BigDecimal(
						unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseOtherFees());
				BigDecimal purchaseOtherFees = existingPurchaseOtherFees.add(boughtPurchaseOtherFees);
				existingUnitedStatesOfAmericaSharesShortTermInvestment
						.setPurchaseOtherFees(String.valueOf(purchaseOtherFees));

				BigDecimal existingPurchaseTotal = new BigDecimal(
						existingUnitedStatesOfAmericaSharesShortTermInvestment.getPurchaseTotal());
				BigDecimal boughtPurchaseTotal = boughtPurchaseGrossTotal.add(boughtPurchaseCommission)
						.add(boughtPurchaseTransactionFees).add(boughtPurchaseOtherFees);
				BigDecimal purchaseTotal = existingPurchaseTotal.add(boughtPurchaseTotal);
				existingUnitedStatesOfAmericaSharesShortTermInvestment.setPurchaseTotal(String.valueOf(purchaseTotal));

				BigDecimal existingPurchaseActualPricePerUnit = new BigDecimal(
						existingUnitedStatesOfAmericaSharesShortTermInvestment.getPurchaseNetPricePerUnit());
				BigDecimal boughtPurchaseActualPricePerUnit = boughtPurchaseTotal.divide(boughtPurchaseQuantity,
						MathContext.DECIMAL128);
				BigDecimal purchaseActualPricePerUnit = existingPurchaseQuantity
						.multiply(existingPurchaseActualPricePerUnit)
						.add(boughtPurchaseQuantity.multiply(boughtPurchaseActualPricePerUnit))
						.divide(purchaseQuantity, MathContext.DECIMAL128);
				existingUnitedStatesOfAmericaSharesShortTermInvestment
						.setPurchaseNetPricePerUnit(String.valueOf(purchaseActualPricePerUnit));

				return unitedStatesOfAmericaSharesShortTermInvestmentList;
			}
		}

		UnitedStatesOfAmericaSharesShortTermInvestment shortTermInvestment = addShortTermInvestment(
				unitedStatesOfAmericaSharesShortTermInvestment);

		shortTermInvestment.setCreatedBy(userEmailId);
		shortTermInvestment.setCreatedDate(dateInddMmmYyyy);
		shortTermInvestment.setLastUpdatedBy(userEmailId);
		shortTermInvestment.setLastUpdatedDate(dateInddMmmYyyy);

		unitedStatesOfAmericaSharesShortTermInvestmentList.add(shortTermInvestment);

		return unitedStatesOfAmericaSharesShortTermInvestmentList;
	}

	private List<UnitedStatesOfAmericaSharesShortTermInvestment> updateIndividualUnitedStatesOfAmericaSharesShortTermInvestment(
			String userEmailId, String dateInddMmmYyyy,
			UnitedStatesOfAmericaSharesShortTermInvestment unitedStatesOfAmericaSharesShortTermInvestment)
			throws AnukyaException {

		File individualUnitedStatesOfAmericaShortTermInvestmentFile = new File(databaseBaseLocation
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ userEmailId + AnukyaConstants.SHORT_TERM_INVESTMENT_USA_EQ_UPDATE_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ unitedStatesOfAmericaSharesShortTermInvestment.getYahooCode() + AnukyaConstants.JSON_EXTENSION);

		List<UnitedStatesOfAmericaSharesShortTermInvestment> individualUnitedStatesOfAmericaSharesShortTermInvestmentList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaSharesShortTermInvestmentList(
						individualUnitedStatesOfAmericaShortTermInvestmentFile);

		UnitedStatesOfAmericaSharesShortTermInvestment shortTermInestment = addShortTermInvestment(
				unitedStatesOfAmericaSharesShortTermInvestment);

		shortTermInestment.setCreatedBy(userEmailId);
		shortTermInestment.setCreatedDate(dateInddMmmYyyy);
		shortTermInestment.setLastUpdatedBy(userEmailId);
		shortTermInestment.setLastUpdatedDate(dateInddMmmYyyy);

		individualUnitedStatesOfAmericaSharesShortTermInvestmentList.add(shortTermInestment);

		return individualUnitedStatesOfAmericaSharesShortTermInvestmentList;
	}

	private UnitedStatesOfAmericaSharesShortTermInvestment addShortTermInvestment(
			UnitedStatesOfAmericaSharesShortTermInvestment unitedStatesOfAmericaSharesShortTermInvestment) {

		UnitedStatesOfAmericaSharesShortTermInvestment shortTermInvestment = new UnitedStatesOfAmericaSharesShortTermInvestment();

		shortTermInvestment.setId(anukyaUtils.generateId());

		shortTermInvestment.setScriptName(unitedStatesOfAmericaSharesShortTermInvestment.getScriptName());
		shortTermInvestment.setYahooCode(unitedStatesOfAmericaSharesShortTermInvestment.getYahooCode());
		shortTermInvestment.setPurchaseDate(unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseDate());
		shortTermInvestment.setPurchaseQuantity(unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseQuantity());
		shortTermInvestment.setPurchasePrice(
				String.valueOf(new BigDecimal(unitedStatesOfAmericaSharesShortTermInvestment.getPurchasePrice())));

		BigDecimal purchaseGrossTotal = new BigDecimal(shortTermInvestment.getPurchaseQuantity())
				.multiply(new BigDecimal(shortTermInvestment.getPurchasePrice()));
		shortTermInvestment.setPurchaseGrossTotal(String.valueOf(purchaseGrossTotal));

		shortTermInvestment.setPurchaseCommission(
				String.valueOf(new BigDecimal(unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseCommission())));

		shortTermInvestment.setPurchaseTransactionFees(String
				.valueOf(new BigDecimal(unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseTransactionFees())));

		shortTermInvestment.setPurchaseOtherFees(
				String.valueOf(new BigDecimal(unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseOtherFees())));

		shortTermInvestment.setPurchaseOtherFees(
				String.valueOf(new BigDecimal(unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseOtherFees())));

		BigDecimal purchaseTotal = purchaseGrossTotal.add(new BigDecimal(shortTermInvestment.getPurchaseCommission()))
				.add(new BigDecimal(shortTermInvestment.getPurchaseTransactionFees()))
				.add(new BigDecimal(shortTermInvestment.getPurchaseOtherFees()));
		shortTermInvestment.setPurchaseTotal(String.valueOf(purchaseTotal));

		BigDecimal purchaseActualPricePerUnit = purchaseTotal
				.divide(new BigDecimal(shortTermInvestment.getPurchaseQuantity()), MathContext.DECIMAL128);
		shortTermInvestment.setPurchaseNetPricePerUnit(String.valueOf(purchaseActualPricePerUnit));

		return shortTermInvestment;
	}

	private UnitedStatesOfAmericaSharesShortTermInvestmentResponse calculateShortTermInvestment(
			List<UnitedStatesOfAmericaSharesShortTermInvestment> unitedStatesOfAmericaSharesShortTermInvestmentList)
			throws AnukyaException {

		UnitedStatesOfAmericaSharesShortTermInvestmentResponse unitedStatesOfAmericaSharesShortTermInvestmentResponse = new UnitedStatesOfAmericaSharesShortTermInvestmentResponse();
		unitedStatesOfAmericaSharesShortTermInvestmentResponse
				.setShortTermInvestmentTotal(new UnitedStatesOfAmericaSharesShortTermInvestment());

		Map<String, Long> sellTransactionFeesScriptCountMap = unitedStatesOfAmericaSharesShortTermInvestmentList
				.parallelStream().collect(Collectors.groupingBy(
						UnitedStatesOfAmericaSharesShortTermInvestment::getYahooCode, Collectors.counting()));

		Map<String, UnitedStatesOfAmericaSharesLastTradingPrice> latestTradingDetailsMap = unitedStatesOfAmericaSharesUtils
				.getUnitedStatesOfAmericaShortTermInvestmentLastTradingPriceMap(
						unitedStatesOfAmericaSharesShortTermInvestmentList);

		for (UnitedStatesOfAmericaSharesShortTermInvestment unitedStatesOfAmericaSharesShortTermInvestment : unitedStatesOfAmericaSharesShortTermInvestmentList) {

			BigDecimal purchaseQuantity = new BigDecimal(
					unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseQuantity());
			BigDecimal existingTotalPurchaseQuantity = new BigDecimal(
					unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
							.getPurchaseQuantity());
			BigDecimal totalPurchaseQuantity = purchaseQuantity.add(existingTotalPurchaseQuantity);
			unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setPurchaseQuantity(String.format(AnukyaConstants.FLOAT_6_DECIMAL, totalPurchaseQuantity));

			BigDecimal purchasePrice = new BigDecimal(
					unitedStatesOfAmericaSharesShortTermInvestment.getPurchasePrice());
			BigDecimal existingTotalPurchasePrice = new BigDecimal(
					unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
							.getPurchasePrice());
			BigDecimal totalPurchasePrice = purchaseQuantity.multiply(purchasePrice)
					.add(existingTotalPurchaseQuantity.multiply(existingTotalPurchasePrice))
					.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
			unitedStatesOfAmericaSharesShortTermInvestment
					.setPurchasePrice(String.format(AnukyaConstants.FLOAT_4_DECIMAL, purchasePrice));
			unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setPurchasePrice(String.format(AnukyaConstants.FLOAT_4_DECIMAL, totalPurchasePrice));

			BigDecimal purchaseGrossTotal = purchaseQuantity.multiply(purchasePrice);
			BigDecimal existingPurchaseGrossTotal = new BigDecimal(
					unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
							.getPurchaseGrossTotal());
			BigDecimal totalPurchaseGrossTotal = purchaseGrossTotal.add(existingPurchaseGrossTotal);
			unitedStatesOfAmericaSharesShortTermInvestment
					.setPurchaseGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseGrossTotal));
			unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setPurchaseGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseGrossTotal));

			BigDecimal purchaseCommission = new BigDecimal(
					unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseCommission());
			BigDecimal existingTotalPurchaseCommission = new BigDecimal(
					unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
							.getPurchaseCommission());
			BigDecimal totalPurchaseCommission = purchaseCommission.add(existingTotalPurchaseCommission);
			unitedStatesOfAmericaSharesShortTermInvestment
					.setPurchaseCommission(String.format(AnukyaConstants.FLOAT_4_DECIMAL, purchaseCommission));
			unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setPurchaseCommission(String.format(AnukyaConstants.FLOAT_4_DECIMAL, totalPurchaseCommission));

			BigDecimal purchaseTransactionFees = new BigDecimal(
					unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseTransactionFees());
			BigDecimal existingTotalPurchaseTransactionFees = new BigDecimal(
					unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
							.getPurchaseTransactionFees());
			BigDecimal totalPurchaseTransactionFees = purchaseTransactionFees.add(existingTotalPurchaseTransactionFees);
			unitedStatesOfAmericaSharesShortTermInvestment.setPurchaseTransactionFees(
					String.format(AnukyaConstants.FLOAT_4_DECIMAL, purchaseTransactionFees));
			unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setPurchaseTransactionFees(
							String.format(AnukyaConstants.FLOAT_4_DECIMAL, totalPurchaseTransactionFees));

			BigDecimal purchaseOtherFees = new BigDecimal(
					unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseOtherFees());
			BigDecimal existingTotalPurchaseOtherFees = new BigDecimal(
					unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
							.getPurchaseOtherFees());
			BigDecimal totalPurchaseOtherFees = purchaseOtherFees.add(existingTotalPurchaseOtherFees);
			unitedStatesOfAmericaSharesShortTermInvestment
					.setPurchaseOtherFees(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseOtherFees));
			unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setPurchaseOtherFees(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseOtherFees));

			BigDecimal purchaseTotal = new BigDecimal(
					unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseTotal());
			BigDecimal existingTotalPurchaseTotal = new BigDecimal(
					unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
							.getPurchaseTotal());
			BigDecimal totalPurchaseTotal = purchaseTotal.add(existingTotalPurchaseTotal);
			unitedStatesOfAmericaSharesShortTermInvestment
					.setPurchaseTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseTotal));
			unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setPurchaseTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseTotal));

			BigDecimal purchaseActualPricePerUnit = new BigDecimal(
					unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseNetPricePerUnit());
			BigDecimal existingPurchaseActualPricePerUnit = new BigDecimal(
					unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
							.getPurchaseNetPricePerUnit());
			BigDecimal totalPurchaseActualCost = purchaseQuantity.multiply(purchaseActualPricePerUnit)
					.add(existingTotalPurchaseQuantity.multiply(existingPurchaseActualPricePerUnit))
					.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
			unitedStatesOfAmericaSharesShortTermInvestment.setPurchaseNetPricePerUnit(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseActualPricePerUnit));
			unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setPurchaseNetPricePerUnit(
							String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseActualCost));

			BigDecimal sellQuantity = purchaseQuantity;
			BigDecimal existingSellQuantity = new BigDecimal(unitedStatesOfAmericaSharesShortTermInvestmentResponse
					.getShortTermInvestmentTotal().getSellQuantity());
			BigDecimal totalSellQuantity = sellQuantity.add(existingSellQuantity);
			unitedStatesOfAmericaSharesShortTermInvestment
					.setSellQuantity(String.format(AnukyaConstants.FLOAT_6_DECIMAL, sellQuantity));
			unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setSellQuantity(String.format(AnukyaConstants.FLOAT_6_DECIMAL, totalSellQuantity));

			BigDecimal sellPrice = new BigDecimal(latestTradingDetailsMap
					.get(unitedStatesOfAmericaSharesShortTermInvestment.getYahooCode()).getLastTradingPrice());
			BigDecimal existingTotalSellPrice = new BigDecimal(unitedStatesOfAmericaSharesShortTermInvestmentResponse
					.getShortTermInvestmentTotal().getSellPrice());
			BigDecimal totalSellPrice = sellQuantity.multiply(sellPrice)
					.add(existingSellQuantity.multiply(existingTotalSellPrice))
					.divide(totalSellQuantity, MathContext.DECIMAL128);
			unitedStatesOfAmericaSharesShortTermInvestment
					.setSellPrice(String.format(AnukyaConstants.FLOAT_4_DECIMAL, sellPrice));
			unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setSellPrice(String.format(AnukyaConstants.FLOAT_4_DECIMAL, totalSellPrice));

			BigDecimal sellGrossTotal = sellQuantity.multiply(sellPrice);
			BigDecimal existingSellGrossTotal = new BigDecimal(unitedStatesOfAmericaSharesShortTermInvestmentResponse
					.getShortTermInvestmentTotal().getSellGrossTotal());
			BigDecimal totalSellGrossTotal = sellGrossTotal.add(existingSellGrossTotal);
			unitedStatesOfAmericaSharesShortTermInvestment
					.setSellGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellGrossTotal));
			unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setSellGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellGrossTotal));

			BigDecimal sellCommission = unitedStatesOfAmericaExpenditureUtils.calculateCommission();
			BigDecimal existingSellCommission = new BigDecimal(unitedStatesOfAmericaSharesShortTermInvestmentResponse
					.getShortTermInvestmentTotal().getSellCommission());
			BigDecimal totalSellCommission = sellCommission.add(existingSellCommission);
			unitedStatesOfAmericaSharesShortTermInvestment
					.setSellCommission(String.format(AnukyaConstants.FLOAT_4_DECIMAL, sellCommission));
			unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setSellCommission(String.format(AnukyaConstants.FLOAT_4_DECIMAL, totalSellCommission));

			BigDecimal sellTransactionFees = unitedStatesOfAmericaExpenditureUtils
					.calculateTransactionFees(sellTransactionFeesScriptCountMap
							.get(unitedStatesOfAmericaSharesShortTermInvestment.getYahooCode()));
			BigDecimal existingSellTransactionFees = new BigDecimal(
					unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
							.getSellTransactionFees());
			BigDecimal totalSellTransactionFees = sellTransactionFees.add(existingSellTransactionFees);
			unitedStatesOfAmericaSharesShortTermInvestment
					.setSellTransactionFees(String.format(AnukyaConstants.FLOAT_4_DECIMAL, sellTransactionFees));
			unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setSellTransactionFees(String.format(AnukyaConstants.FLOAT_4_DECIMAL, totalSellTransactionFees));

			BigDecimal sellOtherFees = unitedStatesOfAmericaExpenditureUtils.getOtherFees();
			BigDecimal existingSellOtherFees = new BigDecimal(unitedStatesOfAmericaSharesShortTermInvestmentResponse
					.getShortTermInvestmentTotal().getSellOtherFees());
			BigDecimal totalSellOtherFees = sellOtherFees.add(existingSellOtherFees);
			unitedStatesOfAmericaSharesShortTermInvestment
					.setSellOtherFees(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellOtherFees));
			unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setSellOtherFees(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellOtherFees));

			BigDecimal sellTotal = sellGrossTotal.subtract(sellCommission).subtract(sellTransactionFees)
					.subtract(sellOtherFees);
			BigDecimal existingSellTotal = new BigDecimal(unitedStatesOfAmericaSharesShortTermInvestmentResponse
					.getShortTermInvestmentTotal().getSellTotal());
			BigDecimal totalSellTotal = sellTotal.add(existingSellTotal);
			unitedStatesOfAmericaSharesShortTermInvestment
					.setSellTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellTotal));
			unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setSellTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellTotal));

			BigDecimal sellActualPricePerUnit = sellTotal.divide(sellQuantity, MathContext.DECIMAL128);
			BigDecimal totalSellActualPricePerUnit = totalSellTotal.divide(totalSellQuantity, MathContext.DECIMAL128);
			unitedStatesOfAmericaSharesShortTermInvestment
					.setSellNetPricePerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellActualPricePerUnit));
			unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal().setSellNetPricePerUnit(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellActualPricePerUnit));

			BigDecimal profitLoss = sellTotal.subtract(purchaseTotal);
			BigDecimal totalProfitLoss = totalSellTotal.subtract(totalPurchaseTotal);
			unitedStatesOfAmericaSharesShortTermInvestment
					.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLoss));
			unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLoss));

			BigDecimal profitLossPercentage = sellTotal.divide(purchaseTotal, MathContext.DECIMAL128)
					.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
					.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
			BigDecimal totalProfitLossPercentage = totalSellTotal.divide(totalPurchaseTotal, MathContext.DECIMAL128)
					.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
					.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
			unitedStatesOfAmericaSharesShortTermInvestment
					.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLossPercentage));
			unitedStatesOfAmericaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLossPercentage));
		}

		unitedStatesOfAmericaSharesShortTermInvestmentResponse
				.setShortTermInvestmentScriptsList(unitedStatesOfAmericaSharesShortTermInvestmentList);

		return unitedStatesOfAmericaSharesShortTermInvestmentResponse;
	}
}
