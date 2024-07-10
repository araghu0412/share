package com.moneymanager.anukya.bhaaratha.eq;

import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.moneymanager.anukya.data.impl.BhaarathaSharesData;
import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.ErrorDetails;
import com.moneymanager.anukya.model.BhaarathaShares;
import com.moneymanager.anukya.model.BhaarathaSharesResponse;
import com.moneymanager.anukya.model.CommonResponse;
import com.moneymanager.anukya.utils.AnukyaConstants;
import com.moneymanager.anukya.utils.AnukyaDataUtils;
import com.moneymanager.anukya.utils.AnukyaUtils;
import com.moneymanager.anukya.utils.BhaarathaSharesUtils;

@Component
public class BhaarathaSoldShares extends AbstractBhaarathaShares {

	@Value("${DATABASE.BASE.LOCATION}")
	private String databaseBaseLocation;

	@Autowired
	private AnukyaDataUtils anukyaDataUtils;

	@Autowired
	private AnukyaUtils anukyaUtils;

	@Autowired
	private BhaarathaSharesData bhaarathaSharesData;

	@Autowired
	private BhaarathaSharesUtils bhaarathaSharesUtils;

	public static final String ERROR_WHILE_ADDING_SOLD_SHARES = "Error while adding sold shares";

	@SuppressWarnings("unchecked")
	@Override
	public CommonResponse addShares(BhaarathaShares soldBhaarathaShares) throws AnukyaException {

		CommonResponse commonResponse = new CommonResponse();

		// Checking if script is purchased
		File singleScriptFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.BHA_EQ_PURCHASE_MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ soldBhaarathaShares.getBseScriptCode() + AnukyaConstants.JSON_EXTENSION);

		if (!anukyaDataUtils.isFileExists(singleScriptFile)) {

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0004);
			errorDetails.setErrorMessage("Script is not purchased");
			errorDetailsList.add(errorDetails);

