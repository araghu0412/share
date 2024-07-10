package com.moneymanager.anukya.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.ThreadContext;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.moneymanager.anukya.bhaaratha.eq.AbstractBhaarathaShares;
import com.moneymanager.anukya.bhaaratha.factories.BhaarathaSharesFactory;
import com.moneymanager.anukya.data.impl.BhaarathaSharesData;
import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.AnukyaUiFieldMapping;
import com.moneymanager.anukya.exception.ErrorDetails;
import com.moneymanager.anukya.exception.handler.BhaarathaSharesExceptionHandler;
import com.moneymanager.anukya.model.BhaarathaShares;
import com.moneymanager.anukya.model.BhaarathaSharesAnalysis;
import com.moneymanager.anukya.model.BhaarathaSharesCalculationDetails;
import com.moneymanager.anukya.model.BhaarathaSharesCategory;
import com.moneymanager.anukya.model.BhaarathaSharesInvestmentResearch;
import com.moneymanager.anukya.model.BhaarathaSharesInvestmentResearchResponse;
import com.moneymanager.anukya.model.BhaarathaSharesLastTradingPrice;
import com.moneymanager.anukya.model.BhaarathaSharesOneCategoryDetails;
import com.moneymanager.anukya.model.BhaarathaSharesOneSectorDetails;
import com.moneymanager.anukya.model.BhaarathaSharesOneShareDetails;
import com.moneymanager.anukya.model.BhaarathaSharesOneShareDetailsResponse;
import com.moneymanager.anukya.model.BhaarathaSharesResponse;
import com.moneymanager.anukya.model.BhaarathaSharesScriptCodeDetails;
import com.moneymanager.anukya.model.BhaarathaSharesSector;
import com.moneymanager.anukya.model.BhaarathaSharesShareNameDetails;
import com.moneymanager.anukya.model.BhaarathaSharesShortTermInvestment;
import com.moneymanager.anukya.model.BhaarathaSharesShortTermInvestmentResponse;
import com.moneymanager.anukya.model.CommonResponse;
import com.moneymanager.anukya.servicecall.BhaarathaSharesServiceCall;
import com.moneymanager.anukya.services.BhaarathaSharesServices;
import com.moneymanager.anukya.utils.AnukyaConstants;
import com.moneymanager.anukya.utils.AnukyaDataUtils;
import com.moneymanager.anukya.utils.AnukyaUtils;
import com.moneymanager.anukya.utils.BhaarathaExpenditureUtils;
import com.moneymanager.anukya.utils.BhaarathaSharesUtils;

@Component
public class BhaarathaSharesServicesImpl implements BhaarathaSharesServices {

	@Value("${DATABASE.BASE.LOCATION}")
	private String databaseBaseLocation;

	@Value("${BHAARATHA.SHARES.DIVIDEND.YIELD.ANALYSIS.VALUE}")
	private String bhaarathaSharesDividendYieldAnalysisValue;

	@Autowired
	private BhaarathaSharesExceptionHandler bhaarathaSharesExceptionHandler;

	@Autowired
	private AnukyaDataUtils anukyaDataUtils;

	@Autowired
	private BhaarathaSharesFactory bhaarathaSharesFactory;

	@Autowired
	private BhaarathaSharesData bhaarathaSharesData;

	@Autowired
	private AnukyaUtils anukyaUtils;

	@Autowired
	private BhaarathaSharesServiceCall bhaarathaSharesServiceCall;

	@Autowired
	private BhaarathaSharesUtils bhaarathaSharesUtils;

	@Autowired
	private BhaarathaExpenditureUtils bhaarathaExpenditureUtils;

	@Override
	public BhaarathaSharesShareNameDetails getShareNameDetails(String stockExchange, String scriptCode)
			throws AnukyaException {

		bhaarathaSharesExceptionHandler.shareNameDetailsException(stockExchange, scriptCode);

		return bhaarathaSharesServiceCall.getBhaarathaSharesShareNameDetails(stockExchange, scriptCode);
	}

