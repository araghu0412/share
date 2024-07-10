package com.moneymanager.anukya.unitedstatesofamerica.eq;

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

import com.moneymanager.anukya.data.impl.UnitedStatesOfAmericaSharesData;
import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.ErrorDetails;
import com.moneymanager.anukya.model.CommonResponse;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaShares;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesResponse;
import com.moneymanager.anukya.utils.AnukyaConstants;
import com.moneymanager.anukya.utils.AnukyaDataUtils;
import com.moneymanager.anukya.utils.AnukyaUtils;
import com.moneymanager.anukya.utils.UnitedStatesOfAmericaSharesUtils;

@Component
public class UnitedStatesOfAmericaSoldShares extends AbstractUnitedStatesOfAmericaShares {

	@Value("${DATABASE.BASE.LOCATION}")
	private String databaseBaseLocation;

	@Autowired
	private AnukyaDataUtils anukyaDataUtils;

	@Autowired
	private UnitedStatesOfAmericaSharesData unitedStatesOfAmericaSharesData;

	@Autowired
	private AnukyaUtils anukyaUtils;

	@Autowired
	private UnitedStatesOfAmericaSharesUtils unitedStatesOfAmericaSharesUtils;

	public static final String ERROR_WHILE_ADDING_SOLD_SHARES = "Error while adding sold shares";

	@SuppressWarnings("unchecked")
	@Override
	public CommonResponse addShares(UnitedStatesOfAmericaShares soldUnitedStatesOfAmericaShares)
			throws AnukyaException {

		CommonResponse commonResponse = new CommonResponse();

		// Checking if script is purchased
		File singleScriptFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.USA_EQ_PURCHASE_MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ soldUnitedStatesOfAmericaShares.getYahooCode() + AnukyaConstants.JSON_EXTENSION);

		if (!anukyaDataUtils.isFileExists(singleScriptFile)) {

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0003);
			errorDetails.setErrorMessage("Script is not purchased");
			errorDetailsList.add(errorDetails);

