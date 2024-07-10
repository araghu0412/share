package com.moneymanager.anukya.unitedstatesofamerica.eq;

import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.moneymanager.anukya.data.impl.UnitedStatesOfAmericaSharesData;
import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.ErrorDetails;
import com.moneymanager.anukya.model.AddUnitedStatesOfAmericaSharesBonus;
import com.moneymanager.anukya.model.AddUnitedStatesOfAmericaSharesSplit;
import com.moneymanager.anukya.model.CommonResponse;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaShares;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesLastTradingPrice;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesResponse;
import com.moneymanager.anukya.utils.AnukyaConstants;
import com.moneymanager.anukya.utils.AnukyaDataUtils;
import com.moneymanager.anukya.utils.AnukyaUtils;
import com.moneymanager.anukya.utils.UnitedStatesOfAmericaExpenditureUtils;
import com.moneymanager.anukya.utils.UnitedStatesOfAmericaSharesUtils;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class UnitedStatesOfAmericaBoughtShares extends AbstractUnitedStatesOfAmericaShares {

	@Value("${DATABASE.BASE.LOCATION}")
	private String databaseBaseLocation;

	@Autowired
	private AnukyaDataUtils anukyaDataUtils;

	@Autowired
	private AnukyaUtils anukyaUtils;

	@Autowired
	private UnitedStatesOfAmericaSharesData unitedStatesOfAmericaSharesData;

	@Autowired
	private UnitedStatesOfAmericaSharesUtils unitedStatesOfAmericaSharesUtils;

	@Autowired
	private UnitedStatesOfAmericaExpenditureUtils unitedStatesOfAmericaExpenditureUtils;

	@Override
	public CommonResponse addShares(UnitedStatesOfAmericaShares boughtUnitedStatesOfAmericaShares)
			throws AnukyaException {

		CommonResponse commonResponse = new CommonResponse();

		File mainDirectory = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_PURCHASE_MAIN_DIRECTORY);
		File updateDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.USA_EQ_PURCHASE_UPDATE_DIRECTORY);
		File mainBackUpDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.USA_EQ_PURCHASE_MAIN_BACKUP_DIRECTORY);

		// Delete update and main backup directory
		anukyaDataUtils.deleteDirectory(updateDirectory);
		anukyaDataUtils.deleteDirectory(mainBackUpDirectory);

		// Copy main directory into update and main backup directory
		anukyaDataUtils.copyDirectory(mainDirectory, updateDirectory);
		anukyaDataUtils.copyDirectory(mainDirectory, mainBackUpDirectory);

		// Modify files in baseLocation/update
		File nonConsolidatedFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USA_EQ_PURCHASE_UPDATE_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);

		File consolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_PURCHASE_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		File singleScriptFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.USA_EQ_PURCHASE_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ boughtUnitedStatesOfAmericaShares.getYahooCode() + AnukyaConstants.JSON_EXTENSION);

		List<UnitedStatesOfAmericaShares> nonConsolidatedList;
		List<UnitedStatesOfAmericaShares> consolidatedList;
		List<UnitedStatesOfAmericaShares> singleScriptList;

		// If shares split
		if (boughtUnitedStatesOfAmericaShares.isSharesSplitSelected()) {
			AddUnitedStatesOfAmericaSharesSplit addUnitedStatesOfAmericaSharesSplit = updateSharesSplit(
					boughtUnitedStatesOfAmericaShares);
			consolidatedList = addUnitedStatesOfAmericaSharesSplit.getConsolidatedList();
			nonConsolidatedList = addUnitedStatesOfAmericaSharesSplit.getNonConsolidatedList();
			singleScriptList = addUnitedStatesOfAmericaSharesSplit.getSingleScriptList();

		} else if (boughtUnitedStatesOfAmericaShares.isSharesBonusSelected()) {
			AddUnitedStatesOfAmericaSharesBonus addUnitedStatesOfAmericaSharesBonus = updateSharesBonus(
					boughtUnitedStatesOfAmericaShares);
			if (addUnitedStatesOfAmericaSharesBonus.getConsolidatedList() == null) {
				commonResponse.setStatus(true);
				commonResponse.setMessage("You do not get any bonus shares, however, bonus is adjusted with cash");

				return commonResponse;
			}
			consolidatedList = addUnitedStatesOfAmericaSharesBonus.getConsolidatedList();
			nonConsolidatedList = addUnitedStatesOfAmericaSharesBonus.getNonConsolidatedList();
			singleScriptList = addUnitedStatesOfAmericaSharesBonus.getSingleScriptList();
		} else {
			consolidatedList = addConsolidated(boughtUnitedStatesOfAmericaShares);
			nonConsolidatedList = addNonConsolidated(boughtUnitedStatesOfAmericaShares);
			singleScriptList = addSingleScript(boughtUnitedStatesOfAmericaShares);
		}

		// Update the file in /update directory
		anukyaDataUtils.createOrUpdateFile(nonConsolidatedFile, nonConsolidatedList);
		anukyaDataUtils.createOrUpdateFile(consolidatedFile, consolidatedList);
		anukyaDataUtils.createOrUpdateFile(singleScriptFile, singleScriptList);

		// Delete main directory
		anukyaDataUtils.deleteDirectory(mainDirectory);

		// Copy from update directory to main directory
		anukyaDataUtils.copyDirectory(updateDirectory, mainDirectory);

		// Finally delete the update directory
		anukyaDataUtils.deleteDirectory(updateDirectory);

		commonResponse.setStatus(true);
		commonResponse.setMessage("Shares added successfully");

		return commonResponse;
	}

	@Override
	public UnitedStatesOfAmericaSharesResponse getUnitedStatesOfAmericaShares(boolean isNonConsolidated,
			String searchTerm, boolean isLongTermOnly) throws AnukyaException {

		List<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesList;

		if (isNonConsolidated) {
			unitedStatesOfAmericaSharesList = getUnitedStatesOfAmericaSharesNonConsolidated(searchTerm, isLongTermOnly);
		} else {
			unitedStatesOfAmericaSharesList = getUnitedStatesOfAmericaSharesConsolidated(searchTerm, isLongTermOnly);
		}

		if (unitedStatesOfAmericaSharesList.isEmpty()) {
			throw new AnukyaException("No content for United States of America", null, AnukyaErrorConstants.NO_CONTENT,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		return calculateUnitedStatesOfAmericaShares(unitedStatesOfAmericaSharesList);
	}

	// Private methods
	private List<UnitedStatesOfAmericaShares> addConsolidated(
			UnitedStatesOfAmericaShares boughtUnitedStatesOfAmericaShares) throws AnukyaException {

		File consolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_PURCHASE_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		String dateInddMmmYyyy = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		List<UnitedStatesOfAmericaShares> consolidatedList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(consolidatedFile);

		for (UnitedStatesOfAmericaShares existingUnitedStatesOfAmericaShare : consolidatedList) {
			if (boughtUnitedStatesOfAmericaShares.getYahooCode()
					.equalsIgnoreCase(existingUnitedStatesOfAmericaShare.getYahooCode())) {

				BigDecimal existingPurchaseQuantity = new BigDecimal(
						existingUnitedStatesOfAmericaShare.getPurchaseQuantity());
				BigDecimal boughtPurchaseQuantity = new BigDecimal(
						boughtUnitedStatesOfAmericaShares.getPurchaseQuantity());
				BigDecimal purchaseQuantity = existingPurchaseQuantity.add(boughtPurchaseQuantity);
				existingUnitedStatesOfAmericaShare.setPurchaseQuantity(String.valueOf(purchaseQuantity));

				BigDecimal existingPurchasePrice = new BigDecimal(
						existingUnitedStatesOfAmericaShare.getPurchasePrice());
				BigDecimal boughtPurchasePrice = new BigDecimal(boughtUnitedStatesOfAmericaShares.getPurchasePrice());
				BigDecimal purchasePrice = existingPurchaseQuantity.multiply(existingPurchasePrice)
						.add(boughtPurchaseQuantity.multiply(boughtPurchasePrice))
						.divide(purchaseQuantity, MathContext.DECIMAL128);
				existingUnitedStatesOfAmericaShare.setPurchasePrice(String.valueOf(purchasePrice));

				BigDecimal existingPurchaseGrossTotal = new BigDecimal(
						existingUnitedStatesOfAmericaShare.getPurchaseGrossTotal());
				BigDecimal boughtPurchaseGrossTotal = boughtPurchaseQuantity.multiply(boughtPurchasePrice);
				BigDecimal purchaseGrossTotal = existingPurchaseGrossTotal.add(boughtPurchaseGrossTotal);
				existingUnitedStatesOfAmericaShare.setPurchaseGrossTotal(String.valueOf(purchaseGrossTotal));

				BigDecimal existingPurchaseCommission = new BigDecimal(
						existingUnitedStatesOfAmericaShare.getPurchaseCommission());
				BigDecimal boughtPurchaseCommission = new BigDecimal(
						boughtUnitedStatesOfAmericaShares.getPurchaseCommission());
				BigDecimal purchaseCommission = existingPurchaseCommission.add(boughtPurchaseCommission);
				existingUnitedStatesOfAmericaShare.setPurchaseCommission(String.valueOf(purchaseCommission));

				BigDecimal existingPurchaseTransactionFees = new BigDecimal(
						existingUnitedStatesOfAmericaShare.getPurchaseTransactionFees());
				BigDecimal boughtPurchaseTransactionFees = new BigDecimal(
						boughtUnitedStatesOfAmericaShares.getPurchaseTransactionFees());
				BigDecimal purchaseTransactionFees = existingPurchaseTransactionFees.add(boughtPurchaseTransactionFees);
				existingUnitedStatesOfAmericaShare.setPurchaseTransactionFees(String.valueOf(purchaseTransactionFees));

				BigDecimal existingPurchaseOtherFees = new BigDecimal(
						existingUnitedStatesOfAmericaShare.getPurchaseOtherFees());
				BigDecimal boughtPurchaseOtherFees = new BigDecimal(
						boughtUnitedStatesOfAmericaShares.getPurchaseOtherFees());
				BigDecimal purchaseOtherFees = existingPurchaseOtherFees.add(boughtPurchaseOtherFees);
				existingUnitedStatesOfAmericaShare.setPurchaseOtherFees(String.valueOf(purchaseOtherFees));

				BigDecimal existingPurchaseTotal = new BigDecimal(
						existingUnitedStatesOfAmericaShare.getPurchaseTotal());
				BigDecimal boughtPurchaseTotal = boughtPurchaseGrossTotal.add(boughtPurchaseCommission)
						.add(boughtPurchaseTransactionFees).add(boughtPurchaseOtherFees);
				BigDecimal purchaseTotal = existingPurchaseTotal.add(boughtPurchaseTotal);
				existingUnitedStatesOfAmericaShare.setPurchaseTotal(String.valueOf(purchaseTotal));

				BigDecimal existingPurchaseNetPricePerUnit = new BigDecimal(
						existingUnitedStatesOfAmericaShare.getPurchaseNetPricePerUnit());
				BigDecimal boughtPurchaseNetPricePerUnit = boughtPurchaseTotal.divide(boughtPurchaseQuantity,
						MathContext.DECIMAL128);
				BigDecimal purchaseNetPricePerUnit = existingPurchaseQuantity.multiply(existingPurchaseNetPricePerUnit)
						.add(boughtPurchaseQuantity.multiply(boughtPurchaseNetPricePerUnit))
						.divide(purchaseQuantity, MathContext.DECIMAL128);
				existingUnitedStatesOfAmericaShare.setPurchaseNetPricePerUnit(String.valueOf(purchaseNetPricePerUnit));

				existingUnitedStatesOfAmericaShare.setLastUpdatedBy(userEmailId);
				existingUnitedStatesOfAmericaShare.setLastUpdatedDate(dateInddMmmYyyy);

				return consolidatedList;
			}
		}

		UnitedStatesOfAmericaShares unitedStatesOfAmericaShares = addUnitedStatesOfAmericaShares(
				boughtUnitedStatesOfAmericaShares);

		unitedStatesOfAmericaShares.setCreatedBy(userEmailId);
		unitedStatesOfAmericaShares.setCreatedDate(dateInddMmmYyyy);
		unitedStatesOfAmericaShares.setLastUpdatedBy(userEmailId);
		unitedStatesOfAmericaShares.setLastUpdatedDate(dateInddMmmYyyy);

		consolidatedList.add(unitedStatesOfAmericaShares);

		return consolidatedList;
	}

	private List<UnitedStatesOfAmericaShares> addNonConsolidated(
			UnitedStatesOfAmericaShares boughtUnitedStatesOfAmericaShares) throws AnukyaException {

		File nonConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_PURCHASE_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);

		String dateInddMmmYyyy = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		List<UnitedStatesOfAmericaShares> nonConsolidatedList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(nonConsolidatedFile);

		UnitedStatesOfAmericaShares unitedStatesOfAmericaShares = addUnitedStatesOfAmericaShares(
				boughtUnitedStatesOfAmericaShares);

		unitedStatesOfAmericaShares.setCreatedBy(userEmailId);
		unitedStatesOfAmericaShares.setCreatedDate(dateInddMmmYyyy);
		unitedStatesOfAmericaShares.setLastUpdatedBy(userEmailId);
		unitedStatesOfAmericaShares.setLastUpdatedDate(dateInddMmmYyyy);

		nonConsolidatedList.add(unitedStatesOfAmericaShares);

		return nonConsolidatedList;
	}

	private List<UnitedStatesOfAmericaShares> addSingleScript(
			UnitedStatesOfAmericaShares boughtUnitedStatesOfAmericaShares) throws AnukyaException {

		File singleScriptFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.USA_EQ_PURCHASE_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ boughtUnitedStatesOfAmericaShares.getYahooCode() + AnukyaConstants.JSON_EXTENSION);

		String dateInddMmmYyyy = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		List<UnitedStatesOfAmericaShares> singleScriptList = new ArrayList<>();

		if (anukyaDataUtils.isFileExists(singleScriptFile)) {
			singleScriptList = unitedStatesOfAmericaSharesData.getUnitedStatesOfAmericaShares(singleScriptFile);
		}

		UnitedStatesOfAmericaShares unitedStatesOfAmericaShares = addUnitedStatesOfAmericaShares(
				boughtUnitedStatesOfAmericaShares);

		unitedStatesOfAmericaShares.setCreatedBy(userEmailId);
		unitedStatesOfAmericaShares.setCreatedDate(dateInddMmmYyyy);
		unitedStatesOfAmericaShares.setLastUpdatedBy(userEmailId);
		unitedStatesOfAmericaShares.setLastUpdatedDate(dateInddMmmYyyy);

		singleScriptList.add(unitedStatesOfAmericaShares);

		return singleScriptList;
	}

	private UnitedStatesOfAmericaShares addUnitedStatesOfAmericaShares(
			UnitedStatesOfAmericaShares boughtUnitedStatesOfAmericaShares) {

		UnitedStatesOfAmericaShares unitedStatesOfAmericaShares = new UnitedStatesOfAmericaShares();

		unitedStatesOfAmericaShares.setId(anukyaUtils.generateId());

		unitedStatesOfAmericaShares.setScriptName(boughtUnitedStatesOfAmericaShares.getScriptName());
		unitedStatesOfAmericaShares.setYahooCode(boughtUnitedStatesOfAmericaShares.getYahooCode());
		unitedStatesOfAmericaShares.setIsinCode(boughtUnitedStatesOfAmericaShares.getIsinCode());
		unitedStatesOfAmericaShares.setCusip(boughtUnitedStatesOfAmericaShares.getCusip());
		unitedStatesOfAmericaShares.setCategory(boughtUnitedStatesOfAmericaShares.getCategory());
		unitedStatesOfAmericaShares.setSector(boughtUnitedStatesOfAmericaShares.getSector());
		unitedStatesOfAmericaShares.setIndustry(boughtUnitedStatesOfAmericaShares.getIndustry());
		unitedStatesOfAmericaShares.setShortTermInvestment(boughtUnitedStatesOfAmericaShares.isShortTermInvestment());
		unitedStatesOfAmericaShares.setSharesSplitSelected(false);
		unitedStatesOfAmericaShares.setSharesBonusSelected(false);
		unitedStatesOfAmericaShares.setPurchaseDate(boughtUnitedStatesOfAmericaShares.getPurchaseDate());
		unitedStatesOfAmericaShares.setPurchaseQuantity(boughtUnitedStatesOfAmericaShares.getPurchaseQuantity());
		unitedStatesOfAmericaShares.setPurchasePrice(boughtUnitedStatesOfAmericaShares.getPurchasePrice());

		BigDecimal purchaseGrossTotal = new BigDecimal(boughtUnitedStatesOfAmericaShares.getPurchaseQuantity())
				.multiply(new BigDecimal(boughtUnitedStatesOfAmericaShares.getPurchasePrice()));
		unitedStatesOfAmericaShares.setPurchaseGrossTotal(String.valueOf(purchaseGrossTotal));

		BigDecimal purchaseCommission = new BigDecimal(boughtUnitedStatesOfAmericaShares.getPurchaseCommission());
		unitedStatesOfAmericaShares.setPurchaseCommission(String.valueOf(purchaseCommission));

		BigDecimal purchaseTransactionFees = new BigDecimal(
				boughtUnitedStatesOfAmericaShares.getPurchaseTransactionFees());
		unitedStatesOfAmericaShares.setPurchaseTransactionFees(String.valueOf(purchaseTransactionFees));

		BigDecimal purchaseOtherFees = new BigDecimal(boughtUnitedStatesOfAmericaShares.getPurchaseOtherFees());
		unitedStatesOfAmericaShares.setPurchaseOtherFees(String.valueOf(purchaseOtherFees));

		BigDecimal purchaseTotal = purchaseGrossTotal.add(purchaseCommission).add(purchaseTransactionFees)
				.add(purchaseOtherFees);
		unitedStatesOfAmericaShares.setPurchaseTotal(String.valueOf(purchaseTotal));

		BigDecimal purchaseNetPricePerUnit = purchaseTotal.divide(
				new BigDecimal(boughtUnitedStatesOfAmericaShares.getPurchaseQuantity()), MathContext.DECIMAL128);
		unitedStatesOfAmericaShares.setPurchaseNetPricePerUnit(String.valueOf(purchaseNetPricePerUnit));

		return unitedStatesOfAmericaShares;
	}

	private AddUnitedStatesOfAmericaSharesSplit updateSharesSplit(
			UnitedStatesOfAmericaShares boughtUnitedStatesOfAmericaShares) throws AnukyaException {

		// Check if the stock is purchased
		File singleScriptFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USA_EQ_PURCHASE_UPDATE_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ boughtUnitedStatesOfAmericaShares.getYahooCode() + AnukyaConstants.JSON_EXTENSION);

		File nonConsolidatedFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USA_EQ_PURCHASE_UPDATE_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);

		File consolidatedFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USA_EQ_PURCHASE_UPDATE_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		if (!anukyaDataUtils.isFileExists(singleScriptFile)) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while adding United States of America bought shares -> ");
			logMessage.append("Message: Updating share split error | ");
			logMessage.append("Reason: Internal error");
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0004);
			errorDetails.setErrorMessage("Script is not purchased");
			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while adding bought shares (Updating share split)", errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		List<UnitedStatesOfAmericaShares> nonConsolidatedList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(nonConsolidatedFile);
		List<UnitedStatesOfAmericaShares> consolidatedList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(consolidatedFile);
		List<UnitedStatesOfAmericaShares> singleScriptList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(singleScriptFile);

		AddUnitedStatesOfAmericaSharesSplit addUnitedStatesOfAmericaSharesSplit = new AddUnitedStatesOfAmericaSharesSplit();

		BigDecimal oldFaceValue = new BigDecimal(
				boughtUnitedStatesOfAmericaShares.getSharesSplitRatio().split(AnukyaConstants.RATIO_CONSTANT)[0]);
		BigDecimal newFaceValue = new BigDecimal(
				boughtUnitedStatesOfAmericaShares.getSharesSplitRatio().split(AnukyaConstants.RATIO_CONSTANT)[1]);

		String dateInddMmmYyyy = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		calculateShareSplit(oldFaceValue, newFaceValue, consolidatedList,
				boughtUnitedStatesOfAmericaShares.getYahooCode(), dateInddMmmYyyy);

		calculateShareSplit(oldFaceValue, newFaceValue, nonConsolidatedList,
				boughtUnitedStatesOfAmericaShares.getYahooCode(), dateInddMmmYyyy);

		calculateShareSplit(oldFaceValue, newFaceValue, singleScriptList,
				boughtUnitedStatesOfAmericaShares.getYahooCode(), dateInddMmmYyyy);

		addUnitedStatesOfAmericaSharesSplit.setConsolidatedList(consolidatedList);
		addUnitedStatesOfAmericaSharesSplit.setNonConsolidatedList(nonConsolidatedList);
		addUnitedStatesOfAmericaSharesSplit.setSingleScriptList(singleScriptList);

		return addUnitedStatesOfAmericaSharesSplit;
	}

	private void calculateShareSplit(BigDecimal oldFaceValue, BigDecimal newFaceValue,
			List<UnitedStatesOfAmericaShares> existingScriptList, String scriptCode, String dateInddMmmYyyy) {

		for (UnitedStatesOfAmericaShares existingUnitedStatesOfAmericaShare : existingScriptList) {
			if (scriptCode.equalsIgnoreCase(existingUnitedStatesOfAmericaShare.getYahooCode())) {

				BigDecimal existingPurchaseQuantity = new BigDecimal(
						existingUnitedStatesOfAmericaShare.getPurchaseQuantity());
				BigDecimal existingPurchasePrice = new BigDecimal(
						existingUnitedStatesOfAmericaShare.getPurchasePrice());

				BigDecimal updatedPurchaseQuantity = existingPurchaseQuantity.multiply(oldFaceValue)
						.divide(newFaceValue);
				BigDecimal updatedPurchasePrice = existingPurchasePrice.multiply(newFaceValue).divide(oldFaceValue);

				existingUnitedStatesOfAmericaShare
						.setPurchaseQuantity(String.format(AnukyaConstants.FLOAT_6_DECIMAL, updatedPurchaseQuantity));
				existingUnitedStatesOfAmericaShare
						.setPurchasePrice(String.format(AnukyaConstants.FLOAT_4_DECIMAL, updatedPurchasePrice));

				existingUnitedStatesOfAmericaShare.setLastUpdatedBy(userEmailId);
				existingUnitedStatesOfAmericaShare.setLastUpdatedDate(dateInddMmmYyyy);

			}
		}
	}

	private AddUnitedStatesOfAmericaSharesBonus updateSharesBonus(
			UnitedStatesOfAmericaShares boughtUnitedStatesOfAmericaShares) throws AnukyaException {

		// Check if the stock is purchased
		File singleScriptFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.USA_EQ_PURCHASE_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ boughtUnitedStatesOfAmericaShares.getYahooCode() + AnukyaConstants.JSON_EXTENSION);

		File nonConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_PURCHASE_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);

		File consolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_PURCHASE_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		if (!singleScriptFile.exists()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while adding United States of America bought shares -> ");
			logMessage.append("Message: Updating share bonus error | ");
			logMessage.append("Reason: Internal error");
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0004);
			errorDetails.setErrorMessage("Script is not purchased");
			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while adding bought shares (Updating share bonus)", errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		List<UnitedStatesOfAmericaShares> nonConsolidatedList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(nonConsolidatedFile);
		List<UnitedStatesOfAmericaShares> consolidatedList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(consolidatedFile);
		List<UnitedStatesOfAmericaShares> singleScriptList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(singleScriptFile);

		AddUnitedStatesOfAmericaSharesBonus addUnitedStatesOfAmericaSharesBonus = new AddUnitedStatesOfAmericaSharesBonus();

		BigDecimal firstValue = new BigDecimal(
				boughtUnitedStatesOfAmericaShares.getSharesBonusRatio().split(AnukyaConstants.RATIO_CONSTANT)[0]);
		BigDecimal secondValue = new BigDecimal(
				boughtUnitedStatesOfAmericaShares.getSharesBonusRatio().split(AnukyaConstants.RATIO_CONSTANT)[1]);

		// Update missing values in boughtUnitedStatesOfAmericaShares
		updateMissingFields(consolidatedList, boughtUnitedStatesOfAmericaShares);

		String dateInddMmmYyyy = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		Optional<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesOptional = consolidatedList.parallelStream()
				.filter(consolidate -> consolidate.getYahooCode()
						.equalsIgnoreCase(boughtUnitedStatesOfAmericaShares.getYahooCode()))
				.findFirst();

		BigDecimal scriptPurchaseQuantity = unitedStatesOfAmericaSharesOptional.isPresent()
				? new BigDecimal(unitedStatesOfAmericaSharesOptional.get().getPurchaseQuantity())
				: new BigDecimal(AnukyaConstants.NUMBER_0);

		BigDecimal scriptUpdatedPurchaseQuantity = scriptPurchaseQuantity.multiply(firstValue).divide(secondValue,
				MathContext.DECIMAL128);

		if (scriptUpdatedPurchaseQuantity.compareTo(new BigDecimal(AnukyaConstants.NUMBER_0)) == 0) {
			return addUnitedStatesOfAmericaSharesBonus;
		}

		UnitedStatesOfAmericaShares unitedStatesOfAmericaShares = createBonusSharesDetails(
				boughtUnitedStatesOfAmericaShares, scriptUpdatedPurchaseQuantity, dateInddMmmYyyy);

		// Updating Bonus details for all existing non consolidated shares
		updateShareBonusDetails(nonConsolidatedList, boughtUnitedStatesOfAmericaShares, dateInddMmmYyyy);
		// Updating Bonus details for all existing single shares
		updateShareBonusDetails(singleScriptList, boughtUnitedStatesOfAmericaShares, dateInddMmmYyyy);
		// Updating bonus shares details for current bought shares
		updateShareBonusDetails(unitedStatesOfAmericaShares, boughtUnitedStatesOfAmericaShares, dateInddMmmYyyy);

		consolidatedList = addConsolidated(unitedStatesOfAmericaShares);
		addUnitedStatesOfAmericaSharesBonus.setConsolidatedList(consolidatedList);

		nonConsolidatedList.add(unitedStatesOfAmericaShares);
		singleScriptList.add(unitedStatesOfAmericaShares);

		addUnitedStatesOfAmericaSharesBonus.setConsolidatedList(consolidatedList);
		addUnitedStatesOfAmericaSharesBonus.setNonConsolidatedList(nonConsolidatedList);
		addUnitedStatesOfAmericaSharesBonus.setSingleScriptList(singleScriptList);

		return addUnitedStatesOfAmericaSharesBonus;
	}

	private void updateMissingFields(List<UnitedStatesOfAmericaShares> consolidatedList,
			UnitedStatesOfAmericaShares boughtUnitedStatesOfAmericaShares) {

		Optional<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesOptional = consolidatedList.parallelStream()
				.filter(consolidate -> consolidate.getYahooCode()
						.equalsIgnoreCase(boughtUnitedStatesOfAmericaShares.getYahooCode()))
				.findFirst();
		UnitedStatesOfAmericaShares unitedStatesOfAmericaShares = unitedStatesOfAmericaSharesOptional.isPresent()
				? unitedStatesOfAmericaSharesOptional.get()
				: new UnitedStatesOfAmericaShares();

		boughtUnitedStatesOfAmericaShares.setYahooCode(unitedStatesOfAmericaShares.getYahooCode());
		boughtUnitedStatesOfAmericaShares.setIsinCode(unitedStatesOfAmericaShares.getIsinCode());
		boughtUnitedStatesOfAmericaShares.setCusip(unitedStatesOfAmericaShares.getCusip());
		boughtUnitedStatesOfAmericaShares.setCategory(unitedStatesOfAmericaShares.getCategory());
		boughtUnitedStatesOfAmericaShares.setSector(unitedStatesOfAmericaShares.getSector());
		boughtUnitedStatesOfAmericaShares.setIndustry(unitedStatesOfAmericaShares.getIndustry());
		boughtUnitedStatesOfAmericaShares.setShortTermInvestment(unitedStatesOfAmericaShares.isShortTermInvestment());
	}

	private UnitedStatesOfAmericaShares createBonusSharesDetails(
			UnitedStatesOfAmericaShares boughtUnitedStatesOfAmericaShares, BigDecimal scriptUpdatedPurchaseQuantity,
			String updatedDate) {

		UnitedStatesOfAmericaShares unitedStatesOfAmericaShares = new UnitedStatesOfAmericaShares();

		unitedStatesOfAmericaShares.setId(anukyaUtils.generateId());
		unitedStatesOfAmericaShares.setScriptName(boughtUnitedStatesOfAmericaShares.getScriptName());
		unitedStatesOfAmericaShares.setYahooCode(boughtUnitedStatesOfAmericaShares.getYahooCode());
		unitedStatesOfAmericaShares.setIsinCode(boughtUnitedStatesOfAmericaShares.getIsinCode());
		unitedStatesOfAmericaShares.setCusip(boughtUnitedStatesOfAmericaShares.getCusip());
		unitedStatesOfAmericaShares.setCategory(boughtUnitedStatesOfAmericaShares.getCategory());
		unitedStatesOfAmericaShares.setSector(boughtUnitedStatesOfAmericaShares.getSector());
		unitedStatesOfAmericaShares.setIndustry(boughtUnitedStatesOfAmericaShares.getIndustry());
		unitedStatesOfAmericaShares.setShortTermInvestment(boughtUnitedStatesOfAmericaShares.isShortTermInvestment());
		unitedStatesOfAmericaShares.setPurchaseDate(boughtUnitedStatesOfAmericaShares.getSharesBonusDate());
		unitedStatesOfAmericaShares
				.setPurchaseQuantity(String.format(AnukyaConstants.FLOAT_6_DECIMAL, scriptUpdatedPurchaseQuantity));
		unitedStatesOfAmericaShares.setLastUpdatedBy(userEmailId);
		unitedStatesOfAmericaShares.setLastUpdatedDate(updatedDate);

		return unitedStatesOfAmericaShares;
	}

	private void updateShareBonusDetails(List<UnitedStatesOfAmericaShares> existingSharesList,
			UnitedStatesOfAmericaShares boughtUnitedStatesOfAmericaShares, String dateInddMmmYyyy) {

		for (UnitedStatesOfAmericaShares existingShare : existingSharesList) {
			if (existingShare.getYahooCode().equalsIgnoreCase(boughtUnitedStatesOfAmericaShares.getYahooCode())) {

				String sharesBonus = existingShare.getSharesBonus();
				existingShare.setSharesBonus(sharesBonus + boughtUnitedStatesOfAmericaShares.getSharesBonusRatio()
						+ AnukyaConstants.SPACE + AnukyaConstants.ON_CONSTANT + AnukyaConstants.SPACE
						+ boughtUnitedStatesOfAmericaShares.getSharesBonusDate() + AnukyaConstants.PIPE_SEPARATOR);

				existingShare.setSharesBonusDisplay(boughtUnitedStatesOfAmericaShares.getSharesBonusRatio()
						+ AnukyaConstants.SPACE + AnukyaConstants.ON_CONSTANT + AnukyaConstants.SPACE
						+ boughtUnitedStatesOfAmericaShares.getSharesBonusDate());

				existingShare.setLastUpdatedBy(userEmailId);
				existingShare.setLastUpdatedDate(dateInddMmmYyyy);
			}
		}
	}

	private void updateShareBonusDetails(UnitedStatesOfAmericaShares existingUnitedStatesOfAmericaShare,
			UnitedStatesOfAmericaShares boughtUnitedStatesOfAmericaShares, String dateInddMmmYyyy) {

		String sharesBonus = existingUnitedStatesOfAmericaShare.getSharesBonus();
		existingUnitedStatesOfAmericaShare
				.setSharesBonus(sharesBonus + boughtUnitedStatesOfAmericaShares.getSharesBonusRatio()
						+ AnukyaConstants.SPACE + AnukyaConstants.ON_CONSTANT + AnukyaConstants.SPACE
						+ boughtUnitedStatesOfAmericaShares.getSharesBonusDate() + AnukyaConstants.PIPE_SEPARATOR);

		existingUnitedStatesOfAmericaShare.setSharesBonusDisplay(boughtUnitedStatesOfAmericaShares.getSharesBonusRatio()
				+ AnukyaConstants.SPACE + AnukyaConstants.ON_CONSTANT + AnukyaConstants.SPACE
				+ boughtUnitedStatesOfAmericaShares.getSharesBonusDate());

		existingUnitedStatesOfAmericaShare.setCreatedBy(userEmailId);
		existingUnitedStatesOfAmericaShare.setCreatedDate(dateInddMmmYyyy);

	}

	private List<UnitedStatesOfAmericaShares> getUnitedStatesOfAmericaSharesNonConsolidated(String searchTerm,
			boolean isLongTermOnly) throws AnukyaException {

		File nonConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_PURCHASE_MAIN_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);

		List<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(nonConsolidatedFile);

		return filterBySearchTerm(unitedStatesOfAmericaSharesList, searchTerm, isLongTermOnly);
	}

	private List<UnitedStatesOfAmericaShares> getUnitedStatesOfAmericaSharesConsolidated(String searchTerm,
			boolean isLongTermOnly) throws AnukyaException {

		File consolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USA_EQ_PURCHASE_MAIN_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		List<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesList = unitedStatesOfAmericaSharesData
				.getUnitedStatesOfAmericaShares(consolidatedFile);

		return filterBySearchTerm(unitedStatesOfAmericaSharesList, searchTerm, isLongTermOnly);
	}

	private List<UnitedStatesOfAmericaShares> filterBySearchTerm(
			List<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesList, String searchTerm,
			boolean isLongTermOnly) {

		if (!searchTerm.isEmpty() && isLongTermOnly) {

			return unitedStatesOfAmericaSharesList.stream()
					.filter(unitedStatesOfAmericaShares -> !unitedStatesOfAmericaShares.isShortTermInvestment()
							&& (unitedStatesOfAmericaShares.getScriptName().equalsIgnoreCase(searchTerm)
									|| unitedStatesOfAmericaShares.getYahooCode().equalsIgnoreCase(searchTerm)
									|| unitedStatesOfAmericaShares.getCategory().equalsIgnoreCase(searchTerm)
									|| unitedStatesOfAmericaShares.getSector().equalsIgnoreCase(searchTerm)
									|| unitedStatesOfAmericaShares.getIndustry().equalsIgnoreCase(searchTerm)))
					.collect(Collectors.toList());
		} else if (searchTerm.isEmpty() && isLongTermOnly) {
			return unitedStatesOfAmericaSharesList.stream()
					.filter(unitedStatesOfAmericaShares -> !unitedStatesOfAmericaShares.isShortTermInvestment())
					.collect(Collectors.toList());
		} else if (!searchTerm.isEmpty() && !isLongTermOnly) {
			return unitedStatesOfAmericaSharesList.stream()
					.filter(unitedStatesOfAmericaShares -> unitedStatesOfAmericaShares.getScriptName().equalsIgnoreCase(
							searchTerm) || unitedStatesOfAmericaShares.getYahooCode().equalsIgnoreCase(searchTerm)
							|| unitedStatesOfAmericaShares.getCategory().equalsIgnoreCase(searchTerm)
							|| unitedStatesOfAmericaShares.getSector().equalsIgnoreCase(searchTerm)
							|| unitedStatesOfAmericaShares.getIndustry().equalsIgnoreCase(searchTerm))
					.collect(Collectors.toList());
		}

		return unitedStatesOfAmericaSharesList;
	}

	private UnitedStatesOfAmericaSharesResponse calculateUnitedStatesOfAmericaShares(
			List<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesList) throws AnukyaException {

		UnitedStatesOfAmericaSharesResponse unitedStatesOfAmericaSharesResponse = new UnitedStatesOfAmericaSharesResponse();
		unitedStatesOfAmericaSharesResponse.setUnitedStatesOfAmericaSharesTotal(new UnitedStatesOfAmericaShares());

		Map<String, Long> sellTransactionFeesScriptCountMap = unitedStatesOfAmericaSharesList.parallelStream()
				.collect(Collectors.groupingBy(UnitedStatesOfAmericaShares::getYahooCode, Collectors.counting()));

		return buildUnitedStatesOfAmericaSharesResponse(
				unitedStatesOfAmericaSharesList, unitedStatesOfAmericaSharesUtils
						.getUnitedStatesOfAmericaSharesLastTradingPriceMap(unitedStatesOfAmericaSharesList),
				sellTransactionFeesScriptCountMap);
	}

	private UnitedStatesOfAmericaSharesResponse buildUnitedStatesOfAmericaSharesResponse(
			List<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesList,
			Map<String, UnitedStatesOfAmericaSharesLastTradingPrice> unitedStatesOfAmericaSharesLastTradingPriceMap,
			Map<String, Long> sellTransactionFeesScriptCountMap) {

		UnitedStatesOfAmericaSharesResponse unitedStatesOfAmericaSharesResponse = new UnitedStatesOfAmericaSharesResponse();
		unitedStatesOfAmericaSharesResponse.setUnitedStatesOfAmericaSharesTotal(new UnitedStatesOfAmericaShares());

		List<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesResponseList = new ArrayList<>();

		for (UnitedStatesOfAmericaShares unitedStatesOfAmericaShare : unitedStatesOfAmericaSharesList) {

			BigDecimal purchaseQuantity = new BigDecimal(unitedStatesOfAmericaShare.getPurchaseQuantity());
			BigDecimal existingTotalPurchaseQuantity = new BigDecimal(
					unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getPurchaseQuantity());
			BigDecimal totalPurchaseQuantity = purchaseQuantity.add(existingTotalPurchaseQuantity);
			unitedStatesOfAmericaShare
					.setPurchaseQuantity(String.format(AnukyaConstants.FLOAT_6_DECIMAL, purchaseQuantity));
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
					.setPurchaseQuantity(String.format(AnukyaConstants.FLOAT_6_DECIMAL, totalPurchaseQuantity));

			BigDecimal purchasePrice = new BigDecimal(unitedStatesOfAmericaShare.getPurchasePrice());
			BigDecimal existingTotalPurchasePrice = new BigDecimal(
					unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getPurchasePrice());
			BigDecimal totalPurchasePrice = purchaseQuantity.multiply(purchasePrice)
					.add(existingTotalPurchaseQuantity.multiply(existingTotalPurchasePrice))
					.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
			unitedStatesOfAmericaShare.setPurchasePrice(String.format(AnukyaConstants.FLOAT_4_DECIMAL, purchasePrice));
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
					.setPurchasePrice(String.format(AnukyaConstants.FLOAT_4_DECIMAL, totalPurchasePrice));

			BigDecimal purchaseGrossTotal = new BigDecimal(unitedStatesOfAmericaShare.getPurchaseGrossTotal());
			BigDecimal existingTotalPurchaseGrossTotal = new BigDecimal(
					unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getPurchaseGrossTotal());
			BigDecimal totalPurchaseGrossTotal = purchaseGrossTotal.add(existingTotalPurchaseGrossTotal);
			unitedStatesOfAmericaShare
					.setPurchaseGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseGrossTotal));
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
					.setPurchaseGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseGrossTotal));

			BigDecimal purchaseCommission = new BigDecimal(unitedStatesOfAmericaShare.getPurchaseCommission());
			BigDecimal existingTotalPurchaseCommission = new BigDecimal(
					unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getPurchaseCommission());
			BigDecimal totalPurchaseCommission = purchaseCommission.add(existingTotalPurchaseCommission);
			unitedStatesOfAmericaShare
					.setPurchaseCommission(String.format(AnukyaConstants.FLOAT_4_DECIMAL, purchaseCommission));
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
					.setPurchaseCommission(String.format(AnukyaConstants.FLOAT_4_DECIMAL, totalPurchaseCommission));

			BigDecimal purchaseTransactionFees = new BigDecimal(
					unitedStatesOfAmericaShare.getPurchaseTransactionFees());
			BigDecimal existingTotalPurchaseTransactionFees = new BigDecimal(unitedStatesOfAmericaSharesResponse
					.getUnitedStatesOfAmericaSharesTotal().getPurchaseTransactionFees());
			BigDecimal totalPurchaseTransactionFees = purchaseTransactionFees.add(existingTotalPurchaseTransactionFees);
			unitedStatesOfAmericaShare.setPurchaseTransactionFees(
					String.format(AnukyaConstants.FLOAT_4_DECIMAL, purchaseTransactionFees));
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().setPurchaseTransactionFees(
					String.format(AnukyaConstants.FLOAT_4_DECIMAL, totalPurchaseTransactionFees));

			BigDecimal purchaseOtherFees = new BigDecimal(unitedStatesOfAmericaShare.getPurchaseOtherFees());
			BigDecimal existingPurchaseOtherFees = new BigDecimal(
					unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getPurchaseOtherFees());
			BigDecimal totalPurchaseOtherFees = purchaseOtherFees.add(existingPurchaseOtherFees);
			unitedStatesOfAmericaShare
					.setPurchaseOtherFees(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseOtherFees));
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
					.setPurchaseOtherFees(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseOtherFees));

			BigDecimal purchaseTotal = new BigDecimal(unitedStatesOfAmericaShare.getPurchaseTotal());
			BigDecimal existingTotalPurchaseTotal = new BigDecimal(
					unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getPurchaseTotal());
			BigDecimal totalPurchaseTotal = purchaseTotal.add(existingTotalPurchaseTotal);
			unitedStatesOfAmericaShare.setPurchaseTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseTotal));
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
					.setPurchaseTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseTotal));

			BigDecimal purchaseNetPricePerUnit = new BigDecimal(
					unitedStatesOfAmericaShare.getPurchaseNetPricePerUnit());
			BigDecimal existingTotalPurchaseNetPricePerUnit = new BigDecimal(unitedStatesOfAmericaSharesResponse
					.getUnitedStatesOfAmericaSharesTotal().getPurchaseNetPricePerUnit());
			BigDecimal totalPurchaseNetPricePerUnit = purchaseQuantity.multiply(purchaseNetPricePerUnit)
					.add(existingTotalPurchaseQuantity.multiply(existingTotalPurchaseNetPricePerUnit))
					.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
			unitedStatesOfAmericaShare.setPurchaseNetPricePerUnit(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseNetPricePerUnit));
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().setPurchaseNetPricePerUnit(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseNetPricePerUnit));

			BigDecimal sellQuantity = new BigDecimal(unitedStatesOfAmericaShare.getPurchaseQuantity());
			unitedStatesOfAmericaShare.setSellQuantity(String.format(AnukyaConstants.FLOAT_6_DECIMAL, sellQuantity));
			BigDecimal existingTotalSellQuantity = new BigDecimal(
					unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getSellQuantity());
			BigDecimal totalSellQuantity = sellQuantity.add(existingTotalSellQuantity);
			unitedStatesOfAmericaShare.setSellQuantity(String.format(AnukyaConstants.FLOAT_6_DECIMAL, sellQuantity));
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
					.setSellQuantity(String.format(AnukyaConstants.FLOAT_6_DECIMAL, totalSellQuantity));

			BigDecimal sellPrice = new BigDecimal(unitedStatesOfAmericaSharesLastTradingPriceMap
					.get(unitedStatesOfAmericaShare.getYahooCode()).getLastTradingPrice());
			BigDecimal existingTotalSellPrice = new BigDecimal(
					unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getSellPrice());
			BigDecimal totalSellPrice = sellQuantity.multiply(sellPrice)
					.add(existingTotalSellQuantity.multiply(existingTotalSellPrice))
					.divide(totalSellQuantity, MathContext.DECIMAL128);
			unitedStatesOfAmericaShare.setSellPrice(String.format(AnukyaConstants.FLOAT_4_DECIMAL, sellPrice));
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
					.setSellPrice(String.format(AnukyaConstants.FLOAT_4_DECIMAL, totalSellPrice));

			BigDecimal sellGrossTotal = sellQuantity.multiply(sellPrice);
			BigDecimal existingTotalSellGrossTotal = new BigDecimal(
					unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getSellGrossTotal());
			BigDecimal totalSellGrossTotal = sellGrossTotal.add(existingTotalSellGrossTotal);
			unitedStatesOfAmericaShare
					.setSellGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellGrossTotal));
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
					.setSellGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellGrossTotal));

			BigDecimal sellCommission = unitedStatesOfAmericaExpenditureUtils.calculateCommission();
			BigDecimal existingTotalSellCommission = new BigDecimal(
					unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getSellCommission());
			BigDecimal totalSellCommission = sellCommission.add(existingTotalSellCommission);
			unitedStatesOfAmericaShare
					.setSellCommission(String.format(AnukyaConstants.FLOAT_4_DECIMAL, sellCommission));
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
					.setSellCommission(String.format(AnukyaConstants.FLOAT_4_DECIMAL, totalSellCommission));

			BigDecimal sellTransactionFees = unitedStatesOfAmericaExpenditureUtils.calculateTransactionFees(
					sellTransactionFeesScriptCountMap.get(unitedStatesOfAmericaShare.getYahooCode()));
			BigDecimal existingTotalSellTransactionFees = new BigDecimal(
					unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getSellTransactionFees());
			BigDecimal totalSellTransactionFees = sellTransactionFees.add(existingTotalSellTransactionFees);
			unitedStatesOfAmericaShare
					.setSellTransactionFees(String.format(AnukyaConstants.FLOAT_4_DECIMAL, sellTransactionFees));
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
					.setSellTransactionFees(String.format(AnukyaConstants.FLOAT_4_DECIMAL, totalSellTransactionFees));

			BigDecimal sellOtherFees = unitedStatesOfAmericaExpenditureUtils.getOtherFees();
			BigDecimal existingSellOtherFees = new BigDecimal(
					unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getSellOtherFees());
			BigDecimal totalSellOtherFees = sellOtherFees.add(existingSellOtherFees);
			unitedStatesOfAmericaShare.setSellOtherFees(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellOtherFees));
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
					.setSellOtherFees(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellOtherFees));

			BigDecimal sellTotal = sellGrossTotal.subtract(sellCommission).subtract(sellTransactionFees)
					.subtract(sellOtherFees);
			BigDecimal existingTotalSellTotal = new BigDecimal(
					unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getSellTotal());
			BigDecimal totalSellTotal = sellTotal.add(existingTotalSellTotal);
			unitedStatesOfAmericaShare.setSellTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellTotal));
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
					.setSellTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellTotal));

			BigDecimal sellNetPricePerUnit = sellTotal.divide(sellQuantity, MathContext.DECIMAL128);
			BigDecimal existingTotalSellNetPricePerUnit = new BigDecimal(
					unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().getSellNetPricePerUnit());
			BigDecimal totalSellNetPricePerUnit = sellQuantity.multiply(sellNetPricePerUnit)
					.add(existingTotalSellQuantity.multiply(existingTotalSellNetPricePerUnit))
					.divide(totalSellQuantity, MathContext.DECIMAL128);
			unitedStatesOfAmericaShare
					.setSellNetPricePerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellNetPricePerUnit));
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
					.setSellNetPricePerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellNetPricePerUnit));

			BigDecimal grossProfitLoss = sellGrossTotal.subtract(purchaseGrossTotal);
			unitedStatesOfAmericaShare
					.setGrossProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, grossProfitLoss));
			BigDecimal totalGrossProfitLoss = totalSellGrossTotal.subtract(totalPurchaseGrossTotal);
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
					.setGrossProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalGrossProfitLoss));

			BigDecimal grossProfitLossPercentage;
			try {
				grossProfitLossPercentage = sellGrossTotal.divide(purchaseGrossTotal, MathContext.DECIMAL128)
						.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
						.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
			} catch (ArithmeticException e) {
				grossProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_0);
			}
			unitedStatesOfAmericaShare.setGrossProfitLossPercentage(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, grossProfitLossPercentage));
			BigDecimal totalGrossProfitLossPercentage;
			try {
				totalGrossProfitLossPercentage = totalSellGrossTotal
						.divide(totalPurchaseGrossTotal, MathContext.DECIMAL128)
						.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
						.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
			} catch (ArithmeticException e) {
				totalGrossProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_0);
			}
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().setGrossProfitLossPercentage(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalGrossProfitLossPercentage));

			BigDecimal totalProfitLoss = sellTotal.subtract(purchaseTotal);
			unitedStatesOfAmericaShare
					.setTotalProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLoss));
			BigDecimal totalTotalProfitLoss = totalSellTotal.subtract(totalPurchaseTotal);
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
					.setTotalProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalTotalProfitLoss));

			BigDecimal totalProfitLossPercentage;
			try {
				totalProfitLossPercentage = sellTotal.divide(purchaseTotal, MathContext.DECIMAL128)
						.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
						.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
			} catch (ArithmeticException e) {
				totalProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_0);
			}
			unitedStatesOfAmericaShare.setTotalProfitLossPercentage(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLossPercentage));
			BigDecimal totalTotalProfitLossPercentage;
			try {
				totalTotalProfitLossPercentage = totalSellTotal.divide(totalPurchaseTotal, MathContext.DECIMAL128)
						.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
						.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
			} catch (ArithmeticException e) {
				totalTotalProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_1);
			}
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal().setTotalProfitLossPercentage(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalTotalProfitLossPercentage));

			BigDecimal netProfitLossPerUnit = sellNetPricePerUnit.subtract(purchaseNetPricePerUnit);
			unitedStatesOfAmericaShare
					.setNetProfitLossPerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL, netProfitLossPerUnit));
			BigDecimal totalNetProfitLossPerUnit = totalSellNetPricePerUnit.subtract(totalPurchaseNetPricePerUnit);
			unitedStatesOfAmericaSharesResponse.getUnitedStatesOfAmericaSharesTotal()
					.setNetProfitLossPerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalNetProfitLossPerUnit));

			unitedStatesOfAmericaSharesResponseList.add(unitedStatesOfAmericaShare);
		}

		unitedStatesOfAmericaSharesResponse.setUnitedStatesOfAmericaSharesList(unitedStatesOfAmericaSharesResponseList);

		return unitedStatesOfAmericaSharesResponse;
	}
}