	@Override
	public CommonResponse addBhaarathaSharesShortTermInvestment(String userEmailId,
			BhaarathaSharesShortTermInvestment bhaarathaSharesShortTermInvestment) throws AnukyaException {

		bhaarathaSharesExceptionHandler.addShortTermInvestmentException(bhaarathaSharesShortTermInvestment);

		File updateDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_BHA_EQ_UPDATE_DIRECTORY);
		File mainBackUpDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_BHA_EQ_MAIN_BACKUP_DIRECTORY);
		File mainDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_BHA_EQ_MAIN_DIRECTORY);

		// Delete update directory and main backup directory
		anukyaDataUtils.deleteDirectory(updateDirectory);
		anukyaDataUtils.deleteDirectory(mainBackUpDirectory);

		// Copy main directory to update directory and main back up directory
		anukyaDataUtils.copyDirectory(mainDirectory, updateDirectory);
		anukyaDataUtils.copyDirectory(mainDirectory, mainBackUpDirectory);

		String dateInddMmmYyyy = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		// Update combined Bhaaratha Shares short term investment object
		List<BhaarathaSharesShortTermInvestment> bhaarathaSharesShortTermInvestmentList = updateBhaarathaSharesShortTermInvestment(
				userEmailId, dateInddMmmYyyy, bhaarathaSharesShortTermInvestment);

		// Update individual Bhaaratha Shares short term investment object
		List<BhaarathaSharesShortTermInvestment> individualBhaarathaSharesShortTermInvestmentList = updateIndividualBhaarathaSharesShortTermInvestment(
				userEmailId, dateInddMmmYyyy, bhaarathaSharesShortTermInvestment);

		// Save the updated combined Bhaaratha shares short term investment
		File shortTremInvestmentFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_BHA_EQ_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_JSON);

		anukyaDataUtils.createOrUpdateFile(shortTremInvestmentFile, bhaarathaSharesShortTermInvestmentList);

		// Save the updated individual Bhaaratha Shares short term investment
		File individualShortTremInvestmentFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_BHA_EQ_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ bhaarathaSharesShortTermInvestment.getBseCode() + AnukyaConstants.JSON_EXTENSION);

		anukyaDataUtils.createOrUpdateFile(individualShortTremInvestmentFile,
				individualBhaarathaSharesShortTermInvestmentList);

		// Delete main directory
		anukyaDataUtils.deleteDirectory(mainDirectory);

		// Copy from update directory to main directory
		anukyaDataUtils.copyDirectory(updateDirectory, mainDirectory);

		// Delete the update directory
		anukyaDataUtils.deleteDirectory(updateDirectory);

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setStatus(true);
		commonResponse.setMessage("Bhaaratha Shares Short term investment added successfully");

		return commonResponse;
	}

	@Override
	public BhaarathaSharesShortTermInvestmentResponse getShortTermInvestment(String userEmailId, String bseCode)
			throws AnukyaException {

		List<BhaarathaSharesShortTermInvestment> bhaarathaSharesShortTermInvestmentList;

		File shortTermInvestmentFile;

		if (bseCode == null || bseCode.isEmpty()) {
			shortTermInvestmentFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
					+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
					+ AnukyaConstants.SHORT_TERM_INVESTMENT_BHA_EQ_MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
					+ AnukyaConstants.SHORT_TERM_INVESTMENT_JSON);
		} else {
			shortTermInvestmentFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
					+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
					+ AnukyaConstants.SHORT_TERM_INVESTMENT_BHA_EQ_MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
					+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH + bseCode
					+ AnukyaConstants.JSON_EXTENSION);
		}

		bhaarathaSharesShortTermInvestmentList = bhaarathaSharesData
				.getBhaarathaSharesShortTermInvestmentList(shortTermInvestmentFile);

		if (bhaarathaSharesShortTermInvestmentList.isEmpty()) {

			throw new AnukyaException("No content for Bhaaratha Shares Short term investment", null,
					AnukyaErrorConstants.NO_CONTENT, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		return calculateShortTermInvestment(bhaarathaSharesShortTermInvestmentList);
	}

	@Override
	public CommonResponse deleteShortTermInvestment(String userEmailId, String bseCode) throws AnukyaException {

		bhaarathaSharesExceptionHandler.deleteShortTermInvestmentException(bseCode);

		// Check if short term investment exists
		File individualShortTermInvestmentFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_BHA_EQ_MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH + bseCode
				+ AnukyaConstants.JSON_EXTENSION);

		if (!anukyaDataUtils.isFileExists(individualShortTermInvestmentFile)) {
			throw new AnukyaException("Bhaaratha Shares Short term investment does not exist", null,
					AnukyaErrorConstants.NO_CONTENT, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		File updateDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_BHA_EQ_UPDATE_DIRECTORY);
		File mainBackUpDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_BHA_EQ_MAIN_BACKUP_DIRECTORY);
		File mainDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_BHA_EQ_MAIN_DIRECTORY);

		// Delete update directory and main backup directory
		anukyaDataUtils.deleteDirectory(updateDirectory);
		anukyaDataUtils.deleteDirectory(mainBackUpDirectory);

		// Copy main directory to update directory and backup directory
		anukyaDataUtils.copyDirectory(mainDirectory, updateDirectory);
		anukyaDataUtils.copyDirectory(mainDirectory, mainBackUpDirectory);

		// Delete individual file
		individualShortTermInvestmentFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_BHA_EQ_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH + bseCode
				+ AnukyaConstants.JSON_EXTENSION);
		anukyaDataUtils.deleteFile(individualShortTermInvestmentFile);

		// Get existing combined short term investment and remove
		File shortTermInvestmentFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_BHA_EQ_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_JSON);
		List<BhaarathaSharesShortTermInvestment> bhaarathaSharesShortTermInvestmentList = bhaarathaSharesData
				.getBhaarathaSharesShortTermInvestmentList(shortTermInvestmentFile);

		List<BhaarathaSharesShortTermInvestment> updatedBhaarathaSharesShortTermInvestmentList = new ArrayList<>();

		String shareName = "";
		for (BhaarathaSharesShortTermInvestment bhaarathaSharesShortTermInvestment : bhaarathaSharesShortTermInvestmentList) {
			if (bhaarathaSharesShortTermInvestment.getBseCode().equalsIgnoreCase(bseCode)) {
				shareName = bhaarathaSharesShortTermInvestment.getShareName();
			} else {
				updatedBhaarathaSharesShortTermInvestmentList.add(bhaarathaSharesShortTermInvestment);
			}
		}

		// Removing existing combined short term investment
		anukyaDataUtils.deleteFile(shortTermInvestmentFile);

		// Add the combined short term investment after removing
		anukyaDataUtils.createOrUpdateFile(shortTermInvestmentFile, updatedBhaarathaSharesShortTermInvestmentList);

		// Delete main directory
		anukyaDataUtils.deleteDirectory(mainDirectory);

		// Copy from update directory to main directory
		anukyaDataUtils.copyDirectory(updateDirectory, mainDirectory);

		// Delete the update directory and main backup directory
		anukyaDataUtils.deleteDirectory(updateDirectory);

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setStatus(true);
		commonResponse.setMessage("Bhaaratha Shares Short term investment deleted successfully for " + shareName
				+ " [BSE code " + bseCode + "]");

		return commonResponse;
	}

	@Override
	public CommonResponse addBhaarathaShares(String userEmailId, String type, BhaarathaShares bhaarathaShares)
			throws AnukyaException {

		if (type.equalsIgnoreCase(AnukyaConstants.BOUGHT_CONSTANT)) {
			bhaarathaSharesExceptionHandler.boughtSharesException(bhaarathaShares);
		} else if (type.equalsIgnoreCase(AnukyaConstants.SOLD_CONSTANT)) {
			bhaarathaSharesExceptionHandler.soldSharesException(bhaarathaShares);
		}

		AbstractBhaarathaShares abstractBhaarathaShares = bhaarathaSharesFactory.getBhaarathaShares(type);
		abstractBhaarathaShares.userEmailId = userEmailId;
		return abstractBhaarathaShares.addShares(bhaarathaShares);
	}

	@Override
	public BhaarathaSharesResponse getBhaarathaShares(String userEmailId, String type, boolean isNonConsolidated,
			String searchTerm, boolean isLongTermOnly) throws AnukyaException {

		AbstractBhaarathaShares abstractBhaarathaShares = bhaarathaSharesFactory.getBhaarathaShares(type);
		abstractBhaarathaShares.userEmailId = userEmailId;
		return abstractBhaarathaShares.getBhaarathaShares(isNonConsolidated, searchTerm, isLongTermOnly);
	}

	@Override
	public BhaarathaSharesOneShareDetailsResponse getOneShareDetails(String userEmailId, String type, String bseCode,
			String nseCode, String moneycontrolCode, String yahooBseCode, String yahooNseCode) throws AnukyaException {

		bhaarathaSharesExceptionHandler.oneShareDetailsException(type, bseCode, nseCode, moneycontrolCode, yahooBseCode,
				yahooNseCode);

		// Check if one share script exists
		File oneShareDetailsFile;
		if (type.equalsIgnoreCase(AnukyaConstants.BOUGHT_CONSTANT)) {
			oneShareDetailsFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
					+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
					+ AnukyaConstants.BHA_EQ_PURCHASE_MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
					+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH + bseCode
					+ AnukyaConstants.JSON_EXTENSION);
		} else if (type.equalsIgnoreCase(AnukyaConstants.SOLD_CONSTANT)) {
			oneShareDetailsFile = new File(
					databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
							+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_SOLD_MAIN_DIRECTORY
							+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SINGLE_DIRECTORY
							+ AnukyaConstants.FORWARD_SLASH + bseCode + AnukyaConstants.JSON_EXTENSION);
		} else {
			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_OSD_0001);
			errorDetails.setErrorMessage("TYPE is invalid");
			errorDetails.setUiField(AnukyaUiFieldMapping.ONE_SHARE_DETAILS_BHAARATHA_TYPE);

			errorDetailsList.add(errorDetails);
			throw new AnukyaException("Exception occured while retrieving Bhaaratha Shares one share details",
					errorDetailsList, AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		if (!anukyaDataUtils.isFileExists(oneShareDetailsFile)) {
			throw new AnukyaException("Bhaaratha Shares Short term investment does not exist", null,
					AnukyaErrorConstants.NO_CONTENT, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		// Get BSE Details
		BhaarathaSharesOneShareDetails bseBhaarathaOneShareDetails = bhaarathaSharesUtils
				.getBhaarathaSharesOneShareDetails(new BhaarathaSharesScriptCodeDetails(AnukyaConstants.BSE, bseCode,
						nseCode, moneycontrolCode, yahooBseCode, yahooNseCode));

		BigDecimal bseFiftyWeelHighLowValue = anukyaUtils.calculateHighLow(
				new BigDecimal(bseBhaarathaOneShareDetails.getFiftyTwoWeekLow()),
				new BigDecimal(bseBhaarathaOneShareDetails.getFiftyTwoWeekHigh()),
				new BigDecimal(bseBhaarathaOneShareDetails.getCurrentMarketPrice()));
		if (bseFiftyWeelHighLowValue != null) {
			bseBhaarathaOneShareDetails.setFiftyTwoWeekHighLowValue(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, bseFiftyWeelHighLowValue));
		}

		BigDecimal bseDailyHighLowValue = anukyaUtils.calculateHighLow(
				new BigDecimal(bseBhaarathaOneShareDetails.getDailyLow()),
				new BigDecimal(bseBhaarathaOneShareDetails.getDailyHigh()),
				new BigDecimal(bseBhaarathaOneShareDetails.getCurrentMarketPrice()));
		if (bseDailyHighLowValue != null) {
			bseBhaarathaOneShareDetails
					.setDailyHighLowValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, bseDailyHighLowValue));
		}

		// Get NSE Details
		BhaarathaSharesOneShareDetails nseBhaarathaOneShareDetails = bhaarathaSharesUtils
				.getBhaarathaSharesOneShareDetails(new BhaarathaSharesScriptCodeDetails(AnukyaConstants.NSE, bseCode,
						nseCode, moneycontrolCode, yahooBseCode, yahooNseCode));

		BigDecimal nseFiftyWeelHighLowValue = anukyaUtils.calculateHighLow(
				new BigDecimal(nseBhaarathaOneShareDetails.getFiftyTwoWeekLow()),
				new BigDecimal(nseBhaarathaOneShareDetails.getFiftyTwoWeekHigh()),
				new BigDecimal(nseBhaarathaOneShareDetails.getCurrentMarketPrice()));
		if (nseFiftyWeelHighLowValue != null) {
			nseBhaarathaOneShareDetails.setFiftyTwoWeekHighLowValue(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, nseFiftyWeelHighLowValue));
		}

		BigDecimal nseDailyHighLowValue = anukyaUtils.calculateHighLow(
				new BigDecimal(nseBhaarathaOneShareDetails.getDailyLow()),
				new BigDecimal(nseBhaarathaOneShareDetails.getDailyHigh()),
				new BigDecimal(nseBhaarathaOneShareDetails.getCurrentMarketPrice()));
		if (nseDailyHighLowValue != null) {
			nseBhaarathaOneShareDetails
					.setDailyHighLowValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, nseDailyHighLowValue));
		}

		// Get one share details
		List<BhaarathaShares> bhaarathaSharesList = bhaarathaSharesData.getBhaarathaShares(oneShareDetailsFile);

		AbstractBhaarathaShares abstractBhaarathaShares = bhaarathaSharesFactory.getBhaarathaShares(type);
		abstractBhaarathaShares.userEmailId = userEmailId;
		BhaarathaSharesResponse bhaarathaSharesResponse = abstractBhaarathaShares
				.getOneShareDetails(bhaarathaSharesList);

		BhaarathaSharesOneShareDetailsResponse bhaarathaSharesOneShareDetailsResponse = new BhaarathaSharesOneShareDetailsResponse();
		bhaarathaSharesOneShareDetailsResponse.setBseCode(bseCode);
		bhaarathaSharesOneShareDetailsResponse.setNseCode(nseCode);
		bhaarathaSharesOneShareDetailsResponse.setMoneycontrolCode(moneycontrolCode);
		bhaarathaSharesOneShareDetailsResponse.setBhaarathaSharesResponse(bhaarathaSharesResponse);
		bhaarathaSharesOneShareDetailsResponse.setBseOneShareDetails(bseBhaarathaOneShareDetails);
		bhaarathaSharesOneShareDetailsResponse.setNseOneShareDetails(nseBhaarathaOneShareDetails);

		return bhaarathaSharesOneShareDetailsResponse;
	}

	@Override
	public BhaarathaSharesAnalysis getCompleteAnalysis(String userEmailId) throws AnukyaException {

		File consolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_PURCHASE_MAIN_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		List<BhaarathaShares> bhaarathaSharesList = bhaarathaSharesData.getBhaarathaShares(consolidatedFile);

		if (bhaarathaSharesList.isEmpty()) {
			throw new AnukyaException("No content for Bhaaratha Shares analysis", null, AnukyaErrorConstants.NO_CONTENT,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		return getAnalysis(bhaarathaSharesList);
	}

	@Override
	public ResponseEntity<InputStreamResource> downloadBulkUploadDocument() throws IOException, AnukyaException {

		File file = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.DOCUMENTS_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.BHA + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.EQ + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.BHAARATHA_SHARES_BULK_UPLOAD_FILE_NAME);

		InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=" + file.getName());
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		headers.add("fileName", file.getName());

		List<String> exposeHeadersList = Arrays.asList("fileName");
		headers.setAccessControlExposeHeaders(exposeHeadersList);

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(inputStreamResource);
	}

	@Override
	public CommonResponse bulkUpload(String userEmailId, MultipartFile bulkUploadFile)
			throws IOException, AnukyaException {

		CommonResponse commonResponse = new CommonResponse();

		Workbook workBook = new XSSFWorkbook(bulkUploadFile.getInputStream());
		Sheet sheet = workBook.getSheet(AnukyaConstants.TRANSACTIONS);

		// Excel Validation Exception
		bhaarathaSharesExceptionHandler.bulkUploadExcelException(sheet);

		// Bulk Upload data exception
		StringBuilder errorMessageStringBuilder = new StringBuilder();

		List<BhaarathaShares> bhaarathaSharesList = new ArrayList<>();

		DataFormatter formatter = new DataFormatter();

		for (int i = 1; i < 26; i++) {
			BhaarathaShares bhaarathaShares = new BhaarathaShares();

			Row row = sheet.getRow(i);

			if (AnukyaConstants.BUY.equalsIgnoreCase(formatter.formatCellValue(row.getCell(1)))) {
				bhaarathaShares.setScriptName(formatter.formatCellValue(row.getCell(3)));
				bhaarathaShares.setBseScriptCode(formatter.formatCellValue(row.getCell(4)));
				bhaarathaShares.setYahooBseScriptCode(formatter.formatCellValue(row.getCell(5)));
				bhaarathaShares.setNseScriptCode(formatter.formatCellValue(row.getCell(6)));
				bhaarathaShares.setYahooNseScriptCode(formatter.formatCellValue(row.getCell(7)));
				bhaarathaShares.setIsinCode(formatter.formatCellValue(row.getCell(8)));
				bhaarathaShares.setMoneycontrolCode(formatter.formatCellValue(row.getCell(9)));
				bhaarathaShares.setCategory(formatter.formatCellValue(row.getCell(10)));
				bhaarathaShares.setSector(formatter.formatCellValue(row.getCell(11)));
				bhaarathaShares.setIndustry(formatter.formatCellValue(row.getCell(12)));
				bhaarathaShares.setShortTermInvestment(
						AnukyaConstants.YES.equalsIgnoreCase(formatter.formatCellValue(row.getCell(13))));

				if (AnukyaConstants.YES.equalsIgnoreCase(formatter.formatCellValue(row.getCell(14)))) {
					bhaarathaShares.setSharesSplitSelected(true);
					bhaarathaShares.setSharesSplitRatio(formatter.formatCellValue(row.getCell(18)).replace("::", ":"));
					bhaarathaShares.setSharesSplitDate(row.getCell(19).toString().toUpperCase());
				}

				if (AnukyaConstants.YES.equalsIgnoreCase(formatter.formatCellValue(row.getCell(15)))) {
					bhaarathaShares.setSharesBonusSelected(true);
					bhaarathaShares.setSharesBonusRatio(formatter.formatCellValue(row.getCell(18)).replace("::", ":"));
					bhaarathaShares.setSharesBonusDate(row.getCell(19).toString().toUpperCase());
				}

				bhaarathaShares.setStockExchange(formatter.formatCellValue(row.getCell(16)));

				bhaarathaShares.setPurchaseDate(row.getCell(21).toString().toUpperCase());
				bhaarathaShares.setPurchaseQuantity(formatter.formatCellValue(row.getCell(22)));
				bhaarathaShares.setPurchasePrice(formatter.formatCellValue(row.getCell(23)));
				bhaarathaShares.setPurchaseBrokerage(formatter.formatCellValue(row.getCell(24)));
				bhaarathaShares.setPurchaseSTT(formatter.formatCellValue(row.getCell(25)));
				bhaarathaShares.setPurchaseExpenditure(formatter.formatCellValue(row.getCell(26)));
				bhaarathaShares.setPurchaseNonExpenditure(formatter.formatCellValue(row.getCell(27)));

				try {
					bhaarathaSharesExceptionHandler.boughtSharesException(bhaarathaShares);
				} catch (AnukyaException ae) {
					int columnIndex = row.getCell(0).getColumnIndex();
					int rowIndex = row.getCell(0).getRowIndex();

					for (ErrorDetails errorDetails : ae.getError()) {
						errorMessageStringBuilder.append(AnukyaConstants.ROW + AnukyaConstants.RATIO_CONSTANT
								+ AnukyaConstants.SPACE + new CellReference(rowIndex, columnIndex).getCellRefParts()[1]
								+ AnukyaConstants.RATIO_CONSTANT + AnukyaConstants.SPACE
								+ errorDetails.getErrorMessage() + AnukyaConstants.BR_CONSTANT);
					}
				}

			} else if (AnukyaConstants.SELL.equalsIgnoreCase(row.getCell(1).toString())) {

				bhaarathaShares.setScriptName(formatter.formatCellValue(row.getCell(3)));
				bhaarathaShares.setBseScriptCode(formatter.formatCellValue(row.getCell(4)));
				bhaarathaShares.setNseScriptCode(formatter.formatCellValue(row.getCell(6)));
				bhaarathaShares.setMoneycontrolCode(formatter.formatCellValue(row.getCell(9)));
				bhaarathaShares.setStockExchange(formatter.formatCellValue(row.getCell(16)));
				bhaarathaShares.setSellDate(row.getCell(21).toString().toUpperCase());
				bhaarathaShares.setSellQuantity(formatter.formatCellValue(row.getCell(22)));
				bhaarathaShares.setSellPrice(formatter.formatCellValue(row.getCell(23)));
				bhaarathaShares.setSellBrokerage(formatter.formatCellValue(row.getCell(24)));
				bhaarathaShares.setSellSTT(formatter.formatCellValue(row.getCell(25)));
				bhaarathaShares.setSellExpenditure(formatter.formatCellValue(row.getCell(26)));
				bhaarathaShares.setSellNonExpenditure(formatter.formatCellValue(row.getCell(27)));

				try {
					bhaarathaSharesExceptionHandler.soldSharesException(bhaarathaShares);
				} catch (AnukyaException ae) {
					int columnIndex = row.getCell(0).getColumnIndex();
					int rowIndex = row.getCell(0).getRowIndex();

					for (ErrorDetails errorDetails : ae.getError()) {
						errorMessageStringBuilder.append(AnukyaConstants.ROW + AnukyaConstants.RATIO_CONSTANT
								+ AnukyaConstants.SPACE + new CellReference(rowIndex, columnIndex).getCellRefParts()[1]
								+ AnukyaConstants.RATIO_CONSTANT + AnukyaConstants.SPACE
								+ errorDetails.getErrorMessage() + AnukyaConstants.BR_CONSTANT);
					}
				}
			}

			bhaarathaSharesList.add(bhaarathaShares);

		}

		if (!errorMessageStringBuilder.toString().isEmpty()) {

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode("");
			errorDetails.setErrorMessage(errorMessageStringBuilder.toString());

			errorDetailsList.add(errorDetails);

			throw new AnukyaException(
					"Exception occured while adding Bhaaratha Shares bulk upload [Data validation exception]",
					errorDetailsList, AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		workBook.close();

		File purchaseMainDirectory = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_PURCHASE_MAIN_DIRECTORY);
		File purcahseUpdateDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY);
		File purchaseMainBackUpDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.BHA_EQ_PURCHASE_MAIN_BACKUP_DIRECTORY);

		File soldMainDirectory = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_SOLD_MAIN_DIRECTORY);
		File soldUpdateDirectory = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_SOLD_UPDATE_DIRECTORY);
		File soldMainBackUpDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.BHA_EQ_SOLD_MAIN_BACKUP_DIRECTORY);

		// Delete update directory and main backup directory
		anukyaDataUtils.deleteDirectory(purcahseUpdateDirectory);
		anukyaDataUtils.deleteDirectory(purchaseMainBackUpDirectory);
		anukyaDataUtils.deleteDirectory(soldUpdateDirectory);
		anukyaDataUtils.deleteDirectory(soldMainBackUpDirectory);

		// Copy main directory to update directory and main back up directory
		anukyaDataUtils.copyDirectory(purchaseMainDirectory, purcahseUpdateDirectory);
		anukyaDataUtils.copyDirectory(purchaseMainDirectory, purchaseMainBackUpDirectory);
		anukyaDataUtils.copyDirectory(soldMainDirectory, soldUpdateDirectory);
		anukyaDataUtils.copyDirectory(soldMainDirectory, soldMainBackUpDirectory);

		StringBuilder successMessage = new StringBuilder();

		int rowCount = 2;
		for (BhaarathaShares bhaarathaShares : bhaarathaSharesList) {

			if (!bhaarathaShares.getPurchaseDate().isEmpty() && bhaarathaShares.getSellDate().isEmpty()) {

				AbstractBhaarathaShares abstractBhaarathaShares = bhaarathaSharesFactory
						.getBhaarathaShares(AnukyaConstants.BOUGHT_CONSTANT);
				abstractBhaarathaShares.userEmailId = userEmailId;

				CommonResponse addSharesCommonResponse = abstractBhaarathaShares.addSharesBulkUpload(bhaarathaShares);

				successMessage.append(AnukyaConstants.ROW + rowCount + AnukyaConstants.RATIO_CONSTANT
						+ AnukyaConstants.SPACE + addSharesCommonResponse.getMessage() + AnukyaConstants.BR_CONSTANT);

			} else if (bhaarathaShares.getPurchaseDate().isEmpty() && !bhaarathaShares.getSellDate().isEmpty()) {
				AbstractBhaarathaShares abstractBhaarathaShares = bhaarathaSharesFactory
						.getBhaarathaShares(AnukyaConstants.SOLD_CONSTANT);
				abstractBhaarathaShares.userEmailId = userEmailId;

				CommonResponse addSharesCommonResponse = abstractBhaarathaShares.addSharesBulkUpload(bhaarathaShares);
				successMessage.append(AnukyaConstants.ROW + rowCount + AnukyaConstants.RATIO_CONSTANT
						+ AnukyaConstants.SPACE + addSharesCommonResponse.getMessage() + AnukyaConstants.BR_CONSTANT);
			}

			rowCount++;
		}

		// Delete main directory
		anukyaDataUtils.deleteDirectory(purchaseMainDirectory);
		anukyaDataUtils.deleteDirectory(soldMainDirectory);

		// Copy from update directory to main directory
		anukyaDataUtils.copyDirectory(purcahseUpdateDirectory, purchaseMainDirectory);
		anukyaDataUtils.copyDirectory(soldUpdateDirectory, soldMainDirectory);

		// Finally delete the update directory
		anukyaDataUtils.deleteDirectory(purcahseUpdateDirectory);
		anukyaDataUtils.deleteDirectory(soldUpdateDirectory);

		commonResponse.setStatus(true);
		commonResponse.setMessage(successMessage.toString());

		return commonResponse;
	}

	@Override
	public BhaarathaSharesAnalysis getDividendYieldAnalysis(String userEmailId) throws AnukyaException, IOException {

		File consolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_PURCHASE_MAIN_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		List<BhaarathaShares> bhaarathaSharesList = bhaarathaSharesData.getBhaarathaShares(consolidatedFile);

		if (bhaarathaSharesList.isEmpty()) {
			throw new AnukyaException("No content for Bhaaratha Shares analysis", null, AnukyaErrorConstants.NO_CONTENT,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		List<BhaarathaShares> dividendYieldBhaarathaSharesList = new ArrayList<>();

		Set<String> bhaarathaSharesSet = new HashSet<>();
		Set<String> bhaarathaSharesDividendYieldSet = new HashSet<>();

		for (BhaarathaShares bhaarathaShares : bhaarathaSharesList) {
			if (!bhaarathaSharesSet.contains(bhaarathaShares.getBseScriptCode())) {

				bhaarathaSharesSet.add(bhaarathaShares.getBseScriptCode());

				BhaarathaSharesLastTradingPrice bhaarathaSharesLastTradingPrice = bhaarathaSharesUtils
						.getBhaarathaSharesLastTradingPrice(new BhaarathaSharesScriptCodeDetails(
								bhaarathaShares.getStockExchange(), bhaarathaShares.getBseScriptCode(),
								bhaarathaShares.getNseScriptCode(), bhaarathaShares.getMoneycontrolCode(),
								bhaarathaShares.getYahooBseScriptCode(), bhaarathaShares.getYahooNseScriptCode()));

				BigDecimal ttmDividendYield;
				if (bhaarathaShares.getBseScriptCode().matches("[0-9]+")) {
					ttmDividendYield = getYahooFinanceDividendYield(bhaarathaShares.getYahooBseScriptCode(),
							bhaarathaSharesLastTradingPrice.getLastTradingPrice());
				} else {
					ttmDividendYield = getYahooFinanceDividendYield(bhaarathaShares.getYahooNseScriptCode(),
							bhaarathaSharesLastTradingPrice.getLastTradingPrice());
				}

				BigDecimal dividendYieldAnalysisValueBigDecimal = new BigDecimal(
						bhaarathaSharesDividendYieldAnalysisValue);

				if (ttmDividendYield.compareTo(dividendYieldAnalysisValueBigDecimal) > 0) {
					bhaarathaSharesDividendYieldSet.add(bhaarathaShares.getBseScriptCode());
					dividendYieldBhaarathaSharesList.add(bhaarathaShares);
				}
			} else if (bhaarathaSharesDividendYieldSet.contains(bhaarathaShares.getBseScriptCode())) {
				dividendYieldBhaarathaSharesList.add(bhaarathaShares);
			}
		}

		return getAnalysis(dividendYieldBhaarathaSharesList);
	}

	@Override
	public BhaarathaSharesInvestmentResearchResponse getInvestmentResearch(String userEmailId) throws AnukyaException {

		BhaarathaSharesInvestmentResearchResponse bhaarathaSharesInvestmentResearchResponse = new BhaarathaSharesInvestmentResearchResponse();
		File consolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_PURCHASE_MAIN_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		List<BhaarathaShares> bhaarathaSharesList = bhaarathaSharesData.getBhaarathaShares(consolidatedFile);

		if (bhaarathaSharesList.isEmpty()) {
			throw new AnukyaException("No content for Bhaaratha Shares analysis", null, AnukyaErrorConstants.NO_CONTENT,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		Set<String> bhaarathaSharesSet = new HashSet<>();

		for (BhaarathaShares bhaarathaShares : bhaarathaSharesList) {
			if (!bhaarathaSharesSet.contains(bhaarathaShares.getMoneycontrolCode())) {

				BhaarathaSharesInvestmentResearch bhaarathaSharesInvestmentResearch = new BhaarathaSharesInvestmentResearch();
				bhaarathaSharesInvestmentResearch.setBseCode(bhaarathaShares.getBseScriptCode());
				bhaarathaSharesInvestmentResearch.setNseCode(bhaarathaShares.getNseScriptCode());
				bhaarathaSharesInvestmentResearch.setMoneycontrolCode(bhaarathaShares.getMoneycontrolCode());
				bhaarathaSharesInvestmentResearch.setScriptName(bhaarathaShares.getScriptName());

				// Price Range
				BhaarathaSharesOneShareDetails bhaarathaSharesOneShareDetails = bhaarathaSharesUtils
						.getBhaarathaSharesOneShareDetails(new BhaarathaSharesScriptCodeDetails(
								bhaarathaShares.getStockExchange().toUpperCase(), bhaarathaShares.getBseScriptCode(),
								bhaarathaShares.getNseScriptCode(), bhaarathaShares.getMoneycontrolCode(),
								bhaarathaShares.getYahooBseScriptCode(), bhaarathaShares.getYahooNseScriptCode()));

				BigDecimal dailyRangeBigDecimal = anukyaUtils.calculateHighLow(
						new BigDecimal(bhaarathaSharesOneShareDetails.getDailyLow()),
						new BigDecimal(bhaarathaSharesOneShareDetails.getDailyHigh()),
						new BigDecimal(bhaarathaSharesOneShareDetails.getCurrentMarketPrice()));
				BigDecimal fiftyTwoWeekRangeBigDecimal = anukyaUtils.calculateHighLow(
						new BigDecimal(bhaarathaSharesOneShareDetails.getFiftyTwoWeekLow()),
						new BigDecimal(bhaarathaSharesOneShareDetails.getFiftyTwoWeekHigh()),
						new BigDecimal(bhaarathaSharesOneShareDetails.getCurrentMarketPrice()));

				bhaarathaSharesInvestmentResearch
						.setDailyRange(String.format(AnukyaConstants.FLOAT_2_DECIMAL, dailyRangeBigDecimal));
				bhaarathaSharesInvestmentResearch.setFiftyTwoWeekRange(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, fiftyTwoWeekRangeBigDecimal));

				// TTM Dividend Yield
				BigDecimal ttmDividendYield;
				if (bhaarathaShares.getBseScriptCode().matches("[0-9]+")) {
					ttmDividendYield = getYahooFinanceDividendYield(bhaarathaShares.getYahooBseScriptCode(),
							bhaarathaSharesOneShareDetails.getCurrentMarketPrice());
				} else {
					ttmDividendYield = getYahooFinanceDividendYield(bhaarathaShares.getYahooNseScriptCode(),
							bhaarathaSharesOneShareDetails.getCurrentMarketPrice());
				}

				bhaarathaSharesInvestmentResearch
						.setTtmDividendYield(String.format(AnukyaConstants.FLOAT_2_DECIMAL, ttmDividendYield));

				bhaarathaSharesInvestmentResearchResponse.getBhaarathaSharesInvestmentResearchList()
						.add(bhaarathaSharesInvestmentResearch);

				bhaarathaSharesSet.add(bhaarathaShares.getMoneycontrolCode());
			}
		}

		return bhaarathaSharesInvestmentResearchResponse;
	}

	// Private methods
	private List<BhaarathaSharesShortTermInvestment> updateBhaarathaSharesShortTermInvestment(String userEmailId,
			String dateInddMmmYyyy, BhaarathaSharesShortTermInvestment bhaarathaSharesShortTermInvestment)
			throws AnukyaException {

		File shortTermInvestmentFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_BHA_EQ_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_JSON);

		List<BhaarathaSharesShortTermInvestment> bhaarathaSharesShortTermInvestmentList = bhaarathaSharesData
				.getBhaarathaSharesShortTermInvestmentList(shortTermInvestmentFile);

		for (BhaarathaSharesShortTermInvestment existingBhaarathaSharesShortTermInvestment : bhaarathaSharesShortTermInvestmentList) {
			if (existingBhaarathaSharesShortTermInvestment.getBseCode()
					.equals(bhaarathaSharesShortTermInvestment.getBseCode())
					&& existingBhaarathaSharesShortTermInvestment.getStockExchange()
							.equalsIgnoreCase(bhaarathaSharesShortTermInvestment.getStockExchange())) {

				BigDecimal existingPurchaseQuantity = new BigDecimal(
						existingBhaarathaSharesShortTermInvestment.getPurchaseQuantity());
				BigDecimal boughtPurchaseQuantity = new BigDecimal(
						bhaarathaSharesShortTermInvestment.getPurchaseQuantity());
				BigDecimal purchaseQuantity = existingPurchaseQuantity.add(boughtPurchaseQuantity);
				existingBhaarathaSharesShortTermInvestment.setPurchaseQuantity(String.valueOf(purchaseQuantity));

				BigDecimal existingPurchasePrice = new BigDecimal(
						existingBhaarathaSharesShortTermInvestment.getPurchasePrice());
				BigDecimal boughtPurchasePrice = new BigDecimal(bhaarathaSharesShortTermInvestment.getPurchasePrice());
				BigDecimal purchasePrice = existingPurchaseQuantity.multiply(existingPurchasePrice)
						.add(boughtPurchaseQuantity.multiply(boughtPurchasePrice))
						.divide(purchaseQuantity, MathContext.DECIMAL128);
				existingBhaarathaSharesShortTermInvestment.setPurchasePrice(String.valueOf(purchasePrice));

				BigDecimal existingPurchaseGrossTotal = new BigDecimal(
						existingBhaarathaSharesShortTermInvestment.getPurchaseGrossTotal());
				BigDecimal boughtPurchaseGrossTotal = boughtPurchaseQuantity.multiply(boughtPurchasePrice);
				BigDecimal purchaseGrossTotal = existingPurchaseGrossTotal.add(boughtPurchaseGrossTotal);
				existingBhaarathaSharesShortTermInvestment.setPurchaseGrossTotal(String.valueOf(purchaseGrossTotal));

				BigDecimal existingPurchaseBrokerage = new BigDecimal(
						existingBhaarathaSharesShortTermInvestment.getPurchaseBrokerage());
				BigDecimal boughtPurchaseBrokerage = new BigDecimal(
						bhaarathaSharesShortTermInvestment.getPurchaseBrokerage());
				BigDecimal purchaseBrokerage = existingPurchaseBrokerage.add(boughtPurchaseBrokerage);
				existingBhaarathaSharesShortTermInvestment.setPurchaseBrokerage(String.valueOf(purchaseBrokerage));

				BigDecimal existingPurchaseSTT = new BigDecimal(
						existingBhaarathaSharesShortTermInvestment.getPurchaseSTT());
				BigDecimal boughtPurchaseSTT = new BigDecimal(bhaarathaSharesShortTermInvestment.getPurchaseSTT());
				BigDecimal purchaseSTT = existingPurchaseSTT.add(boughtPurchaseSTT);
				existingBhaarathaSharesShortTermInvestment.setPurchaseSTT(String.valueOf(purchaseSTT));

				BigDecimal existingPurchaseExpenditure = new BigDecimal(
						existingBhaarathaSharesShortTermInvestment.getPurchaseExpenditure());
				BigDecimal boughtPurchaseExpenditure = new BigDecimal(
						bhaarathaSharesShortTermInvestment.getPurchaseExpenditure());
				BigDecimal purchaseExpenditure = existingPurchaseExpenditure.add(boughtPurchaseExpenditure);
				existingBhaarathaSharesShortTermInvestment.setPurchaseExpenditure(String.valueOf(purchaseExpenditure));

				BigDecimal existingPurchaseNonExpenditure = new BigDecimal(
						existingBhaarathaSharesShortTermInvestment.getPurchaseNonExpenditure());
				BigDecimal boughtPurchaseNonExpenditure = new BigDecimal(
						bhaarathaSharesShortTermInvestment.getPurchaseNonExpenditure());
				BigDecimal purchaseNonExpenditure = existingPurchaseNonExpenditure.add(boughtPurchaseNonExpenditure);
				existingBhaarathaSharesShortTermInvestment
						.setPurchaseNonExpenditure(String.valueOf(purchaseNonExpenditure));

				BigDecimal existingPurchaseTotal = new BigDecimal(
						existingBhaarathaSharesShortTermInvestment.getPurchaseTotal());
				BigDecimal boughtPurchaseTotal = boughtPurchaseGrossTotal.add(boughtPurchaseBrokerage)
						.add(boughtPurchaseSTT).add(boughtPurchaseExpenditure).add(boughtPurchaseNonExpenditure);
				BigDecimal purchaseTotal = existingPurchaseTotal.add(boughtPurchaseTotal);
				existingBhaarathaSharesShortTermInvestment.setPurchaseTotal(String.valueOf(purchaseTotal));

				BigDecimal existingPurchaseActualPricePerUnit = new BigDecimal(
						existingBhaarathaSharesShortTermInvestment.getPurchaseNetPricePerUnit());
				BigDecimal boughtPurchaseActualPricePerUnit = boughtPurchaseTotal.divide(boughtPurchaseQuantity,
						MathContext.DECIMAL128);
				BigDecimal purchaseActualPricePerUnit = existingPurchaseQuantity
						.multiply(existingPurchaseActualPricePerUnit)
						.add(boughtPurchaseQuantity.multiply(boughtPurchaseActualPricePerUnit))
						.divide(purchaseQuantity, MathContext.DECIMAL128);
				existingBhaarathaSharesShortTermInvestment
						.setPurchaseNetPricePerUnit(String.valueOf(purchaseActualPricePerUnit));

				return bhaarathaSharesShortTermInvestmentList;
			}
		}

		BhaarathaSharesShortTermInvestment shortTermInvestment = addShortTermInvestment(
				bhaarathaSharesShortTermInvestment);

		shortTermInvestment.setCreatedBy(userEmailId);
		shortTermInvestment.setCreatedDate(dateInddMmmYyyy);
		shortTermInvestment.setLastUpdatedBy(userEmailId);
		shortTermInvestment.setLastUpdatedDate(dateInddMmmYyyy);

		bhaarathaSharesShortTermInvestmentList.add(shortTermInvestment);

		return bhaarathaSharesShortTermInvestmentList;
	}

	private List<BhaarathaSharesShortTermInvestment> updateIndividualBhaarathaSharesShortTermInvestment(
			String userEmailId, String dateInddMmmYyyy,
			BhaarathaSharesShortTermInvestment bhaarathaSharesShortTermInvestment) throws AnukyaException {

		File individualBhaarathaShortTermInvestmentFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.SHORT_TERM_INVESTMENT_BHA_EQ_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ bhaarathaSharesShortTermInvestment.getBseCode() + AnukyaConstants.JSON_EXTENSION);

		List<BhaarathaSharesShortTermInvestment> individualBhaarathaSharesShortTermInvestmentList = bhaarathaSharesData
				.getBhaarathaSharesShortTermInvestmentList(individualBhaarathaShortTermInvestmentFile);

		BhaarathaSharesShortTermInvestment shortTermInestment = addShortTermInvestment(
				bhaarathaSharesShortTermInvestment);

		shortTermInestment.setCreatedBy(userEmailId);
		shortTermInestment.setCreatedDate(dateInddMmmYyyy);
		shortTermInestment.setLastUpdatedBy(userEmailId);
		shortTermInestment.setLastUpdatedDate(dateInddMmmYyyy);

		individualBhaarathaSharesShortTermInvestmentList.add(shortTermInestment);

		return individualBhaarathaSharesShortTermInvestmentList;
	}

	private BhaarathaSharesShortTermInvestment addShortTermInvestment(
			BhaarathaSharesShortTermInvestment bhaarathaSharesShortTermInvestment) {

		BhaarathaSharesShortTermInvestment shortTermInvestment = new BhaarathaSharesShortTermInvestment();

		shortTermInvestment.setId(anukyaUtils.generateId());

		shortTermInvestment.setShareName(bhaarathaSharesShortTermInvestment.getShareName());
		shortTermInvestment.setStockExchange(bhaarathaSharesShortTermInvestment.getStockExchange());
		shortTermInvestment.setBseCode(bhaarathaSharesShortTermInvestment.getBseCode());
		shortTermInvestment.setYahooBseCode(bhaarathaSharesShortTermInvestment.getYahooBseCode());
		shortTermInvestment.setNseCode(bhaarathaSharesShortTermInvestment.getNseCode());
		shortTermInvestment.setYahooNseCode(bhaarathaSharesShortTermInvestment.getYahooNseCode());
		shortTermInvestment.setMoneycontrolCode(bhaarathaSharesShortTermInvestment.getMoneycontrolCode());
		shortTermInvestment.setPurchaseDate(bhaarathaSharesShortTermInvestment.getPurchaseDate());
		shortTermInvestment.setPurchaseQuantity(bhaarathaSharesShortTermInvestment.getPurchaseQuantity());
		shortTermInvestment.setPurchasePrice(
				String.valueOf(new BigDecimal(bhaarathaSharesShortTermInvestment.getPurchasePrice())));

		BigDecimal purchaseGrossTotal = new BigDecimal(shortTermInvestment.getPurchaseQuantity())
				.multiply(new BigDecimal(shortTermInvestment.getPurchasePrice()));
		shortTermInvestment.setPurchaseGrossTotal(String.valueOf(purchaseGrossTotal));

		shortTermInvestment.setPurchaseBrokerage(
				String.valueOf(new BigDecimal(bhaarathaSharesShortTermInvestment.getPurchaseBrokerage())));

		shortTermInvestment
				.setPurchaseSTT(String.valueOf(new BigDecimal(bhaarathaSharesShortTermInvestment.getPurchaseSTT())));

		shortTermInvestment.setPurchaseExpenditure(
				String.valueOf(new BigDecimal(bhaarathaSharesShortTermInvestment.getPurchaseExpenditure())));

		shortTermInvestment.setPurchaseNonExpenditure(
				String.valueOf(new BigDecimal(bhaarathaSharesShortTermInvestment.getPurchaseNonExpenditure())));

		BigDecimal purchaseTotal = purchaseGrossTotal.add(new BigDecimal(shortTermInvestment.getPurchaseBrokerage()))
				.add(new BigDecimal(shortTermInvestment.getPurchaseSTT()))
				.add(new BigDecimal(shortTermInvestment.getPurchaseExpenditure()))
				.add(new BigDecimal(shortTermInvestment.getPurchaseNonExpenditure()));
		shortTermInvestment.setPurchaseTotal(String.valueOf(purchaseTotal));

		BigDecimal purchaseActualPricePerUnit = purchaseTotal
				.divide(new BigDecimal(shortTermInvestment.getPurchaseQuantity()), MathContext.DECIMAL128);
		shortTermInvestment.setPurchaseNetPricePerUnit(String.valueOf(purchaseActualPricePerUnit));

		return shortTermInvestment;
	}

	private BhaarathaSharesShortTermInvestmentResponse calculateShortTermInvestment(
			List<BhaarathaSharesShortTermInvestment> bhaarathaSharesShortTermInvestmentList) throws AnukyaException {

		BhaarathaSharesShortTermInvestmentResponse bhaarathaSharesShortTermInvestmentResponse = new BhaarathaSharesShortTermInvestmentResponse();
		bhaarathaSharesShortTermInvestmentResponse
				.setShortTermInvestmentTotal(new BhaarathaSharesShortTermInvestment());

		Map<String, Long> sellDepositoryChargesScriptCountMap = bhaarathaSharesShortTermInvestmentList.parallelStream()
				.collect(Collectors.groupingBy(BhaarathaSharesShortTermInvestment::getBseCode, Collectors.counting()));

		BhaarathaSharesCalculationDetails bhaarathaSharesCalculationDetails = getBhaarathaSharesCalculationDetailsShortTermInvestment(
				bhaarathaSharesShortTermInvestmentList);

		for (BhaarathaSharesShortTermInvestment bhaarathaSharesShortTermInvestment : bhaarathaSharesShortTermInvestmentList) {

			BigDecimal purchaseQuantity = new BigDecimal(bhaarathaSharesShortTermInvestment.getPurchaseQuantity());
			BigDecimal existingTotalPurchaseQuantity = new BigDecimal(
					bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal().getPurchaseQuantity());
			BigDecimal totalPurchaseQuantity = purchaseQuantity.add(existingTotalPurchaseQuantity);
			bhaarathaSharesShortTermInvestment.setPurchaseQuantity(String.valueOf(purchaseQuantity));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setPurchaseQuantity(String.valueOf(totalPurchaseQuantity));

			BigDecimal purchasePrice = new BigDecimal(bhaarathaSharesShortTermInvestment.getPurchasePrice());
			BigDecimal existingTotalPurchasePrice = new BigDecimal(
					bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal().getPurchasePrice());
			BigDecimal totalPurchasePrice = purchaseQuantity.multiply(purchasePrice)
					.add(existingTotalPurchaseQuantity.multiply(existingTotalPurchasePrice))
					.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
			bhaarathaSharesShortTermInvestment
					.setPurchasePrice(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchasePrice));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setPurchasePrice(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchasePrice));

			BigDecimal purchaseGrossTotal = purchaseQuantity.multiply(purchasePrice);
			BigDecimal existingPurchaseGrossTotal = new BigDecimal(
					bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal().getPurchaseGrossTotal());
			BigDecimal totalPurchaseGrossTotal = purchaseGrossTotal.add(existingPurchaseGrossTotal);
			bhaarathaSharesShortTermInvestment
					.setPurchaseGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseGrossTotal));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setPurchaseGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseGrossTotal));

			BigDecimal purchaseBrokerage = new BigDecimal(bhaarathaSharesShortTermInvestment.getPurchaseBrokerage());
			BigDecimal existingTotalPurchaseBrokerage = new BigDecimal(
					bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal().getPurchaseBrokerage());
			BigDecimal totalPurchaseBrokerage = purchaseBrokerage.add(existingTotalPurchaseBrokerage);
			bhaarathaSharesShortTermInvestment
					.setPurchaseBrokerage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseBrokerage));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setPurchaseBrokerage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseBrokerage));

			BigDecimal purchaseSTT = new BigDecimal(bhaarathaSharesShortTermInvestment.getPurchaseSTT());
			BigDecimal existingTotalPurchaseSTT = new BigDecimal(
					bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal().getPurchaseSTT());
			BigDecimal totalPurchaseSTT = purchaseSTT.add(existingTotalPurchaseSTT);
			bhaarathaSharesShortTermInvestment
					.setPurchaseSTT(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseSTT));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setPurchaseSTT(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseSTT));

			BigDecimal purchaseExpenditure = new BigDecimal(
					bhaarathaSharesShortTermInvestment.getPurchaseExpenditure());
			BigDecimal existingTotalPurchaseExpenditure = new BigDecimal(
					bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal().getPurchaseExpenditure());
			BigDecimal totalPurchaseExpenditure = purchaseExpenditure.add(existingTotalPurchaseExpenditure);
			bhaarathaSharesShortTermInvestment
					.setPurchaseExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseExpenditure));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setPurchaseExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseExpenditure));

			BigDecimal purchaseNonExpenditure = new BigDecimal(
					bhaarathaSharesShortTermInvestment.getPurchaseNonExpenditure());
			BigDecimal existingTotalPurchaseNonExpenditure = new BigDecimal(bhaarathaSharesShortTermInvestmentResponse
					.getShortTermInvestmentTotal().getPurchaseNonExpenditure());
			BigDecimal totalPurchaseNonExpenditure = purchaseNonExpenditure.add(existingTotalPurchaseNonExpenditure);
			bhaarathaSharesShortTermInvestment
					.setPurchaseNonExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseNonExpenditure));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal().setPurchaseNonExpenditure(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseNonExpenditure));

			BigDecimal purchaseTotal = new BigDecimal(bhaarathaSharesShortTermInvestment.getPurchaseTotal());
			BigDecimal existingTotalPurchaseTotal = new BigDecimal(
					bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal().getPurchaseTotal());
			BigDecimal totalPurchaseTotal = purchaseTotal.add(existingTotalPurchaseTotal);
			bhaarathaSharesShortTermInvestment
					.setPurchaseTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseTotal));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setPurchaseTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseTotal));

			BigDecimal purchaseActualPricePerUnit = new BigDecimal(
					bhaarathaSharesShortTermInvestment.getPurchaseNetPricePerUnit());
			BigDecimal existingPurchaseActualPricePerUnit = new BigDecimal(bhaarathaSharesShortTermInvestmentResponse
					.getShortTermInvestmentTotal().getPurchaseNetPricePerUnit());
			BigDecimal totalPurchaseActualCost = purchaseQuantity.multiply(purchaseActualPricePerUnit)
					.add(existingTotalPurchaseQuantity.multiply(existingPurchaseActualPricePerUnit))
					.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
			bhaarathaSharesShortTermInvestment.setPurchaseNetPricePerUnit(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseActualPricePerUnit));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal().setPurchaseNetPricePerUnit(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseActualCost));

			BigDecimal sellQuantity = purchaseQuantity;
			BigDecimal existingSellQuantity = new BigDecimal(
					bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal().getSellQuantity());
			BigDecimal totalSellQuantity = sellQuantity.add(existingSellQuantity);
			bhaarathaSharesShortTermInvestment.setSellQuantity(String.valueOf(sellQuantity));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setSellQuantity(String.valueOf(totalSellQuantity));

			BigDecimal sellPrice = new BigDecimal(AnukyaConstants.NUMBER_0);
			if (bhaarathaSharesShortTermInvestment.getStockExchange().equalsIgnoreCase(AnukyaConstants.BSE)) {
				sellPrice = new BigDecimal(bhaarathaSharesCalculationDetails.getLatestTradingDetailsMap()
						.get(bhaarathaSharesShortTermInvestment.getBseCode()).getLastTradingPrice());
			} else if (bhaarathaSharesShortTermInvestment.getStockExchange().equalsIgnoreCase(AnukyaConstants.NSE)) {
				sellPrice = new BigDecimal(bhaarathaSharesCalculationDetails.getLatestTradingDetailsMap()
						.get(bhaarathaSharesShortTermInvestment.getNseCode()).getLastTradingPrice());
			}
			BigDecimal existingTotalSellPrice = new BigDecimal(
					bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal().getSellPrice());
			BigDecimal totalSellPrice = sellQuantity.multiply(sellPrice)
					.add(existingSellQuantity.multiply(existingTotalSellPrice))
					.divide(totalSellQuantity, MathContext.DECIMAL128);
			bhaarathaSharesShortTermInvestment.setSellPrice(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellPrice));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setSellPrice(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellPrice));

			BigDecimal sellGrossTotal = sellQuantity.multiply(sellPrice);
			BigDecimal existingSellGrossTotal = new BigDecimal(
					bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal().getSellGrossTotal());
			BigDecimal totalSellGrossTotal = sellGrossTotal.add(existingSellGrossTotal);
			bhaarathaSharesShortTermInvestment
					.setSellGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellGrossTotal));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setSellGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellGrossTotal));

			BigDecimal sellBrokerage = bhaarathaExpenditureUtils.calculateBrokerage(sellGrossTotal, sellQuantity,
					bhaarathaSharesCalculationDetails.getTotalSoldQuantityMap()
							.get(bhaarathaSharesShortTermInvestment.getBseCode()),
					bhaarathaSharesCalculationDetails.getTotalSoldGrossTotalMap()
							.get(bhaarathaSharesShortTermInvestment.getBseCode()));
			BigDecimal existingSellBrokerage = new BigDecimal(
					bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal().getSellBrokerage());
			BigDecimal totalSellBrokerage = sellBrokerage.add(existingSellBrokerage);
			bhaarathaSharesShortTermInvestment
					.setSellBrokerage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellBrokerage));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setSellBrokerage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellBrokerage));

			BigDecimal sellSTT = bhaarathaExpenditureUtils.calculateSTT(sellGrossTotal);
			BigDecimal existingSellSTT = new BigDecimal(
					bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal().getSellSTT());
			BigDecimal totalSellSTT = sellSTT.add(existingSellSTT);
			bhaarathaSharesShortTermInvestment.setSellSTT(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellSTT));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setSellSTT(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellSTT));

			BigDecimal sellExpenditure = bhaarathaExpenditureUtils
					.getBhaarathaSharesExpenditure(sellGrossTotal, sellBrokerage)
					.add(bhaarathaExpenditureUtils.calculateDepositoryCharges(sellDepositoryChargesScriptCountMap,
							bhaarathaSharesShortTermInvestment.getBseCode()));
			BigDecimal existingSellExpenditure = new BigDecimal(
					bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal().getSellExpenditure());
			BigDecimal totalSellExpenditure = sellExpenditure.add(existingSellExpenditure);
			bhaarathaSharesShortTermInvestment
					.setSellExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellExpenditure));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setSellExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellExpenditure));

			BigDecimal sellNonExpenditure = new BigDecimal(bhaarathaSharesShortTermInvestment.getSellNonExpenditure());
			BigDecimal existingSellNonExpenditure = new BigDecimal(
					bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal().getSellNonExpenditure());
			BigDecimal totalSellNonExpenditure = sellNonExpenditure.add(existingSellNonExpenditure);
			bhaarathaSharesShortTermInvestment
					.setSellNonExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellNonExpenditure));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setSellNonExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellNonExpenditure));

			BigDecimal sellTotal = sellGrossTotal.subtract(sellBrokerage).subtract(sellSTT).subtract(sellExpenditure)
					.subtract(sellNonExpenditure);
			BigDecimal existingSellTotal = new BigDecimal(
					bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal().getSellTotal());
			BigDecimal totalSellTotal = sellTotal.add(existingSellTotal);
			bhaarathaSharesShortTermInvestment.setSellTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellTotal));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setSellTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellTotal));

			BigDecimal sellActualPricePerUnit = sellTotal.divide(sellQuantity, MathContext.DECIMAL128);
			BigDecimal totalSellActualPricePerUnit = totalSellTotal.divide(totalSellQuantity, MathContext.DECIMAL128);
			bhaarathaSharesShortTermInvestment
					.setSellNetPricePerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellActualPricePerUnit));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal().setSellNetPricePerUnit(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellActualPricePerUnit));

			BigDecimal profitLoss = sellTotal.subtract(purchaseTotal);
			BigDecimal totalProfitLoss = totalSellTotal.subtract(totalPurchaseTotal);
			bhaarathaSharesShortTermInvestment
					.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLoss));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLoss));

			BigDecimal profitLossPercentage = sellTotal.divide(purchaseTotal, MathContext.DECIMAL128)
					.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
					.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
			BigDecimal totalProfitLossPercentage = totalSellTotal.divide(totalPurchaseTotal, MathContext.DECIMAL128)
					.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
					.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
			bhaarathaSharesShortTermInvestment
					.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLossPercentage));
			bhaarathaSharesShortTermInvestmentResponse.getShortTermInvestmentTotal()
					.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLossPercentage));
		}

		bhaarathaSharesShortTermInvestmentResponse
				.setShortTermInvestmentScriptsList(bhaarathaSharesShortTermInvestmentList);

		return bhaarathaSharesShortTermInvestmentResponse;
	}

	private BhaarathaSharesCalculationDetails getBhaarathaSharesCalculationDetailsShortTermInvestment(
			List<BhaarathaSharesShortTermInvestment> bhaarathaSharesShortTermInvestmentList) throws AnukyaException {

		Map<String, BhaarathaSharesLastTradingPrice> latestTradingDetailsMap = new HashMap<>();
		Map<String, BigDecimal> totalSoldQuantityMap = new HashMap<>();
		Map<String, BigDecimal> totalSoldGrossTotalMap = new HashMap<>();

		for (BhaarathaSharesShortTermInvestment bhaarathaSharesShortTermInvestment : bhaarathaSharesShortTermInvestmentList) {

			BigDecimal totalQuantity;
			if (totalSoldQuantityMap.get(bhaarathaSharesShortTermInvestment.getBseCode()) != null) {
				totalQuantity = totalSoldQuantityMap.get(bhaarathaSharesShortTermInvestment.getBseCode())
						.add(new BigDecimal(bhaarathaSharesShortTermInvestment.getPurchaseQuantity()));
			} else {
				totalQuantity = new BigDecimal(bhaarathaSharesShortTermInvestment.getPurchaseQuantity());
			}

			totalSoldQuantityMap.put(bhaarathaSharesShortTermInvestment.getBseCode(), totalQuantity);

			BigDecimal scriptSellPrice = new BigDecimal(AnukyaConstants.NUMBER_0);

			if (AnukyaConstants.BSE.equalsIgnoreCase(bhaarathaSharesShortTermInvestment.getStockExchange())) {
				if (latestTradingDetailsMap.get(bhaarathaSharesShortTermInvestment.getBseCode()) == null) {
					latestTradingDetailsMap.put(bhaarathaSharesShortTermInvestment.getBseCode(),
							bhaarathaSharesUtils.getBhaarathaSharesLastTradingPrice(
									new BhaarathaSharesScriptCodeDetails(AnukyaConstants.BSE,
											bhaarathaSharesShortTermInvestment.getBseCode(),
											bhaarathaSharesShortTermInvestment.getNseCode(),
											bhaarathaSharesShortTermInvestment.getMoneycontrolCode(),
											bhaarathaSharesShortTermInvestment.getYahooBseCode(),
											bhaarathaSharesShortTermInvestment.getYahooNseCode())));
				}
				scriptSellPrice = new BigDecimal(latestTradingDetailsMap
						.get(bhaarathaSharesShortTermInvestment.getBseCode()).getLastTradingPrice());
			} else if (AnukyaConstants.NSE.equalsIgnoreCase(bhaarathaSharesShortTermInvestment.getStockExchange())) {
				if (latestTradingDetailsMap.get(bhaarathaSharesShortTermInvestment.getNseCode()) == null) {
					latestTradingDetailsMap.put(bhaarathaSharesShortTermInvestment.getNseCode(),
							bhaarathaSharesUtils.getBhaarathaSharesLastTradingPrice(
									new BhaarathaSharesScriptCodeDetails(AnukyaConstants.NSE,
											bhaarathaSharesShortTermInvestment.getBseCode(),
											bhaarathaSharesShortTermInvestment.getNseCode(),
											bhaarathaSharesShortTermInvestment.getMoneycontrolCode(),
											bhaarathaSharesShortTermInvestment.getYahooBseCode(),
											bhaarathaSharesShortTermInvestment.getYahooNseCode())));
				}
				scriptSellPrice = new BigDecimal(latestTradingDetailsMap
						.get(bhaarathaSharesShortTermInvestment.getNseCode()).getLastTradingPrice());
			}

			BigDecimal totalGrossTotal = totalQuantity.multiply(scriptSellPrice);
			totalSoldGrossTotalMap.put(bhaarathaSharesShortTermInvestment.getBseCode(), totalGrossTotal);
		}

		BhaarathaSharesCalculationDetails bhaarathaSharesCalculationDetails = new BhaarathaSharesCalculationDetails();

		bhaarathaSharesCalculationDetails.setLatestTradingDetailsMap(latestTradingDetailsMap);
		bhaarathaSharesCalculationDetails.setTotalSoldQuantityMap(totalSoldQuantityMap);
		bhaarathaSharesCalculationDetails.setTotalSoldGrossTotalMap(totalSoldGrossTotalMap);

		return bhaarathaSharesCalculationDetails;
	}

	private BhaarathaSharesAnalysis getAnalysis(List<BhaarathaShares> bhaarathaSharesList) throws AnukyaException {
		Map<String, BhaarathaSharesLastTradingPrice> latestTradingDetailsMap = new HashMap<>();
		Map<String, BigDecimal> totalSoldQuantityMap = new HashMap<>();
		Map<String, BigDecimal> totalSoldGrossTotalMap = new HashMap<>();

		// To avoid duplicate scripts when invested in both BSE and NSE
		Set<String> sectorNumberOfScriptsSet = new HashSet<>();
		Set<String> sectorNumberOfScriptsLongTermOnlySet = new HashSet<>();
		Map<String, BigDecimal> sectorNumberOfScriptsMap = new HashMap<>();
		Map<String, BigDecimal> sectorNumberOfScriptsLongTermOnlyMap = new HashMap<>();

		Set<String> categoryNumberOfScriptsSet = new HashSet<>();
		Set<String> categoryNumberOfScriptsLongTermOnlySet = new HashSet<>();
		Map<String, BigDecimal> categoryNumberOfScriptsMap = new HashMap<>();
		Map<String, BigDecimal> categoryNumberOfScriptsLongTermOnlyMap = new HashMap<>();

		Map<String, Long> sellDepositoryChargesScriptCountMap = new HashMap<>();
		Map<String, Long> sellDepositoryChargesSectorCountLongTermOnlyMap = new HashMap<>();

		for (BhaarathaShares bhaarathaShare : bhaarathaSharesList) {

			updateNumberOfScripts(sectorNumberOfScriptsSet, sectorNumberOfScriptsLongTermOnlySet,
					sectorNumberOfScriptsMap, sectorNumberOfScriptsLongTermOnlyMap, bhaarathaShare.getBseScriptCode(),
					bhaarathaShare.getSector(), bhaarathaShare.isShortTermInvestment());

			updateNumberOfScripts(categoryNumberOfScriptsSet, categoryNumberOfScriptsLongTermOnlySet,
					categoryNumberOfScriptsMap, categoryNumberOfScriptsLongTermOnlyMap,
					bhaarathaShare.getBseScriptCode(), bhaarathaShare.getCategory(),
					bhaarathaShare.isShortTermInvestment());

			BigDecimal totalQuantity;
			if (totalSoldQuantityMap.get(bhaarathaShare.getBseScriptCode()) != null) {
				totalQuantity = totalSoldQuantityMap.get(bhaarathaShare.getBseScriptCode())
						.add(new BigDecimal(bhaarathaShare.getPurchaseQuantity()));
			} else {
				totalQuantity = new BigDecimal(bhaarathaShare.getPurchaseQuantity());
			}

			totalSoldQuantityMap.put(bhaarathaShare.getBseScriptCode(), totalQuantity);

			BigDecimal scriptSellPrice = new BigDecimal(AnukyaConstants.NUMBER_0);

			if (AnukyaConstants.BSE.equalsIgnoreCase(bhaarathaShare.getStockExchange())) {
				if (latestTradingDetailsMap.get(bhaarathaShare.getBseScriptCode()) == null) {
					latestTradingDetailsMap.put(bhaarathaShare.getBseScriptCode(), bhaarathaSharesUtils
							.getBhaarathaSharesLastTradingPrice(new BhaarathaSharesScriptCodeDetails(
									AnukyaConstants.BSE, bhaarathaShare.getBseScriptCode(),
									bhaarathaShare.getNseScriptCode(), bhaarathaShare.getMoneycontrolCode(),
									bhaarathaShare.getYahooBseScriptCode(), bhaarathaShare.getYahooNseScriptCode())));
				}
				scriptSellPrice = new BigDecimal(
						latestTradingDetailsMap.get(bhaarathaShare.getBseScriptCode()).getLastTradingPrice());
			} else if (AnukyaConstants.NSE.equalsIgnoreCase(bhaarathaShare.getStockExchange())) {
				if (latestTradingDetailsMap.get(bhaarathaShare.getNseScriptCode()) == null) {
					latestTradingDetailsMap.put(bhaarathaShare.getNseScriptCode(), bhaarathaSharesUtils
							.getBhaarathaSharesLastTradingPrice(new BhaarathaSharesScriptCodeDetails(
									AnukyaConstants.NSE, bhaarathaShare.getBseScriptCode(),
									bhaarathaShare.getNseScriptCode(), bhaarathaShare.getMoneycontrolCode(),
									bhaarathaShare.getYahooBseScriptCode(), bhaarathaShare.getYahooNseScriptCode())));
				}
				scriptSellPrice = new BigDecimal(
						latestTradingDetailsMap.get(bhaarathaShare.getNseScriptCode()).getLastTradingPrice());
			}

			BigDecimal totalGrossTotal = totalQuantity.multiply(scriptSellPrice);
			totalSoldGrossTotalMap.put(bhaarathaShare.getBseScriptCode(), totalGrossTotal);

			if (sellDepositoryChargesScriptCountMap.get(bhaarathaShare.getBseScriptCode()) != null) {
				sellDepositoryChargesScriptCountMap.put(bhaarathaShare.getBseScriptCode(),
						sellDepositoryChargesScriptCountMap.get(bhaarathaShare.getBseScriptCode()) + 1);
			} else {
				sellDepositoryChargesScriptCountMap.put(bhaarathaShare.getBseScriptCode(), 1L);
			}

			if (!bhaarathaShare.isShortTermInvestment()) {
				if (sellDepositoryChargesSectorCountLongTermOnlyMap.get(bhaarathaShare.getBseScriptCode()) != null) {
					sellDepositoryChargesSectorCountLongTermOnlyMap.put(bhaarathaShare.getBseScriptCode(),
							sellDepositoryChargesSectorCountLongTermOnlyMap.get(bhaarathaShare.getBseScriptCode()) + 1);
				} else {
					sellDepositoryChargesSectorCountLongTermOnlyMap.put(bhaarathaShare.getBseScriptCode(), 1L);
				}
			}
		}

		BhaarathaSharesCalculationDetails bhaarathaSharesCalculationDetails = new BhaarathaSharesCalculationDetails();

		bhaarathaSharesCalculationDetails.setLatestTradingDetailsMap(latestTradingDetailsMap);
		bhaarathaSharesCalculationDetails.setTotalSoldQuantityMap(totalSoldQuantityMap);
		bhaarathaSharesCalculationDetails.setTotalSoldGrossTotalMap(totalSoldGrossTotalMap);

		Map<String, BhaarathaSharesSector> bhaarathaSharesSectorMap = new LinkedHashMap<>();
		bhaarathaSharesSectorMap.put(AnukyaConstants.TOTAL_CONSTANT, new BhaarathaSharesSector());
		Map<String, BhaarathaSharesSector> bhaarathaSharesSectorLongTermOnlyMap = new LinkedHashMap<>();
		bhaarathaSharesSectorLongTermOnlyMap.put(AnukyaConstants.TOTAL_CONSTANT, new BhaarathaSharesSector());

		Map<String, BhaarathaSharesCategory> bhaarathaSharesCategoryMap = new LinkedHashMap<>();
		bhaarathaSharesCategoryMap.put(AnukyaConstants.TOTAL_CONSTANT, new BhaarathaSharesCategory());
		Map<String, BhaarathaSharesCategory> bhaarathaSharesCategoryLongTermOnlyMap = new LinkedHashMap<>();
		bhaarathaSharesCategoryLongTermOnlyMap.put(AnukyaConstants.TOTAL_CONSTANT, new BhaarathaSharesCategory());

		Map<String, Map<String, BhaarathaSharesOneSectorDetails>> bhaarathaSharesOneSectorDetailsMap = new LinkedHashMap<>();
		Map<String, Map<String, BhaarathaSharesOneSectorDetails>> bhaarathaSharesOneSectorDetailsLongTermOnlyMap = new LinkedHashMap<>();

		Map<String, Map<String, BhaarathaSharesOneCategoryDetails>> bhaarathaSharesOneCategoryDetailsMap = new LinkedHashMap<>();
		Map<String, Map<String, BhaarathaSharesOneCategoryDetails>> bhaarathaSharesOneCategoryDetailsLongTermOnlyMap = new LinkedHashMap<>();

		for (BhaarathaShares bhaarathaShare : bhaarathaSharesList) {

			buildBhaarathaSharesSector(bhaarathaShare, bhaarathaSharesCalculationDetails, bhaarathaSharesSectorMap,
					sellDepositoryChargesScriptCountMap);
			buildBhaarathaSharesCategory(bhaarathaShare, bhaarathaSharesCalculationDetails, bhaarathaSharesCategoryMap,
					sellDepositoryChargesScriptCountMap);

			buildBhaarathaSharesOneSectorDetails(bhaarathaShare, bhaarathaSharesCalculationDetails,
					bhaarathaSharesOneSectorDetailsMap, sellDepositoryChargesScriptCountMap);
			buildBhaarathaSharesOneCategoryDetails(bhaarathaShare, bhaarathaSharesCalculationDetails,
					bhaarathaSharesOneCategoryDetailsMap, sellDepositoryChargesScriptCountMap);

			if (!bhaarathaShare.isShortTermInvestment()) {
				buildBhaarathaSharesSector(bhaarathaShare, bhaarathaSharesCalculationDetails,
						bhaarathaSharesSectorLongTermOnlyMap, sellDepositoryChargesSectorCountLongTermOnlyMap);
				buildBhaarathaSharesCategory(bhaarathaShare, bhaarathaSharesCalculationDetails,
						bhaarathaSharesCategoryLongTermOnlyMap, sellDepositoryChargesSectorCountLongTermOnlyMap);

				buildBhaarathaSharesOneSectorDetails(bhaarathaShare, bhaarathaSharesCalculationDetails,
						bhaarathaSharesOneSectorDetailsLongTermOnlyMap,
						sellDepositoryChargesSectorCountLongTermOnlyMap);
				buildBhaarathaSharesOneCategoryDetails(bhaarathaShare, bhaarathaSharesCalculationDetails,
						bhaarathaSharesOneCategoryDetailsLongTermOnlyMap,
						sellDepositoryChargesSectorCountLongTermOnlyMap);
			}
		}

		BigDecimal totalSectorInvestedValue = new BigDecimal(
				bhaarathaSharesSectorMap.get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue());
		BigDecimal totalSectorCurrentValue = new BigDecimal(
				bhaarathaSharesSectorMap.get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue());

		BigDecimal totalSectorInvestedValueLongTermOnly = new BigDecimal(
				bhaarathaSharesSectorLongTermOnlyMap.get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue());
		BigDecimal totalSectorCurrentValueLongTermOnly = new BigDecimal(
				bhaarathaSharesSectorLongTermOnlyMap.get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue());

		for (Map.Entry<String, BhaarathaSharesSector> entry : bhaarathaSharesSectorMap.entrySet()) {
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

		for (Map.Entry<String, BhaarathaSharesSector> entry : bhaarathaSharesSectorLongTermOnlyMap.entrySet()) {
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

		bhaarathaSharesSectorMap.get(AnukyaConstants.TOTAL_CONSTANT)
				.setNumberOfScripts(String.valueOf(sectorNumberOfScriptsMap.get(AnukyaConstants.TOTAL_CONSTANT)));
		bhaarathaSharesSectorLongTermOnlyMap.get(AnukyaConstants.TOTAL_CONSTANT).setNumberOfScripts(
				String.valueOf(sectorNumberOfScriptsLongTermOnlyMap.get(AnukyaConstants.TOTAL_CONSTANT)));

		BigDecimal totalCategoryInvestedValue = new BigDecimal(
				bhaarathaSharesCategoryMap.get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue());
		BigDecimal totalCategoryCurrentValue = new BigDecimal(
				bhaarathaSharesCategoryMap.get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue());

		BigDecimal totalCategoryInvestedValueLongTermOnly = new BigDecimal(
				bhaarathaSharesCategoryLongTermOnlyMap.get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue());
		BigDecimal totalCategoryCurrentValueLongTermOnly = new BigDecimal(
				bhaarathaSharesCategoryLongTermOnlyMap.get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue());

		for (Map.Entry<String, BhaarathaSharesCategory> entry : bhaarathaSharesCategoryMap.entrySet()) {
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

		for (Map.Entry<String, BhaarathaSharesCategory> entry : bhaarathaSharesCategoryLongTermOnlyMap.entrySet()) {
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

		bhaarathaSharesCategoryMap.get(AnukyaConstants.TOTAL_CONSTANT)
				.setNumberOfScripts(String.valueOf(categoryNumberOfScriptsMap.get(AnukyaConstants.TOTAL_CONSTANT)));
		bhaarathaSharesCategoryLongTermOnlyMap.get(AnukyaConstants.TOTAL_CONSTANT).setNumberOfScripts(
				String.valueOf(categoryNumberOfScriptsLongTermOnlyMap.get(AnukyaConstants.TOTAL_CONSTANT)));

		for (Map.Entry<String, Map<String, BhaarathaSharesOneSectorDetails>> oneSectorDetailsMap : bhaarathaSharesOneSectorDetailsMap
				.entrySet()) {

			BigDecimal totalOneSectorInvestedValue = new BigDecimal(
					oneSectorDetailsMap.getValue().get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue());
			BigDecimal totalOneSectorCurrentValue = new BigDecimal(
					oneSectorDetailsMap.getValue().get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue());

			for (Map.Entry<String, BhaarathaSharesOneSectorDetails> sectorMap : oneSectorDetailsMap.getValue()
					.entrySet()) {
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

		for (Map.Entry<String, Map<String, BhaarathaSharesOneSectorDetails>> oneSectorDetailsMap : bhaarathaSharesOneSectorDetailsLongTermOnlyMap
				.entrySet()) {

			BigDecimal totalOneSectorInvestedValue = new BigDecimal(
					oneSectorDetailsMap.getValue().get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue());
			BigDecimal totalOneSectorCurrentValue = new BigDecimal(
					oneSectorDetailsMap.getValue().get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue());

			for (Map.Entry<String, BhaarathaSharesOneSectorDetails> sectorMap : oneSectorDetailsMap.getValue()
					.entrySet()) {
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

		for (Map.Entry<String, Map<String, BhaarathaSharesOneCategoryDetails>> oneCategoryDetailsMap : bhaarathaSharesOneCategoryDetailsMap
				.entrySet()) {

			BigDecimal totalOneCategoryInvestedValue = new BigDecimal(
					oneCategoryDetailsMap.getValue().get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue());
			BigDecimal totalOneCategoryCurrentValue = new BigDecimal(
					oneCategoryDetailsMap.getValue().get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue());

			for (Map.Entry<String, BhaarathaSharesOneCategoryDetails> categoryMap : oneCategoryDetailsMap.getValue()
					.entrySet()) {
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

		for (Map.Entry<String, Map<String, BhaarathaSharesOneCategoryDetails>> oneCategoryDetailsMap : bhaarathaSharesOneCategoryDetailsLongTermOnlyMap
				.entrySet()) {

			BigDecimal totalOneCategoryInvestedValue = new BigDecimal(
					oneCategoryDetailsMap.getValue().get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue());
			BigDecimal totalOneCategoryCurrentValue = new BigDecimal(
					oneCategoryDetailsMap.getValue().get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue());

			for (Map.Entry<String, BhaarathaSharesOneCategoryDetails> categoryMap : oneCategoryDetailsMap.getValue()
					.entrySet()) {
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

		BhaarathaSharesAnalysis bhaarathaSharesAnalysis = new BhaarathaSharesAnalysis();

		bhaarathaSharesAnalysis.setBhaarathaSharesSectorMap(bhaarathaSharesSectorMap);
		bhaarathaSharesAnalysis.setBhaarathaSharesSectorLongTermOnlyMap(bhaarathaSharesSectorLongTermOnlyMap);

		bhaarathaSharesAnalysis.setBhaarathaSharesCategoryMap(bhaarathaSharesCategoryMap);
		bhaarathaSharesAnalysis.setBhaarathaSharesCategoryLongTermOnlyMap(bhaarathaSharesCategoryLongTermOnlyMap);

		bhaarathaSharesAnalysis.setBhaarathaSharesOneSectorDetailsMap(bhaarathaSharesOneSectorDetailsMap);
		bhaarathaSharesAnalysis
				.setBhaarathaSharesOneSectorDetailsLongTermOnlyMap(bhaarathaSharesOneSectorDetailsLongTermOnlyMap);

		bhaarathaSharesAnalysis.setBhaarathaSharesOneCategoryDetailsMap(bhaarathaSharesOneCategoryDetailsMap);
		bhaarathaSharesAnalysis
				.setBhaarathaSharesOneCategoryDetailsLongTermOnlyMap(bhaarathaSharesOneCategoryDetailsLongTermOnlyMap);

		return bhaarathaSharesAnalysis;
	}

	private void updateNumberOfScripts(Set<String> numberOfScriptsSet, Set<String> numberOfScriptsLongTermOnlySet,
			Map<String, BigDecimal> numberOfScriptsMap, Map<String, BigDecimal> numberOfScriptsLongTermOnlyMap,
			String bseCode, String key, boolean isShortTermInvestment) {

		if (!numberOfScriptsSet.contains(bseCode)) {
			numberOfScriptsSet.add(bseCode);

			BigDecimal existingNumberOfScripts = numberOfScriptsMap.get(key) != null ? numberOfScriptsMap.get(key)
					: new BigDecimal(AnukyaConstants.NUMBER_0);
			BigDecimal numberOfScripts = existingNumberOfScripts.add(new BigDecimal(AnukyaConstants.NUMBER_1));
			numberOfScriptsMap.put(key, numberOfScripts);

			BigDecimal totalExistingNumberOfScripts = numberOfScriptsMap.get(AnukyaConstants.TOTAL_CONSTANT) != null
					? numberOfScriptsMap.get(AnukyaConstants.TOTAL_CONSTANT)
					: new BigDecimal(AnukyaConstants.NUMBER_0);
			BigDecimal totalNumberOfScripts = totalExistingNumberOfScripts
					.add(new BigDecimal(AnukyaConstants.NUMBER_1));
			numberOfScriptsMap.put(AnukyaConstants.TOTAL_CONSTANT, totalNumberOfScripts);
		}

		if (!isShortTermInvestment && !numberOfScriptsLongTermOnlySet.contains(bseCode)) {
			numberOfScriptsLongTermOnlySet.add(bseCode);

			BigDecimal existingNumberOfScripts = numberOfScriptsLongTermOnlyMap.get(key) != null
					? numberOfScriptsLongTermOnlyMap.get(key)
					: new BigDecimal(AnukyaConstants.NUMBER_0);
			BigDecimal numberOfScripts = existingNumberOfScripts.add(new BigDecimal(AnukyaConstants.NUMBER_1));
			numberOfScriptsLongTermOnlyMap.put(key, numberOfScripts);

			BigDecimal totalExistingNumberOfScripts = numberOfScriptsLongTermOnlyMap
					.get(AnukyaConstants.TOTAL_CONSTANT) != null
							? numberOfScriptsLongTermOnlyMap.get(AnukyaConstants.TOTAL_CONSTANT)
							: new BigDecimal(AnukyaConstants.NUMBER_0);
			BigDecimal totalNumberOfScripts = totalExistingNumberOfScripts
					.add(new BigDecimal(AnukyaConstants.NUMBER_1));
			numberOfScriptsLongTermOnlyMap.put(AnukyaConstants.TOTAL_CONSTANT, totalNumberOfScripts);
		}
	}

	private void buildBhaarathaSharesSector(BhaarathaShares bhaarathaShare,
			BhaarathaSharesCalculationDetails bhaarathaSharesCalculationDetails,
			Map<String, BhaarathaSharesSector> bhaarathaSharesSectorMap, Map<String, Long> sellDepositoryChargesMap) {

		if (!bhaarathaSharesSectorMap.containsKey(bhaarathaShare.getSector())) {
			bhaarathaSharesSectorMap.put(bhaarathaShare.getSector(), new BhaarathaSharesSector());
		}

		BigDecimal purchaseQuantity = new BigDecimal(bhaarathaShare.getPurchaseQuantity());

		BigDecimal purchaseTotal = new BigDecimal(bhaarathaShare.getPurchaseTotal());

		BigDecimal investedValue = new BigDecimal(
				bhaarathaSharesSectorMap.get(bhaarathaShare.getSector()).getInvestedValue()).add(purchaseTotal);

		BigDecimal totalInvestedValue = new BigDecimal(
				bhaarathaSharesSectorMap.get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue()).add(purchaseTotal);

		BigDecimal sellQuantity = purchaseQuantity;

		BigDecimal sellPrice = new BigDecimal(AnukyaConstants.NUMBER_0);
		if (AnukyaConstants.BSE.equalsIgnoreCase(bhaarathaShare.getStockExchange())) {
			sellPrice = new BigDecimal(bhaarathaSharesCalculationDetails.getLatestTradingDetailsMap()
					.get(bhaarathaShare.getBseScriptCode()).getLastTradingPrice());
		} else if (AnukyaConstants.NSE.equalsIgnoreCase(bhaarathaShare.getStockExchange())) {
			sellPrice = new BigDecimal(bhaarathaSharesCalculationDetails.getLatestTradingDetailsMap()
					.get(bhaarathaShare.getNseScriptCode()).getLastTradingPrice());
		}

		BigDecimal sellGrossTotal = sellQuantity.multiply(sellPrice);

		BigDecimal sellBrokerage = bhaarathaExpenditureUtils.calculateBrokerage(sellGrossTotal, sellQuantity,
				bhaarathaSharesCalculationDetails.getTotalSoldQuantityMap().get(bhaarathaShare.getBseScriptCode()),
				bhaarathaSharesCalculationDetails.getTotalSoldGrossTotalMap().get(bhaarathaShare.getBseScriptCode()));

		BigDecimal sellSTT = bhaarathaExpenditureUtils.calculateSTT(sellGrossTotal);

		BigDecimal sellExpenditure = bhaarathaExpenditureUtils
				.getBhaarathaSharesExpenditure(sellGrossTotal, sellBrokerage).add(bhaarathaExpenditureUtils
						.calculateDepositoryCharges(sellDepositoryChargesMap, bhaarathaShare.getBseScriptCode()));

		BigDecimal sellNonExpenditure = bhaarathaExpenditureUtils.getBhaarathaSharesNonExpenditure();

		BigDecimal sellTotal = sellGrossTotal.subtract(sellBrokerage).subtract(sellSTT).subtract(sellExpenditure)
				.subtract(sellNonExpenditure);

		BigDecimal currentValue = new BigDecimal(
				bhaarathaSharesSectorMap.get(bhaarathaShare.getSector()).getCurrentValue()).add(sellTotal);
		BigDecimal totalCurrentValue = new BigDecimal(
				bhaarathaSharesSectorMap.get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue()).add(sellTotal);

		BigDecimal profitLoss = currentValue.subtract(investedValue);
		BigDecimal profitLossPercentage = currentValue.divide(investedValue, MathContext.DECIMAL128)
				.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
				.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));

		BigDecimal totalProfitLoss = totalCurrentValue.subtract(totalInvestedValue);

		BigDecimal totalProfitLossPercentage = totalCurrentValue.divide(totalInvestedValue, MathContext.DECIMAL128)
				.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
				.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));

		BhaarathaSharesSector bhaarathaSharesSector = new BhaarathaSharesSector();
		bhaarathaSharesSector.setSectorName(bhaarathaShare.getSector());
		bhaarathaSharesSector.setInvestedValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, investedValue));
		bhaarathaSharesSector.setCurrentValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, currentValue));
		bhaarathaSharesSector.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLoss));
		bhaarathaSharesSector
				.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLossPercentage));

		bhaarathaSharesSectorMap.put(bhaarathaShare.getSector(), bhaarathaSharesSector);

		BhaarathaSharesSector bhaarathaSharesSectorTotal = new BhaarathaSharesSector();
		bhaarathaSharesSectorTotal.setSectorName(AnukyaConstants.TOTAL_CONSTANT);
		bhaarathaSharesSectorTotal.setInvestedValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalInvestedValue));
		bhaarathaSharesSectorTotal.setInvestedValuePercentage(
				String.format(AnukyaConstants.FLOAT_2_DECIMAL, new BigDecimal(AnukyaConstants.NUMBER_100)));
		bhaarathaSharesSectorTotal.setCurrentValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalCurrentValue));
		bhaarathaSharesSectorTotal.setCurrentValuePercentage(
				String.format(AnukyaConstants.FLOAT_2_DECIMAL, new BigDecimal(AnukyaConstants.NUMBER_100)));
		bhaarathaSharesSectorTotal.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLoss));
		bhaarathaSharesSectorTotal
				.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLossPercentage));

		bhaarathaSharesSectorMap.put(AnukyaConstants.TOTAL_CONSTANT, bhaarathaSharesSectorTotal);
	}

	private void buildBhaarathaSharesCategory(BhaarathaShares bhaarathaShare,
			BhaarathaSharesCalculationDetails bhaarathaSharesCalculationDetails,
			Map<String, BhaarathaSharesCategory> bhaarathaSharesCategoryMap,
			Map<String, Long> sellDepositoryChargesMap) {

		if (!bhaarathaSharesCategoryMap.containsKey(bhaarathaShare.getCategory())) {
			bhaarathaSharesCategoryMap.put(bhaarathaShare.getCategory(), new BhaarathaSharesCategory());
		}

		BigDecimal purchaseQuantity = new BigDecimal(bhaarathaShare.getPurchaseQuantity());

		BigDecimal purchaseTotal = new BigDecimal(bhaarathaShare.getPurchaseTotal());

		BigDecimal investedValue = new BigDecimal(
				bhaarathaSharesCategoryMap.get(bhaarathaShare.getCategory()).getInvestedValue()).add(purchaseTotal);

		BigDecimal totalInvestedValue = new BigDecimal(
				bhaarathaSharesCategoryMap.get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue()).add(purchaseTotal);

		BigDecimal sellQuantity = purchaseQuantity;

		BigDecimal sellPrice = new BigDecimal(AnukyaConstants.NUMBER_0);
		if (AnukyaConstants.BSE.equalsIgnoreCase(bhaarathaShare.getStockExchange())) {
			sellPrice = new BigDecimal(bhaarathaSharesCalculationDetails.getLatestTradingDetailsMap()
					.get(bhaarathaShare.getBseScriptCode()).getLastTradingPrice());
		} else if (AnukyaConstants.NSE.equalsIgnoreCase(bhaarathaShare.getStockExchange())) {
			sellPrice = new BigDecimal(bhaarathaSharesCalculationDetails.getLatestTradingDetailsMap()
					.get(bhaarathaShare.getNseScriptCode()).getLastTradingPrice());
		}

		BigDecimal sellGrossTotal = sellQuantity.multiply(sellPrice);

		BigDecimal sellBrokerage = bhaarathaExpenditureUtils.calculateBrokerage(sellGrossTotal, sellQuantity,
				bhaarathaSharesCalculationDetails.getTotalSoldQuantityMap().get(bhaarathaShare.getBseScriptCode()),
				bhaarathaSharesCalculationDetails.getTotalSoldGrossTotalMap().get(bhaarathaShare.getBseScriptCode()));

		BigDecimal sellSTT = bhaarathaExpenditureUtils.calculateSTT(sellGrossTotal);

		BigDecimal sellExpenditure = bhaarathaExpenditureUtils
				.getBhaarathaSharesExpenditure(sellGrossTotal, sellBrokerage).add(bhaarathaExpenditureUtils
						.calculateDepositoryCharges(sellDepositoryChargesMap, bhaarathaShare.getBseScriptCode()));

		BigDecimal sellNonExpenditure = bhaarathaExpenditureUtils.getBhaarathaSharesNonExpenditure();

		BigDecimal sellTotal = sellGrossTotal.subtract(sellBrokerage).subtract(sellSTT).subtract(sellExpenditure)
				.subtract(sellNonExpenditure);

		BigDecimal currentValue = new BigDecimal(
				bhaarathaSharesCategoryMap.get(bhaarathaShare.getCategory()).getCurrentValue()).add(sellTotal);
		BigDecimal totalCurrentValue = new BigDecimal(
				bhaarathaSharesCategoryMap.get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue()).add(sellTotal);

		BigDecimal profitLoss = currentValue.subtract(investedValue);
		BigDecimal profitLossPercentage = currentValue.divide(investedValue, MathContext.DECIMAL128)
				.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
				.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));

		BigDecimal totalProfitLoss = totalCurrentValue.subtract(totalInvestedValue);

		BigDecimal totalProfitLossPercentage = totalCurrentValue.divide(totalInvestedValue, MathContext.DECIMAL128)
				.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
				.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));

		BhaarathaSharesCategory bhaarathaSharesCategory = new BhaarathaSharesCategory();
		bhaarathaSharesCategory.setCategoryName(bhaarathaShare.getCategory());
		bhaarathaSharesCategory.setInvestedValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, investedValue));
		bhaarathaSharesCategory.setCurrentValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, currentValue));
		bhaarathaSharesCategory.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLoss));
		bhaarathaSharesCategory
				.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLossPercentage));

		bhaarathaSharesCategoryMap.put(bhaarathaShare.getCategory(), bhaarathaSharesCategory);

		BhaarathaSharesCategory bhaarathaSharesCategoryTotal = new BhaarathaSharesCategory();
		bhaarathaSharesCategoryTotal.setCategoryName(AnukyaConstants.TOTAL_CONSTANT);
		bhaarathaSharesCategoryTotal
				.setInvestedValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalInvestedValue));
		bhaarathaSharesCategoryTotal.setInvestedValuePercentage(
				String.format(AnukyaConstants.FLOAT_2_DECIMAL, new BigDecimal(AnukyaConstants.NUMBER_100)));
		bhaarathaSharesCategoryTotal.setCurrentValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalCurrentValue));
		bhaarathaSharesCategoryTotal.setCurrentValuePercentage(
				String.format(AnukyaConstants.FLOAT_2_DECIMAL, new BigDecimal(AnukyaConstants.NUMBER_100)));
		bhaarathaSharesCategoryTotal.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLoss));
		bhaarathaSharesCategoryTotal
				.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLossPercentage));

		bhaarathaSharesCategoryMap.put(AnukyaConstants.TOTAL_CONSTANT, bhaarathaSharesCategoryTotal);
	}

	private void buildBhaarathaSharesOneSectorDetails(BhaarathaShares bhaarathaShare,
			BhaarathaSharesCalculationDetails bhaarathaSharesCalculationDetails,
			Map<String, Map<String, BhaarathaSharesOneSectorDetails>> bhaarathaSharesOneSectorDetailsMap,
			Map<String, Long> sellDepositoryChargesMap) {

		if (bhaarathaSharesOneSectorDetailsMap.isEmpty()) {
			Map<String, BhaarathaSharesOneSectorDetails> oneSectorDetailsMap = new LinkedHashMap<>();
			oneSectorDetailsMap.put(AnukyaConstants.TOTAL_CONSTANT, new BhaarathaSharesOneSectorDetails());
		}

		if (!bhaarathaSharesOneSectorDetailsMap.containsKey(bhaarathaShare.getSector())) {
			Map<String, BhaarathaSharesOneSectorDetails> oneSectorDetailsMap = new LinkedHashMap<>();
			oneSectorDetailsMap.put(AnukyaConstants.TOTAL_CONSTANT, new BhaarathaSharesOneSectorDetails());
			bhaarathaSharesOneSectorDetailsMap.put(bhaarathaShare.getSector(), oneSectorDetailsMap);
		}

		if (!bhaarathaSharesOneSectorDetailsMap.get(bhaarathaShare.getSector())
				.containsKey(bhaarathaShare.getBseScriptCode())) {
			bhaarathaSharesOneSectorDetailsMap.get(bhaarathaShare.getSector()).put(bhaarathaShare.getBseScriptCode(),
					new BhaarathaSharesOneSectorDetails());
		}

		BigDecimal purchaseQuantity = new BigDecimal(bhaarathaShare.getPurchaseQuantity());

		BigDecimal purchaseTotal = new BigDecimal(bhaarathaShare.getPurchaseTotal());

		BigDecimal investedValue = new BigDecimal(bhaarathaSharesOneSectorDetailsMap.get(bhaarathaShare.getSector())
				.get(bhaarathaShare.getBseScriptCode()).getInvestedValue()).add(purchaseTotal);
		BigDecimal totalInvestedValue = new BigDecimal(bhaarathaSharesOneSectorDetailsMap
				.get(bhaarathaShare.getSector()).get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue())
						.add(purchaseTotal);

		BigDecimal sellQuantity = purchaseQuantity;

		BigDecimal sellPrice = new BigDecimal(AnukyaConstants.NUMBER_0);
		if (AnukyaConstants.BSE.equalsIgnoreCase(bhaarathaShare.getStockExchange())) {
			sellPrice = new BigDecimal(bhaarathaSharesCalculationDetails.getLatestTradingDetailsMap()
					.get(bhaarathaShare.getBseScriptCode()).getLastTradingPrice());
		} else if (AnukyaConstants.NSE.equalsIgnoreCase(bhaarathaShare.getStockExchange())) {
			sellPrice = new BigDecimal(bhaarathaSharesCalculationDetails.getLatestTradingDetailsMap()
					.get(bhaarathaShare.getNseScriptCode()).getLastTradingPrice());
		}

		BigDecimal sellGrossTotal = sellQuantity.multiply(sellPrice);

		BigDecimal sellBrokerage = bhaarathaExpenditureUtils.calculateBrokerage(sellGrossTotal, sellQuantity,
				bhaarathaSharesCalculationDetails.getTotalSoldQuantityMap().get(bhaarathaShare.getBseScriptCode()),
				bhaarathaSharesCalculationDetails.getTotalSoldGrossTotalMap().get(bhaarathaShare.getBseScriptCode()));

		BigDecimal sellSTT = bhaarathaExpenditureUtils.calculateSTT(sellGrossTotal);

		BigDecimal sellExpenditure = bhaarathaExpenditureUtils
				.getBhaarathaSharesExpenditure(sellGrossTotal, sellBrokerage).add(bhaarathaExpenditureUtils
						.calculateDepositoryCharges(sellDepositoryChargesMap, bhaarathaShare.getBseScriptCode()));

		BigDecimal sellNonExpenditure = bhaarathaExpenditureUtils.getBhaarathaSharesNonExpenditure();

		BigDecimal sellTotal = sellGrossTotal.subtract(sellBrokerage).subtract(sellSTT).subtract(sellExpenditure)
				.subtract(sellNonExpenditure);

		BigDecimal currentValue = new BigDecimal(bhaarathaSharesOneSectorDetailsMap.get(bhaarathaShare.getSector())
				.get(bhaarathaShare.getBseScriptCode()).getCurrentValue()).add(sellTotal);
		BigDecimal totalCurrentValue = new BigDecimal(bhaarathaSharesOneSectorDetailsMap.get(bhaarathaShare.getSector())
				.get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue()).add(sellTotal);

		BigDecimal profitLoss = currentValue.subtract(investedValue);
		BigDecimal profitLossPercentage = currentValue.divide(investedValue, MathContext.DECIMAL128)
				.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
				.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));

		BigDecimal totalProfitLoss = totalCurrentValue.subtract(totalInvestedValue);
		BigDecimal totalProfitLossPercentage = totalCurrentValue.divide(totalInvestedValue, MathContext.DECIMAL128)
				.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
				.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));

		BhaarathaSharesOneSectorDetails bhaarathaSharesOneSectorDetails = new BhaarathaSharesOneSectorDetails();
		bhaarathaSharesOneSectorDetails.setScriptName(bhaarathaShare.getScriptName());
		bhaarathaSharesOneSectorDetails.setBseCode(bhaarathaShare.getBseScriptCode());
		bhaarathaSharesOneSectorDetails.setNseCode(bhaarathaShare.getNseScriptCode());
		bhaarathaSharesOneSectorDetails.setMoneycontrolCode(bhaarathaShare.getMoneycontrolCode());
		bhaarathaSharesOneSectorDetails.setInvestedValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, investedValue));
		bhaarathaSharesOneSectorDetails.setCurrentValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, currentValue));
		bhaarathaSharesOneSectorDetails.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLoss));
		bhaarathaSharesOneSectorDetails
				.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLossPercentage));

		bhaarathaSharesOneSectorDetailsMap.get(bhaarathaShare.getSector()).put(bhaarathaShare.getBseScriptCode(),
				bhaarathaSharesOneSectorDetails);

		BhaarathaSharesOneSectorDetails bhaarathaSharesOneSectorDetailsTotal = new BhaarathaSharesOneSectorDetails();
		bhaarathaSharesOneSectorDetailsTotal
				.setInvestedValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalInvestedValue));
		bhaarathaSharesOneSectorDetailsTotal.setInvestedValuePercentage(
				String.format(AnukyaConstants.FLOAT_2_DECIMAL, new BigDecimal(AnukyaConstants.NUMBER_100)));
		bhaarathaSharesOneSectorDetailsTotal
				.setCurrentValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalCurrentValue));
		bhaarathaSharesOneSectorDetailsTotal.setCurrentValuePercentage(
				String.format(AnukyaConstants.FLOAT_2_DECIMAL, new BigDecimal(AnukyaConstants.NUMBER_100)));
		bhaarathaSharesOneSectorDetailsTotal
				.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLoss));
		bhaarathaSharesOneSectorDetailsTotal
				.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLossPercentage));

		bhaarathaSharesOneSectorDetailsMap.get(bhaarathaShare.getSector()).put(AnukyaConstants.TOTAL_CONSTANT,
				bhaarathaSharesOneSectorDetailsTotal);
	}

	private void buildBhaarathaSharesOneCategoryDetails(BhaarathaShares bhaarathaShare,
			BhaarathaSharesCalculationDetails bhaarathaSharesCalculationDetails,
			Map<String, Map<String, BhaarathaSharesOneCategoryDetails>> bhaarathaSharesOneCategoryDetailsMap,
			Map<String, Long> sellDepositoryChargesMap) {

		if (bhaarathaSharesOneCategoryDetailsMap.isEmpty()) {
			Map<String, BhaarathaSharesOneCategoryDetails> oneCategoryDetailsMap = new LinkedHashMap<>();
			oneCategoryDetailsMap.put(AnukyaConstants.TOTAL_CONSTANT, new BhaarathaSharesOneCategoryDetails());
		}

		if (!bhaarathaSharesOneCategoryDetailsMap.containsKey(bhaarathaShare.getCategory())) {
			Map<String, BhaarathaSharesOneCategoryDetails> oneCategoryDetailsMap = new LinkedHashMap<>();
			oneCategoryDetailsMap.put(AnukyaConstants.TOTAL_CONSTANT, new BhaarathaSharesOneCategoryDetails());
			bhaarathaSharesOneCategoryDetailsMap.put(bhaarathaShare.getCategory(), oneCategoryDetailsMap);
		}

		if (!bhaarathaSharesOneCategoryDetailsMap.get(bhaarathaShare.getCategory())
				.containsKey(bhaarathaShare.getBseScriptCode())) {
			bhaarathaSharesOneCategoryDetailsMap.get(bhaarathaShare.getCategory())
					.put(bhaarathaShare.getBseScriptCode(), new BhaarathaSharesOneCategoryDetails());
		}

		BigDecimal purchaseQuantity = new BigDecimal(bhaarathaShare.getPurchaseQuantity());

		BigDecimal purchaseTotal = new BigDecimal(bhaarathaShare.getPurchaseTotal());

		BigDecimal investedValue = new BigDecimal(bhaarathaSharesOneCategoryDetailsMap.get(bhaarathaShare.getCategory())
				.get(bhaarathaShare.getBseScriptCode()).getInvestedValue()).add(purchaseTotal);
		BigDecimal totalInvestedValue = new BigDecimal(bhaarathaSharesOneCategoryDetailsMap
				.get(bhaarathaShare.getCategory()).get(AnukyaConstants.TOTAL_CONSTANT).getInvestedValue())
						.add(purchaseTotal);

		BigDecimal sellQuantity = purchaseQuantity;

		BigDecimal sellPrice = new BigDecimal(AnukyaConstants.NUMBER_0);
		if (AnukyaConstants.BSE.equalsIgnoreCase(bhaarathaShare.getStockExchange())) {
			sellPrice = new BigDecimal(bhaarathaSharesCalculationDetails.getLatestTradingDetailsMap()
					.get(bhaarathaShare.getBseScriptCode()).getLastTradingPrice());
		} else if (AnukyaConstants.NSE.equalsIgnoreCase(bhaarathaShare.getStockExchange())) {
			sellPrice = new BigDecimal(bhaarathaSharesCalculationDetails.getLatestTradingDetailsMap()
					.get(bhaarathaShare.getNseScriptCode()).getLastTradingPrice());
		}

		BigDecimal sellGrossTotal = sellQuantity.multiply(sellPrice);

		BigDecimal sellBrokerage = bhaarathaExpenditureUtils.calculateBrokerage(sellGrossTotal, sellQuantity,
				bhaarathaSharesCalculationDetails.getTotalSoldQuantityMap().get(bhaarathaShare.getBseScriptCode()),
				bhaarathaSharesCalculationDetails.getTotalSoldGrossTotalMap().get(bhaarathaShare.getBseScriptCode()));

		BigDecimal sellSTT = bhaarathaExpenditureUtils.calculateSTT(sellGrossTotal);

		BigDecimal sellExpenditure = bhaarathaExpenditureUtils
				.getBhaarathaSharesExpenditure(sellGrossTotal, sellBrokerage).add(bhaarathaExpenditureUtils
						.calculateDepositoryCharges(sellDepositoryChargesMap, bhaarathaShare.getBseScriptCode()));

		BigDecimal sellNonExpenditure = bhaarathaExpenditureUtils.getBhaarathaSharesNonExpenditure();

		BigDecimal sellTotal = sellGrossTotal.subtract(sellBrokerage).subtract(sellSTT).subtract(sellExpenditure)
				.subtract(sellNonExpenditure);

		BigDecimal currentValue = new BigDecimal(bhaarathaSharesOneCategoryDetailsMap.get(bhaarathaShare.getCategory())
				.get(bhaarathaShare.getBseScriptCode()).getCurrentValue()).add(sellTotal);
		BigDecimal totalCurrentValue = new BigDecimal(bhaarathaSharesOneCategoryDetailsMap
				.get(bhaarathaShare.getCategory()).get(AnukyaConstants.TOTAL_CONSTANT).getCurrentValue())
						.add(sellTotal);

		BigDecimal profitLoss = currentValue.subtract(investedValue);
		BigDecimal profitLossPercentage = currentValue.divide(investedValue, MathContext.DECIMAL128)
				.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
				.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));

		BigDecimal totalProfitLoss = totalCurrentValue.subtract(totalInvestedValue);
		BigDecimal totalProfitLossPercentage = totalCurrentValue.divide(totalInvestedValue, MathContext.DECIMAL128)
				.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
				.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));

		BhaarathaSharesOneCategoryDetails bhaarathaSharesOneCategoryDetails = new BhaarathaSharesOneCategoryDetails();
		bhaarathaSharesOneCategoryDetails.setScriptName(bhaarathaShare.getScriptName());
		bhaarathaSharesOneCategoryDetails.setBseCode(bhaarathaShare.getBseScriptCode());
		bhaarathaSharesOneCategoryDetails.setNseCode(bhaarathaShare.getNseScriptCode());
		bhaarathaSharesOneCategoryDetails.setMoneycontrolCode(bhaarathaShare.getMoneycontrolCode());
		bhaarathaSharesOneCategoryDetails
				.setInvestedValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, investedValue));
		bhaarathaSharesOneCategoryDetails.setCurrentValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, currentValue));
		bhaarathaSharesOneCategoryDetails.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLoss));
		bhaarathaSharesOneCategoryDetails
				.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, profitLossPercentage));

		bhaarathaSharesOneCategoryDetailsMap.get(bhaarathaShare.getCategory()).put(bhaarathaShare.getBseScriptCode(),
				bhaarathaSharesOneCategoryDetails);

		BhaarathaSharesOneCategoryDetails bhaarathaSharesOneCategoryDetailsTotal = new BhaarathaSharesOneCategoryDetails();
		bhaarathaSharesOneCategoryDetailsTotal
				.setInvestedValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalInvestedValue));
		bhaarathaSharesOneCategoryDetailsTotal.setInvestedValuePercentage(
				String.format(AnukyaConstants.FLOAT_2_DECIMAL, new BigDecimal(AnukyaConstants.NUMBER_100)));
		bhaarathaSharesOneCategoryDetailsTotal
				.setCurrentValue(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalCurrentValue));
		bhaarathaSharesOneCategoryDetailsTotal.setCurrentValuePercentage(
				String.format(AnukyaConstants.FLOAT_2_DECIMAL, new BigDecimal(AnukyaConstants.NUMBER_100)));
		bhaarathaSharesOneCategoryDetailsTotal
				.setProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLoss));
		bhaarathaSharesOneCategoryDetailsTotal
				.setProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLossPercentage));

		bhaarathaSharesOneCategoryDetailsMap.get(bhaarathaShare.getCategory()).put(AnukyaConstants.TOTAL_CONSTANT,
				bhaarathaSharesOneCategoryDetailsTotal);

	}

	private BigDecimal getYahooFinanceDividendYield(String scriptCode, String latestTradingPrice)
			throws AnukyaException {

		BigDecimal ttmDividend = bhaarathaSharesServiceCall.getYahooFinanceTtmDividend(scriptCode);

		return ttmDividend.divide(new BigDecimal(latestTradingPrice), MathContext.DECIMAL128)
				.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
	}

}