			throw new AnukyaException(ERROR_WHILE_ADDING_SOLD_SHARES, errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		List<BhaarathaShares> singleScriptList = bhaarathaSharesData.getBhaarathaShares(singleScriptFile);
		int existingPurchasedQuantity = singleScriptList.stream()
				.mapToInt(singleScript -> Integer.parseInt(singleScript.getPurchaseQuantity())).sum();

		// Checking if selling more shares than purchased
		if (existingPurchasedQuantity < Integer.parseInt(soldBhaarathaShares.getSellQuantity())) {

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0004);
			errorDetails.setErrorMessage(
					"Trying to sell more shares than purchased for " + soldBhaarathaShares.getScriptName()
							+ ". You currently own " + existingPurchasedQuantity + " number of share/s");
			errorDetailsList.add(errorDetails);

			throw new AnukyaException(ERROR_WHILE_ADDING_SOLD_SHARES, errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		File purchaseMainDirectory = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_PURCHASE_MAIN_DIRECTORY);
		File purchaseUpdateDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
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

		// Delete update and main backup directory for purchase and sold shares
		anukyaDataUtils.deleteDirectory(purchaseUpdateDirectory);
		anukyaDataUtils.deleteDirectory(purchaseMainBackUpDirectory);
		anukyaDataUtils.deleteDirectory(soldUpdateDirectory);
		anukyaDataUtils.deleteDirectory(soldMainBackUpDirectory);

		// Copy main directory into update and main backup directory for purchase and
		// sold shares
		anukyaDataUtils.copyDirectory(purchaseMainDirectory, purchaseUpdateDirectory);
		anukyaDataUtils.copyDirectory(purchaseMainDirectory, purchaseMainBackUpDirectory);
		anukyaDataUtils.copyDirectory(soldMainDirectory, soldUpdateDirectory);
		anukyaDataUtils.copyDirectory(soldMainDirectory, soldMainBackUpDirectory);

		File purchasedSingleScriptFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ soldBhaarathaShares.getBseScriptCode() + AnukyaConstants.JSON_EXTENSION);
		File purchasedNonConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);
		File purchasedConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		File soldSingleScriptFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.BHA_EQ_SOLD_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ soldBhaarathaShares.getBseScriptCode() + AnukyaConstants.JSON_EXTENSION);
		File soldNonConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_SOLD_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);
		File soldConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_SOLD_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		BhaarathaShares soldSingleScriptTotal = new BhaarathaShares();
		BhaarathaShares soldNonConsolidatedTotal = new BhaarathaShares();
		BhaarathaShares soldConsolidatedTotal = new BhaarathaShares();

		List<BhaarathaShares> purchasedSingleScriptList = bhaarathaSharesData
				.getBhaarathaShares(purchasedSingleScriptFile);
		List<BhaarathaShares> soldSingleScriptList = new ArrayList<>();
		if (anukyaDataUtils.isFileExists(soldSingleScriptFile)) {
			soldSingleScriptList = bhaarathaSharesData.getBhaarathaShares(soldSingleScriptFile);
			soldSingleScriptTotal = soldSingleScriptList.get(soldSingleScriptList.size() - 1);
			// Removing the last record for total calculation
			soldSingleScriptList.remove(soldSingleScriptList.size() - 1);
		}

		List<BhaarathaShares> purchasedNonConsolidatedList = bhaarathaSharesData
				.getBhaarathaShares(purchasedNonConsolidatedFile);
		List<BhaarathaShares> soldNonConsolidatedList = bhaarathaSharesData.getBhaarathaShares(soldNonConsolidatedFile);
		if (!soldNonConsolidatedList.isEmpty()) {
			soldNonConsolidatedTotal = soldNonConsolidatedList.get(soldNonConsolidatedList.size() - 1);
			// Removing the last record for total calculation
			soldNonConsolidatedList.remove(soldNonConsolidatedList.size() - 1);
		}

		List<BhaarathaShares> purchasedConsolidatedList = bhaarathaSharesData
				.getBhaarathaShares(purchasedConsolidatedFile);
		List<BhaarathaShares> soldConsolidatedList = bhaarathaSharesData.getBhaarathaShares(soldConsolidatedFile);
		if (!soldConsolidatedList.isEmpty()) {
			soldConsolidatedTotal = soldConsolidatedList.get(soldConsolidatedList.size() - 1);
			// Removing the last record for total calculation
			soldConsolidatedList.remove(soldConsolidatedList.size() - 1);
		}

		// Single script
		Map<String, Object> singleScriptSoldResponseMap = getNonConsolidatedSoldShares(purchasedSingleScriptList,
				soldBhaarathaShares, soldSingleScriptTotal);
		if (((List<BhaarathaShares>) singleScriptSoldResponseMap.get(AnukyaConstants.BHAARATHA_BOUGHT_SHARES_LIST))
				.isEmpty()) {
			anukyaDataUtils.deleteFile(purchasedSingleScriptFile);
		} else {
			anukyaDataUtils.createOrUpdateFile(purchasedSingleScriptFile,
					(singleScriptSoldResponseMap.get(AnukyaConstants.BHAARATHA_BOUGHT_SHARES_LIST)));
		}
		soldSingleScriptList.addAll((Collection<? extends BhaarathaShares>) singleScriptSoldResponseMap
				.get(AnukyaConstants.BHAARATHA_SOLD_SHARES_LIST));
		soldSingleScriptList.add(soldSingleScriptTotal);
		anukyaDataUtils.createOrUpdateFile(soldSingleScriptFile, soldSingleScriptList);

		// Non consolidated
		Map<String, Object> nonConsolidatedSoldResponseMap = getNonConsolidatedSoldShares(purchasedNonConsolidatedList,
				soldBhaarathaShares, soldNonConsolidatedTotal);
		anukyaDataUtils.createOrUpdateFile(purchasedNonConsolidatedFile,
				(nonConsolidatedSoldResponseMap.get(AnukyaConstants.BHAARATHA_BOUGHT_SHARES_LIST)));
		soldNonConsolidatedList.addAll((Collection<? extends BhaarathaShares>) nonConsolidatedSoldResponseMap
				.get(AnukyaConstants.BHAARATHA_SOLD_SHARES_LIST));
		soldNonConsolidatedList.add(soldNonConsolidatedTotal);
		anukyaDataUtils.createOrUpdateFile(soldNonConsolidatedFile, soldNonConsolidatedList);

		// Consolidated
		Map<String, BhaarathaShares> scriptConsolidatedSoldShareMap = soldConsolidatedList.stream()
				.filter(soldConsolidate -> soldConsolidate.getBseScriptCode()
						.equalsIgnoreCase(soldBhaarathaShares.getBseScriptCode()))
				.collect(Collectors.toMap(BhaarathaShares::getStockExchange, i -> i));

		if (scriptConsolidatedSoldShareMap.get(AnukyaConstants.BSE) == null) {
			scriptConsolidatedSoldShareMap.put(AnukyaConstants.BSE, new BhaarathaShares());
		}

		if (scriptConsolidatedSoldShareMap.get(AnukyaConstants.NSE) == null) {
			scriptConsolidatedSoldShareMap.put(AnukyaConstants.NSE, new BhaarathaShares());
		}

		Map<String, Object> consolidatedSoldResponseMap = getConsolidatedSoldShares(purchasedConsolidatedList,
				soldBhaarathaShares,
				(Map<String, BigDecimal>) singleScriptSoldResponseMap
						.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_MAP),
				scriptConsolidatedSoldShareMap, soldConsolidatedTotal);
		anukyaDataUtils.createOrUpdateFile(purchasedConsolidatedFile,
				(consolidatedSoldResponseMap.get(AnukyaConstants.BHAARATHA_BOUGHT_SHARES_LIST)));

		List<BhaarathaShares> soldConsolidatedSharesList = new ArrayList<>();

		Map<String, Boolean> addSoldConsolidatedListByStockExchange = new HashMap<>();

		// To make sure BSE and NSE sold consolidated scripts are added, esp when sold
		// consolidated list is empty or contain only 1 stock exchange.
		addSoldConsolidatedListByStockExchange.put(AnukyaConstants.BSE,
				consolidatedSoldResponseMap.get(AnukyaConstants.BSE) != null);
		addSoldConsolidatedListByStockExchange.put(AnukyaConstants.NSE,
				consolidatedSoldResponseMap.get(AnukyaConstants.NSE) != null);

		for (BhaarathaShares soldConsolidated : soldConsolidatedList) {
			if (soldConsolidated.getBseScriptCode().equalsIgnoreCase(soldBhaarathaShares.getBseScriptCode())
					&& soldConsolidated.getStockExchange().equalsIgnoreCase(AnukyaConstants.BSE)
					&& consolidatedSoldResponseMap.get(AnukyaConstants.BSE) != null) {
				soldConsolidatedSharesList.add((BhaarathaShares) consolidatedSoldResponseMap.get(AnukyaConstants.BSE));
				addSoldConsolidatedListByStockExchange.put(AnukyaConstants.BSE, false);
			} else if (soldConsolidated.getBseScriptCode().equalsIgnoreCase(soldBhaarathaShares.getBseScriptCode())
					&& soldConsolidated.getStockExchange().equalsIgnoreCase(AnukyaConstants.NSE)
					&& consolidatedSoldResponseMap.get(AnukyaConstants.NSE) != null) {
				soldConsolidatedSharesList.add((BhaarathaShares) consolidatedSoldResponseMap.get(AnukyaConstants.NSE));
				addSoldConsolidatedListByStockExchange.put(AnukyaConstants.NSE, false);
			} else {
				soldConsolidatedSharesList.add(soldConsolidated);
			}
		}

		if (Boolean.TRUE.equals(addSoldConsolidatedListByStockExchange.get(AnukyaConstants.BSE))) {
			soldConsolidatedSharesList.add((BhaarathaShares) consolidatedSoldResponseMap.get(AnukyaConstants.BSE));
		}

		if (Boolean.TRUE.equals(addSoldConsolidatedListByStockExchange.get(AnukyaConstants.NSE))) {
			soldConsolidatedSharesList.add((BhaarathaShares) consolidatedSoldResponseMap.get(AnukyaConstants.NSE));
		}

		soldConsolidatedSharesList.add(soldConsolidatedTotal);
		anukyaDataUtils.createOrUpdateFile(soldConsolidatedFile, soldConsolidatedSharesList);

		// Delete main directory
		anukyaDataUtils.deleteDirectory(purchaseMainDirectory);
		anukyaDataUtils.deleteDirectory(soldMainDirectory);

		// Copy from update directory to main directory
		anukyaDataUtils.copyDirectory(purchaseUpdateDirectory, purchaseMainDirectory);
		anukyaDataUtils.copyDirectory(soldUpdateDirectory, soldMainDirectory);

		// Finally delete the update directory
		anukyaDataUtils.deleteDirectory(purchaseUpdateDirectory);
		anukyaDataUtils.deleteDirectory(soldUpdateDirectory);

		commonResponse.setStatus(true);
		commonResponse.setMessage("Shares added successfully");

		return commonResponse;
	}

	@Override
	public BhaarathaSharesResponse getBhaarathaShares(boolean isNonConsolidated, String searchTerm,
			boolean isLongTermOnly) throws AnukyaException {

		List<BhaarathaShares> bhaarathaSharesList;

		if (isNonConsolidated) {
			bhaarathaSharesList = getBhaarathaSharesNonConsolidated(searchTerm);
		} else {
			bhaarathaSharesList = getBhaarathaSharesConsolidated(searchTerm);
		}

		if (bhaarathaSharesList.isEmpty()) {
			throw new AnukyaException("No content for Bhaaratha Shares Short term investment", null,
					AnukyaErrorConstants.NO_CONTENT, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		return calculateBhaarathaShares(bhaarathaSharesList, searchTerm);
	}

	@Override
	public BhaarathaSharesResponse getOneShareDetails(List<BhaarathaShares> bhaarathaSharesList)
			throws AnukyaException {

		return calculateBhaarathaShares(bhaarathaSharesList, AnukyaConstants.EMPTY_STRING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CommonResponse addSharesBulkUpload(BhaarathaShares soldBhaarathaShares) throws AnukyaException {

		CommonResponse commonResponse = new CommonResponse();

		// Checking if script is purchased
		File singleScriptFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ soldBhaarathaShares.getBseScriptCode() + AnukyaConstants.JSON_EXTENSION);

		if (!anukyaDataUtils.isFileExists(singleScriptFile)) {

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0004);
			errorDetails.setErrorMessage("Script is not purchased");
			errorDetailsList.add(errorDetails);

			throw new AnukyaException(ERROR_WHILE_ADDING_SOLD_SHARES, errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		List<BhaarathaShares> singleScriptList = bhaarathaSharesData.getBhaarathaShares(singleScriptFile);
		int existingPurchasedQuantity = singleScriptList.stream()
				.mapToInt(singleScript -> Integer.parseInt(singleScript.getPurchaseQuantity())).sum();

		// Checking if selling more shares than purchased
		if (existingPurchasedQuantity < Integer.parseInt(soldBhaarathaShares.getSellQuantity())) {

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0004);
			errorDetails.setErrorMessage(
					"Trying to sell more shares than purchased for " + soldBhaarathaShares.getScriptName()
							+ ". You currently own " + existingPurchasedQuantity + " number of share/s");
			errorDetailsList.add(errorDetails);

			throw new AnukyaException(ERROR_WHILE_ADDING_SOLD_SHARES, errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		File purchasedSingleScriptFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ soldBhaarathaShares.getBseScriptCode() + AnukyaConstants.JSON_EXTENSION);
		File purchasedNonConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);
		File purchasedConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		File soldSingleScriptFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.BHA_EQ_SOLD_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ soldBhaarathaShares.getBseScriptCode() + AnukyaConstants.JSON_EXTENSION);
		File soldNonConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_SOLD_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);
		File soldConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_SOLD_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		BhaarathaShares soldSingleScriptTotal = new BhaarathaShares();
		BhaarathaShares soldNonConsolidatedTotal = new BhaarathaShares();
		BhaarathaShares soldConsolidatedTotal = new BhaarathaShares();

		List<BhaarathaShares> purchasedSingleScriptList = bhaarathaSharesData
				.getBhaarathaShares(purchasedSingleScriptFile);
		List<BhaarathaShares> soldSingleScriptList = new ArrayList<>();
		if (anukyaDataUtils.isFileExists(soldSingleScriptFile)) {
			soldSingleScriptList = bhaarathaSharesData.getBhaarathaShares(soldSingleScriptFile);
			soldSingleScriptTotal = soldSingleScriptList.get(soldSingleScriptList.size() - 1);
			// Removing the last record for total calculation
			soldSingleScriptList.remove(soldSingleScriptList.size() - 1);
		}

		List<BhaarathaShares> purchasedNonConsolidatedList = bhaarathaSharesData
				.getBhaarathaShares(purchasedNonConsolidatedFile);
		List<BhaarathaShares> soldNonConsolidatedList = bhaarathaSharesData.getBhaarathaShares(soldNonConsolidatedFile);
		if (!soldNonConsolidatedList.isEmpty()) {
			soldNonConsolidatedTotal = soldNonConsolidatedList.get(soldNonConsolidatedList.size() - 1);
			// Removing the last record for total calculation
			soldNonConsolidatedList.remove(soldNonConsolidatedList.size() - 1);
		}

		List<BhaarathaShares> purchasedConsolidatedList = bhaarathaSharesData
				.getBhaarathaShares(purchasedConsolidatedFile);
		List<BhaarathaShares> soldConsolidatedList = bhaarathaSharesData.getBhaarathaShares(soldConsolidatedFile);
		if (!soldConsolidatedList.isEmpty()) {
			soldConsolidatedTotal = soldConsolidatedList.get(soldConsolidatedList.size() - 1);
			// Removing the last record for total calculation
			soldConsolidatedList.remove(soldConsolidatedList.size() - 1);
		}

		// Single script
		Map<String, Object> singleScriptSoldResponseMap = getNonConsolidatedSoldShares(purchasedSingleScriptList,
				soldBhaarathaShares, soldSingleScriptTotal);
		if (((List<BhaarathaShares>) singleScriptSoldResponseMap.get(AnukyaConstants.BHAARATHA_BOUGHT_SHARES_LIST))
				.isEmpty()) {
			anukyaDataUtils.deleteFile(purchasedSingleScriptFile);
		} else {
			anukyaDataUtils.createOrUpdateFile(purchasedSingleScriptFile,
					(singleScriptSoldResponseMap.get(AnukyaConstants.BHAARATHA_BOUGHT_SHARES_LIST)));
		}
		soldSingleScriptList.addAll((Collection<? extends BhaarathaShares>) singleScriptSoldResponseMap
				.get(AnukyaConstants.BHAARATHA_SOLD_SHARES_LIST));
		soldSingleScriptList.add(soldSingleScriptTotal);
		anukyaDataUtils.createOrUpdateFile(soldSingleScriptFile, soldSingleScriptList);

		// Non consolidated
		Map<String, Object> nonConsolidatedSoldResponseMap = getNonConsolidatedSoldShares(purchasedNonConsolidatedList,
				soldBhaarathaShares, soldNonConsolidatedTotal);
		anukyaDataUtils.createOrUpdateFile(purchasedNonConsolidatedFile,
				(nonConsolidatedSoldResponseMap.get(AnukyaConstants.BHAARATHA_BOUGHT_SHARES_LIST)));
		soldNonConsolidatedList.addAll((Collection<? extends BhaarathaShares>) nonConsolidatedSoldResponseMap
				.get(AnukyaConstants.BHAARATHA_SOLD_SHARES_LIST));
		soldNonConsolidatedList.add(soldNonConsolidatedTotal);
		anukyaDataUtils.createOrUpdateFile(soldNonConsolidatedFile, soldNonConsolidatedList);

		// Consolidated
		Map<String, BhaarathaShares> scriptConsolidatedSoldShareMap = soldConsolidatedList.stream()
				.filter(soldConsolidate -> soldConsolidate.getBseScriptCode()
						.equalsIgnoreCase(soldBhaarathaShares.getBseScriptCode()))
				.collect(Collectors.toMap(BhaarathaShares::getStockExchange, i -> i));

		if (scriptConsolidatedSoldShareMap.get(AnukyaConstants.BSE) == null) {
			scriptConsolidatedSoldShareMap.put(AnukyaConstants.BSE, new BhaarathaShares());
		}

		if (scriptConsolidatedSoldShareMap.get(AnukyaConstants.NSE) == null) {
			scriptConsolidatedSoldShareMap.put(AnukyaConstants.NSE, new BhaarathaShares());
		}

		Map<String, Object> consolidatedSoldResponseMap = getConsolidatedSoldShares(purchasedConsolidatedList,
				soldBhaarathaShares,
				(Map<String, BigDecimal>) singleScriptSoldResponseMap
						.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_MAP),
				scriptConsolidatedSoldShareMap, soldConsolidatedTotal);
		anukyaDataUtils.createOrUpdateFile(purchasedConsolidatedFile,
				(consolidatedSoldResponseMap.get(AnukyaConstants.BHAARATHA_BOUGHT_SHARES_LIST)));

		List<BhaarathaShares> soldConsolidatedSharesList = new ArrayList<>();

		Map<String, Boolean> addSoldConsolidatedListByStockExchange = new HashMap<>();

		// To make sure BSE and NSE sold consolidated scripts are added, esp when sold
		// consolidated list is empty or contain only 1 stock exchange.
		addSoldConsolidatedListByStockExchange.put(AnukyaConstants.BSE,
				consolidatedSoldResponseMap.get(AnukyaConstants.BSE) != null);
		addSoldConsolidatedListByStockExchange.put(AnukyaConstants.NSE,
				consolidatedSoldResponseMap.get(AnukyaConstants.NSE) != null);

		for (BhaarathaShares soldConsolidated : soldConsolidatedList) {
			if (soldConsolidated.getBseScriptCode().equalsIgnoreCase(soldBhaarathaShares.getBseScriptCode())
					&& soldConsolidated.getStockExchange().equalsIgnoreCase(AnukyaConstants.BSE)
					&& consolidatedSoldResponseMap.get(AnukyaConstants.BSE) != null) {
				soldConsolidatedSharesList.add((BhaarathaShares) consolidatedSoldResponseMap.get(AnukyaConstants.BSE));
				addSoldConsolidatedListByStockExchange.put(AnukyaConstants.BSE, false);
			} else if (soldConsolidated.getBseScriptCode().equalsIgnoreCase(soldBhaarathaShares.getBseScriptCode())
					&& soldConsolidated.getStockExchange().equalsIgnoreCase(AnukyaConstants.NSE)
					&& consolidatedSoldResponseMap.get(AnukyaConstants.NSE) != null) {
				soldConsolidatedSharesList.add((BhaarathaShares) consolidatedSoldResponseMap.get(AnukyaConstants.NSE));
				addSoldConsolidatedListByStockExchange.put(AnukyaConstants.NSE, false);
			} else {
				soldConsolidatedSharesList.add(soldConsolidated);
			}
		}

		if (Boolean.TRUE.equals(addSoldConsolidatedListByStockExchange.get(AnukyaConstants.BSE))) {
			soldConsolidatedSharesList.add((BhaarathaShares) consolidatedSoldResponseMap.get(AnukyaConstants.BSE));
		}

		if (Boolean.TRUE.equals(addSoldConsolidatedListByStockExchange.get(AnukyaConstants.NSE))) {
			soldConsolidatedSharesList.add((BhaarathaShares) consolidatedSoldResponseMap.get(AnukyaConstants.NSE));
		}

		soldConsolidatedSharesList.add(soldConsolidatedTotal);
		anukyaDataUtils.createOrUpdateFile(soldConsolidatedFile, soldConsolidatedSharesList);

		commonResponse.setStatus(true);
		commonResponse.setMessage("Shares added successfully");

		return commonResponse;
	}

	// Private methods
	private Map<String, Object> getNonConsolidatedSoldShares(List<BhaarathaShares> purchaseBhaarathaSharesList,
			BhaarathaShares soldBhaarathaShares, BhaarathaShares soldTotal) throws AnukyaException {

		List<BhaarathaShares> boughtSharesList = new ArrayList<>();
		List<BhaarathaShares> soldSharesList = new ArrayList<>();

		Map<String, BigDecimal> consolidatedCalculationMap = new HashMap<>();
		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_QUANTITY
				+ AnukyaConstants.UNDERSCORE + AnukyaConstants.BSE, new BigDecimal(AnukyaConstants.NUMBER_0));
		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_QUANTITY
				+ AnukyaConstants.UNDERSCORE + AnukyaConstants.NSE, new BigDecimal(AnukyaConstants.NUMBER_0));

		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_PRICE
				+ AnukyaConstants.UNDERSCORE + AnukyaConstants.BSE, new BigDecimal(AnukyaConstants.NUMBER_0));
		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_PRICE
				+ AnukyaConstants.UNDERSCORE + AnukyaConstants.NSE, new BigDecimal(AnukyaConstants.NUMBER_0));

		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_GROSS_TOTAL
				+ AnukyaConstants.UNDERSCORE + AnukyaConstants.BSE, new BigDecimal(AnukyaConstants.NUMBER_0));
		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_GROSS_TOTAL
				+ AnukyaConstants.UNDERSCORE + AnukyaConstants.NSE, new BigDecimal(AnukyaConstants.NUMBER_0));

		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_BROKERAGE
				+ AnukyaConstants.UNDERSCORE + AnukyaConstants.BSE, new BigDecimal(AnukyaConstants.NUMBER_0));
		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_BROKERAGE
				+ AnukyaConstants.UNDERSCORE + AnukyaConstants.NSE, new BigDecimal(AnukyaConstants.NUMBER_0));

		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_STT
				+ AnukyaConstants.UNDERSCORE + AnukyaConstants.BSE, new BigDecimal(AnukyaConstants.NUMBER_0));
		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_STT
				+ AnukyaConstants.UNDERSCORE + AnukyaConstants.NSE, new BigDecimal(AnukyaConstants.NUMBER_0));

		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_EXPENDITURE
				+ AnukyaConstants.UNDERSCORE + AnukyaConstants.BSE, new BigDecimal(AnukyaConstants.NUMBER_0));
		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_EXPENDITURE
				+ AnukyaConstants.UNDERSCORE + AnukyaConstants.NSE, new BigDecimal(AnukyaConstants.NUMBER_0));

		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_NON_EXPENDITURE
				+ AnukyaConstants.UNDERSCORE + AnukyaConstants.BSE, new BigDecimal(AnukyaConstants.NUMBER_0));
		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_NON_EXPENDITURE
				+ AnukyaConstants.UNDERSCORE + AnukyaConstants.NSE, new BigDecimal(AnukyaConstants.NUMBER_0));

		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_TOTAL
				+ AnukyaConstants.UNDERSCORE + AnukyaConstants.BSE, new BigDecimal(AnukyaConstants.NUMBER_0));
		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_TOTAL
				+ AnukyaConstants.UNDERSCORE + AnukyaConstants.NSE, new BigDecimal(AnukyaConstants.NUMBER_0));

		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_NET_PRICE_PER_UNIT
				+ AnukyaConstants.UNDERSCORE + AnukyaConstants.BSE, new BigDecimal(AnukyaConstants.NUMBER_0));
		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_NET_PRICE_PER_UNIT
				+ AnukyaConstants.UNDERSCORE + AnukyaConstants.NSE, new BigDecimal(AnukyaConstants.NUMBER_0));

		consolidatedCalculationMap.put(AnukyaConstants.BSE, new BigDecimal(AnukyaConstants.NUMBER_0));
		consolidatedCalculationMap.put(AnukyaConstants.NSE, new BigDecimal(AnukyaConstants.NUMBER_0));

		String dateInddMmmYyyy = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);
		BigDecimal sellingCount = new BigDecimal(soldBhaarathaShares.getSellQuantity());

		for (BhaarathaShares bhaarathaShares : purchaseBhaarathaSharesList) {
			if (bhaarathaShares.getBseScriptCode().equalsIgnoreCase(soldBhaarathaShares.getBseScriptCode())) {
				if (new BigDecimal(bhaarathaShares.getPurchaseQuantity()).intValue() <= sellingCount.intValue()) {
					sellingCount = sellingCount.subtract(new BigDecimal(bhaarathaShares.getPurchaseQuantity()));

					BigDecimal sellQuantity = new BigDecimal(bhaarathaShares.getPurchaseQuantity());

					buildNonConsolidatedSellShares(soldBhaarathaShares, bhaarathaShares, sellQuantity, soldTotal);

					bhaarathaShares.setCreatedBy(bhaarathaShares.getCreatedBy());
					bhaarathaShares.setCreatedDate(bhaarathaShares.getCreatedDate());
					bhaarathaShares.setLastUpdatedBy(userEmailId);
					bhaarathaShares.setLastUpdatedDate(dateInddMmmYyyy);

					soldSharesList.add(bhaarathaShares);

					getDetailsForConsolidatedSoldShares(bhaarathaShares, consolidatedCalculationMap);

					BigDecimal soldQuantityCount = consolidatedCalculationMap
							.get(bhaarathaShares.getStockExchange().toUpperCase()).add(sellQuantity);
					consolidatedCalculationMap.put(bhaarathaShares.getStockExchange().toUpperCase(), soldQuantityCount);

				} else if (sellingCount.intValue() != 0) {

					BigDecimal existingPurchaseQuantity = new BigDecimal(bhaarathaShares.getPurchaseQuantity());
					BigDecimal updatedPurchaseQuantity = existingPurchaseQuantity.subtract(sellingCount);
					BigDecimal sellQuantity = sellingCount;

					// Adjusting bought shares when sell quantity is less than purchase quantity
					BhaarathaShares temp = new BhaarathaShares();
					temp.setId(bhaarathaShares.getId());
					temp.setStockExchange(bhaarathaShares.getStockExchange());
					temp.setBseScriptCode(bhaarathaShares.getBseScriptCode());
					temp.setNseScriptCode(bhaarathaShares.getNseScriptCode());
					temp.setYahooBseScriptCode(bhaarathaShares.getYahooBseScriptCode());
					temp.setYahooNseScriptCode(bhaarathaShares.getYahooNseScriptCode());
					temp.setIsinCode(bhaarathaShares.getIsinCode());
					temp.setMoneycontrolCode(bhaarathaShares.getMoneycontrolCode());
					temp.setScriptName(bhaarathaShares.getScriptName());
					temp.setCategory(bhaarathaShares.getCategory());
					temp.setSector(bhaarathaShares.getSector());
					temp.setIndustry(bhaarathaShares.getIndustry());
					temp.setShortTermInvestment(bhaarathaShares.isShortTermInvestment());
					temp.setSharesSplitSelected(bhaarathaShares.isSharesSplitSelected());
					temp.setSharesSplitRatio(bhaarathaShares.getSharesSplitRatio());
					temp.setSharesSplitDate(bhaarathaShares.getSharesSplitDate());
					temp.setSharesSplit(bhaarathaShares.getSharesSplit());
					temp.setSharesSplitDisplay(bhaarathaShares.getSharesSplitDisplay());
					temp.setSharesBonusSelected(bhaarathaShares.isSharesBonusSelected());
					temp.setSharesBonusRatio(bhaarathaShares.getSharesBonusRatio());
					temp.setSharesBonusDate(bhaarathaShares.getSharesBonusDate());
					temp.setSharesBonus(bhaarathaShares.getSharesBonus());
					temp.setSharesBonusDisplay(bhaarathaShares.getSharesBonusDisplay());
					temp.setPurchaseDate(bhaarathaShares.getPurchaseDate());
					temp.setPurchaseQuantity(String.valueOf(updatedPurchaseQuantity));
					temp.setPurchasePrice(bhaarathaShares.getPurchasePrice());

					BigDecimal updatedPurchaseGrossTotal = updatedPurchaseQuantity
							.multiply(new BigDecimal(bhaarathaShares.getPurchasePrice()));
					temp.setPurchaseGrossTotal(String.valueOf(updatedPurchaseGrossTotal));

					BigDecimal existingPurchaseBrokerage = new BigDecimal(bhaarathaShares.getPurchaseBrokerage());
					BigDecimal updatedPurchaseBrokerage = existingPurchaseBrokerage.multiply(existingPurchaseQuantity
							.subtract(sellingCount).divide(existingPurchaseQuantity, MathContext.DECIMAL128));
					temp.setPurchaseBrokerage(String.valueOf(updatedPurchaseBrokerage));

					BigDecimal existingPurchaseSTT = new BigDecimal(bhaarathaShares.getPurchaseSTT());
					BigDecimal updatedPurchaseSTT = existingPurchaseSTT.multiply(existingPurchaseQuantity
							.subtract(sellingCount).divide(existingPurchaseQuantity, MathContext.DECIMAL128));
					temp.setPurchaseSTT(String.valueOf(updatedPurchaseSTT));

					BigDecimal existingPurchaseExpenditure = new BigDecimal(bhaarathaShares.getPurchaseExpenditure());
					BigDecimal updatedPurchaseExpenditure = existingPurchaseExpenditure
							.multiply(existingPurchaseQuantity.subtract(sellingCount).divide(existingPurchaseQuantity,
									MathContext.DECIMAL128));
					temp.setPurchaseExpenditure(String.valueOf(updatedPurchaseExpenditure));

					BigDecimal existingPurchaseNonExpenditure = new BigDecimal(
							bhaarathaShares.getPurchaseNonExpenditure());
					BigDecimal updatedPurchaseNonExpenditure = existingPurchaseNonExpenditure
							.multiply(existingPurchaseQuantity.subtract(sellingCount).divide(existingPurchaseQuantity,
									MathContext.DECIMAL128));
					temp.setPurchaseNonExpenditure(String.valueOf(updatedPurchaseNonExpenditure));

					BigDecimal updatedPurchaseTotal = updatedPurchaseGrossTotal.add(updatedPurchaseBrokerage)
							.add(updatedPurchaseSTT).add(updatedPurchaseExpenditure).add(updatedPurchaseNonExpenditure);
					temp.setPurchaseTotal(String.valueOf(updatedPurchaseTotal));

					BigDecimal updatedPurchaseNetPricePerUnit = updatedPurchaseTotal.divide(updatedPurchaseQuantity,
							MathContext.DECIMAL128);
					temp.setPurchaseNetPricePerUnit(String.valueOf(updatedPurchaseNetPricePerUnit));

					temp.setShortTermInvestment(bhaarathaShares.isShortTermInvestment());
					temp.setShortTerm(bhaarathaShares.isShortTerm());
					temp.setCreatedBy(bhaarathaShares.getCreatedBy());
					temp.setCreatedDate(bhaarathaShares.getCreatedDate());
					temp.setLastUpdatedBy(userEmailId);
					temp.setLastUpdatedDate(dateInddMmmYyyy);

					boughtSharesList.add(temp);

					// Updating sell shares
					BigDecimal purchaseGrossTotal = sellQuantity
							.multiply(new BigDecimal(bhaarathaShares.getPurchasePrice()));
					bhaarathaShares.setPurchaseGrossTotal(String.valueOf(purchaseGrossTotal));

					BigDecimal purchaseBrokerage = new BigDecimal(bhaarathaShares.getPurchaseBrokerage())
							.multiply(sellQuantity.divide(new BigDecimal(bhaarathaShares.getPurchaseQuantity()),
									MathContext.DECIMAL128));
					bhaarathaShares.setPurchaseBrokerage(String.valueOf(purchaseBrokerage));

					BigDecimal purchaseSTT = new BigDecimal(bhaarathaShares.getPurchaseSTT()).multiply(sellQuantity
							.divide(new BigDecimal(bhaarathaShares.getPurchaseQuantity()), MathContext.DECIMAL128));
					bhaarathaShares.setPurchaseSTT(String.valueOf(purchaseSTT));

					BigDecimal purchaseExpenditure = new BigDecimal(bhaarathaShares.getPurchaseExpenditure())
							.multiply(sellQuantity.divide(new BigDecimal(bhaarathaShares.getPurchaseQuantity()),
									MathContext.DECIMAL128));
					bhaarathaShares.setPurchaseExpenditure(String.valueOf(purchaseExpenditure));

					BigDecimal purchaseNonExpenditure = new BigDecimal(bhaarathaShares.getPurchaseNonExpenditure())
							.multiply(sellQuantity.divide(new BigDecimal(bhaarathaShares.getPurchaseQuantity()),
									MathContext.DECIMAL128));
					bhaarathaShares.setPurchaseNonExpenditure(String.valueOf(purchaseNonExpenditure));

					BigDecimal purchaseTotal = purchaseGrossTotal.add(purchaseBrokerage).add(purchaseSTT)
							.add(purchaseExpenditure).add(purchaseNonExpenditure);
					bhaarathaShares.setPurchaseTotal(String.valueOf(purchaseTotal));

					BigDecimal purchaseNetPricePerUnit = purchaseTotal.divide(sellQuantity, MathContext.DECIMAL128);
					bhaarathaShares.setPurchaseNetPricePerUnit(String.valueOf(purchaseNetPricePerUnit));

					bhaarathaShares.setPurchaseQuantity(String.valueOf(sellQuantity));

					buildNonConsolidatedSellShares(soldBhaarathaShares, bhaarathaShares, sellQuantity, soldTotal);

					bhaarathaShares.setCreatedBy(bhaarathaShares.getCreatedBy());
					bhaarathaShares.setCreatedDate(bhaarathaShares.getCreatedDate());
					bhaarathaShares.setLastUpdatedBy(userEmailId);
					bhaarathaShares.setLastUpdatedDate(dateInddMmmYyyy);

					soldSharesList.add(bhaarathaShares);

					getDetailsForConsolidatedSoldShares(bhaarathaShares, consolidatedCalculationMap);

					sellingCount = new BigDecimal(AnukyaConstants.NUMBER_0);

					BigDecimal soldQuantityCount = consolidatedCalculationMap
							.get(bhaarathaShares.getStockExchange().toUpperCase()).add(sellQuantity);
					consolidatedCalculationMap.put(bhaarathaShares.getStockExchange().toUpperCase(), soldQuantityCount);

				} else {
					boughtSharesList.add(bhaarathaShares);
				}
			} else {
				boughtSharesList.add(bhaarathaShares);
			}
		}

		Map<String, Object> nonConsolidatedSoldSharesMap = new HashMap<>();
		nonConsolidatedSoldSharesMap.put(AnukyaConstants.BHAARATHA_BOUGHT_SHARES_LIST, boughtSharesList);
		nonConsolidatedSoldSharesMap.put(AnukyaConstants.BHAARATHA_SOLD_SHARES_LIST, soldSharesList);
		nonConsolidatedSoldSharesMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_MAP,
				consolidatedCalculationMap);

		return nonConsolidatedSoldSharesMap;
	}

	private void buildNonConsolidatedSellShares(BhaarathaShares soldBhaarathaShares, BhaarathaShares bhaarathaShares,
			BigDecimal sellQuantity, BhaarathaShares soldTotal) throws AnukyaException {

		BigDecimal totalSoldQuantity = new BigDecimal(soldBhaarathaShares.getSellQuantity());

		bhaarathaShares.setId(anukyaUtils.generateId());

		BigDecimal purchaseQuantity = new BigDecimal(bhaarathaShares.getPurchaseQuantity());
		BigDecimal existingTotalPurchaseQuantity = new BigDecimal(soldTotal.getPurchaseQuantity());
		BigDecimal totalPurchaseQuantity = purchaseQuantity.add(existingTotalPurchaseQuantity);
		soldTotal.setPurchaseQuantity(String.valueOf(totalPurchaseQuantity));

		BigDecimal purchasePrice = new BigDecimal(bhaarathaShares.getPurchasePrice());
		BigDecimal existingTotalPurchasePrice = new BigDecimal(soldTotal.getPurchasePrice());
		BigDecimal totalPurchasePrice = purchaseQuantity.multiply(purchasePrice)
				.add(existingTotalPurchaseQuantity.multiply(existingTotalPurchasePrice))
				.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
		soldTotal.setPurchasePrice(String.valueOf(totalPurchasePrice));

		BigDecimal purchaseGrossTotal = new BigDecimal(bhaarathaShares.getPurchaseGrossTotal());
		BigDecimal existingTotalPurchaseGrossTotal = new BigDecimal(soldTotal.getPurchaseGrossTotal());
		BigDecimal totalPurchaseGrossTotal = purchaseGrossTotal.add(existingTotalPurchaseGrossTotal);
		soldTotal.setPurchaseGrossTotal(String.valueOf(totalPurchaseGrossTotal));

		BigDecimal purchaseBrokerage = new BigDecimal(bhaarathaShares.getPurchaseBrokerage());
		BigDecimal existingTotalPurchaseBrokerage = new BigDecimal(soldTotal.getPurchaseBrokerage());
		BigDecimal totalPurchaseBrokerage = purchaseBrokerage.add(existingTotalPurchaseBrokerage);
		soldTotal.setPurchaseBrokerage(String.valueOf(totalPurchaseBrokerage));

		BigDecimal purchaseSTT = new BigDecimal(bhaarathaShares.getPurchaseSTT());
		BigDecimal existingTotalPurchaseSTT = new BigDecimal(soldTotal.getPurchaseSTT());
		BigDecimal totalPurchaseSTT = purchaseSTT.add(existingTotalPurchaseSTT);
		soldTotal.setPurchaseSTT(String.valueOf(totalPurchaseSTT));

		BigDecimal purchaseExpenditure = new BigDecimal(bhaarathaShares.getPurchaseExpenditure());
		BigDecimal existingTotalPurchaseExpenditure = new BigDecimal(soldTotal.getPurchaseExpenditure());
		BigDecimal totalPurchaseExpenditure = purchaseExpenditure.add(existingTotalPurchaseExpenditure);
		soldTotal.setPurchaseExpenditure(String.valueOf(totalPurchaseExpenditure));

		BigDecimal purchaseNonExpenditure = new BigDecimal(bhaarathaShares.getPurchaseNonExpenditure());
		BigDecimal existingTotalPurchaseNonExpenditure = new BigDecimal(soldTotal.getPurchaseNonExpenditure());
		BigDecimal totalPurchaseNonExpenditure = purchaseNonExpenditure.add(existingTotalPurchaseNonExpenditure);
		soldTotal.setPurchaseNonExpenditure(String.valueOf(totalPurchaseNonExpenditure));

		BigDecimal purchaseTotal = new BigDecimal(bhaarathaShares.getPurchaseTotal());
		BigDecimal existingTotalPurchaseTotal = new BigDecimal(soldTotal.getPurchaseTotal());
		BigDecimal totalPurchaseTotal = purchaseTotal.add(existingTotalPurchaseTotal);
		soldTotal.setPurchaseTotal(String.valueOf(totalPurchaseTotal));

		BigDecimal purchaseNetPricePerUnit = new BigDecimal(bhaarathaShares.getPurchaseNetPricePerUnit());
		BigDecimal existingTotalPurchaseNetPricePerUnit = new BigDecimal(soldTotal.getPurchaseNetPricePerUnit());
		BigDecimal totalPurchaseNetPricePerUnit = purchaseQuantity.multiply(purchaseNetPricePerUnit)
				.add(existingTotalPurchaseQuantity.multiply(existingTotalPurchaseNetPricePerUnit))
				.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
		soldTotal.setPurchaseNetPricePerUnit(String.valueOf(totalPurchaseNetPricePerUnit));

		bhaarathaShares.setSellDate(soldBhaarathaShares.getSellDate());

		bhaarathaShares.setSellQuantity(String.valueOf(sellQuantity));
		BigDecimal existingTotalSellQuantity = new BigDecimal(soldTotal.getSellQuantity());
		BigDecimal totalSellQuantity = sellQuantity.add(existingTotalSellQuantity);
		soldTotal.setSellQuantity(String.valueOf(totalSellQuantity));

		BigDecimal sellPrice = new BigDecimal(soldBhaarathaShares.getSellPrice());
		bhaarathaShares.setSellPrice(String.valueOf(sellPrice));
		BigDecimal existingTotalSellPrice = new BigDecimal(soldTotal.getSellPrice());
		BigDecimal totalSellPrice = sellQuantity.multiply(sellPrice)
				.add(existingTotalSellQuantity.multiply(existingTotalSellPrice))
				.divide(totalSellQuantity, MathContext.DECIMAL128);
		soldTotal.setSellPrice(String.valueOf(totalSellPrice));

		BigDecimal sellGrossTotal = sellQuantity.multiply(sellPrice);
		bhaarathaShares.setSellGrossTotal(String.valueOf(sellGrossTotal));
		BigDecimal existingTotalSellGrossTotal = new BigDecimal(soldTotal.getSellGrossTotal());
		BigDecimal totalSellGrossTotal = sellGrossTotal.add(existingTotalSellGrossTotal);
		soldTotal.setSellGrossTotal(String.valueOf(totalSellGrossTotal));

		BigDecimal sellBrokerage = new BigDecimal(soldBhaarathaShares.getSellBrokerage()).multiply(sellQuantity)
				.divide(totalSoldQuantity, MathContext.DECIMAL128);
		bhaarathaShares.setSellBrokerage(String.valueOf(sellBrokerage));
		BigDecimal existingTotalSellBrokerage = new BigDecimal(soldTotal.getSellBrokerage());
		BigDecimal totalSellBrokerage = sellBrokerage.add(existingTotalSellBrokerage);
		soldTotal.setSellBrokerage(String.valueOf(totalSellBrokerage));

		BigDecimal sellSTT = new BigDecimal(soldBhaarathaShares.getSellSTT()).multiply(sellQuantity)
				.divide(totalSoldQuantity, MathContext.DECIMAL128);
		bhaarathaShares.setSellSTT(String.valueOf(sellSTT));
		BigDecimal existingTotalSellSTT = new BigDecimal(soldTotal.getSellSTT());
		BigDecimal totalSellSTT = sellSTT.add(existingTotalSellSTT);
		soldTotal.setSellSTT(String.valueOf(totalSellSTT));

		BigDecimal sellExpenditure = new BigDecimal(soldBhaarathaShares.getSellExpenditure()).multiply(sellQuantity)
				.divide(totalSoldQuantity, MathContext.DECIMAL128);
		bhaarathaShares.setSellExpenditure(String.valueOf(sellExpenditure));
		BigDecimal existingTotalSellExpenditure = new BigDecimal(soldTotal.getSellExpenditure());
		BigDecimal totalSellExpenditure = sellExpenditure.add(existingTotalSellExpenditure);
		soldTotal.setSellExpenditure(String.valueOf(totalSellExpenditure));

		BigDecimal sellNonExpenditure = new BigDecimal(soldBhaarathaShares.getSellNonExpenditure())
				.multiply(sellQuantity).divide(totalSoldQuantity, MathContext.DECIMAL128);
		bhaarathaShares.setSellNonExpenditure(String.valueOf(sellNonExpenditure));
		BigDecimal existingTotalSellNonExpenditure = new BigDecimal(soldTotal.getSellNonExpenditure());
		BigDecimal totalSellNonExpenditure = sellNonExpenditure.add(existingTotalSellNonExpenditure);
		soldTotal.setSellNonExpenditure(String.valueOf(totalSellNonExpenditure));

		BigDecimal sellTotal = sellGrossTotal.subtract(sellBrokerage).subtract(sellSTT).subtract(sellExpenditure)
				.subtract(sellNonExpenditure);
		bhaarathaShares.setSellTotal(String.valueOf(sellTotal));
		BigDecimal existingTotalSellTotal = new BigDecimal(soldTotal.getSellTotal());
		BigDecimal totalSellTotal = sellTotal.add(existingTotalSellTotal);
		soldTotal.setSellTotal(String.valueOf(totalSellTotal));

		BigDecimal sellNetPricePerUnit = sellTotal.divide(sellQuantity, MathContext.DECIMAL128);
		bhaarathaShares.setSellNetPricePerUnit(String.valueOf(sellNetPricePerUnit));
		BigDecimal existingTotalSellNetPricePerUnit = new BigDecimal(soldTotal.getSellNetPricePerUnit());
		BigDecimal totalSellNetPricePerUnit = sellQuantity.multiply(sellNetPricePerUnit)
				.add(existingTotalSellQuantity.multiply(existingTotalSellNetPricePerUnit))
				.divide(totalSellQuantity, MathContext.DECIMAL128);
		soldTotal.setSellNetPricePerUnit(String.valueOf(totalSellNetPricePerUnit));

		bhaarathaShares.setShortTerm(
				bhaarathaSharesUtils.isShortTerm(bhaarathaShares.getPurchaseDate(), bhaarathaShares.getSellDate()));

		BigDecimal grossProfitLoss = sellGrossTotal.subtract(purchaseGrossTotal);
		bhaarathaShares.setGrossProfitLoss(String.valueOf(grossProfitLoss));
		BigDecimal totalGrossProfitLoss = totalSellGrossTotal.subtract(totalPurchaseGrossTotal);
		soldTotal.setGrossProfitLoss(String.valueOf(totalGrossProfitLoss));

		BigDecimal grossProfitLossPercentage;
		try {
			grossProfitLossPercentage = sellGrossTotal.divide(purchaseGrossTotal, MathContext.DECIMAL128)
					.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
					.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
		} catch (ArithmeticException e) {
			grossProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_0);
		}
		bhaarathaShares.setGrossProfitLossPercentage(String.valueOf(grossProfitLossPercentage));

		BigDecimal totalGrossProfitLossPercentage;
		try {
			totalGrossProfitLossPercentage = totalSellGrossTotal.divide(totalPurchaseGrossTotal, MathContext.DECIMAL128)
					.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
					.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
		} catch (ArithmeticException e) {
			totalGrossProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_0);
		}
		soldTotal.setGrossProfitLossPercentage(String.valueOf(totalGrossProfitLossPercentage));

		BigDecimal totalProfitLoss = sellTotal.subtract(new BigDecimal(bhaarathaShares.getPurchaseTotal()));
		bhaarathaShares.setTotalProfitLoss(String.valueOf(totalProfitLoss));
		BigDecimal totalTotalProfitLoss = totalSellTotal.subtract(totalPurchaseTotal);
		soldTotal.setTotalProfitLoss(String.valueOf(totalTotalProfitLoss));

		BigDecimal totalProfitLossPercentage;
		try {
			totalProfitLossPercentage = sellTotal
					.divide(new BigDecimal(bhaarathaShares.getPurchaseTotal()), MathContext.DECIMAL128)
					.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
					.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
		} catch (ArithmeticException e) {
			totalProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_0);
		}
		bhaarathaShares.setTotalProfitLossPercentage(String.valueOf(totalProfitLossPercentage));

		BigDecimal totalTotalProfitLossPercentage;
		try {
			totalTotalProfitLossPercentage = totalSellTotal.divide(totalPurchaseTotal, MathContext.DECIMAL128)
					.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
					.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
		} catch (ArithmeticException e) {
			totalTotalProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_0);
		}
		soldTotal.setTotalProfitLossPercentage(String.valueOf(totalTotalProfitLossPercentage));

		BigDecimal netProfitLossPerUnit = sellNetPricePerUnit
				.subtract(new BigDecimal(bhaarathaShares.getPurchaseNetPricePerUnit()));
		bhaarathaShares.setNetProfitLossPerUnit(String.valueOf(netProfitLossPerUnit));
		BigDecimal totalNetProfitLossPerUnit = totalSellNetPricePerUnit.subtract(totalPurchaseNetPricePerUnit);
		soldTotal.setNetProfitLossPerUnit(String.valueOf(totalNetProfitLossPerUnit));

	}

	private void getDetailsForConsolidatedSoldShares(BhaarathaShares bhaarathaShares,
			Map<String, BigDecimal> consolidatedCalculationMap) {

		BigDecimal existingTotalPurchaseQuantity = consolidatedCalculationMap
				.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_QUANTITY + AnukyaConstants.UNDERSCORE
						+ bhaarathaShares.getStockExchange().toUpperCase());
		BigDecimal purchaseQuantity = new BigDecimal(bhaarathaShares.getPurchaseQuantity());
		BigDecimal totalPurchaseQuantity = existingTotalPurchaseQuantity.add(purchaseQuantity);
		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_QUANTITY
				+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase(), totalPurchaseQuantity);

		BigDecimal existingTotalPurchasePrice = consolidatedCalculationMap
				.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_PRICE + AnukyaConstants.UNDERSCORE
						+ bhaarathaShares.getStockExchange().toUpperCase());
		BigDecimal purchasePrice = new BigDecimal(bhaarathaShares.getPurchasePrice());
		BigDecimal totalPurchasePrice = existingTotalPurchaseQuantity.multiply(existingTotalPurchasePrice)
				.add(purchaseQuantity.multiply(purchasePrice)).divide(totalPurchaseQuantity, MathContext.DECIMAL128);
		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_PRICE
				+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase(), totalPurchasePrice);

		BigDecimal existingTotalPurchaseGrossTotal = consolidatedCalculationMap
				.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_GROSS_TOTAL
						+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase());
		BigDecimal purchaseGrossTotal = new BigDecimal(bhaarathaShares.getPurchaseGrossTotal());
		BigDecimal totalPurchaseGrossTotal = existingTotalPurchaseGrossTotal.add(purchaseGrossTotal);
		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_GROSS_TOTAL
				+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase(),
				totalPurchaseGrossTotal);

		BigDecimal existingTotalPurchaseBrokerage = consolidatedCalculationMap
				.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_BROKERAGE + AnukyaConstants.UNDERSCORE
						+ bhaarathaShares.getStockExchange().toUpperCase());
		BigDecimal purchaseBrokerage = new BigDecimal(bhaarathaShares.getPurchaseBrokerage());
		BigDecimal totalPurchaseBrokerage = existingTotalPurchaseBrokerage.add(purchaseBrokerage);
		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_BROKERAGE
				+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase(),
				totalPurchaseBrokerage);

		BigDecimal existingTotalPurchaseSTT = consolidatedCalculationMap
				.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_STT + AnukyaConstants.UNDERSCORE
						+ bhaarathaShares.getStockExchange().toUpperCase());
		BigDecimal purchaseSTT = new BigDecimal(bhaarathaShares.getPurchaseSTT());
		BigDecimal totalPurchaseSTT = existingTotalPurchaseSTT.add(purchaseSTT);
		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_STT
				+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase(), totalPurchaseSTT);

		BigDecimal existingTotalPurchaseExpenditure = consolidatedCalculationMap
				.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_EXPENDITURE
						+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase());
		BigDecimal purchaseExpenditure = new BigDecimal(bhaarathaShares.getPurchaseExpenditure());
		BigDecimal totalPurchaseExpenditure = existingTotalPurchaseExpenditure.add(purchaseExpenditure);
		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_EXPENDITURE
				+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase(),
				totalPurchaseExpenditure);

		BigDecimal existingTotalPurchaseNonExpenditure = consolidatedCalculationMap
				.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_NON_EXPENDITURE
						+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase());
		BigDecimal purchaseNonExpenditure = new BigDecimal(bhaarathaShares.getPurchaseNonExpenditure());
		BigDecimal totalPurchaseNonExpenditure = existingTotalPurchaseNonExpenditure.add(purchaseNonExpenditure);
		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_NON_EXPENDITURE
				+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase(),
				totalPurchaseNonExpenditure);

		BigDecimal existingTotalPurchaseTotal = consolidatedCalculationMap
				.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_TOTAL + AnukyaConstants.UNDERSCORE
						+ bhaarathaShares.getStockExchange().toUpperCase());
		BigDecimal purchaseTotal = new BigDecimal(bhaarathaShares.getPurchaseTotal());
		BigDecimal totalPurchaseTotal = existingTotalPurchaseTotal.add(purchaseTotal);
		consolidatedCalculationMap.put(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_TOTAL
				+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase(), totalPurchaseTotal);

		BigDecimal existingTotalPurchaseNetPricePerUnit = consolidatedCalculationMap
				.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_NET_PRICE_PER_UNIT
						+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase());
		BigDecimal purchaseNetPricePerUnit = new BigDecimal(bhaarathaShares.getPurchaseNetPricePerUnit());
		BigDecimal totalPurchaseNetPricePerUnit = existingTotalPurchaseQuantity
				.multiply(existingTotalPurchaseNetPricePerUnit).add(purchaseQuantity.multiply(purchaseNetPricePerUnit))
				.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
		consolidatedCalculationMap.put(
				AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_NET_PRICE_PER_UNIT
						+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase(),
				totalPurchaseNetPricePerUnit);
	}

	private Map<String, Object> getConsolidatedSoldShares(List<BhaarathaShares> purchaseBhaarathaSharesList,
			BhaarathaShares soldBhaarathaShares, Map<String, BigDecimal> bhaarathaConsolidatedCalculationMap,
			Map<String, BhaarathaShares> scriptConsolidatedSoldShareMap, BhaarathaShares soldTotal) {

		Map<String, Object> consolidatedSoldSharesMap = new HashMap<>();

		List<BhaarathaShares> boughtSharesList = new ArrayList<>();

		String dateInddMmmYyyy = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		for (BhaarathaShares bhaarathaShares : purchaseBhaarathaSharesList) {
			if (bhaarathaShares.getBseScriptCode().equalsIgnoreCase(soldBhaarathaShares.getBseScriptCode())) {
				if (new BigDecimal(bhaarathaShares.getPurchaseQuantity())
						.intValue() == bhaarathaConsolidatedCalculationMap
								.get(bhaarathaShares.getStockExchange().toUpperCase()).intValue()) {
					BhaarathaShares soldShare = scriptConsolidatedSoldShareMap
							.get(bhaarathaShares.getStockExchange().toUpperCase());

					buildConsolidatedSellShares(soldBhaarathaShares, bhaarathaShares, soldShare, soldTotal,
							bhaarathaConsolidatedCalculationMap);

					soldShare.setCreatedBy(bhaarathaShares.getCreatedBy());
					soldShare.setCreatedDate(bhaarathaShares.getCreatedDate());
					soldShare.setLastUpdatedBy(userEmailId);
					soldShare.setLastUpdatedDate(dateInddMmmYyyy);

					consolidatedSoldSharesMap.put(bhaarathaShares.getStockExchange().toUpperCase(), soldShare);
				} else if (bhaarathaConsolidatedCalculationMap.get(bhaarathaShares.getStockExchange().toUpperCase())
						.intValue() != 0) {
					// Adjusting bought shares when sell quantity is less than purchase quantity
					BhaarathaShares temp = new BhaarathaShares();
					temp.setId(bhaarathaShares.getId());
					temp.setStockExchange(bhaarathaShares.getStockExchange());
					temp.setBseScriptCode(bhaarathaShares.getBseScriptCode());
					temp.setNseScriptCode(bhaarathaShares.getNseScriptCode());
					temp.setYahooBseScriptCode(bhaarathaShares.getYahooBseScriptCode());
					temp.setYahooNseScriptCode(bhaarathaShares.getYahooNseScriptCode());
					temp.setIsinCode(bhaarathaShares.getIsinCode());
					temp.setMoneycontrolCode(bhaarathaShares.getMoneycontrolCode());
					temp.setScriptName(bhaarathaShares.getScriptName());
					temp.setCategory(bhaarathaShares.getCategory());
					temp.setSector(bhaarathaShares.getSector());
					temp.setIndustry(bhaarathaShares.getIndustry());
					temp.setShortTermInvestment(bhaarathaShares.isShortTermInvestment());
					temp.setSharesSplitSelected(bhaarathaShares.isSharesSplitSelected());
					temp.setSharesSplitRatio(bhaarathaShares.getSharesSplitRatio());
					temp.setSharesSplitDate(bhaarathaShares.getSharesSplitDate());
					temp.setSharesSplit(bhaarathaShares.getSharesSplit());
					temp.setSharesSplitDisplay(bhaarathaShares.getSharesSplitDisplay());
					temp.setSharesBonusSelected(bhaarathaShares.isSharesBonusSelected());
					temp.setSharesBonusRatio(bhaarathaShares.getSharesBonusRatio());
					temp.setSharesBonusDate(bhaarathaShares.getSharesBonusDate());
					temp.setSharesBonus(bhaarathaShares.getSharesBonus());
					temp.setSharesBonusDisplay(bhaarathaShares.getSharesBonusDisplay());
					temp.setPurchaseDate(bhaarathaShares.getPurchaseDate());

					BigDecimal existingPurchaseQuantity = new BigDecimal(bhaarathaShares.getPurchaseQuantity());
					BigDecimal soldPurchaseQuantity = bhaarathaConsolidatedCalculationMap
							.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_QUANTITY
									+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase());
					BigDecimal purchaseQuantity = existingPurchaseQuantity.subtract(soldPurchaseQuantity);
					temp.setPurchaseQuantity(String.valueOf(purchaseQuantity));

					BigDecimal existingPurchasePrice = new BigDecimal(bhaarathaShares.getPurchasePrice());
					BigDecimal soldPurchasePrice = bhaarathaConsolidatedCalculationMap
							.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_PRICE
									+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase());
					BigDecimal purchasePrice = existingPurchaseQuantity.multiply(existingPurchasePrice)
							.subtract(soldPurchaseQuantity.multiply(soldPurchasePrice))
							.divide(purchaseQuantity, MathContext.DECIMAL128);
					temp.setPurchasePrice(String.valueOf(purchasePrice));

					BigDecimal existingPurchaseGrossTotal = new BigDecimal(bhaarathaShares.getPurchaseGrossTotal());
					BigDecimal soldPurchaseGrossTotal = soldPurchaseQuantity.multiply(soldPurchasePrice);
					BigDecimal purchaseGrossTotal = existingPurchaseGrossTotal.subtract(soldPurchaseGrossTotal);
					temp.setPurchaseGrossTotal(String.valueOf(purchaseGrossTotal));

					BigDecimal existingPurchaseBrokerage = new BigDecimal(bhaarathaShares.getPurchaseBrokerage());
					BigDecimal soldPurchaseBrokerage = bhaarathaConsolidatedCalculationMap
							.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_BROKERAGE
									+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase());
					BigDecimal purchaseBrokerage = existingPurchaseBrokerage.subtract(soldPurchaseBrokerage);
					temp.setPurchaseBrokerage(String.valueOf(purchaseBrokerage));

					BigDecimal existingPurchaseSTT = new BigDecimal(bhaarathaShares.getPurchaseSTT());
					BigDecimal soldPurchaseSTT = bhaarathaConsolidatedCalculationMap
							.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_STT
									+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase());
					BigDecimal purchaseSTT = existingPurchaseSTT.subtract(soldPurchaseSTT);
					temp.setPurchaseSTT(String.valueOf(purchaseSTT));

					BigDecimal existingPurchaseExpenditure = new BigDecimal(bhaarathaShares.getPurchaseExpenditure());
					BigDecimal soldPurchaseExpenditure = bhaarathaConsolidatedCalculationMap
							.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_EXPENDITURE
									+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase());
					BigDecimal purchaseExpenditure = existingPurchaseExpenditure.subtract(soldPurchaseExpenditure);
					temp.setPurchaseExpenditure(String.valueOf(purchaseExpenditure));

					BigDecimal existingPurchaseNonExpenditure = new BigDecimal(
							bhaarathaShares.getPurchaseNonExpenditure());
					BigDecimal soldPurchaseNonExpenditure = bhaarathaConsolidatedCalculationMap
							.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_NON_EXPENDITURE
									+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase());
					BigDecimal purchaseNonExpenditure = existingPurchaseNonExpenditure
							.subtract(soldPurchaseNonExpenditure);
					temp.setPurchaseNonExpenditure(String.valueOf(purchaseNonExpenditure));

					BigDecimal existingPurchaseTotal = new BigDecimal(bhaarathaShares.getPurchaseTotal());
					BigDecimal soldPurchaseTotal = bhaarathaConsolidatedCalculationMap
							.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_TOTAL
									+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase());
					BigDecimal purchaseTotal = existingPurchaseTotal.subtract(soldPurchaseTotal);
					temp.setPurchaseTotal(String.valueOf(purchaseTotal));

					BigDecimal existingPurchaseNetPricePerUnit = new BigDecimal(
							bhaarathaShares.getPurchaseNetPricePerUnit());
					BigDecimal soldPurchaseNetPricePerUnit = bhaarathaConsolidatedCalculationMap
							.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_NET_PRICE_PER_UNIT
									+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase());
					BigDecimal purchaseNetPricePerUnit = existingPurchaseQuantity
							.multiply(existingPurchaseNetPricePerUnit)
							.subtract(soldPurchaseQuantity.multiply(soldPurchaseNetPricePerUnit))
							.divide(purchaseQuantity, MathContext.DECIMAL128);
					temp.setPurchaseNetPricePerUnit(String.valueOf(purchaseNetPricePerUnit));

					temp.setShortTermInvestment(bhaarathaShares.isShortTermInvestment());
					temp.setShortTerm(bhaarathaShares.isShortTerm());
					temp.setCreatedBy(bhaarathaShares.getCreatedBy());
					temp.setCreatedDate(bhaarathaShares.getCreatedDate());
					temp.setLastUpdatedBy(userEmailId);
					temp.setLastUpdatedDate(dateInddMmmYyyy);

					boughtSharesList.add(temp);

					// Updating sell shares
					BigDecimal soldQuantity = bhaarathaConsolidatedCalculationMap
							.get(bhaarathaShares.getStockExchange().toUpperCase());

					purchaseQuantity = soldQuantity;
					bhaarathaShares.setPurchaseQuantity(String.valueOf(purchaseQuantity));

					purchaseGrossTotal = soldQuantity.multiply(new BigDecimal(bhaarathaShares.getPurchasePrice()));
					bhaarathaShares.setPurchaseGrossTotal(String.valueOf(purchaseGrossTotal));

					BhaarathaShares soldShare = scriptConsolidatedSoldShareMap
							.get(bhaarathaShares.getStockExchange().toUpperCase());

					buildConsolidatedSellShares(soldBhaarathaShares, bhaarathaShares, soldShare, soldTotal,
							bhaarathaConsolidatedCalculationMap);

					soldShare.setCreatedBy(bhaarathaShares.getCreatedBy());
					soldShare.setCreatedDate(bhaarathaShares.getCreatedDate());
					soldShare.setLastUpdatedBy(userEmailId);
					soldShare.setLastUpdatedDate(dateInddMmmYyyy);

					consolidatedSoldSharesMap.put(bhaarathaShares.getStockExchange().toUpperCase(), soldShare);
				} else {
					boughtSharesList.add(bhaarathaShares);
				}
			} else {
				boughtSharesList.add(bhaarathaShares);
			}
		}

		consolidatedSoldSharesMap.put(AnukyaConstants.BHAARATHA_BOUGHT_SHARES_LIST, boughtSharesList);

		return consolidatedSoldSharesMap;
	}

	private void buildConsolidatedSellShares(BhaarathaShares soldBhaarathaShares, BhaarathaShares bhaarathaShares,
			BhaarathaShares scriptConsolidatedSoldShare, BhaarathaShares soldTotal,
			Map<String, BigDecimal> bhaarathaConsolidatedCalculationMap) {

		scriptConsolidatedSoldShare.setId(anukyaUtils.generateId());

		scriptConsolidatedSoldShare.setStockExchange(bhaarathaShares.getStockExchange());
		scriptConsolidatedSoldShare.setBseScriptCode(bhaarathaShares.getBseScriptCode());
		scriptConsolidatedSoldShare.setNseScriptCode(bhaarathaShares.getNseScriptCode());
		scriptConsolidatedSoldShare.setYahooBseScriptCode(bhaarathaShares.getYahooBseScriptCode());
		scriptConsolidatedSoldShare.setYahooNseScriptCode(bhaarathaShares.getYahooNseScriptCode());
		scriptConsolidatedSoldShare.setIsinCode(bhaarathaShares.getIsinCode());
		scriptConsolidatedSoldShare.setMoneycontrolCode(bhaarathaShares.getMoneycontrolCode());
		scriptConsolidatedSoldShare.setScriptName(bhaarathaShares.getScriptName());
		scriptConsolidatedSoldShare.setCategory(bhaarathaShares.getCategory());
		scriptConsolidatedSoldShare.setSector(bhaarathaShares.getSector());
		scriptConsolidatedSoldShare.setIndustry(bhaarathaShares.getIndustry());
		scriptConsolidatedSoldShare.setShortTermInvestment(bhaarathaShares.isShortTermInvestment());
		scriptConsolidatedSoldShare.setSharesSplitSelected(bhaarathaShares.isSharesSplitSelected());
		scriptConsolidatedSoldShare.setSharesSplitRatio(bhaarathaShares.getSharesSplitRatio());
		scriptConsolidatedSoldShare.setSharesSplitDate(bhaarathaShares.getSharesSplitDate());
		scriptConsolidatedSoldShare.setSharesSplit(bhaarathaShares.getSharesSplit());
		scriptConsolidatedSoldShare.setSharesSplitDisplay(bhaarathaShares.getSharesSplitDisplay());
		scriptConsolidatedSoldShare.setSharesBonusSelected(bhaarathaShares.isSharesBonusSelected());
		scriptConsolidatedSoldShare.setSharesBonusRatio(bhaarathaShares.getSharesBonusRatio());
		scriptConsolidatedSoldShare.setSharesBonusDate(bhaarathaShares.getSharesBonusDate());
		scriptConsolidatedSoldShare.setSharesBonus(bhaarathaShares.getSharesBonus());
		scriptConsolidatedSoldShare.setSharesBonusDisplay(bhaarathaShares.getSharesBonusDisplay());

		BigDecimal purchaseQuantity = bhaarathaConsolidatedCalculationMap
				.get(bhaarathaShares.getStockExchange().toUpperCase());
		BigDecimal existingScriptConsolidatedPurchaseQuantity = new BigDecimal(
				scriptConsolidatedSoldShare.getPurchaseQuantity());
		BigDecimal totalScriptConsolidatedPurchaseQuantity = purchaseQuantity
				.add(existingScriptConsolidatedPurchaseQuantity);
		scriptConsolidatedSoldShare.setPurchaseQuantity(String.valueOf(totalScriptConsolidatedPurchaseQuantity));
		BigDecimal existingTotalPurchaseQuantity = new BigDecimal(soldTotal.getPurchaseQuantity());
		BigDecimal totalPurchaseQuantity = purchaseQuantity.add(existingTotalPurchaseQuantity);
		soldTotal.setPurchaseQuantity(String.valueOf(totalPurchaseQuantity));

		BigDecimal purchasePrice = bhaarathaConsolidatedCalculationMap
				.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_PRICE + AnukyaConstants.UNDERSCORE
						+ bhaarathaShares.getStockExchange().toUpperCase());
		BigDecimal existingScriptConsolidatedPurchasePrice = new BigDecimal(
				scriptConsolidatedSoldShare.getPurchasePrice());
		BigDecimal totalScriptConsolidatedPurchasePrice = purchaseQuantity.multiply(purchasePrice)
				.add(existingScriptConsolidatedPurchaseQuantity.multiply(existingScriptConsolidatedPurchasePrice))
				.divide(totalScriptConsolidatedPurchaseQuantity, MathContext.DECIMAL128);
		scriptConsolidatedSoldShare.setPurchasePrice(String.valueOf(totalScriptConsolidatedPurchasePrice));
		BigDecimal existingTotalPurchasePrice = new BigDecimal(soldTotal.getPurchasePrice());
		BigDecimal totalPurchasePrice = purchaseQuantity.multiply(purchasePrice)
				.add(existingTotalPurchaseQuantity.multiply(existingTotalPurchasePrice))
				.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
		soldTotal.setPurchasePrice(String.valueOf(totalPurchasePrice));

		BigDecimal purchaseGrossTotal = purchaseQuantity.multiply(purchasePrice);
		BigDecimal existingScriptConsolidatedPurchaseGrossTotal = new BigDecimal(
				scriptConsolidatedSoldShare.getPurchaseGrossTotal());
		BigDecimal totalScriptConsolidatedPurchaseGrossTotal = purchaseGrossTotal
				.add(existingScriptConsolidatedPurchaseGrossTotal);
		scriptConsolidatedSoldShare.setPurchaseGrossTotal(String.valueOf(totalScriptConsolidatedPurchaseGrossTotal));
		BigDecimal existingTotalPurchaseGrossTotal = new BigDecimal(soldTotal.getPurchaseGrossTotal());
		BigDecimal totalPurchaseGrossTotal = purchaseGrossTotal.add(existingTotalPurchaseGrossTotal);
		soldTotal.setPurchaseGrossTotal(String.valueOf(totalPurchaseGrossTotal));

		BigDecimal purchaseBrokerage = bhaarathaConsolidatedCalculationMap
				.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_BROKERAGE + AnukyaConstants.UNDERSCORE
						+ bhaarathaShares.getStockExchange().toUpperCase());
		BigDecimal existingScriptConsolidatedPurchaseBrokerage = new BigDecimal(
				scriptConsolidatedSoldShare.getPurchaseBrokerage());
		BigDecimal totalScriptConsolidatedPurchaseBrokerage = purchaseBrokerage
				.add(existingScriptConsolidatedPurchaseBrokerage);
		scriptConsolidatedSoldShare.setPurchaseBrokerage(String.valueOf(totalScriptConsolidatedPurchaseBrokerage));
		BigDecimal existingTotalPurchaseBrokerage = new BigDecimal(soldTotal.getPurchaseBrokerage());
		BigDecimal totalPurchaseBrokerage = purchaseBrokerage.add(existingTotalPurchaseBrokerage);
		soldTotal.setPurchaseBrokerage(String.valueOf(totalPurchaseBrokerage));

		BigDecimal purchaseSTT = bhaarathaConsolidatedCalculationMap
				.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_STT + AnukyaConstants.UNDERSCORE
						+ bhaarathaShares.getStockExchange().toUpperCase());
		BigDecimal existingScriptConsolidatedPurchaseSTT = new BigDecimal(scriptConsolidatedSoldShare.getPurchaseSTT());
		BigDecimal totalScriptConsolidatedPurchaseSTT = purchaseSTT.add(existingScriptConsolidatedPurchaseSTT);
		scriptConsolidatedSoldShare.setPurchaseSTT(String.valueOf(totalScriptConsolidatedPurchaseSTT));
		BigDecimal existingTotalPurchaseSTT = new BigDecimal(soldTotal.getPurchaseSTT());
		BigDecimal totalPurchaseSTT = purchaseSTT.add(existingTotalPurchaseSTT);
		soldTotal.setPurchaseSTT(String.valueOf(totalPurchaseSTT));

		BigDecimal purchaseExpenditure = bhaarathaConsolidatedCalculationMap
				.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_EXPENDITURE
						+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase());
		BigDecimal existingScriptConsolidatedPurchaseExpenditure = new BigDecimal(
				scriptConsolidatedSoldShare.getPurchaseExpenditure());
		BigDecimal totalScriptConsolidatedPurchaseExpenditure = purchaseExpenditure
				.add(existingScriptConsolidatedPurchaseExpenditure);
		scriptConsolidatedSoldShare.setPurchaseExpenditure(String.valueOf(totalScriptConsolidatedPurchaseExpenditure));
		BigDecimal existingTotalPurchaseExpenditure = new BigDecimal(soldTotal.getPurchaseExpenditure());
		BigDecimal totalPurchaseExpenditure = purchaseExpenditure.add(existingTotalPurchaseExpenditure);
		soldTotal.setPurchaseExpenditure(String.valueOf(totalPurchaseExpenditure));

		BigDecimal purchaseNonExpenditure = bhaarathaConsolidatedCalculationMap
				.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_NON_EXPENDITURE
						+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase());
		BigDecimal existingScriptConsolidatedPurchaseNonExpenditure = new BigDecimal(
				scriptConsolidatedSoldShare.getPurchaseNonExpenditure());
		BigDecimal totalScriptConsolidatedPurchaseNonExpenditure = purchaseNonExpenditure
				.add(existingScriptConsolidatedPurchaseNonExpenditure);
		scriptConsolidatedSoldShare
				.setPurchaseNonExpenditure(String.valueOf(totalScriptConsolidatedPurchaseNonExpenditure));
		BigDecimal existingTotalPurchaseNonExpenditure = new BigDecimal(soldTotal.getPurchaseNonExpenditure());
		BigDecimal totalPurchaseNonExpenditure = purchaseNonExpenditure.add(existingTotalPurchaseNonExpenditure);
		soldTotal.setPurchaseNonExpenditure(String.valueOf(totalPurchaseNonExpenditure));

		BigDecimal purchaseTotal = bhaarathaConsolidatedCalculationMap
				.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_TOTAL + AnukyaConstants.UNDERSCORE
						+ bhaarathaShares.getStockExchange().toUpperCase());
		BigDecimal existingScriptConsolidatedPurchaseTotal = new BigDecimal(
				scriptConsolidatedSoldShare.getPurchaseTotal());
		BigDecimal totalScriptConsolidatedPurchaseTotal = purchaseTotal.add(existingScriptConsolidatedPurchaseTotal);
		scriptConsolidatedSoldShare.setPurchaseTotal(String.valueOf(totalScriptConsolidatedPurchaseTotal));
		BigDecimal existingTotalPurchaseTotal = new BigDecimal(soldTotal.getPurchaseTotal());
		BigDecimal totalPurchaseTotal = purchaseTotal.add(existingTotalPurchaseTotal);
		soldTotal.setPurchaseTotal(String.valueOf(totalPurchaseTotal));

		BigDecimal purchaseNetPricePerUnit = bhaarathaConsolidatedCalculationMap
				.get(AnukyaConstants.BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_NET_PRICE_PER_UNIT
						+ AnukyaConstants.UNDERSCORE + bhaarathaShares.getStockExchange().toUpperCase());
		BigDecimal existingScriptConsolidatedPurchaseNetPricePerUnit = new BigDecimal(
				scriptConsolidatedSoldShare.getPurchaseNetPricePerUnit());
		BigDecimal totalScriptConsolidatedPurchaseNetPricePerUnit = purchaseQuantity.multiply(purchaseNetPricePerUnit)
				.add(existingScriptConsolidatedPurchaseQuantity
						.multiply(existingScriptConsolidatedPurchaseNetPricePerUnit))
				.divide(totalScriptConsolidatedPurchaseQuantity, MathContext.DECIMAL128);
		scriptConsolidatedSoldShare
				.setPurchaseNetPricePerUnit(String.valueOf(totalScriptConsolidatedPurchaseNetPricePerUnit));
		BigDecimal existingTotalPurchaseNetPricePerUnit = new BigDecimal(soldTotal.getPurchaseNetPricePerUnit());
		BigDecimal totalPurchaseNetPricePerUnit = purchaseQuantity.multiply(purchaseNetPricePerUnit)
				.add(existingTotalPurchaseQuantity.multiply(existingTotalPurchaseNetPricePerUnit))
				.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
		soldTotal.setPurchaseNetPricePerUnit(String.valueOf(totalPurchaseNetPricePerUnit));

		scriptConsolidatedSoldShare.setSellDate(soldBhaarathaShares.getSellDate());

		BigDecimal sellQuantity = bhaarathaConsolidatedCalculationMap
				.get(bhaarathaShares.getStockExchange().toUpperCase());
		BigDecimal existingScriptConsolidatedSellQuantity = new BigDecimal(
				scriptConsolidatedSoldShare.getSellQuantity());
		BigDecimal totalScriptConsolidatedSellQuantity = sellQuantity.add(existingScriptConsolidatedSellQuantity);
		scriptConsolidatedSoldShare.setSellQuantity(String.valueOf(totalScriptConsolidatedSellQuantity));
		BigDecimal existingTotalSellQuantity = new BigDecimal(soldTotal.getSellQuantity());
		BigDecimal totalSellQuantity = sellQuantity.add(existingTotalSellQuantity);
		soldTotal.setSellQuantity(String.valueOf(totalSellQuantity));

		BigDecimal sellPrice = new BigDecimal(soldBhaarathaShares.getSellPrice());
		BigDecimal existingScriptConsolidatedSellPrice = new BigDecimal(scriptConsolidatedSoldShare.getSellPrice());
		BigDecimal totalScriptConsolidatedSellPrice = sellQuantity.multiply(sellPrice)
				.add(existingScriptConsolidatedSellQuantity.multiply(existingScriptConsolidatedSellPrice))
				.divide(totalScriptConsolidatedSellQuantity, MathContext.DECIMAL128);
		scriptConsolidatedSoldShare.setSellPrice(String.valueOf(totalScriptConsolidatedSellPrice));
		BigDecimal existingTotalSellPrice = new BigDecimal(soldTotal.getSellPrice());
		BigDecimal totalSellPrice = sellQuantity.multiply(sellPrice)
				.add(existingTotalSellQuantity.multiply(existingTotalSellPrice))
				.divide(totalSellQuantity, MathContext.DECIMAL128);
		soldTotal.setSellPrice(String.valueOf(totalSellPrice));

		BigDecimal sellGrossTotal = sellQuantity.multiply(sellPrice);
		BigDecimal existingScriptConsolidatedSellGrossTotal = new BigDecimal(
				scriptConsolidatedSoldShare.getSellGrossTotal());
		BigDecimal totalScriptConsolidatedSellGrossTotal = sellGrossTotal.add(existingScriptConsolidatedSellGrossTotal);
		scriptConsolidatedSoldShare.setSellGrossTotal(String.valueOf(totalScriptConsolidatedSellGrossTotal));
		BigDecimal existingTotalSellGrossTotal = new BigDecimal(soldTotal.getSellGrossTotal());
		BigDecimal totalSellGrossTotal = sellGrossTotal.add(existingTotalSellGrossTotal);
		soldTotal.setSellGrossTotal(String.valueOf(totalSellGrossTotal));

		BigDecimal sellBrokerage = new BigDecimal(soldBhaarathaShares.getSellBrokerage()).multiply(
				sellQuantity.divide(new BigDecimal(soldBhaarathaShares.getSellQuantity()), MathContext.DECIMAL128));
		BigDecimal existingScriptConsolidatedSellBrokerage = new BigDecimal(
				scriptConsolidatedSoldShare.getSellBrokerage());
		BigDecimal totalScriptConsolidatedSellBrokerage = sellBrokerage.add(existingScriptConsolidatedSellBrokerage);
		scriptConsolidatedSoldShare.setSellBrokerage(String.valueOf(totalScriptConsolidatedSellBrokerage));
		BigDecimal existingTotalSellBrokerage = new BigDecimal(soldTotal.getSellBrokerage());
		BigDecimal totalSellBrokerage = sellBrokerage.add(existingTotalSellBrokerage);
		soldTotal.setSellBrokerage(String.valueOf(totalSellBrokerage));

		BigDecimal sellSTT = new BigDecimal(soldBhaarathaShares.getSellSTT()).multiply(
				sellQuantity.divide(new BigDecimal(soldBhaarathaShares.getSellQuantity()), MathContext.DECIMAL128));
		BigDecimal existingScriptConsolidatedSellSTT = new BigDecimal(scriptConsolidatedSoldShare.getSellSTT());
		BigDecimal totalScriptConsolidatedSellSTT = sellSTT.add(existingScriptConsolidatedSellSTT);
		scriptConsolidatedSoldShare.setSellSTT(String.valueOf(totalScriptConsolidatedSellSTT));
		BigDecimal existingTotalSellSTT = new BigDecimal(soldTotal.getSellSTT());
		BigDecimal totalSellSTT = sellSTT.add(existingTotalSellSTT);
		soldTotal.setSellSTT(String.valueOf(totalSellSTT));

		BigDecimal sellExpenditure = new BigDecimal(soldBhaarathaShares.getSellExpenditure()).multiply(
				sellQuantity.divide(new BigDecimal(soldBhaarathaShares.getSellQuantity()), MathContext.DECIMAL128));
		BigDecimal existingScriptConsolidatedSellExpenditure = new BigDecimal(
				scriptConsolidatedSoldShare.getSellExpenditure());
		BigDecimal totalScriptConsolidatedSellExpenditure = sellExpenditure
				.add(existingScriptConsolidatedSellExpenditure);
		scriptConsolidatedSoldShare.setSellExpenditure(String.valueOf(totalScriptConsolidatedSellExpenditure));
		BigDecimal existingTotalSellExpenditure = new BigDecimal(soldTotal.getSellExpenditure());
		BigDecimal totalSellExpenditure = sellExpenditure.add(existingTotalSellExpenditure);
		soldTotal.setSellExpenditure(String.valueOf(totalSellExpenditure));

		BigDecimal sellNonExpenditure = new BigDecimal(soldBhaarathaShares.getSellNonExpenditure()).multiply(
				sellQuantity.divide(new BigDecimal(soldBhaarathaShares.getSellQuantity()), MathContext.DECIMAL128));
		BigDecimal existingScriptConsolidatedSellNonExpenditure = new BigDecimal(
				scriptConsolidatedSoldShare.getSellNonExpenditure());
		BigDecimal totalScriptConsolidatedSellNonExpenditure = sellNonExpenditure
				.add(existingScriptConsolidatedSellNonExpenditure);
		scriptConsolidatedSoldShare.setSellNonExpenditure(String.valueOf(totalScriptConsolidatedSellNonExpenditure));
		BigDecimal existingTotalSellNonExpenditure = new BigDecimal(soldTotal.getSellNonExpenditure());
		BigDecimal totalSellNonExpenditure = sellNonExpenditure.add(existingTotalSellNonExpenditure);
		soldTotal.setSellNonExpenditure(String.valueOf(totalSellNonExpenditure));

		BigDecimal sellTotal = sellGrossTotal.subtract(sellBrokerage).subtract(sellSTT).subtract(sellExpenditure)
				.subtract(sellNonExpenditure);
		BigDecimal existingScriptConsolidatedSellTotal = new BigDecimal(scriptConsolidatedSoldShare.getSellTotal());
		BigDecimal totalScriptConsolidatedSellTotal = sellTotal.add(existingScriptConsolidatedSellTotal);
		scriptConsolidatedSoldShare.setSellTotal(String.valueOf(totalScriptConsolidatedSellTotal));
		BigDecimal existingTotalSellTotal = new BigDecimal(soldTotal.getSellTotal());
		BigDecimal totalSellTotal = sellTotal.add(existingTotalSellTotal);
		soldTotal.setSellTotal(String.valueOf(totalSellTotal));

		BigDecimal sellNetPricePerUnit = sellTotal.divide(sellQuantity, MathContext.DECIMAL128);
		BigDecimal existingScriptConsolidatedSellNetPricePerUnit = new BigDecimal(
				scriptConsolidatedSoldShare.getSellNetPricePerUnit());
		BigDecimal totalScriptConsolidatedSellNetPricePerUnit = sellQuantity.multiply(sellNetPricePerUnit)
				.add(existingScriptConsolidatedSellQuantity.multiply(existingScriptConsolidatedSellNetPricePerUnit))
				.divide(totalScriptConsolidatedSellQuantity, MathContext.DECIMAL128);
		scriptConsolidatedSoldShare.setSellNetPricePerUnit(String.valueOf(totalScriptConsolidatedSellNetPricePerUnit));
		BigDecimal existingTotalSellNetPricePerUnit = new BigDecimal(soldTotal.getSellNetPricePerUnit());
		BigDecimal totalSellNetPricePerUnit = sellQuantity.multiply(sellNetPricePerUnit)
				.add(existingTotalSellQuantity.multiply(existingTotalSellNetPricePerUnit))
				.divide(totalSellQuantity, MathContext.DECIMAL128);
		soldTotal.setSellNetPricePerUnit(String.valueOf(totalSellNetPricePerUnit));

		BigDecimal grossProfitLoss = totalScriptConsolidatedSellGrossTotal
				.subtract(totalScriptConsolidatedPurchaseGrossTotal);
		scriptConsolidatedSoldShare.setGrossProfitLoss(String.valueOf(grossProfitLoss));
		BigDecimal totalGrossProfitLoss = totalSellGrossTotal.subtract(totalPurchaseGrossTotal);
		soldTotal.setGrossProfitLoss(String.valueOf(totalGrossProfitLoss));

		BigDecimal grossProfitLossPercentage;
		try {
			grossProfitLossPercentage = totalScriptConsolidatedSellGrossTotal
					.divide(totalScriptConsolidatedPurchaseGrossTotal, MathContext.DECIMAL128)
					.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
					.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
		} catch (ArithmeticException e) {
			grossProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_0);
		}
		scriptConsolidatedSoldShare.setGrossProfitLossPercentage(String.valueOf(grossProfitLossPercentage));

		BigDecimal totalGrossProfitLossPercentage;
		try {
			totalGrossProfitLossPercentage = totalSellGrossTotal.divide(totalPurchaseGrossTotal, MathContext.DECIMAL128)
					.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
					.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
		} catch (ArithmeticException e) {
			totalGrossProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_0);
		}
		soldTotal.setGrossProfitLossPercentage(String.valueOf(totalGrossProfitLossPercentage));

		BigDecimal totalProfitLoss = totalScriptConsolidatedSellTotal.subtract(totalScriptConsolidatedPurchaseTotal);
		scriptConsolidatedSoldShare.setTotalProfitLoss(String.valueOf(totalProfitLoss));
		BigDecimal totalTotalProfitLoss = totalSellTotal.subtract(totalPurchaseTotal);
		soldTotal.setTotalProfitLoss(String.valueOf(totalTotalProfitLoss));

		BigDecimal totalProfitLossPercentage;
		try {
			totalProfitLossPercentage = totalScriptConsolidatedSellTotal
					.divide(totalScriptConsolidatedPurchaseTotal, MathContext.DECIMAL128)
					.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
					.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
		} catch (ArithmeticException e) {
			totalProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_0);
		}
		scriptConsolidatedSoldShare.setTotalProfitLossPercentage(String.valueOf(totalProfitLossPercentage));

		BigDecimal totalTotalProfitLossPercentage;
		try {
			totalTotalProfitLossPercentage = totalSellTotal.divide(totalPurchaseTotal, MathContext.DECIMAL128)
					.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
					.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
		} catch (ArithmeticException e) {
			totalTotalProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_0);
		}
		soldTotal.setTotalProfitLossPercentage(String.valueOf(totalTotalProfitLossPercentage));

		BigDecimal netProfitLossPerUnit = totalScriptConsolidatedSellNetPricePerUnit
				.subtract(totalScriptConsolidatedPurchaseNetPricePerUnit);
		scriptConsolidatedSoldShare.setNetProfitLossPerUnit(String.valueOf(netProfitLossPerUnit));
		BigDecimal totalNetProfitLossPerUnit = totalSellNetPricePerUnit.subtract(totalPurchaseNetPricePerUnit);
		soldTotal.setNetProfitLossPerUnit(String.valueOf(totalNetProfitLossPerUnit));

	}

	private List<BhaarathaShares> getBhaarathaSharesNonConsolidated(String searchTerm) throws AnukyaException {

		File nonConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_SOLD_MAIN_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);

		List<BhaarathaShares> bhaarathaSharesList = bhaarathaSharesData.getBhaarathaShares(nonConsolidatedFile);

		if (searchTerm.isEmpty()) {
			return bhaarathaSharesList;
		}

		return filterBySearchTerm(bhaarathaSharesList, searchTerm);
	}

	private List<BhaarathaShares> getBhaarathaSharesConsolidated(String searchTerm) throws AnukyaException {

		File consolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_SOLD_MAIN_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		List<BhaarathaShares> bhaarathaSharesList = bhaarathaSharesData.getBhaarathaShares(consolidatedFile);

		if (searchTerm.isEmpty()) {
			return bhaarathaSharesList;
		}

		return filterBySearchTerm(bhaarathaSharesList, searchTerm);
	}

	private List<BhaarathaShares> filterBySearchTerm(List<BhaarathaShares> bhaarathaSharesList, String searchTerm) {

		return bhaarathaSharesList.stream()
				.filter(bhaarathaShares -> bhaarathaShares.getScriptName().equalsIgnoreCase(searchTerm)
						|| bhaarathaShares.getBseScriptCode().equalsIgnoreCase(searchTerm)
						|| bhaarathaShares.getNseScriptCode().equalsIgnoreCase(searchTerm)
						|| bhaarathaShares.getCategory().equalsIgnoreCase(searchTerm)
						|| bhaarathaShares.getSector().equalsIgnoreCase(searchTerm)
						|| bhaarathaShares.getIndustry().equalsIgnoreCase(searchTerm)
						|| bhaarathaShares.getStockExchange().equalsIgnoreCase(searchTerm))
				.collect(Collectors.toList());
	}

	private BhaarathaSharesResponse calculateBhaarathaShares(List<BhaarathaShares> bhaarathaSharesList,
			String searchTerm) {

		BhaarathaSharesResponse bhaarathaSharesResponse = new BhaarathaSharesResponse();
		bhaarathaSharesResponse.setBhaarathaSharesTotal(new BhaarathaShares());

		if (!searchTerm.isEmpty()) {
			for (BhaarathaShares bhaarathaShares : bhaarathaSharesList) {
				BigDecimal purchaseQuantity = new BigDecimal(String.valueOf(bhaarathaShares.getPurchaseQuantity()));
				BigDecimal existingTotalPurchaseQuantity = new BigDecimal(
						bhaarathaSharesResponse.getBhaarathaSharesTotal().getPurchaseQuantity());
				BigDecimal totalPurchaseQuantity = purchaseQuantity.add(existingTotalPurchaseQuantity);
				bhaarathaShares.setPurchaseQuantity(String.valueOf(purchaseQuantity));
				bhaarathaSharesResponse.getBhaarathaSharesTotal()
						.setPurchaseQuantity(String.valueOf(totalPurchaseQuantity));

				BigDecimal purchasePrice = new BigDecimal(String.valueOf(bhaarathaShares.getPurchasePrice()));
				BigDecimal existingTotalPurchasePrice = new BigDecimal(
						bhaarathaSharesResponse.getBhaarathaSharesTotal().getPurchasePrice());
				BigDecimal totalPurchasePrice = purchaseQuantity.multiply(purchasePrice)
						.add(existingTotalPurchaseQuantity.multiply(existingTotalPurchasePrice))
						.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
				bhaarathaShares.setPurchasePrice(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchasePrice));
				bhaarathaSharesResponse.getBhaarathaSharesTotal()
						.setPurchasePrice(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchasePrice));

				BigDecimal purchaseGrossTotal = new BigDecimal(String.valueOf(bhaarathaShares.getPurchaseGrossTotal()));
				BigDecimal existingTotalPurchaseGrossTotal = new BigDecimal(
						bhaarathaSharesResponse.getBhaarathaSharesTotal().getPurchaseGrossTotal());
				BigDecimal totalPurchaseGrossTotal = purchaseGrossTotal.add(existingTotalPurchaseGrossTotal);
				bhaarathaShares
						.setPurchaseGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseGrossTotal));
				bhaarathaSharesResponse.getBhaarathaSharesTotal()
						.setPurchaseGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseGrossTotal));

				BigDecimal purchaseBrokerage = new BigDecimal(String.valueOf(bhaarathaShares.getPurchaseBrokerage()));
				BigDecimal existingTotalPurchaseBrokerage = new BigDecimal(
						bhaarathaSharesResponse.getBhaarathaSharesTotal().getPurchaseBrokerage());
				BigDecimal totalPurchaseBrokerage = purchaseBrokerage.add(existingTotalPurchaseBrokerage);
				bhaarathaShares.setPurchaseBrokerage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseBrokerage));
				bhaarathaSharesResponse.getBhaarathaSharesTotal()
						.setPurchaseBrokerage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseBrokerage));

				BigDecimal purchaseSTT = new BigDecimal(String.valueOf(bhaarathaShares.getPurchaseSTT()));
				BigDecimal existingTotalPurchaseSTT = new BigDecimal(
						bhaarathaSharesResponse.getBhaarathaSharesTotal().getPurchaseSTT());
				BigDecimal totalPurchaseSTT = purchaseSTT.add(existingTotalPurchaseSTT);
				bhaarathaShares.setPurchaseSTT(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseSTT));
				bhaarathaSharesResponse.getBhaarathaSharesTotal()
						.setPurchaseSTT(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseSTT));

				BigDecimal purchaseExpenditure = new BigDecimal(
						String.valueOf(bhaarathaShares.getPurchaseExpenditure()));
				BigDecimal existingTotalPurchaseExpenditure = new BigDecimal(
						bhaarathaSharesResponse.getBhaarathaSharesTotal().getPurchaseExpenditure());
				BigDecimal totalPurchaseExpenditure = purchaseExpenditure.add(existingTotalPurchaseExpenditure);
				bhaarathaShares
						.setPurchaseExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseExpenditure));
				bhaarathaSharesResponse.getBhaarathaSharesTotal().setPurchaseExpenditure(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseExpenditure));

				BigDecimal purchaseNonExpenditure = new BigDecimal(
						String.valueOf(bhaarathaShares.getPurchaseNonExpenditure()));
				BigDecimal existingTotalPurchaseNonExpenditure = new BigDecimal(
						bhaarathaSharesResponse.getBhaarathaSharesTotal().getPurchaseNonExpenditure());
				BigDecimal totalPurchaseNonExpenditure = purchaseNonExpenditure
						.add(existingTotalPurchaseNonExpenditure);
				bhaarathaShares.setPurchaseNonExpenditure(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseNonExpenditure));
				bhaarathaSharesResponse.getBhaarathaSharesTotal().setPurchaseNonExpenditure(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseNonExpenditure));

				BigDecimal purchaseTotal = new BigDecimal(String.valueOf(bhaarathaShares.getPurchaseTotal()));
				BigDecimal existingTotalPurchaseTotal = new BigDecimal(
						bhaarathaSharesResponse.getBhaarathaSharesTotal().getPurchaseTotal());
				BigDecimal totalPurchaseTotal = purchaseTotal.add(existingTotalPurchaseTotal);
				bhaarathaShares.setPurchaseTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseTotal));
				bhaarathaSharesResponse.getBhaarathaSharesTotal()
						.setPurchaseTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseTotal));

				BigDecimal purchaseNetPricePerUnit = new BigDecimal(
						String.valueOf(bhaarathaShares.getPurchaseNetPricePerUnit()));
				BigDecimal existingTotalPurchaseNetPricePerUnit = new BigDecimal(
						bhaarathaSharesResponse.getBhaarathaSharesTotal().getPurchaseNetPricePerUnit());
				BigDecimal totalPurchaseNetPricePerUnit = purchaseQuantity.multiply(purchaseNetPricePerUnit)
						.add(existingTotalPurchaseQuantity.multiply(existingTotalPurchaseNetPricePerUnit))
						.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
				bhaarathaShares.setPurchaseNetPricePerUnit(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseNetPricePerUnit));
				bhaarathaSharesResponse.getBhaarathaSharesTotal().setPurchaseNetPricePerUnit(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseNetPricePerUnit));

				BigDecimal sellQuantity = new BigDecimal(String.valueOf(bhaarathaShares.getSellQuantity()));
				BigDecimal existingTotalSellQuantity = new BigDecimal(
						bhaarathaSharesResponse.getBhaarathaSharesTotal().getSellQuantity());
				BigDecimal totalSellQuantity = sellQuantity.add(existingTotalSellQuantity);
				bhaarathaShares.setSellQuantity(String.valueOf(sellQuantity));
				bhaarathaSharesResponse.getBhaarathaSharesTotal().setSellQuantity(String.valueOf(totalSellQuantity));

				BigDecimal sellPrice = new BigDecimal(String.valueOf(bhaarathaShares.getSellPrice()));
				BigDecimal existingTotalSellPrice = new BigDecimal(
						bhaarathaSharesResponse.getBhaarathaSharesTotal().getSellPrice());
				BigDecimal totalSellPrice = sellQuantity.multiply(sellPrice)
						.add(existingTotalSellQuantity.multiply(existingTotalSellPrice))
						.divide(totalSellQuantity, MathContext.DECIMAL128);
				bhaarathaShares.setSellPrice(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellPrice));
				bhaarathaSharesResponse.getBhaarathaSharesTotal()
						.setSellPrice(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellPrice));

				BigDecimal sellGrossTotal = new BigDecimal(String.valueOf(bhaarathaShares.getSellGrossTotal()));
				BigDecimal existingTotalSellGrossTotal = new BigDecimal(
						bhaarathaSharesResponse.getBhaarathaSharesTotal().getSellGrossTotal());
				BigDecimal totalSellGrossTotal = sellGrossTotal.add(existingTotalSellGrossTotal);
				bhaarathaShares.setSellGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellGrossTotal));
				bhaarathaSharesResponse.getBhaarathaSharesTotal()
						.setSellGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellGrossTotal));

				BigDecimal sellBrokerage = new BigDecimal(String.valueOf(bhaarathaShares.getSellBrokerage()));
				BigDecimal existingTotalSellBrokerage = new BigDecimal(
						bhaarathaSharesResponse.getBhaarathaSharesTotal().getSellBrokerage());
				BigDecimal totalSellBrokerage = sellBrokerage.add(existingTotalSellBrokerage);
				bhaarathaShares.setSellBrokerage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellBrokerage));
				bhaarathaSharesResponse.getBhaarathaSharesTotal()
						.setSellBrokerage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellBrokerage));

				BigDecimal sellSTT = new BigDecimal(String.valueOf(bhaarathaShares.getSellSTT()));
				BigDecimal existingTotalSellSTT = new BigDecimal(
						bhaarathaSharesResponse.getBhaarathaSharesTotal().getSellSTT());
				BigDecimal totalSellSTT = sellSTT.add(existingTotalSellSTT);
				bhaarathaShares.setSellSTT(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellSTT));
				bhaarathaSharesResponse.getBhaarathaSharesTotal()
						.setSellSTT(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellSTT));

				BigDecimal sellExpenditure = new BigDecimal(String.valueOf(bhaarathaShares.getSellExpenditure()));
				BigDecimal existingTotalSellExpenditure = new BigDecimal(
						bhaarathaSharesResponse.getBhaarathaSharesTotal().getSellExpenditure());
				BigDecimal totalSellExpenditure = sellExpenditure.add(existingTotalSellExpenditure);
				bhaarathaShares.setSellExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellExpenditure));
				bhaarathaSharesResponse.getBhaarathaSharesTotal()
						.setSellExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellExpenditure));

				BigDecimal sellNonExpenditure = new BigDecimal(String.valueOf(bhaarathaShares.getSellNonExpenditure()));
				BigDecimal existingTotalSellNonExpenditure = new BigDecimal(
						bhaarathaSharesResponse.getBhaarathaSharesTotal().getSellNonExpenditure());
				BigDecimal totalSellNonExpenditure = sellSTT.add(existingTotalSellNonExpenditure);
				bhaarathaShares
						.setSellNonExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellNonExpenditure));
				bhaarathaSharesResponse.getBhaarathaSharesTotal()
						.setSellNonExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellNonExpenditure));

				BigDecimal sellTotal = new BigDecimal(String.valueOf(bhaarathaShares.getSellTotal()));
				BigDecimal existingTotalSellTotal = new BigDecimal(
						bhaarathaSharesResponse.getBhaarathaSharesTotal().getSellTotal());
				BigDecimal totalSellTotal = sellTotal.add(existingTotalSellTotal);
				bhaarathaShares.setSellTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellTotal));
				bhaarathaSharesResponse.getBhaarathaSharesTotal()
						.setSellTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellTotal));

				BigDecimal sellNetPricePerUnit = new BigDecimal(
						String.valueOf(bhaarathaShares.getSellNetPricePerUnit()));
				BigDecimal existingTotalSellNetPricePerUnit = new BigDecimal(
						bhaarathaSharesResponse.getBhaarathaSharesTotal().getSellNetPricePerUnit());
				BigDecimal totalSellNetPricePerUnit = sellNetPricePerUnit.add(existingTotalSellNetPricePerUnit);
				bhaarathaShares
						.setSellNetPricePerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellNetPricePerUnit));
				bhaarathaSharesResponse.getBhaarathaSharesTotal().setSellNetPricePerUnit(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellNetPricePerUnit));

				BigDecimal grossProfitLoss = new BigDecimal(bhaarathaShares.getGrossProfitLoss());
				BigDecimal totalGrossProfitLoss = totalSellGrossTotal.subtract(totalPurchaseGrossTotal);
				bhaarathaShares.setGrossProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, grossProfitLoss));
				bhaarathaSharesResponse.getBhaarathaSharesTotal()
						.setGrossProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalGrossProfitLoss));

				BigDecimal grossProfitLossPercentage = new BigDecimal(bhaarathaShares.getGrossProfitLossPercentage());
				BigDecimal totalGrossProfitLossPercentage;
				try {
					totalGrossProfitLossPercentage = totalSellGrossTotal
							.divide(totalPurchaseGrossTotal, MathContext.DECIMAL128)
							.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
							.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
				} catch (ArithmeticException e) {
					totalGrossProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_0);
				}
				bhaarathaShares.setGrossProfitLossPercentage(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, grossProfitLossPercentage));
				bhaarathaSharesResponse.getBhaarathaSharesTotal().setGrossProfitLossPercentage(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalGrossProfitLossPercentage));

				BigDecimal totalProfitLoss = new BigDecimal(bhaarathaShares.getTotalProfitLoss());
				BigDecimal totalTotalProfitLoss = totalSellTotal.subtract(totalPurchaseTotal);
				bhaarathaShares.setTotalProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLoss));
				bhaarathaSharesResponse.getBhaarathaSharesTotal()
						.setTotalProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalTotalProfitLoss));

				BigDecimal totalProfitLossPercentage = new BigDecimal(bhaarathaShares.getTotalProfitLossPercentage());
				BigDecimal totalTotalProfitLossPercentage;
				try {
					totalTotalProfitLossPercentage = totalSellTotal.divide(totalPurchaseTotal, MathContext.DECIMAL128)
							.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
							.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
				} catch (ArithmeticException e) {
					totalTotalProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_0);
				}
				bhaarathaShares.setTotalProfitLossPercentage(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLossPercentage));
				bhaarathaSharesResponse.getBhaarathaSharesTotal().setTotalProfitLossPercentage(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalTotalProfitLossPercentage));

				BigDecimal netProfitLossPerUnit = new BigDecimal(bhaarathaShares.getNetProfitLossPerUnit());
				BigDecimal totalNetProfitLossPerUnit = totalSellNetPricePerUnit.subtract(totalPurchaseNetPricePerUnit);
				bhaarathaShares
						.setNetProfitLossPerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL, netProfitLossPerUnit));
				bhaarathaSharesResponse.getBhaarathaSharesTotal().setNetProfitLossPerUnit(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalNetProfitLossPerUnit));
			}
			bhaarathaSharesResponse.setBhaarathaSharesList(bhaarathaSharesList);
		} else {
			for (BhaarathaShares bhaarathaShares : bhaarathaSharesList) {
				bhaarathaShares.setPurchaseQuantity(String.valueOf(bhaarathaShares.getPurchaseQuantity()));
				bhaarathaShares.setPurchasePrice(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(bhaarathaShares.getPurchasePrice())));
				bhaarathaShares.setPurchaseGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(bhaarathaShares.getPurchaseGrossTotal())));
				bhaarathaShares.setPurchaseBrokerage(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(bhaarathaShares.getPurchaseBrokerage())));
				bhaarathaShares.setPurchaseSTT(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(bhaarathaShares.getPurchaseSTT())));

				bhaarathaShares.setPurchaseExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(bhaarathaShares.getPurchaseExpenditure())));
				bhaarathaShares.setPurchaseNonExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(bhaarathaShares.getPurchaseNonExpenditure())));

				bhaarathaShares.setPurchaseTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(bhaarathaShares.getPurchaseTotal())));

				bhaarathaShares.setPurchaseNetPricePerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(bhaarathaShares.getPurchaseNetPricePerUnit())));

				bhaarathaShares.setSellQuantity(String.valueOf(bhaarathaShares.getSellQuantity()));
				bhaarathaShares.setSellPrice(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, new BigDecimal(bhaarathaShares.getSellPrice())));
				bhaarathaShares.setSellGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(bhaarathaShares.getSellGrossTotal())));
				bhaarathaShares.setSellBrokerage(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(bhaarathaShares.getSellBrokerage())));
				bhaarathaShares.setSellSTT(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, new BigDecimal(bhaarathaShares.getSellSTT())));

				bhaarathaShares.setSellExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(bhaarathaShares.getSellExpenditure())));
				bhaarathaShares.setSellNonExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(bhaarathaShares.getSellNonExpenditure())));

				bhaarathaShares.setSellTotal(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, new BigDecimal(bhaarathaShares.getSellTotal())));

				bhaarathaShares.setSellNetPricePerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(bhaarathaShares.getSellNetPricePerUnit())));

				bhaarathaShares.setGrossProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(bhaarathaShares.getGrossProfitLoss())));
				bhaarathaShares.setGrossProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(bhaarathaShares.getGrossProfitLossPercentage())));
				bhaarathaShares.setTotalProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(bhaarathaShares.getTotalProfitLoss())));
				bhaarathaShares.setTotalProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(bhaarathaShares.getTotalProfitLossPercentage())));
				bhaarathaShares.setNetProfitLossPerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(bhaarathaShares.getNetProfitLossPerUnit())));
			}
			bhaarathaSharesResponse.setBhaarathaSharesTotal(bhaarathaSharesList.get(bhaarathaSharesList.size() - 1));
			bhaarathaSharesList.remove(bhaarathaSharesList.size() - 1);
			bhaarathaSharesResponse.setBhaarathaSharesList(bhaarathaSharesList);
		}

		return bhaarathaSharesResponse;
	}

}