			throw new AnukyaException(ERROR_WHILE_ADDING_SOLD_SHARES, errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		List<UnitedStatesOfAmericaShares> singleScriptList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(singleScriptFile);

		BigDecimal existingPurchasedQuantity = new BigDecimal(AnukyaConstants.NUMBER_0);
		for (UnitedStatesOfAmericaShares single : singleScriptList) {
			existingPurchasedQuantity = existingPurchasedQuantity.add(new BigDecimal(single.getPurchaseQuantity()));
		}

		// Checking if selling more shares than purchased
		if (existingPurchasedQuantity
				.compareTo(new BigDecimal(soldUnitedStatesOfAmericaShares.getSellQuantity())) < 0) {

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0003);
			errorDetails.setErrorMessage(
					"Trying to sell more shares than purchased for " + soldUnitedStatesOfAmericaShares.getScriptName()
							+ ". You currently own " + existingPurchasedQuantity + " number of share/s");
			errorDetailsList.add(errorDetails);

			throw new AnukyaException(ERROR_WHILE_ADDING_SOLD_SHARES, errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		File purchaseMainDirectory = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_PURCHASE_MAIN_DIRECTORY);
		File purchaseUpdateDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.USA_EQ_PURCHASE_UPDATE_DIRECTORY);
		File purchaseMainBackUpDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.USA_EQ_PURCHASE_MAIN_BACKUP_DIRECTORY);

		File soldMainDirectory = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_SOLD_MAIN_DIRECTORY);
		File soldUpdateDirectory = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_SOLD_UPDATE_DIRECTORY);
		File soldMainBackUpDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.USA_EQ_SOLD_MAIN_BACKUP_DIRECTORY);

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
				+ AnukyaConstants.USA_EQ_PURCHASE_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ soldUnitedStatesOfAmericaShares.getYahooCode() + AnukyaConstants.JSON_EXTENSION);
		File purchasedNonConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_PURCHASE_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);
		File purchasedConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_PURCHASE_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		File soldSingleScriptFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.USA_EQ_SOLD_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ soldUnitedStatesOfAmericaShares.getYahooCode() + AnukyaConstants.JSON_EXTENSION);
		File soldNonConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_SOLD_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);
		File soldConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_SOLD_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		UnitedStatesOfAmericaShares soldSingleScriptTotal = new UnitedStatesOfAmericaShares();
		UnitedStatesOfAmericaShares soldNonConsolidatedTotal = new UnitedStatesOfAmericaShares();
		UnitedStatesOfAmericaShares soldConsolidatedTotal = new UnitedStatesOfAmericaShares();

		List<UnitedStatesOfAmericaShares> purchasedSingleScriptList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(purchasedSingleScriptFile);
		List<UnitedStatesOfAmericaShares> soldSingleScriptList = new ArrayList<>();
		if (anukyaDataUtils.isFileExists(soldSingleScriptFile)) {
			soldSingleScriptList = unitedStatesOfAmericaSharesData.getUnitedStatesOfAmericaShares(soldSingleScriptFile);
			soldSingleScriptTotal = soldSingleScriptList.get(soldSingleScriptList.size() - 1);
			// Removing the last record for total calculation
			soldSingleScriptList.remove(soldSingleScriptList.size() - 1);
		}

		List<UnitedStatesOfAmericaShares> purchasedNonConsolidatedList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(purchasedNonConsolidatedFile);
		List<UnitedStatesOfAmericaShares> soldNonConsolidatedList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(soldNonConsolidatedFile);
		if (!soldNonConsolidatedList.isEmpty()) {
			soldNonConsolidatedTotal = soldNonConsolidatedList.get(soldNonConsolidatedList.size() - 1);
			// Removing the last record for total calculation
			soldNonConsolidatedList.remove(soldNonConsolidatedList.size() - 1);
		}

		List<UnitedStatesOfAmericaShares> purchasedConsolidatedList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(purchasedConsolidatedFile);
		List<UnitedStatesOfAmericaShares> soldConsolidatedList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(soldConsolidatedFile);
		if (!soldConsolidatedList.isEmpty()) {
			soldConsolidatedTotal = soldConsolidatedList.get(soldConsolidatedList.size() - 1);
			// Removing the last record for total calculation
			soldConsolidatedList.remove(soldConsolidatedList.size() - 1);
		}

		// Single script
		Map<String, Object> singleScriptSoldResponseMap = getNonConsolidatedSharesMap(purchasedSingleScriptList,
				soldUnitedStatesOfAmericaShares, soldSingleScriptTotal);
		if (((List<UnitedStatesOfAmericaShares>) singleScriptSoldResponseMap
				.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_BOUGHT_SHARES_LIST)).isEmpty()) {
			anukyaDataUtils.deleteFile(purchasedSingleScriptFile);
		} else {
			anukyaDataUtils.createOrUpdateFile(purchasedSingleScriptFile,
					(singleScriptSoldResponseMap.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_BOUGHT_SHARES_LIST)));
		}
		soldSingleScriptList.addAll((Collection<? extends UnitedStatesOfAmericaShares>) singleScriptSoldResponseMap
				.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_SOLD_SHARES_LIST));
		soldSingleScriptList.add(soldSingleScriptTotal);
		anukyaDataUtils.createOrUpdateFile(soldSingleScriptFile, soldSingleScriptList);

		// Non consolidated
		Map<String, Object> nonConsolidatedSoldResponseMap = getNonConsolidatedSharesMap(purchasedNonConsolidatedList,
				soldUnitedStatesOfAmericaShares, soldNonConsolidatedTotal);
		anukyaDataUtils.createOrUpdateFile(purchasedNonConsolidatedFile,
				(nonConsolidatedSoldResponseMap.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_BOUGHT_SHARES_LIST)));
		soldNonConsolidatedList
				.addAll((Collection<? extends UnitedStatesOfAmericaShares>) nonConsolidatedSoldResponseMap
						.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_SOLD_SHARES_LIST));
		soldNonConsolidatedList.add(soldNonConsolidatedTotal);
		anukyaDataUtils.createOrUpdateFile(soldNonConsolidatedFile, soldNonConsolidatedList);

		// Consolidated
		Map<String, UnitedStatesOfAmericaShares> scriptConsolidatedSoldShareMap = soldConsolidatedList.stream()
				.filter(soldConsolidate -> soldConsolidate.getYahooCode()
						.equalsIgnoreCase(soldUnitedStatesOfAmericaShares.getYahooCode()))
				.collect(Collectors.toMap(UnitedStatesOfAmericaShares::getYahooCode, i -> i));

		if (!scriptConsolidatedSoldShareMap.containsKey(soldUnitedStatesOfAmericaShares.getYahooCode().toUpperCase())) {
			scriptConsolidatedSoldShareMap.put(soldUnitedStatesOfAmericaShares.getYahooCode().toUpperCase(),
					new UnitedStatesOfAmericaShares());
		}

		Map<String, Object> consolidatedSoldResponseMap = getConsolidatedSoldShares(purchasedConsolidatedList,
				soldUnitedStatesOfAmericaShares,
				(Map<String, BigDecimal>) singleScriptSoldResponseMap
						.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_MAP),
				scriptConsolidatedSoldShareMap, soldConsolidatedTotal);
		anukyaDataUtils.createOrUpdateFile(purchasedConsolidatedFile,
				(consolidatedSoldResponseMap.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_BOUGHT_SHARES_LIST)));

		List<UnitedStatesOfAmericaShares> soldConsolidatedSharesList = new ArrayList<>();
		boolean addSoldConsolidatedScript = true;

		for (UnitedStatesOfAmericaShares soldConsolidated : soldConsolidatedList) {
			if (soldConsolidated.getYahooCode().equalsIgnoreCase(soldUnitedStatesOfAmericaShares.getYahooCode())) {
				soldConsolidatedSharesList.add((UnitedStatesOfAmericaShares) consolidatedSoldResponseMap
						.get(soldConsolidated.getYahooCode().toUpperCase()));
				addSoldConsolidatedScript = false;
			} else {
				soldConsolidatedSharesList.add(soldConsolidated);
			}
		}

		// When adding 1st sell for a script
		if (addSoldConsolidatedScript) {
			soldConsolidatedSharesList.add((UnitedStatesOfAmericaShares) consolidatedSoldResponseMap
					.get(soldUnitedStatesOfAmericaShares.getYahooCode().toUpperCase()));
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
	public UnitedStatesOfAmericaSharesResponse getUnitedStatesOfAmericaShares(boolean isNonConsolidated,
			String searchTerm, boolean isLongTermOnly) throws AnukyaException {

		List<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesList;

		if (isNonConsolidated) {
			unitedStatesOfAmericaSharesList = getUnitedStatesOfAmericaSharesNonConsolidated(searchTerm);
		} else {
			unitedStatesOfAmericaSharesList = getUnitedStatesOfAmericaSharesConsolidated(searchTerm);
		}

		if (unitedStatesOfAmericaSharesList.isEmpty()) {
			throw new AnukyaException("No content for United States of America Shares Short term investment", null,
					AnukyaErrorConstants.NO_CONTENT, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		return calculateUnitedStatesOfAmericaShares(unitedStatesOfAmericaSharesList, searchTerm);
	}

	// Private methods
	private Map<String, Object> getNonConsolidatedSharesMap(
			List<UnitedStatesOfAmericaShares> purchasedUnitedStatesOfAmericaSharesList,
			UnitedStatesOfAmericaShares soldUnitedStatesOfAmericaShares, UnitedStatesOfAmericaShares soldTotal)
			throws AnukyaException {

		List<UnitedStatesOfAmericaShares> boughtSharesList = new ArrayList<>();
		List<UnitedStatesOfAmericaShares> soldSharesList = new ArrayList<>();

		Map<String, BigDecimal> consolidatedCalculationMap = new HashMap<>();
		consolidatedCalculationMap.put(
				AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_QUANTITY,
				new BigDecimal(AnukyaConstants.NUMBER_0));

		consolidatedCalculationMap.put(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_PRICE,
				new BigDecimal(AnukyaConstants.NUMBER_0));

		consolidatedCalculationMap.put(
				AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_GROSS_TOTAL,
				new BigDecimal(AnukyaConstants.NUMBER_0));

		consolidatedCalculationMap.put(
				AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_COMMISSION,
				new BigDecimal(AnukyaConstants.NUMBER_0));

		consolidatedCalculationMap.put(
				AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_TRANSACTION_FEES,
				new BigDecimal(AnukyaConstants.NUMBER_0));

		consolidatedCalculationMap.put(
				AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_OTHER_FEES,
				new BigDecimal(AnukyaConstants.NUMBER_0));

		consolidatedCalculationMap.put(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_TOTAL,
				new BigDecimal(AnukyaConstants.NUMBER_0));

		consolidatedCalculationMap.put(
				AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_NET_PRICE_PER_UNIT,
				new BigDecimal(AnukyaConstants.NUMBER_0));

		String dateInddMmmYyyy = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);
		BigDecimal sellingCount = new BigDecimal(soldUnitedStatesOfAmericaShares.getSellQuantity());

		for (UnitedStatesOfAmericaShares unitedStatesOfAmericaShares : purchasedUnitedStatesOfAmericaSharesList) {

			if (unitedStatesOfAmericaShares.getYahooCode()
					.equalsIgnoreCase(soldUnitedStatesOfAmericaShares.getYahooCode())) {
				if (new BigDecimal(unitedStatesOfAmericaShares.getPurchaseQuantity()).compareTo(sellingCount) <= 0) {
					sellingCount = sellingCount
							.subtract(new BigDecimal(unitedStatesOfAmericaShares.getPurchaseQuantity()));

					BigDecimal sellQuantity = new BigDecimal(unitedStatesOfAmericaShares.getPurchaseQuantity());

					buildNonConsolidatedSellShares(soldUnitedStatesOfAmericaShares, unitedStatesOfAmericaShares,
							sellQuantity, soldTotal);

					unitedStatesOfAmericaShares.setCreatedBy(unitedStatesOfAmericaShares.getCreatedBy());
					unitedStatesOfAmericaShares.setCreatedDate(unitedStatesOfAmericaShares.getCreatedDate());
					unitedStatesOfAmericaShares.setLastUpdatedBy(userEmailId);
					unitedStatesOfAmericaShares.setLastUpdatedDate(dateInddMmmYyyy);

					soldSharesList.add(unitedStatesOfAmericaShares);

					getDetailsForConsolidatedSoldShares(unitedStatesOfAmericaShares, consolidatedCalculationMap);

					if (consolidatedCalculationMap
							.containsKey(unitedStatesOfAmericaShares.getYahooCode().toUpperCase())) {
						BigDecimal soldQuantityCount = consolidatedCalculationMap
								.get(unitedStatesOfAmericaShares.getYahooCode().toUpperCase()).add(sellQuantity);

						consolidatedCalculationMap.put(unitedStatesOfAmericaShares.getYahooCode().toUpperCase(),
								soldQuantityCount);
					} else {
						BigDecimal soldQuantityCount = new BigDecimal(AnukyaConstants.NUMBER_0).add(sellQuantity);
						consolidatedCalculationMap.put(unitedStatesOfAmericaShares.getYahooCode().toUpperCase(),
								soldQuantityCount);
					}
				} else if (sellingCount.compareTo(new BigDecimal(AnukyaConstants.NUMBER_0)) != 0) {

					BigDecimal existingPurchaseQuantity = new BigDecimal(
							unitedStatesOfAmericaShares.getPurchaseQuantity());
					BigDecimal updatedPurchaseQuantity = existingPurchaseQuantity.subtract(sellingCount);
					BigDecimal sellQuantity = sellingCount;

					// Adjusting bought shares when sell quantity is less than purchase quantity
					UnitedStatesOfAmericaShares temp = new UnitedStatesOfAmericaShares();
					temp.setId(unitedStatesOfAmericaShares.getId());
					temp.setYahooCode(unitedStatesOfAmericaShares.getYahooCode());
					temp.setIsinCode(unitedStatesOfAmericaShares.getIsinCode());
					temp.setCusip(unitedStatesOfAmericaShares.getCusip());
					temp.setScriptName(unitedStatesOfAmericaShares.getScriptName());
					temp.setCategory(unitedStatesOfAmericaShares.getCategory());
					temp.setSector(unitedStatesOfAmericaShares.getSector());
					temp.setIndustry(unitedStatesOfAmericaShares.getIndustry());
					temp.setShortTermInvestment(unitedStatesOfAmericaShares.isShortTermInvestment());
					temp.setSharesSplitSelected(unitedStatesOfAmericaShares.isSharesSplitSelected());
					temp.setSharesSplitRatio(unitedStatesOfAmericaShares.getSharesSplitRatio());
					temp.setSharesSplitDate(unitedStatesOfAmericaShares.getSharesSplitDate());
					temp.setSharesSplit(unitedStatesOfAmericaShares.getSharesSplit());
					temp.setSharesSplitDisplay(unitedStatesOfAmericaShares.getSharesSplitDisplay());
					temp.setSharesBonusSelected(unitedStatesOfAmericaShares.isSharesBonusSelected());
					temp.setSharesBonusRatio(unitedStatesOfAmericaShares.getSharesBonusRatio());
					temp.setSharesBonusDate(unitedStatesOfAmericaShares.getSharesBonusDate());
					temp.setSharesBonus(unitedStatesOfAmericaShares.getSharesBonus());
					temp.setSharesBonusDisplay(unitedStatesOfAmericaShares.getSharesBonusDisplay());
					temp.setPurchaseDate(unitedStatesOfAmericaShares.getPurchaseDate());
					temp.setPurchaseQuantity(String.valueOf(updatedPurchaseQuantity));
					temp.setPurchasePrice(unitedStatesOfAmericaShares.getPurchasePrice());

					BigDecimal updatedPurchaseGrossTotal = updatedPurchaseQuantity
							.multiply(new BigDecimal(unitedStatesOfAmericaShares.getPurchasePrice()));
					temp.setPurchaseGrossTotal(String.valueOf(updatedPurchaseGrossTotal));

					BigDecimal existingPurchaseCommission = new BigDecimal(
							unitedStatesOfAmericaShares.getPurchaseCommission());
					BigDecimal updatedPurchaseCommission = existingPurchaseCommission.multiply(existingPurchaseQuantity
							.subtract(sellingCount).divide(existingPurchaseQuantity, MathContext.DECIMAL128));
					temp.setPurchaseCommission(String.valueOf(updatedPurchaseCommission));

					BigDecimal existingPurchaseTransactionFees = new BigDecimal(
							unitedStatesOfAmericaShares.getPurchaseTransactionFees());
					BigDecimal updatedPurchaseTransactionFees = existingPurchaseTransactionFees
							.multiply(existingPurchaseQuantity.subtract(sellingCount).divide(existingPurchaseQuantity,
									MathContext.DECIMAL128));
					temp.setPurchaseTransactionFees(String.valueOf(updatedPurchaseTransactionFees));

					BigDecimal existingPurchaseOtherFees = new BigDecimal(
							unitedStatesOfAmericaShares.getPurchaseOtherFees());
					BigDecimal updatedPurchaseOtherFees = existingPurchaseOtherFees.multiply(existingPurchaseQuantity
							.subtract(sellingCount).divide(existingPurchaseQuantity, MathContext.DECIMAL128));
					temp.setPurchaseOtherFees(String.valueOf(updatedPurchaseOtherFees));

					BigDecimal updatedPurchaseTotal = updatedPurchaseGrossTotal.add(updatedPurchaseCommission)
							.add(updatedPurchaseTransactionFees).add(updatedPurchaseOtherFees);
					temp.setPurchaseTotal(String.valueOf(updatedPurchaseTotal));

					BigDecimal updatedPurchaseNetPricePerUnit = updatedPurchaseTotal.divide(updatedPurchaseQuantity,
							MathContext.DECIMAL128);
					temp.setPurchaseNetPricePerUnit(String.valueOf(updatedPurchaseNetPricePerUnit));

					temp.setShortTermInvestment(unitedStatesOfAmericaShares.isShortTermInvestment());
					temp.setShortTerm(unitedStatesOfAmericaShares.isShortTerm());
					temp.setCreatedBy(unitedStatesOfAmericaShares.getCreatedBy());
					temp.setCreatedDate(unitedStatesOfAmericaShares.getCreatedDate());
					temp.setLastUpdatedBy(userEmailId);
					temp.setLastUpdatedDate(dateInddMmmYyyy);

					boughtSharesList.add(temp);

					// Updating sell shares
					BigDecimal purchaseGrossTotal = sellQuantity
							.multiply(new BigDecimal(unitedStatesOfAmericaShares.getPurchasePrice()));
					unitedStatesOfAmericaShares.setPurchaseGrossTotal(String.valueOf(purchaseGrossTotal));

					BigDecimal purchaseCommission = new BigDecimal(unitedStatesOfAmericaShares.getPurchaseCommission())
							.multiply(sellQuantity.divide(
									new BigDecimal(unitedStatesOfAmericaShares.getPurchaseQuantity()),
									MathContext.DECIMAL128));
					unitedStatesOfAmericaShares.setPurchaseCommission(String.valueOf(purchaseCommission));

					BigDecimal purchaseTransactionFees = new BigDecimal(
							unitedStatesOfAmericaShares.getPurchaseTransactionFees())
									.multiply(sellQuantity.divide(
											new BigDecimal(unitedStatesOfAmericaShares.getPurchaseQuantity()),
											MathContext.DECIMAL128));
					unitedStatesOfAmericaShares.setPurchaseTransactionFees(String.valueOf(purchaseTransactionFees));

					BigDecimal purchaseOtherFees = new BigDecimal(unitedStatesOfAmericaShares.getPurchaseOtherFees())
							.multiply(sellQuantity.divide(
									new BigDecimal(unitedStatesOfAmericaShares.getPurchaseQuantity()),
									MathContext.DECIMAL128));
					unitedStatesOfAmericaShares.setPurchaseOtherFees(String.valueOf(purchaseOtherFees));

					BigDecimal purchaseTotal = purchaseGrossTotal.add(purchaseCommission).add(purchaseTransactionFees)
							.add(purchaseOtherFees);
					unitedStatesOfAmericaShares.setPurchaseTotal(String.valueOf(purchaseTotal));

					BigDecimal purchaseNetPricePerUnit = purchaseTotal.divide(sellQuantity, MathContext.DECIMAL128);
					unitedStatesOfAmericaShares.setPurchaseNetPricePerUnit(String.valueOf(purchaseNetPricePerUnit));

					unitedStatesOfAmericaShares.setPurchaseQuantity(String.valueOf(sellQuantity));

					buildNonConsolidatedSellShares(soldUnitedStatesOfAmericaShares, unitedStatesOfAmericaShares,
							sellQuantity, soldTotal);

					unitedStatesOfAmericaShares.setCreatedBy(unitedStatesOfAmericaShares.getCreatedBy());
					unitedStatesOfAmericaShares.setCreatedDate(unitedStatesOfAmericaShares.getCreatedDate());
					unitedStatesOfAmericaShares.setLastUpdatedBy(userEmailId);
					unitedStatesOfAmericaShares.setLastUpdatedDate(dateInddMmmYyyy);

					soldSharesList.add(unitedStatesOfAmericaShares);

					getDetailsForConsolidatedSoldShares(unitedStatesOfAmericaShares, consolidatedCalculationMap);

					sellingCount = new BigDecimal(AnukyaConstants.NUMBER_0);

					if (consolidatedCalculationMap
							.containsKey(unitedStatesOfAmericaShares.getYahooCode().toUpperCase())) {
						BigDecimal soldQuantityCount = consolidatedCalculationMap
								.get(unitedStatesOfAmericaShares.getYahooCode().toUpperCase()).add(sellQuantity);

						consolidatedCalculationMap.put(unitedStatesOfAmericaShares.getYahooCode().toUpperCase(),
								soldQuantityCount);
					} else {
						BigDecimal soldQuantityCount = new BigDecimal(AnukyaConstants.NUMBER_0).add(sellQuantity);
						consolidatedCalculationMap.put(unitedStatesOfAmericaShares.getYahooCode().toUpperCase(),
								soldQuantityCount);
					}

				} else {
					boughtSharesList.add(unitedStatesOfAmericaShares);
				}
			} else {
				boughtSharesList.add(unitedStatesOfAmericaShares);
			}
		}

		Map<String, Object> nonConsolidatedSoldSharesMap = new HashMap<>();
		nonConsolidatedSoldSharesMap.put(AnukyaConstants.UNITED_STATES_OF_AMERICA_BOUGHT_SHARES_LIST, boughtSharesList);
		nonConsolidatedSoldSharesMap.put(AnukyaConstants.UNITED_STATES_OF_AMERICA_SOLD_SHARES_LIST, soldSharesList);
		nonConsolidatedSoldSharesMap.put(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_MAP,
				consolidatedCalculationMap);

		return nonConsolidatedSoldSharesMap;
	}

	private void buildNonConsolidatedSellShares(UnitedStatesOfAmericaShares soldUnitedStatesOfAmericaShares,
			UnitedStatesOfAmericaShares unitedStatesOfAmericaShares, BigDecimal sellQuantity,
			UnitedStatesOfAmericaShares soldTotal) throws AnukyaException {

		BigDecimal totalSoldQuantity = new BigDecimal(soldUnitedStatesOfAmericaShares.getSellQuantity());

		unitedStatesOfAmericaShares.setId(anukyaUtils.generateId());

		BigDecimal purchaseQuantity = new BigDecimal(unitedStatesOfAmericaShares.getPurchaseQuantity());
		BigDecimal existingTotalPurchaseQuantity = new BigDecimal(soldTotal.getPurchaseQuantity());
		BigDecimal totalPurchaseQuantity = purchaseQuantity.add(existingTotalPurchaseQuantity);
		soldTotal.setPurchaseQuantity(String.valueOf(totalPurchaseQuantity));

		BigDecimal purchasePrice = new BigDecimal(unitedStatesOfAmericaShares.getPurchasePrice());
		BigDecimal existingTotalPurchasePrice = new BigDecimal(soldTotal.getPurchasePrice());
		BigDecimal totalPurchasePrice = purchaseQuantity.multiply(purchasePrice)
				.add(existingTotalPurchaseQuantity.multiply(existingTotalPurchasePrice))
				.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
		soldTotal.setPurchasePrice(String.valueOf(totalPurchasePrice));

		BigDecimal purchaseGrossTotal = new BigDecimal(unitedStatesOfAmericaShares.getPurchaseGrossTotal());
		BigDecimal existingTotalPurchaseGrossTotal = new BigDecimal(soldTotal.getPurchaseGrossTotal());
		BigDecimal totalPurchaseGrossTotal = purchaseGrossTotal.add(existingTotalPurchaseGrossTotal);
		soldTotal.setPurchaseGrossTotal(String.valueOf(totalPurchaseGrossTotal));

		BigDecimal purchaseCommission = new BigDecimal(unitedStatesOfAmericaShares.getPurchaseCommission());
		BigDecimal existingTotalPurchaseCommission = new BigDecimal(soldTotal.getPurchaseCommission());
		BigDecimal totalPurchaseCommission = purchaseCommission.add(existingTotalPurchaseCommission);
		soldTotal.setPurchaseCommission(String.valueOf(totalPurchaseCommission));

		BigDecimal purchaseTransactionFees = new BigDecimal(unitedStatesOfAmericaShares.getPurchaseTransactionFees());
		BigDecimal existingTotalPurchaseTransactionFees = new BigDecimal(soldTotal.getPurchaseTransactionFees());
		BigDecimal totalPurchaseTransactionFees = purchaseTransactionFees.add(existingTotalPurchaseTransactionFees);
		soldTotal.setPurchaseTransactionFees(String.valueOf(totalPurchaseTransactionFees));

		BigDecimal purchaseOtherFees = new BigDecimal(unitedStatesOfAmericaShares.getPurchaseOtherFees());
		BigDecimal existingTotalPurchaseOtherFees = new BigDecimal(soldTotal.getPurchaseOtherFees());
		BigDecimal totalPurchaseOtherFees = purchaseOtherFees.add(existingTotalPurchaseOtherFees);
		soldTotal.setPurchaseOtherFees(String.valueOf(totalPurchaseOtherFees));

		BigDecimal purchaseTotal = new BigDecimal(unitedStatesOfAmericaShares.getPurchaseTotal());
		BigDecimal existingTotalPurchaseTotal = new BigDecimal(soldTotal.getPurchaseTotal());
		BigDecimal totalPurchaseTotal = purchaseTotal.add(existingTotalPurchaseTotal);
		soldTotal.setPurchaseTotal(String.valueOf(totalPurchaseTotal));

		BigDecimal purchaseNetPricePerUnit = new BigDecimal(unitedStatesOfAmericaShares.getPurchaseNetPricePerUnit());
		BigDecimal existingTotalPurchaseNetPricePerUnit = new BigDecimal(soldTotal.getPurchaseNetPricePerUnit());
		BigDecimal totalPurchaseNetPricePerUnit = purchaseQuantity.multiply(purchaseNetPricePerUnit)
				.add(existingTotalPurchaseQuantity.multiply(existingTotalPurchaseNetPricePerUnit))
				.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
		soldTotal.setPurchaseNetPricePerUnit(String.valueOf(totalPurchaseNetPricePerUnit));

		unitedStatesOfAmericaShares.setSellDate(soldUnitedStatesOfAmericaShares.getSellDate());

		unitedStatesOfAmericaShares.setSellQuantity(String.valueOf(sellQuantity));
		BigDecimal existingTotalSellQuantity = new BigDecimal(soldTotal.getSellQuantity());
		BigDecimal totalSellQuantity = sellQuantity.add(existingTotalSellQuantity);
		soldTotal.setSellQuantity(String.valueOf(totalSellQuantity));

		BigDecimal sellPrice = new BigDecimal(soldUnitedStatesOfAmericaShares.getSellPrice());
		unitedStatesOfAmericaShares.setSellPrice(String.valueOf(sellPrice));
		BigDecimal existingTotalSellPrice = new BigDecimal(soldTotal.getSellPrice());
		BigDecimal totalSellPrice = sellQuantity.multiply(sellPrice)
				.add(existingTotalSellQuantity.multiply(existingTotalSellPrice))
				.divide(totalSellQuantity, MathContext.DECIMAL128);
		soldTotal.setSellPrice(String.valueOf(totalSellPrice));

		BigDecimal sellGrossTotal = sellQuantity.multiply(sellPrice);
		unitedStatesOfAmericaShares.setSellGrossTotal(String.valueOf(sellGrossTotal));
		BigDecimal existingTotalSellGrossTotal = new BigDecimal(soldTotal.getSellGrossTotal());
		BigDecimal totalSellGrossTotal = sellGrossTotal.add(existingTotalSellGrossTotal);
		soldTotal.setSellGrossTotal(String.valueOf(totalSellGrossTotal));

		BigDecimal sellCommission = new BigDecimal(soldUnitedStatesOfAmericaShares.getSellCommission())
				.multiply(sellQuantity).divide(totalSoldQuantity, MathContext.DECIMAL128);
		unitedStatesOfAmericaShares.setSellCommission(String.valueOf(sellCommission));
		BigDecimal existingTotalSellCommission = new BigDecimal(soldTotal.getSellCommission());
		BigDecimal totalSellCommission = sellCommission.add(existingTotalSellCommission);
		soldTotal.setSellCommission(String.valueOf(totalSellCommission));

		BigDecimal sellTransactionFees = new BigDecimal(soldUnitedStatesOfAmericaShares.getSellTransactionFees())
				.multiply(sellQuantity).divide(totalSoldQuantity, MathContext.DECIMAL128);
		unitedStatesOfAmericaShares.setSellTransactionFees(String.valueOf(sellTransactionFees));
		BigDecimal existingTotalSellTransactionFees = new BigDecimal(soldTotal.getSellTransactionFees());
		BigDecimal totalSellTransactionFees = sellTransactionFees.add(existingTotalSellTransactionFees);
		soldTotal.setSellTransactionFees(String.valueOf(totalSellTransactionFees));

		BigDecimal sellOtherFees = new BigDecimal(soldUnitedStatesOfAmericaShares.getSellOtherFees())
				.multiply(sellQuantity).divide(totalSoldQuantity, MathContext.DECIMAL128);
		unitedStatesOfAmericaShares.setSellOtherFees(String.valueOf(sellOtherFees));
		BigDecimal existingTotalSellOtherFees = new BigDecimal(soldTotal.getSellOtherFees());
		BigDecimal totalSellOtherFees = sellOtherFees.add(existingTotalSellOtherFees);
		soldTotal.setSellOtherFees(String.valueOf(totalSellOtherFees));

		BigDecimal sellTotal = sellGrossTotal.subtract(sellCommission).subtract(sellTransactionFees)
				.subtract(sellOtherFees);
		unitedStatesOfAmericaShares.setSellTotal(String.valueOf(sellTotal));
		BigDecimal existingTotalSellTotal = new BigDecimal(soldTotal.getSellTotal());
		BigDecimal totalSellTotal = sellTotal.add(existingTotalSellTotal);
		soldTotal.setSellTotal(String.valueOf(totalSellTotal));

		BigDecimal sellNetPricePerUnit = sellTotal.divide(sellQuantity, MathContext.DECIMAL128);
		unitedStatesOfAmericaShares.setSellNetPricePerUnit(String.valueOf(sellNetPricePerUnit));
		BigDecimal existingTotalSellNetPricePerUnit = new BigDecimal(soldTotal.getSellNetPricePerUnit());
		BigDecimal totalSellNetPricePerUnit = sellQuantity.multiply(sellNetPricePerUnit)
				.add(existingTotalSellQuantity.multiply(existingTotalSellNetPricePerUnit))
				.divide(totalSellQuantity, MathContext.DECIMAL128);
		soldTotal.setSellNetPricePerUnit(String.valueOf(totalSellNetPricePerUnit));

		unitedStatesOfAmericaShares.setShortTerm(unitedStatesOfAmericaSharesUtils
				.isShortTerm(unitedStatesOfAmericaShares.getPurchaseDate(), unitedStatesOfAmericaShares.getSellDate()));

		BigDecimal grossProfitLoss = sellGrossTotal.subtract(purchaseGrossTotal);
		unitedStatesOfAmericaShares.setGrossProfitLoss(String.valueOf(grossProfitLoss));
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
		unitedStatesOfAmericaShares.setGrossProfitLossPercentage(String.valueOf(grossProfitLossPercentage));

		BigDecimal totalGrossProfitLossPercentage;
		try {
			totalGrossProfitLossPercentage = totalSellGrossTotal.divide(totalPurchaseGrossTotal, MathContext.DECIMAL128)
					.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
					.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
		} catch (ArithmeticException e) {
			totalGrossProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_0);
		}
		soldTotal.setGrossProfitLossPercentage(String.valueOf(totalGrossProfitLossPercentage));

		BigDecimal totalProfitLoss = sellTotal.subtract(new BigDecimal(unitedStatesOfAmericaShares.getPurchaseTotal()));
		unitedStatesOfAmericaShares.setTotalProfitLoss(String.valueOf(totalProfitLoss));
		BigDecimal totalTotalProfitLoss = totalSellTotal.subtract(totalPurchaseTotal);
		soldTotal.setTotalProfitLoss(String.valueOf(totalTotalProfitLoss));

		BigDecimal totalProfitLossPercentage;
		try {
			totalProfitLossPercentage = sellTotal
					.divide(new BigDecimal(unitedStatesOfAmericaShares.getPurchaseTotal()), MathContext.DECIMAL128)
					.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
					.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
		} catch (ArithmeticException e) {
			totalProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_0);
		}
		unitedStatesOfAmericaShares.setTotalProfitLossPercentage(String.valueOf(totalProfitLossPercentage));

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
				.subtract(new BigDecimal(unitedStatesOfAmericaShares.getPurchaseNetPricePerUnit()));
		unitedStatesOfAmericaShares.setNetProfitLossPerUnit(String.valueOf(netProfitLossPerUnit));
		BigDecimal totalNetProfitLossPerUnit = totalSellNetPricePerUnit.subtract(totalPurchaseNetPricePerUnit);
		soldTotal.setNetProfitLossPerUnit(String.valueOf(totalNetProfitLossPerUnit));

	}

	private void getDetailsForConsolidatedSoldShares(UnitedStatesOfAmericaShares unitedStatesOfAmericaShares,
			Map<String, BigDecimal> consolidatedCalculationMap) {

		BigDecimal existingTotalPurchaseQuantity = consolidatedCalculationMap
				.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_QUANTITY);
		BigDecimal purchaseQuantity = new BigDecimal(unitedStatesOfAmericaShares.getPurchaseQuantity());
		BigDecimal totalPurchaseQuantity = existingTotalPurchaseQuantity.add(purchaseQuantity);
		consolidatedCalculationMap.put(
				AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_QUANTITY,
				totalPurchaseQuantity);

		BigDecimal existingTotalPurchasePrice = consolidatedCalculationMap
				.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_PRICE);
		BigDecimal purchasePrice = new BigDecimal(unitedStatesOfAmericaShares.getPurchasePrice());
		BigDecimal totalPurchasePrice = existingTotalPurchaseQuantity.multiply(existingTotalPurchasePrice)
				.add(purchaseQuantity.multiply(purchasePrice)).divide(totalPurchaseQuantity, MathContext.DECIMAL128);
		consolidatedCalculationMap.put(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_PRICE,
				totalPurchasePrice);

		BigDecimal existingTotalPurchaseGrossTotal = consolidatedCalculationMap
				.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_GROSS_TOTAL);
		BigDecimal purchaseGrossTotal = new BigDecimal(unitedStatesOfAmericaShares.getPurchaseGrossTotal());
		BigDecimal totalPurchaseGrossTotal = existingTotalPurchaseGrossTotal.add(purchaseGrossTotal);
		consolidatedCalculationMap.put(
				AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_GROSS_TOTAL,
				totalPurchaseGrossTotal);

		BigDecimal existingTotalPurchaseCommission = consolidatedCalculationMap
				.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_COMMISSION);
		BigDecimal purchaseCommission = new BigDecimal(unitedStatesOfAmericaShares.getPurchaseCommission());
		BigDecimal totalPurchaseCommission = existingTotalPurchaseCommission.add(purchaseCommission);
		consolidatedCalculationMap.put(
				AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_COMMISSION,
				totalPurchaseCommission);

		BigDecimal existingTotalPurchaseTransactionFees = consolidatedCalculationMap
				.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_TRANSACTION_FEES);
		BigDecimal purchaseTransactionFees = new BigDecimal(unitedStatesOfAmericaShares.getPurchaseTransactionFees());
		BigDecimal totalPurchaseTransactionFees = existingTotalPurchaseTransactionFees.add(purchaseTransactionFees);
		consolidatedCalculationMap.put(
				AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_TRANSACTION_FEES,
				totalPurchaseTransactionFees);

		BigDecimal existingTotalPurchaseOtherFees = consolidatedCalculationMap
				.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_OTHER_FEES);
		BigDecimal purchaseOtherFees = new BigDecimal(unitedStatesOfAmericaShares.getPurchaseOtherFees());
		BigDecimal totalPurchaseOtherFees = existingTotalPurchaseOtherFees.add(purchaseOtherFees);
		consolidatedCalculationMap.put(
				AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_OTHER_FEES,
				totalPurchaseOtherFees);

		BigDecimal existingTotalPurchaseTotal = consolidatedCalculationMap
				.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_TOTAL);
		BigDecimal purchaseTotal = new BigDecimal(unitedStatesOfAmericaShares.getPurchaseTotal());
		BigDecimal totalPurchaseTotal = existingTotalPurchaseTotal.add(purchaseTotal);
		consolidatedCalculationMap.put(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_TOTAL,
				totalPurchaseTotal);

		BigDecimal existingTotalPurchaseNetPricePerUnit = consolidatedCalculationMap
				.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_NET_PRICE_PER_UNIT);
		BigDecimal purchaseNetPricePerUnit = new BigDecimal(unitedStatesOfAmericaShares.getPurchaseNetPricePerUnit());
		BigDecimal totalPurchaseNetPricePerUnit = existingTotalPurchaseQuantity
				.multiply(existingTotalPurchaseNetPricePerUnit).add(purchaseQuantity.multiply(purchaseNetPricePerUnit))
				.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
		consolidatedCalculationMap.put(
				AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_NET_PRICE_PER_UNIT,
				totalPurchaseNetPricePerUnit);
	}

	private Map<String, Object> getConsolidatedSoldShares(
			List<UnitedStatesOfAmericaShares> purchaseUnitedStatesOfAmericaSharesList,
			UnitedStatesOfAmericaShares soldUnitedStatesOfAmericaShares,
			Map<String, BigDecimal> unitedStatesOfAmericaConsolidatedCalculationMap,
			Map<String, UnitedStatesOfAmericaShares> scriptConsolidatedSoldShareMap,
			UnitedStatesOfAmericaShares soldTotal) {

		Map<String, Object> consolidatedSoldSharesMap = new HashMap<>();

		List<UnitedStatesOfAmericaShares> boughtSharesList = new ArrayList<>();

		String dateInddMmmYyyy = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		for (UnitedStatesOfAmericaShares unitedStatesOfAmericaShares : purchaseUnitedStatesOfAmericaSharesList) {
			if (unitedStatesOfAmericaShares.getYahooCode()
					.equalsIgnoreCase(soldUnitedStatesOfAmericaShares.getYahooCode())) {
				if (new BigDecimal(unitedStatesOfAmericaShares.getPurchaseQuantity())
						.compareTo(new BigDecimal(soldUnitedStatesOfAmericaShares.getSellQuantity())) == 0) {
					UnitedStatesOfAmericaShares soldShare = scriptConsolidatedSoldShareMap
							.get(unitedStatesOfAmericaShares.getYahooCode().toUpperCase());

					buildConsolidatedSellShares(soldUnitedStatesOfAmericaShares, unitedStatesOfAmericaShares, soldShare,
							soldTotal, unitedStatesOfAmericaConsolidatedCalculationMap);

					soldShare.setCreatedBy(unitedStatesOfAmericaShares.getCreatedBy());
					soldShare.setCreatedDate(unitedStatesOfAmericaShares.getCreatedDate());
					soldShare.setLastUpdatedBy(userEmailId);
					soldShare.setLastUpdatedDate(dateInddMmmYyyy);

					consolidatedSoldSharesMap.put(unitedStatesOfAmericaShares.getYahooCode().toUpperCase(), soldShare);
				} else if (unitedStatesOfAmericaConsolidatedCalculationMap
						.get(unitedStatesOfAmericaShares.getYahooCode().toUpperCase())
						.compareTo(new BigDecimal(AnukyaConstants.NUMBER_0)) != 0) {

					// Adjusting bought shares when sell quantity is less than purchase quantity
					UnitedStatesOfAmericaShares temp = new UnitedStatesOfAmericaShares();
					temp.setId(unitedStatesOfAmericaShares.getId());
					temp.setYahooCode(unitedStatesOfAmericaShares.getYahooCode());
					temp.setIsinCode(unitedStatesOfAmericaShares.getIsinCode());
					temp.setScriptName(unitedStatesOfAmericaShares.getScriptName());
					temp.setCategory(unitedStatesOfAmericaShares.getCategory());
					temp.setSector(unitedStatesOfAmericaShares.getSector());
					temp.setIndustry(unitedStatesOfAmericaShares.getIndustry());
					temp.setShortTermInvestment(unitedStatesOfAmericaShares.isShortTermInvestment());
					temp.setSharesSplitSelected(unitedStatesOfAmericaShares.isSharesSplitSelected());
					temp.setSharesSplitRatio(unitedStatesOfAmericaShares.getSharesSplitRatio());
					temp.setSharesSplitDate(unitedStatesOfAmericaShares.getSharesSplitDate());
					temp.setSharesSplit(unitedStatesOfAmericaShares.getSharesSplit());
					temp.setSharesSplitDisplay(unitedStatesOfAmericaShares.getSharesSplitDisplay());
					temp.setSharesBonusSelected(unitedStatesOfAmericaShares.isSharesBonusSelected());
					temp.setSharesBonusRatio(unitedStatesOfAmericaShares.getSharesBonusRatio());
					temp.setSharesBonusDate(unitedStatesOfAmericaShares.getSharesBonusDate());
					temp.setSharesBonus(unitedStatesOfAmericaShares.getSharesBonus());
					temp.setSharesBonusDisplay(unitedStatesOfAmericaShares.getSharesBonusDisplay());
					temp.setPurchaseDate(unitedStatesOfAmericaShares.getPurchaseDate());

					BigDecimal existingPurchaseQuantity = new BigDecimal(
							unitedStatesOfAmericaShares.getPurchaseQuantity());
					BigDecimal soldPurchaseQuantity = unitedStatesOfAmericaConsolidatedCalculationMap
							.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_QUANTITY);
					BigDecimal purchaseQuantity = existingPurchaseQuantity.subtract(soldPurchaseQuantity);
					temp.setPurchaseQuantity(String.valueOf(purchaseQuantity));

					BigDecimal existingPurchasePrice = new BigDecimal(unitedStatesOfAmericaShares.getPurchasePrice());
					BigDecimal soldPurchasePrice = unitedStatesOfAmericaConsolidatedCalculationMap
							.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_PRICE);
					BigDecimal purchasePrice = existingPurchaseQuantity.multiply(existingPurchasePrice)
							.subtract(soldPurchaseQuantity.multiply(soldPurchasePrice))
							.divide(purchaseQuantity, MathContext.DECIMAL128);
					temp.setPurchasePrice(String.valueOf(purchasePrice));

					BigDecimal existingPurchaseGrossTotal = new BigDecimal(
							unitedStatesOfAmericaShares.getPurchaseGrossTotal());
					BigDecimal soldPurchaseGrossTotal = soldPurchaseQuantity.multiply(soldPurchasePrice);
					BigDecimal purchaseGrossTotal = existingPurchaseGrossTotal.subtract(soldPurchaseGrossTotal);
					temp.setPurchaseGrossTotal(String.valueOf(purchaseGrossTotal));

					BigDecimal existingPurchaseCommission = new BigDecimal(
							unitedStatesOfAmericaShares.getPurchaseCommission());
					BigDecimal soldPurchaseCommission = unitedStatesOfAmericaConsolidatedCalculationMap
							.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_COMMISSION);
					BigDecimal purchaseCommission = existingPurchaseCommission.subtract(soldPurchaseCommission);
					temp.setPurchaseCommission(String.valueOf(purchaseCommission));

					BigDecimal existingPurchaseTransactionFees = new BigDecimal(
							unitedStatesOfAmericaShares.getPurchaseTransactionFees());
					BigDecimal soldPurchaseTransactionFees = unitedStatesOfAmericaConsolidatedCalculationMap.get(
							AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_TRANSACTION_FEES);
					BigDecimal purchaseTransactionFees = existingPurchaseTransactionFees
							.subtract(soldPurchaseTransactionFees);
					temp.setPurchaseTransactionFees(String.valueOf(purchaseTransactionFees));

					BigDecimal existingPurchaseOtherFees = new BigDecimal(
							unitedStatesOfAmericaShares.getPurchaseOtherFees());
					BigDecimal soldPurchaseOtherFees = unitedStatesOfAmericaConsolidatedCalculationMap
							.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_OTHER_FEES);
					BigDecimal purchaseOtherFees = existingPurchaseOtherFees.subtract(soldPurchaseOtherFees);
					temp.setPurchaseOtherFees(String.valueOf(purchaseOtherFees));

					BigDecimal existingPurchaseTotal = new BigDecimal(unitedStatesOfAmericaShares.getPurchaseTotal());
					BigDecimal soldPurchaseTotal = unitedStatesOfAmericaConsolidatedCalculationMap
							.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_TOTAL);
					BigDecimal purchaseTotal = existingPurchaseTotal.subtract(soldPurchaseTotal);
					temp.setPurchaseTotal(String.valueOf(purchaseTotal));

					BigDecimal existingPurchaseNetPricePerUnit = new BigDecimal(
							unitedStatesOfAmericaShares.getPurchaseNetPricePerUnit());
					BigDecimal soldPurchaseNetPricePerUnit = unitedStatesOfAmericaConsolidatedCalculationMap.get(
							AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_NET_PRICE_PER_UNIT);
					BigDecimal purchaseNetPricePerUnit = existingPurchaseQuantity
							.multiply(existingPurchaseNetPricePerUnit)
							.subtract(soldPurchaseQuantity.multiply(soldPurchaseNetPricePerUnit))
							.divide(purchaseQuantity, MathContext.DECIMAL128);
					temp.setPurchaseNetPricePerUnit(String.valueOf(purchaseNetPricePerUnit));

					temp.setShortTermInvestment(unitedStatesOfAmericaShares.isShortTermInvestment());
					temp.setShortTerm(unitedStatesOfAmericaShares.isShortTerm());
					temp.setCreatedBy(unitedStatesOfAmericaShares.getCreatedBy());
					temp.setCreatedDate(unitedStatesOfAmericaShares.getCreatedDate());
					temp.setLastUpdatedBy(userEmailId);
					temp.setLastUpdatedDate(dateInddMmmYyyy);

					boughtSharesList.add(temp);

					// Updating sell shares
					BigDecimal soldQuantity = unitedStatesOfAmericaConsolidatedCalculationMap
							.get(unitedStatesOfAmericaShares.getYahooCode().toUpperCase());

					purchaseQuantity = soldQuantity;
					unitedStatesOfAmericaShares.setPurchaseQuantity(String.valueOf(purchaseQuantity));

					purchaseGrossTotal = soldQuantity
							.multiply(new BigDecimal(unitedStatesOfAmericaShares.getPurchasePrice()));
					unitedStatesOfAmericaShares.setPurchaseGrossTotal(String.valueOf(purchaseGrossTotal));

					UnitedStatesOfAmericaShares soldShare = scriptConsolidatedSoldShareMap
							.get(unitedStatesOfAmericaShares.getYahooCode().toUpperCase());

					buildConsolidatedSellShares(soldUnitedStatesOfAmericaShares, unitedStatesOfAmericaShares, soldShare,
							soldTotal, unitedStatesOfAmericaConsolidatedCalculationMap);

					soldShare.setCreatedBy(unitedStatesOfAmericaShares.getCreatedBy());
					soldShare.setCreatedDate(unitedStatesOfAmericaShares.getCreatedDate());
					soldShare.setLastUpdatedBy(userEmailId);
					soldShare.setLastUpdatedDate(dateInddMmmYyyy);

					consolidatedSoldSharesMap.put(unitedStatesOfAmericaShares.getYahooCode().toUpperCase(), soldShare);
				} else {
					boughtSharesList.add(unitedStatesOfAmericaShares);
				}
			} else {
				boughtSharesList.add(unitedStatesOfAmericaShares);
			}
		}

		consolidatedSoldSharesMap.put(AnukyaConstants.UNITED_STATES_OF_AMERICA_BOUGHT_SHARES_LIST, boughtSharesList);

		return consolidatedSoldSharesMap;
	}

	private void buildConsolidatedSellShares(UnitedStatesOfAmericaShares soldUnitedStatesOfAmericaShares,
			UnitedStatesOfAmericaShares unitedStatesOfAmericaShares,
			UnitedStatesOfAmericaShares scriptConsolidatedSoldShare, UnitedStatesOfAmericaShares soldTotal,
			Map<String, BigDecimal> unitedStatesOfAmericaConsolidatedCalculationMap) {

		scriptConsolidatedSoldShare.setId(anukyaUtils.generateId());

		scriptConsolidatedSoldShare.setYahooCode(unitedStatesOfAmericaShares.getYahooCode());
		scriptConsolidatedSoldShare.setIsinCode(unitedStatesOfAmericaShares.getIsinCode());
		scriptConsolidatedSoldShare.setScriptName(unitedStatesOfAmericaShares.getScriptName());
		scriptConsolidatedSoldShare.setCategory(unitedStatesOfAmericaShares.getCategory());
		scriptConsolidatedSoldShare.setSector(unitedStatesOfAmericaShares.getSector());
		scriptConsolidatedSoldShare.setIndustry(unitedStatesOfAmericaShares.getIndustry());
		scriptConsolidatedSoldShare.setShortTermInvestment(unitedStatesOfAmericaShares.isShortTermInvestment());
		scriptConsolidatedSoldShare.setSharesSplitSelected(unitedStatesOfAmericaShares.isSharesSplitSelected());
		scriptConsolidatedSoldShare.setSharesSplitRatio(unitedStatesOfAmericaShares.getSharesSplitRatio());
		scriptConsolidatedSoldShare.setSharesSplitDate(unitedStatesOfAmericaShares.getSharesSplitDate());
		scriptConsolidatedSoldShare.setSharesSplit(unitedStatesOfAmericaShares.getSharesSplit());
		scriptConsolidatedSoldShare.setSharesSplitDisplay(unitedStatesOfAmericaShares.getSharesSplitDisplay());
		scriptConsolidatedSoldShare.setSharesBonusSelected(unitedStatesOfAmericaShares.isSharesBonusSelected());
		scriptConsolidatedSoldShare.setSharesBonusRatio(unitedStatesOfAmericaShares.getSharesBonusRatio());
		scriptConsolidatedSoldShare.setSharesBonusDate(unitedStatesOfAmericaShares.getSharesBonusDate());
		scriptConsolidatedSoldShare.setSharesBonus(unitedStatesOfAmericaShares.getSharesBonus());
		scriptConsolidatedSoldShare.setSharesBonusDisplay(unitedStatesOfAmericaShares.getSharesBonusDisplay());

		BigDecimal purchaseQuantity = unitedStatesOfAmericaConsolidatedCalculationMap
				.get(unitedStatesOfAmericaShares.getYahooCode().toUpperCase());
		BigDecimal existingScriptConsolidatedPurchaseQuantity = new BigDecimal(
				scriptConsolidatedSoldShare.getPurchaseQuantity());
		BigDecimal totalScriptConsolidatedPurchaseQuantity = purchaseQuantity
				.add(existingScriptConsolidatedPurchaseQuantity);
		scriptConsolidatedSoldShare.setPurchaseQuantity(String.valueOf(totalScriptConsolidatedPurchaseQuantity));
		BigDecimal existingTotalPurchaseQuantity = new BigDecimal(soldTotal.getPurchaseQuantity());
		BigDecimal totalPurchaseQuantity = purchaseQuantity.add(existingTotalPurchaseQuantity);
		soldTotal.setPurchaseQuantity(String.valueOf(totalPurchaseQuantity));

		BigDecimal purchasePrice = unitedStatesOfAmericaConsolidatedCalculationMap
				.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_PRICE);
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

		BigDecimal purchaseCommission = unitedStatesOfAmericaConsolidatedCalculationMap
				.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_COMMISSION);
		BigDecimal existingScriptConsolidatedPurchaseCommission = new BigDecimal(
				scriptConsolidatedSoldShare.getPurchaseCommission());
		BigDecimal totalScriptConsolidatedPurchaseCommission = purchaseCommission
				.add(existingScriptConsolidatedPurchaseCommission);
		scriptConsolidatedSoldShare.setPurchaseCommission(String.valueOf(totalScriptConsolidatedPurchaseCommission));
		BigDecimal existingTotalPurchaseCommission = new BigDecimal(soldTotal.getPurchaseCommission());
		BigDecimal totalPurchaseCommission = purchaseCommission.add(existingTotalPurchaseCommission);
		soldTotal.setPurchaseCommission(String.valueOf(totalPurchaseCommission));

		BigDecimal purchaseTransactionFees = unitedStatesOfAmericaConsolidatedCalculationMap
				.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_TRANSACTION_FEES);
		BigDecimal existingScriptConsolidatedPurchaseTransactionFees = new BigDecimal(
				scriptConsolidatedSoldShare.getPurchaseTransactionFees());
		BigDecimal totalScriptConsolidatedPurchaseTransactionFees = purchaseTransactionFees
				.add(existingScriptConsolidatedPurchaseTransactionFees);
		scriptConsolidatedSoldShare
				.setPurchaseTransactionFees(String.valueOf(totalScriptConsolidatedPurchaseTransactionFees));
		BigDecimal existingTotalPurchaseTransactionFees = new BigDecimal(soldTotal.getPurchaseTransactionFees());
		BigDecimal totalPurchaseTransactionFees = purchaseTransactionFees.add(existingTotalPurchaseTransactionFees);
		soldTotal.setPurchaseTransactionFees(String.valueOf(totalPurchaseTransactionFees));

		BigDecimal purchaseOtherFees = unitedStatesOfAmericaConsolidatedCalculationMap
				.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_OTHER_FEES);
		BigDecimal existingScriptConsolidatedPurchaseOtherFees = new BigDecimal(
				scriptConsolidatedSoldShare.getPurchaseOtherFees());
		BigDecimal totalScriptConsolidatedPurchaseOtherFees = purchaseOtherFees
				.add(existingScriptConsolidatedPurchaseOtherFees);
		scriptConsolidatedSoldShare.setPurchaseOtherFees(String.valueOf(totalScriptConsolidatedPurchaseOtherFees));
		BigDecimal existingTotalPurchaseOtherFees = new BigDecimal(soldTotal.getPurchaseOtherFees());
		BigDecimal totalPurchaseOtherFees = purchaseOtherFees.add(existingTotalPurchaseOtherFees);
		soldTotal.setPurchaseOtherFees(String.valueOf(totalPurchaseOtherFees));

		BigDecimal purchaseTotal = unitedStatesOfAmericaConsolidatedCalculationMap
				.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_TOTAL);
		BigDecimal existingScriptConsolidatedPurchaseTotal = new BigDecimal(
				scriptConsolidatedSoldShare.getPurchaseTotal());
		BigDecimal totalScriptConsolidatedPurchaseTotal = purchaseTotal.add(existingScriptConsolidatedPurchaseTotal);
		scriptConsolidatedSoldShare.setPurchaseTotal(String.valueOf(totalScriptConsolidatedPurchaseTotal));
		BigDecimal existingTotalPurchaseTotal = new BigDecimal(soldTotal.getPurchaseTotal());
		BigDecimal totalPurchaseTotal = purchaseTotal.add(existingTotalPurchaseTotal);
		soldTotal.setPurchaseTotal(String.valueOf(totalPurchaseTotal));

		BigDecimal purchaseNetPricePerUnit = unitedStatesOfAmericaConsolidatedCalculationMap
				.get(AnukyaConstants.UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_NET_PRICE_PER_UNIT);
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

		scriptConsolidatedSoldShare.setSellDate(soldUnitedStatesOfAmericaShares.getSellDate());

		BigDecimal sellQuantity = unitedStatesOfAmericaConsolidatedCalculationMap
				.get(unitedStatesOfAmericaShares.getYahooCode().toUpperCase());
		BigDecimal existingScriptConsolidatedSellQuantity = new BigDecimal(
				scriptConsolidatedSoldShare.getSellQuantity());
		BigDecimal totalScriptConsolidatedSellQuantity = sellQuantity.add(existingScriptConsolidatedSellQuantity);
		scriptConsolidatedSoldShare.setSellQuantity(String.valueOf(totalScriptConsolidatedSellQuantity));
		BigDecimal existingTotalSellQuantity = new BigDecimal(soldTotal.getSellQuantity());
		BigDecimal totalSellQuantity = sellQuantity.add(existingTotalSellQuantity);
		soldTotal.setSellQuantity(String.valueOf(totalSellQuantity));

		BigDecimal sellPrice = new BigDecimal(soldUnitedStatesOfAmericaShares.getSellPrice());
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

		BigDecimal sellCommission = new BigDecimal(soldUnitedStatesOfAmericaShares.getSellCommission())
				.multiply(sellQuantity.divide(new BigDecimal(soldUnitedStatesOfAmericaShares.getSellQuantity())));
		BigDecimal existingScriptConsolidatedSellCommission = new BigDecimal(
				scriptConsolidatedSoldShare.getSellCommission());
		BigDecimal totalScriptConsolidatedSellCommission = sellCommission.add(existingScriptConsolidatedSellCommission);
		scriptConsolidatedSoldShare.setSellCommission(String.valueOf(totalScriptConsolidatedSellCommission));
		BigDecimal existingTotalSellCommission = new BigDecimal(soldTotal.getSellCommission());
		BigDecimal totalSellCommission = sellCommission.add(existingTotalSellCommission);
		soldTotal.setSellCommission(String.valueOf(totalSellCommission));

		BigDecimal sellTransactionFees = new BigDecimal(soldUnitedStatesOfAmericaShares.getSellTransactionFees())
				.multiply(sellQuantity.divide(new BigDecimal(soldUnitedStatesOfAmericaShares.getSellQuantity())));
		BigDecimal existingScriptConsolidatedSellTransactionFees = new BigDecimal(
				scriptConsolidatedSoldShare.getSellTransactionFees());
		BigDecimal totalScriptConsolidatedSellTransactionFees = sellTransactionFees
				.add(existingScriptConsolidatedSellTransactionFees);
		scriptConsolidatedSoldShare.setSellTransactionFees(String.valueOf(totalScriptConsolidatedSellTransactionFees));
		BigDecimal existingTotalSellTransactionFees = new BigDecimal(soldTotal.getSellTransactionFees());
		BigDecimal totalSellTransactionFees = sellTransactionFees.add(existingTotalSellTransactionFees);
		soldTotal.setSellTransactionFees(String.valueOf(totalSellTransactionFees));

		BigDecimal sellOtherFees = new BigDecimal(soldUnitedStatesOfAmericaShares.getSellOtherFees())
				.multiply(sellQuantity.divide(new BigDecimal(soldUnitedStatesOfAmericaShares.getSellQuantity())));
		BigDecimal existingScriptConsolidatedSellOtherFees = new BigDecimal(
				scriptConsolidatedSoldShare.getSellOtherFees());
		BigDecimal totalScriptConsolidatedSellOtherFees = sellOtherFees.add(existingScriptConsolidatedSellOtherFees);
		scriptConsolidatedSoldShare.setSellOtherFees(String.valueOf(totalScriptConsolidatedSellOtherFees));
		BigDecimal existingTotalSellOtherFees = new BigDecimal(soldTotal.getSellOtherFees());
		BigDecimal totalSellOtherFees = sellOtherFees.add(existingTotalSellOtherFees);
		soldTotal.setSellOtherFees(String.valueOf(totalSellOtherFees));

		BigDecimal sellTotal = sellGrossTotal.subtract(sellCommission).subtract(sellTransactionFees)
				.subtract(sellOtherFees);
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

	private List<UnitedStatesOfAmericaShares> getUnitedStatesOfAmericaSharesNonConsolidated(String searchTerm)
			throws AnukyaException {

		File nonConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_SOLD_MAIN_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);

		List<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(nonConsolidatedFile);

		if (searchTerm.isEmpty()) {
			return unitedStatesOfAmericaSharesList;
		}

		return filterBySearchTerm(unitedStatesOfAmericaSharesList, searchTerm);
	}

	private List<UnitedStatesOfAmericaShares> getUnitedStatesOfAmericaSharesConsolidated(String searchTerm)
			throws AnukyaException {

		File consolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_SOLD_MAIN_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		List<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(consolidatedFile);

		if (searchTerm.isEmpty()) {
			return unitedStatesOfAmericaSharesList;
		}

		return filterBySearchTerm(unitedStatesOfAmericaSharesList, searchTerm);
	}

	private List<UnitedStatesOfAmericaShares> filterBySearchTerm(
			List<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesList, String searchTerm) {

		return unitedStatesOfAmericaSharesList.stream()
				.filter(unitedStatesOfAmericaShares -> unitedStatesOfAmericaShares.getScriptName().equalsIgnoreCase(
						searchTerm) || unitedStatesOfAmericaShares.getYahooCode().equalsIgnoreCase(searchTerm)
						|| unitedStatesOfAmericaShares.getCategory().equalsIgnoreCase(searchTerm)
						|| unitedStatesOfAmericaShares.getSector().equalsIgnoreCase(searchTerm)
						|| unitedStatesOfAmericaShares.getIndustry().equalsIgnoreCase(searchTerm))
				.collect(Collectors.toList());
	}

	private UnitedStatesOfAmericaSharesResponse calculateUnitedStatesOfAmericaShares(
			List<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesList, String searchTerm) {

		UnitedStatesOfAmericaSharesResponse unitedStatesOfAmericaSharesResponse = new UnitedStatesOfAmericaSharesResponse();
		unitedStatesOfAmericaSharesResponse.setUnitedStatesOfAmericaSharesTotal(new UnitedStatesOfAmericaShares());

		if (!searchTerm.isEmpty()) {
			for (UnitedStatesOfAmericaShares unitedStatesOfAmericaShares : unitedStatesOfAmericaSharesList) {
				BigDecimal purchaseQuantity = new BigDecimal(
						String.valueOf(unitedStatesOfAmericaShares.getPurchaseQuantity()));
				BigDecimal existingTotalPurchaseQuantity = new BigDecimal(unitedStatesOfAmericaSharesResponse
						.getUnitedStatesOfAmericaSharesTotal().getPurchaseQuantity());
				BigDecimal totalPurchaseQuantity = purchaseQuantity.add(existingTotalPurchaseQuantity);
				unitedStatesOfAmericaShares
						.setPurchaseQuantity(String.format(AnukyaConstants.FLOAT_6_DECIMAL, purchaseQuantity));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
						.setPurchaseQuantity(String.format(AnukyaConstants.FLOAT_6_DECIMAL, totalPurchaseQuantity));

				BigDecimal purchasePrice = new BigDecimal(
						String.valueOf(unitedStatesOfAmericaShares.getPurchasePrice()));
				BigDecimal existingTotalPurchasePrice = new BigDecimal(
						unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getPurchasePrice());
				BigDecimal totalPurchasePrice = purchaseQuantity.multiply(purchasePrice)
						.add(existingTotalPurchaseQuantity.multiply(existingTotalPurchasePrice))
						.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
				unitedStatesOfAmericaShares
						.setPurchasePrice(String.format(AnukyaConstants.FLOAT_4_DECIMAL, purchasePrice));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
						.setPurchasePrice(String.format(AnukyaConstants.FLOAT_4_DECIMAL, totalPurchasePrice));

				BigDecimal purchaseGrossTotal = new BigDecimal(
						String.valueOf(unitedStatesOfAmericaShares.getPurchaseGrossTotal()));
				BigDecimal existingTotalPurchaseGrossTotal = new BigDecimal(unitedStatesOfAmericaSharesResponse
						.getUnitedStatesOfAmericaSharesTotal().getPurchaseGrossTotal());
				BigDecimal totalPurchaseGrossTotal = purchaseGrossTotal.add(existingTotalPurchaseGrossTotal);
				unitedStatesOfAmericaShares
						.setPurchaseGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseGrossTotal));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
						.setPurchaseGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseGrossTotal));

				BigDecimal purchaseCommission = new BigDecimal(
						String.valueOf(unitedStatesOfAmericaShares.getPurchaseCommission()));
				BigDecimal existingTotalPurchaseCommission = new BigDecimal(unitedStatesOfAmericaSharesResponse
						.getUnitedStatesOfAmericaSharesTotal().getPurchaseCommission());
				BigDecimal totalPurchaseCommission = purchaseCommission.add(existingTotalPurchaseCommission);
				unitedStatesOfAmericaShares
						.setPurchaseCommission(String.format(AnukyaConstants.FLOAT_4_DECIMAL, purchaseCommission));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
						.setPurchaseCommission(String.format(AnukyaConstants.FLOAT_4_DECIMAL, totalPurchaseCommission));

				BigDecimal purchaseTransactionFees = new BigDecimal(
						String.valueOf(unitedStatesOfAmericaShares.getPurchaseTransactionFees()));
				BigDecimal existingTotalPurchaseTransactionFees = new BigDecimal(unitedStatesOfAmericaSharesResponse
						.getUnitedStatesOfAmericaSharesTotal().getPurchaseTransactionFees());
				BigDecimal totalPurchaseTransaction = purchaseTransactionFees.add(existingTotalPurchaseTransactionFees);
				unitedStatesOfAmericaShares.setPurchaseTransactionFees(
						String.format(AnukyaConstants.FLOAT_4_DECIMAL, purchaseTransactionFees));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().setPurchaseTransactionFees(
						String.format(AnukyaConstants.FLOAT_4_DECIMAL, totalPurchaseTransaction));

				BigDecimal purchaseOtherFees = new BigDecimal(
						String.valueOf(unitedStatesOfAmericaShares.getPurchaseOtherFees()));
				BigDecimal existingTotalPurchaseOtherFees = new BigDecimal(unitedStatesOfAmericaSharesResponse
						.getUnitedStatesOfAmericaSharesTotal().getPurchaseOtherFees());
				BigDecimal totalPurchaseOtherFees = purchaseOtherFees.add(existingTotalPurchaseOtherFees);
				unitedStatesOfAmericaShares
						.setPurchaseOtherFees(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseOtherFees));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
						.setPurchaseOtherFees(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseOtherFees));

				BigDecimal purchaseTotal = new BigDecimal(
						String.valueOf(unitedStatesOfAmericaShares.getPurchaseTotal()));
				BigDecimal existingTotalPurchaseTotal = new BigDecimal(
						unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getPurchaseTotal());
				BigDecimal totalPurchaseTotal = purchaseTotal.add(existingTotalPurchaseTotal);
				unitedStatesOfAmericaShares
						.setPurchaseTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseTotal));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
						.setPurchaseTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseTotal));

				BigDecimal purchaseNetPricePerUnit = new BigDecimal(
						String.valueOf(unitedStatesOfAmericaShares.getPurchaseNetPricePerUnit()));
				BigDecimal existingTotalPurchaseNetPricePerUnit = new BigDecimal(unitedStatesOfAmericaSharesResponse
						.getUnitedStatesOfAmericaSharesTotal().getPurchaseNetPricePerUnit());
				BigDecimal totalPurchaseNetPricePerUnit = purchaseQuantity.multiply(purchaseNetPricePerUnit)
						.add(existingTotalPurchaseQuantity.multiply(existingTotalPurchaseNetPricePerUnit))
						.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
				unitedStatesOfAmericaShares.setPurchaseNetPricePerUnit(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseNetPricePerUnit));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().setPurchaseNetPricePerUnit(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseNetPricePerUnit));

				BigDecimal sellQuantity = new BigDecimal(String.valueOf(unitedStatesOfAmericaShares.getSellQuantity()));
				BigDecimal existingTotalSellQuantity = new BigDecimal(
						unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getSellQuantity());
				BigDecimal totalSellQuantity = sellQuantity.add(existingTotalSellQuantity);
				unitedStatesOfAmericaShares
						.setSellQuantity(String.format(AnukyaConstants.FLOAT_6_DECIMAL, sellQuantity));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
						.setSellQuantity(String.format(AnukyaConstants.FLOAT_6_DECIMAL, totalSellQuantity));

				BigDecimal sellPrice = new BigDecimal(String.valueOf(unitedStatesOfAmericaShares.getSellPrice()));
				BigDecimal existingTotalSellPrice = new BigDecimal(
						unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getSellPrice());
				BigDecimal totalSellPrice = sellQuantity.multiply(sellPrice)
						.add(existingTotalSellQuantity.multiply(existingTotalSellPrice))
						.divide(totalSellQuantity, MathContext.DECIMAL128);
				unitedStatesOfAmericaShares.setSellPrice(String.format(AnukyaConstants.FLOAT_4_DECIMAL, sellPrice));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
						.setSellPrice(String.format(AnukyaConstants.FLOAT_4_DECIMAL, totalSellPrice));

				BigDecimal sellGrossTotal = new BigDecimal(
						String.valueOf(unitedStatesOfAmericaShares.getSellGrossTotal()));
				BigDecimal existingTotalSellGrossTotal = new BigDecimal(
						unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getSellGrossTotal());
				BigDecimal totalSellGrossTotal = sellGrossTotal.add(existingTotalSellGrossTotal);
				unitedStatesOfAmericaShares
						.setSellGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellGrossTotal));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
						.setSellGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellGrossTotal));

				BigDecimal sellCommission = new BigDecimal(
						String.valueOf(unitedStatesOfAmericaShares.getSellCommission()));
				BigDecimal existingTotalSellCommission = new BigDecimal(
						unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getSellCommission());
				BigDecimal totalSellCommission = sellCommission.add(existingTotalSellCommission);
				unitedStatesOfAmericaShares
						.setSellCommission(String.format(AnukyaConstants.FLOAT_4_DECIMAL, sellCommission));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
						.setSellCommission(String.format(AnukyaConstants.FLOAT_4_DECIMAL, totalSellCommission));

				BigDecimal sellTransactionFees = new BigDecimal(
						String.valueOf(unitedStatesOfAmericaShares.getSellTransactionFees()));
				BigDecimal existingTotalSellTransactionFees = new BigDecimal(unitedStatesOfAmericaSharesResponse
						.getUnitedStatesOfAmericaSharesTotal().getSellTransactionFees());
				BigDecimal totalSellTransactionFees = sellTransactionFees.add(existingTotalSellTransactionFees);
				unitedStatesOfAmericaShares
						.setSellTransactionFees(String.format(AnukyaConstants.FLOAT_4_DECIMAL, sellTransactionFees));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().setSellTransactionFees(
						String.format(AnukyaConstants.FLOAT_4_DECIMAL, totalSellTransactionFees));

				BigDecimal sellOtherFees = new BigDecimal(
						String.valueOf(unitedStatesOfAmericaShares.getSellOtherFees()));
				BigDecimal existingTotalSellOtherFees = new BigDecimal(
						unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getSellOtherFees());
				BigDecimal totalSellOtherFees = sellOtherFees.add(existingTotalSellOtherFees);
				unitedStatesOfAmericaShares
						.setSellOtherFees(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellOtherFees));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
						.setSellOtherFees(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellOtherFees));

				BigDecimal sellTotal = new BigDecimal(String.valueOf(unitedStatesOfAmericaShares.getSellTotal()));
				BigDecimal existingTotalSellTotal = new BigDecimal(
						unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getSellTotal());
				BigDecimal totalSellTotal = sellTotal.add(existingTotalSellTotal);
				unitedStatesOfAmericaShares.setSellTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellTotal));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
						.setSellTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellTotal));

				BigDecimal sellNetPricePerUnit = new BigDecimal(
						String.valueOf(unitedStatesOfAmericaShares.getSellNetPricePerUnit()));
				BigDecimal existingTotalSellNetPricePerUnit = new BigDecimal(unitedStatesOfAmericaSharesResponse
						.getUnitedStatesOfAmericaSharesTotal().getSellNetPricePerUnit());
				BigDecimal totalSellNetPricePerUnit = sellNetPricePerUnit.add(existingTotalSellNetPricePerUnit);
				unitedStatesOfAmericaShares
						.setSellNetPricePerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellNetPricePerUnit));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().setSellNetPricePerUnit(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellNetPricePerUnit));

				BigDecimal grossProfitLoss = new BigDecimal(unitedStatesOfAmericaShares.getGrossProfitLoss());
				BigDecimal totalGrossProfitLoss = totalSellGrossTotal.subtract(totalPurchaseGrossTotal);
				unitedStatesOfAmericaShares
						.setGrossProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, grossProfitLoss));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
						.setGrossProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalGrossProfitLoss));

				BigDecimal grossProfitLossPercentage = new BigDecimal(
						unitedStatesOfAmericaShares.getGrossProfitLossPercentage());
				BigDecimal totalGrossProfitLossPercentage;
				try {
					totalGrossProfitLossPercentage = totalSellGrossTotal
							.divide(totalPurchaseGrossTotal, MathContext.DECIMAL128)
							.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
							.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
				} catch (ArithmeticException e) {
					totalGrossProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_0);
				}
				unitedStatesOfAmericaShares.setGrossProfitLossPercentage(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, grossProfitLossPercentage));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().setGrossProfitLossPercentage(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalGrossProfitLossPercentage));

				BigDecimal totalProfitLoss = new BigDecimal(unitedStatesOfAmericaShares.getTotalProfitLoss());
				BigDecimal totalTotalProfitLoss = totalSellTotal.subtract(totalPurchaseTotal);
				unitedStatesOfAmericaShares
						.setTotalProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLoss));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
						.setTotalProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalTotalProfitLoss));

				BigDecimal totalProfitLossPercentage = new BigDecimal(
						unitedStatesOfAmericaShares.getTotalProfitLossPercentage());
				BigDecimal totalTotalProfitLossPercentage;
				try {
					totalTotalProfitLossPercentage = totalSellTotal.divide(totalPurchaseTotal, MathContext.DECIMAL128)
							.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
							.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
				} catch (ArithmeticException e) {
					totalTotalProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_0);
				}
				unitedStatesOfAmericaShares.setTotalProfitLossPercentage(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLossPercentage));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().setTotalProfitLossPercentage(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalTotalProfitLossPercentage));

				BigDecimal netProfitLossPerUnit = new BigDecimal(unitedStatesOfAmericaShares.getNetProfitLossPerUnit());
				BigDecimal totalNetProfitLossPerUnit = totalSellNetPricePerUnit.subtract(totalPurchaseNetPricePerUnit);
				unitedStatesOfAmericaShares
						.setNetProfitLossPerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL, netProfitLossPerUnit));
				unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().setNetProfitLossPerUnit(
						String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalNetProfitLossPerUnit));
			}
			unitedStatesOfAmericaSharesResponse.setUnitedStatesOfAmericaSharesList(unitedStatesOfAmericaSharesList);
		} else {
			for (UnitedStatesOfAmericaShares unitedStatesOfAmericaShares : unitedStatesOfAmericaSharesList) {
				unitedStatesOfAmericaShares.setPurchaseQuantity(String.format(AnukyaConstants.FLOAT_6_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getPurchaseQuantity())));
				unitedStatesOfAmericaShares.setPurchasePrice(String.format(AnukyaConstants.FLOAT_4_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getPurchasePrice())));
				unitedStatesOfAmericaShares.setPurchaseGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getPurchaseGrossTotal())));
				unitedStatesOfAmericaShares.setPurchaseCommission(String.format(AnukyaConstants.FLOAT_4_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getPurchaseCommission())));
				unitedStatesOfAmericaShares.setPurchaseTransactionFees(String.format(AnukyaConstants.FLOAT_4_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getPurchaseTransactionFees())));

				unitedStatesOfAmericaShares.setPurchaseOtherFees(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getPurchaseOtherFees())));

				unitedStatesOfAmericaShares.setPurchaseTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getPurchaseTotal())));

				unitedStatesOfAmericaShares.setPurchaseNetPricePerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getPurchaseNetPricePerUnit())));

				unitedStatesOfAmericaShares.setSellQuantity(String.format(AnukyaConstants.FLOAT_6_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getSellQuantity())));
				unitedStatesOfAmericaShares.setSellPrice(String.format(AnukyaConstants.FLOAT_4_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getSellPrice())));
				unitedStatesOfAmericaShares.setSellGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getSellGrossTotal())));
				unitedStatesOfAmericaShares.setSellCommission(String.format(AnukyaConstants.FLOAT_4_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getSellCommission())));
				unitedStatesOfAmericaShares.setSellTransactionFees(String.format(AnukyaConstants.FLOAT_4_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getSellTransactionFees())));

				unitedStatesOfAmericaShares.setSellOtherFees(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getSellOtherFees())));

				unitedStatesOfAmericaShares.setSellTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getSellTotal())));

				unitedStatesOfAmericaShares.setSellNetPricePerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getSellNetPricePerUnit())));

				unitedStatesOfAmericaShares.setGrossProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getGrossProfitLoss())));
				unitedStatesOfAmericaShares.setGrossProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getGrossProfitLossPercentage())));
				unitedStatesOfAmericaShares.setTotalProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getTotalProfitLoss())));
				unitedStatesOfAmericaShares.setTotalProfitLossPercentage(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getTotalProfitLossPercentage())));
				unitedStatesOfAmericaShares.setNetProfitLossPerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL,
						new BigDecimal(unitedStatesOfAmericaShares.getNetProfitLossPerUnit())));
			}
			unitedStatesOfAmericaSharesResponse.setUnitedStatesOfAmericaSharesTotal(
					unitedStatesOfAmericaSharesList.get(unitedStatesOfAmericaSharesList.size() - 1));
			unitedStatesOfAmericaSharesList.remove(unitedStatesOfAmericaSharesList.size() - 1);
			unitedStatesOfAmericaSharesResponse.setUnitedStatesOfAmericaSharesList(unitedStatesOfAmericaSharesList);
		}

		return unitedStatesOfAmericaSharesResponse;
	}
}
