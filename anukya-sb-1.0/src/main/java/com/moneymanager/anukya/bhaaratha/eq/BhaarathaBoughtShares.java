package com.moneymanager.anukya.bhaaratha.eq;

import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.moneymanager.anukya.data.impl.BhaarathaSharesData;
import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.ErrorDetails;
import com.moneymanager.anukya.model.AddBhaarathaSharesBonus;
import com.moneymanager.anukya.model.AddBhaarathaSharesSplit;
import com.moneymanager.anukya.model.BhaarathaShares;
import com.moneymanager.anukya.model.BhaarathaSharesCalculationDetails;
import com.moneymanager.anukya.model.BhaarathaSharesLastTradingPrice;
import com.moneymanager.anukya.model.BhaarathaSharesResponse;
import com.moneymanager.anukya.model.BhaarathaSharesScriptCodeDetails;
import com.moneymanager.anukya.model.CommonResponse;
import com.moneymanager.anukya.utils.AnukyaConstants;
import com.moneymanager.anukya.utils.AnukyaDataUtils;
import com.moneymanager.anukya.utils.AnukyaUtils;
import com.moneymanager.anukya.utils.BhaarathaExpenditureUtils;
import com.moneymanager.anukya.utils.BhaarathaSharesUtils;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class BhaarathaBoughtShares extends AbstractBhaarathaShares {

	@Value("${DATABASE.BASE.LOCATION}")
	private String databaseBaseLocation;

	@Autowired
	private AnukyaDataUtils anukyaDataUtils;

	@Autowired
	private AnukyaUtils anukyaUtils;

	@Autowired
	private BhaarathaSharesData bhaarathaSharesData;

	@Autowired
	private BhaarathaExpenditureUtils bhaarathaExpenditureUtils;

	@Autowired
	private BhaarathaSharesUtils bhaarathaSharesUtils;

	@Override
	public CommonResponse addShares(BhaarathaShares boughtBhaarathaShares) throws AnukyaException {

		CommonResponse commonResponse = new CommonResponse();

		File mainDirectory = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_PURCHASE_MAIN_DIRECTORY);
		File updateDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY);
		File mainBackUpDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.BHA_EQ_PURCHASE_MAIN_BACKUP_DIRECTORY);

		// Delete update and main backup directory
		anukyaDataUtils.deleteDirectory(updateDirectory);
		anukyaDataUtils.deleteDirectory(mainBackUpDirectory);

		// Copy main directory into update and main backup directory
		anukyaDataUtils.copyDirectory(mainDirectory, updateDirectory);
		anukyaDataUtils.copyDirectory(mainDirectory, mainBackUpDirectory);

		// Modify files in baseLocation/update
		File nonConsolidatedFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);

		File consolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		File singleScriptFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ boughtBhaarathaShares.getBseScriptCode() + AnukyaConstants.JSON_EXTENSION);

		List<BhaarathaShares> nonConsolidatedList;
		List<BhaarathaShares> consolidatedList;
		List<BhaarathaShares> singleScriptList;

		// If shares split
		if (boughtBhaarathaShares.isSharesSplitSelected()) {
			AddBhaarathaSharesSplit addBhaarathaSharesSplit = updateSharesSplit(boughtBhaarathaShares);
			consolidatedList = addBhaarathaSharesSplit.getConsolidatedList();
			nonConsolidatedList = addBhaarathaSharesSplit.getNonConsolidatedList();
			singleScriptList = addBhaarathaSharesSplit.getSingleScriptList();

		} else if (boughtBhaarathaShares.isSharesBonusSelected()) {
			AddBhaarathaSharesBonus addBhaarathaSharesBonus = updateSharesBonus(boughtBhaarathaShares);
			if (addBhaarathaSharesBonus.getConsolidatedList() == null) {
				commonResponse.setStatus(true);
				commonResponse.setMessage("You do not get any bonus shares, however, bonus is adjusted with cash");

				return commonResponse;
			}
			consolidatedList = addBhaarathaSharesBonus.getConsolidatedList();
			nonConsolidatedList = addBhaarathaSharesBonus.getNonConsolidatedList();
			singleScriptList = addBhaarathaSharesBonus.getSingleScriptList();
		} else {
			consolidatedList = addConsolidated(boughtBhaarathaShares);
			nonConsolidatedList = addNonConsolidated(boughtBhaarathaShares);
			singleScriptList = addSingleScript(boughtBhaarathaShares);
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
	public BhaarathaSharesResponse getBhaarathaShares(boolean isNonConsolidated, String searchTerm,
			boolean isLongTermOnly) throws AnukyaException {

		List<BhaarathaShares> bhaarathaSharesList;

		if (isNonConsolidated) {
			bhaarathaSharesList = getBhaarathaSharesNonConsolidated(searchTerm, isLongTermOnly);
		} else {
			bhaarathaSharesList = getBhaarathaSharesConsolidated(searchTerm, isLongTermOnly);
		}

		if (bhaarathaSharesList.isEmpty()) {
			throw new AnukyaException("No content for Bhaaratha Shares", null, AnukyaErrorConstants.NO_CONTENT,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		return calculateBhaarathaShares(bhaarathaSharesList);
	}

	@Override
	public BhaarathaSharesResponse getOneShareDetails(List<BhaarathaShares> bhaarathaSharesList)
			throws AnukyaException {

		Map<String, Long> sellDepositoryChargesScriptCountMap = bhaarathaSharesList.parallelStream()
				.collect(Collectors.groupingBy(BhaarathaShares::getBseScriptCode, Collectors.counting()));

		return buildBhaarathaSharesResponse(bhaarathaSharesList,
				getBhaarathaSharesCalculationDetails(bhaarathaSharesList), sellDepositoryChargesScriptCountMap);
	}

	@Override
	public CommonResponse addSharesBulkUpload(BhaarathaShares boughtBhaarathaShares) throws AnukyaException {

		CommonResponse commonResponse = new CommonResponse();
		// Modify files in baseLocation/update
		File nonConsolidatedFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);

		File consolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		File singleScriptFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ boughtBhaarathaShares.getBseScriptCode() + AnukyaConstants.JSON_EXTENSION);

		List<BhaarathaShares> nonConsolidatedList;
		List<BhaarathaShares> consolidatedList;
		List<BhaarathaShares> singleScriptList;

		// If shares split
		if (boughtBhaarathaShares.isSharesSplitSelected()) {
			AddBhaarathaSharesSplit addBhaarathaSharesSplit = updateSharesSplit(boughtBhaarathaShares);
			consolidatedList = addBhaarathaSharesSplit.getConsolidatedList();
			nonConsolidatedList = addBhaarathaSharesSplit.getNonConsolidatedList();
			singleScriptList = addBhaarathaSharesSplit.getSingleScriptList();

		} else if (boughtBhaarathaShares.isSharesBonusSelected()) {
			AddBhaarathaSharesBonus addBhaarathaSharesBonus = updateSharesBonus(boughtBhaarathaShares);
			if (addBhaarathaSharesBonus.getConsolidatedList() == null) {
				commonResponse.setStatus(true);
				commonResponse.setMessage("You do not get any bonus shares, however, bonus is adjusted with cash");

				return commonResponse;
			}
			consolidatedList = addBhaarathaSharesBonus.getConsolidatedList();
			nonConsolidatedList = addBhaarathaSharesBonus.getNonConsolidatedList();
			singleScriptList = addBhaarathaSharesBonus.getSingleScriptList();
		} else {
			consolidatedList = addConsolidated(boughtBhaarathaShares);
			nonConsolidatedList = addNonConsolidated(boughtBhaarathaShares);
			singleScriptList = addSingleScript(boughtBhaarathaShares);
		}

		// Update the file in /update directory
		anukyaDataUtils.createOrUpdateFile(nonConsolidatedFile, nonConsolidatedList);
		anukyaDataUtils.createOrUpdateFile(consolidatedFile, consolidatedList);
		anukyaDataUtils.createOrUpdateFile(singleScriptFile, singleScriptList);

		commonResponse.setStatus(true);
		commonResponse.setMessage("Shares added successfully");

		return commonResponse;
	}

	// Private methods
	private List<BhaarathaShares> addConsolidated(BhaarathaShares boughtBhaarathaShares) throws AnukyaException {

		File consolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		String dateInddMmmYyyy = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		List<BhaarathaShares> consolidatedList = bhaarathaSharesData.getBhaarathaShares(consolidatedFile);

		for (BhaarathaShares existingBhaarathaShare : consolidatedList) {
			if (boughtBhaarathaShares.getBseScriptCode().equalsIgnoreCase(existingBhaarathaShare.getBseScriptCode())
					&& boughtBhaarathaShares.getStockExchange()
							.equalsIgnoreCase(existingBhaarathaShare.getStockExchange())) {

				BigDecimal existingPurchaseQuantity = new BigDecimal(existingBhaarathaShare.getPurchaseQuantity());
				BigDecimal boughtPurchaseQuantity = new BigDecimal(boughtBhaarathaShares.getPurchaseQuantity());
				BigDecimal purchaseQuantity = existingPurchaseQuantity.add(boughtPurchaseQuantity);
				existingBhaarathaShare.setPurchaseQuantity(String.valueOf(purchaseQuantity));

				BigDecimal existingPurchasePrice = new BigDecimal(existingBhaarathaShare.getPurchasePrice());
				BigDecimal boughtPurchasePrice = new BigDecimal(boughtBhaarathaShares.getPurchasePrice());
				BigDecimal purchasePrice = existingPurchaseQuantity.multiply(existingPurchasePrice)
						.add(boughtPurchaseQuantity.multiply(boughtPurchasePrice))
						.divide(purchaseQuantity, MathContext.DECIMAL128);
				existingBhaarathaShare.setPurchasePrice(String.valueOf(purchasePrice));

				BigDecimal existingPurchaseGrossTotal = new BigDecimal(existingBhaarathaShare.getPurchaseGrossTotal());
				BigDecimal boughtPurchaseGrossTotal = boughtPurchaseQuantity.multiply(boughtPurchasePrice);
				BigDecimal purchaseGrossTotal = existingPurchaseGrossTotal.add(boughtPurchaseGrossTotal);
				existingBhaarathaShare.setPurchaseGrossTotal(String.valueOf(purchaseGrossTotal));

				BigDecimal existingPurchaseBrokerage = new BigDecimal(existingBhaarathaShare.getPurchaseBrokerage());
				BigDecimal boughtPurchaseBrokerage = new BigDecimal(boughtBhaarathaShares.getPurchaseBrokerage());
				BigDecimal purchaseBrokerage = existingPurchaseBrokerage.add(boughtPurchaseBrokerage);
				existingBhaarathaShare.setPurchaseBrokerage(String.valueOf(purchaseBrokerage));

				BigDecimal existingPurchaseSTT = new BigDecimal(existingBhaarathaShare.getPurchaseSTT());
				BigDecimal boughtPurchaseSTT = new BigDecimal(boughtBhaarathaShares.getPurchaseSTT());
				BigDecimal purchaseSTT = existingPurchaseSTT.add(boughtPurchaseSTT);
				existingBhaarathaShare.setPurchaseSTT(String.valueOf(purchaseSTT));

				BigDecimal existingPurchaseExpenditure = new BigDecimal(
						existingBhaarathaShare.getPurchaseExpenditure());
				BigDecimal boughtPurchaseExpenditure = new BigDecimal(boughtBhaarathaShares.getPurchaseExpenditure());
				BigDecimal purchaseExpenditure = existingPurchaseExpenditure.add(boughtPurchaseExpenditure);
				existingBhaarathaShare.setPurchaseExpenditure(String.valueOf(purchaseExpenditure));

				BigDecimal existingPurchaseNonExpenditure = new BigDecimal(
						existingBhaarathaShare.getPurchaseNonExpenditure());
				BigDecimal boughtPurchaseNonExpenditure = new BigDecimal(
						boughtBhaarathaShares.getPurchaseNonExpenditure());
				BigDecimal purchaseNonExpenditure = existingPurchaseNonExpenditure.add(boughtPurchaseNonExpenditure);
				existingBhaarathaShare.setPurchaseNonExpenditure(String.valueOf(purchaseNonExpenditure));

				BigDecimal existingPurchaseTotal = new BigDecimal(existingBhaarathaShare.getPurchaseTotal());
				BigDecimal boughtPurchaseTotal = boughtPurchaseGrossTotal.add(boughtPurchaseBrokerage)
						.add(boughtPurchaseSTT).add(boughtPurchaseExpenditure).add(boughtPurchaseNonExpenditure);
				BigDecimal purchaseTotal = existingPurchaseTotal.add(boughtPurchaseTotal);
				existingBhaarathaShare.setPurchaseTotal(String.valueOf(purchaseTotal));

				BigDecimal existingPurchaseNetPricePerUnit = new BigDecimal(
						existingBhaarathaShare.getPurchaseNetPricePerUnit());
				BigDecimal boughtPurchaseNetPricePerUnit = boughtPurchaseTotal.divide(boughtPurchaseQuantity,
						MathContext.DECIMAL128);
				BigDecimal purchaseNetPricePerUnit = existingPurchaseQuantity.multiply(existingPurchaseNetPricePerUnit)
						.add(boughtPurchaseQuantity.multiply(boughtPurchaseNetPricePerUnit))
						.divide(purchaseQuantity, MathContext.DECIMAL128);
				existingBhaarathaShare.setPurchaseNetPricePerUnit(String.valueOf(purchaseNetPricePerUnit));

				existingBhaarathaShare.setLastUpdatedBy(userEmailId);
				existingBhaarathaShare.setLastUpdatedDate(dateInddMmmYyyy);

				return consolidatedList;
			}
		}

		BhaarathaShares bhaarathaShares = addBhaarathaShares(boughtBhaarathaShares);

		bhaarathaShares.setCreatedBy(userEmailId);
		bhaarathaShares.setCreatedDate(dateInddMmmYyyy);
		bhaarathaShares.setLastUpdatedBy(userEmailId);
		bhaarathaShares.setLastUpdatedDate(dateInddMmmYyyy);

		consolidatedList.add(bhaarathaShares);

		return consolidatedList;
	}

	// Adding bonus shares
	private List<BhaarathaShares> addConsolidated(BhaarathaShares bseBhaarathaShares,
			BhaarathaShares nseBhaarathaShares) throws AnukyaException {

		File consolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		String dateInddMmmYyyy = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		List<BhaarathaShares> consolidatedList = bhaarathaSharesData.getBhaarathaShares(consolidatedFile);

		for (BhaarathaShares existingBhaarathaShare : consolidatedList) {
			if (bseBhaarathaShares.getBseScriptCode().equalsIgnoreCase(existingBhaarathaShare.getBseScriptCode())
					&& bseBhaarathaShares.getStockExchange()
							.equalsIgnoreCase(existingBhaarathaShare.getStockExchange())) {

				BigDecimal existingPurchaseQuantity = new BigDecimal(existingBhaarathaShare.getPurchaseQuantity());
				BigDecimal boughtPurchaseQuantity = new BigDecimal(bseBhaarathaShares.getPurchaseQuantity());
				BigDecimal purchaseQuantity = existingPurchaseQuantity.add(boughtPurchaseQuantity);
				existingBhaarathaShare.setPurchaseQuantity(String.valueOf(purchaseQuantity));

				BigDecimal existingPurchasePrice = new BigDecimal(existingBhaarathaShare.getPurchasePrice());
				BigDecimal boughtPurchasePrice = new BigDecimal(bseBhaarathaShares.getPurchasePrice());
				BigDecimal purchasePrice = existingPurchaseQuantity.multiply(existingPurchasePrice)
						.add(boughtPurchaseQuantity.multiply(boughtPurchasePrice))
						.divide(purchaseQuantity, MathContext.DECIMAL128);
				existingBhaarathaShare.setPurchasePrice(String.valueOf(purchasePrice));

				BigDecimal existingPurchaseGrossTotal = new BigDecimal(existingBhaarathaShare.getPurchaseGrossTotal());
				BigDecimal boughtPurchaseGrossTotal = boughtPurchaseQuantity.multiply(boughtPurchasePrice);
				BigDecimal purchaseGrossTotal = existingPurchaseGrossTotal.add(boughtPurchaseGrossTotal);
				existingBhaarathaShare.setPurchaseGrossTotal(String.valueOf(purchaseGrossTotal));

				BigDecimal existingPurchaseBrokerage = new BigDecimal(existingBhaarathaShare.getPurchaseBrokerage());
				BigDecimal boughtPurchaseBrokerage = new BigDecimal(bseBhaarathaShares.getPurchaseBrokerage());
				BigDecimal purchaseBrokerage = existingPurchaseBrokerage.add(boughtPurchaseBrokerage);
				existingBhaarathaShare.setPurchaseBrokerage(String.valueOf(purchaseBrokerage));

				BigDecimal existingPurchaseSTT = new BigDecimal(existingBhaarathaShare.getPurchaseSTT());
				BigDecimal boughtPurchaseSTT = new BigDecimal(bseBhaarathaShares.getPurchaseSTT());
				BigDecimal purchaseSTT = existingPurchaseSTT.add(boughtPurchaseSTT);
				existingBhaarathaShare.setPurchaseSTT(String.valueOf(purchaseSTT));

				BigDecimal existingPurchaseExpenditure = new BigDecimal(
						existingBhaarathaShare.getPurchaseExpenditure());
				BigDecimal boughtPurchaseExpenditure = new BigDecimal(bseBhaarathaShares.getPurchaseExpenditure());
				BigDecimal purchaseExpenditure = existingPurchaseExpenditure.add(boughtPurchaseExpenditure);
				existingBhaarathaShare.setPurchaseExpenditure(String.valueOf(purchaseExpenditure));

				BigDecimal existingPurchaseNonExpenditure = new BigDecimal(
						existingBhaarathaShare.getPurchaseNonExpenditure());
				BigDecimal boughtPurchaseNonExpenditure = new BigDecimal(
						bseBhaarathaShares.getPurchaseNonExpenditure());
				BigDecimal purchaseNonExpenditure = existingPurchaseNonExpenditure.add(boughtPurchaseNonExpenditure);
				existingBhaarathaShare.setPurchaseNonExpenditure(String.valueOf(purchaseNonExpenditure));

				BigDecimal existingPurchaseTotal = new BigDecimal(existingBhaarathaShare.getPurchaseTotal());
				BigDecimal boughtPurchaseTotal = boughtPurchaseGrossTotal.add(boughtPurchaseBrokerage)
						.add(boughtPurchaseSTT).add(boughtPurchaseExpenditure).add(boughtPurchaseNonExpenditure);
				BigDecimal purchaseTotal = existingPurchaseTotal.add(boughtPurchaseTotal);
				existingBhaarathaShare.setPurchaseTotal(String.valueOf(purchaseTotal));

				BigDecimal existingPurchaseNetPricePerUnit = new BigDecimal(
						existingBhaarathaShare.getPurchaseNetPricePerUnit());
				BigDecimal boughtPurchaseNetPricePerUnit = boughtPurchaseTotal.divide(boughtPurchaseQuantity,
						MathContext.DECIMAL128);
				BigDecimal purchaseNetPricePerUnit = existingPurchaseQuantity.multiply(existingPurchaseNetPricePerUnit)
						.add(boughtPurchaseQuantity.multiply(boughtPurchaseNetPricePerUnit))
						.divide(purchaseQuantity, MathContext.DECIMAL128);
				existingBhaarathaShare.setPurchaseNetPricePerUnit(String.valueOf(purchaseNetPricePerUnit));

				existingBhaarathaShare.setLastUpdatedBy(userEmailId);
				existingBhaarathaShare.setLastUpdatedDate(dateInddMmmYyyy);

			} else if (nseBhaarathaShares.getBseScriptCode().equalsIgnoreCase(existingBhaarathaShare.getBseScriptCode())
					&& nseBhaarathaShares.getStockExchange()
							.equalsIgnoreCase(existingBhaarathaShare.getStockExchange())) {

				BigDecimal existingPurchaseQuantity = new BigDecimal(existingBhaarathaShare.getPurchaseQuantity());
				BigDecimal boughtPurchaseQuantity = new BigDecimal(nseBhaarathaShares.getPurchaseQuantity());
				BigDecimal purchaseQuantity = existingPurchaseQuantity.add(boughtPurchaseQuantity);
				existingBhaarathaShare.setPurchaseQuantity(String.valueOf(purchaseQuantity));

				BigDecimal existingPurchasePrice = new BigDecimal(existingBhaarathaShare.getPurchasePrice());
				BigDecimal boughtPurchasePrice = new BigDecimal(nseBhaarathaShares.getPurchasePrice());
				BigDecimal purchasePrice = existingPurchaseQuantity.multiply(existingPurchasePrice)
						.add(boughtPurchaseQuantity.multiply(boughtPurchasePrice))
						.divide(purchaseQuantity, MathContext.DECIMAL128);
				existingBhaarathaShare.setPurchasePrice(String.valueOf(purchasePrice));

				BigDecimal existingPurchaseGrossTotal = new BigDecimal(existingBhaarathaShare.getPurchaseGrossTotal());
				BigDecimal boughtPurchaseGrossTotal = boughtPurchaseQuantity.multiply(boughtPurchasePrice);
				BigDecimal purchaseGrossTotal = existingPurchaseGrossTotal.add(boughtPurchaseGrossTotal);
				existingBhaarathaShare.setPurchaseGrossTotal(String.valueOf(purchaseGrossTotal));

				BigDecimal existingPurchaseBrokerage = new BigDecimal(existingBhaarathaShare.getPurchaseBrokerage());
				BigDecimal boughtPurchaseBrokerage = new BigDecimal(nseBhaarathaShares.getPurchaseBrokerage());
				BigDecimal purchaseBrokerage = existingPurchaseBrokerage.add(boughtPurchaseBrokerage);
				existingBhaarathaShare.setPurchaseBrokerage(String.valueOf(purchaseBrokerage));

				BigDecimal existingPurchaseSTT = new BigDecimal(existingBhaarathaShare.getPurchaseSTT());
				BigDecimal boughtPurchaseSTT = new BigDecimal(nseBhaarathaShares.getPurchaseSTT());
				BigDecimal purchaseSTT = existingPurchaseSTT.add(boughtPurchaseSTT);
				existingBhaarathaShare.setPurchaseSTT(String.valueOf(purchaseSTT));

				BigDecimal existingPurchaseExpenditure = new BigDecimal(
						existingBhaarathaShare.getPurchaseExpenditure());
				BigDecimal boughtPurchaseExpenditure = new BigDecimal(nseBhaarathaShares.getPurchaseExpenditure());
				BigDecimal purchaseExpenditure = existingPurchaseExpenditure.add(boughtPurchaseExpenditure);
				existingBhaarathaShare.setPurchaseExpenditure(String.valueOf(purchaseExpenditure));

				BigDecimal existingPurchaseNonExpenditure = new BigDecimal(
						existingBhaarathaShare.getPurchaseNonExpenditure());
				BigDecimal boughtPurchaseNonExpenditure = new BigDecimal(
						nseBhaarathaShares.getPurchaseNonExpenditure());
				BigDecimal purchaseNonExpenditure = existingPurchaseNonExpenditure.add(boughtPurchaseNonExpenditure);
				existingBhaarathaShare.setPurchaseNonExpenditure(String.valueOf(purchaseNonExpenditure));

				BigDecimal existingPurchaseTotal = new BigDecimal(existingBhaarathaShare.getPurchaseTotal());
				BigDecimal boughtPurchaseTotal = boughtPurchaseGrossTotal.add(boughtPurchaseBrokerage)
						.add(boughtPurchaseSTT).add(boughtPurchaseExpenditure).add(boughtPurchaseNonExpenditure);
				BigDecimal purchaseTotal = existingPurchaseTotal.add(boughtPurchaseTotal);
				existingBhaarathaShare.setPurchaseTotal(String.valueOf(purchaseTotal));

				BigDecimal existingPurchaseNetPricePerUnit = new BigDecimal(
						existingBhaarathaShare.getPurchaseNetPricePerUnit());
				BigDecimal boughtPurchaseNetPricePerUnit = boughtPurchaseGrossTotal.add(boughtPurchaseBrokerage)
						.add(boughtPurchaseSTT).add(boughtPurchaseExpenditure).add(boughtPurchaseNonExpenditure);
				BigDecimal purchaseNetPricePerUnit = existingPurchaseQuantity.multiply(existingPurchaseNetPricePerUnit)
						.add(boughtPurchaseQuantity.multiply(boughtPurchaseNetPricePerUnit))
						.divide(purchaseQuantity, MathContext.DECIMAL128);
				existingBhaarathaShare.setPurchaseNetPricePerUnit(String.valueOf(purchaseNetPricePerUnit));

				existingBhaarathaShare.setLastUpdatedBy(userEmailId);
				existingBhaarathaShare.setLastUpdatedDate(dateInddMmmYyyy);

			}
		}

		return consolidatedList;
	}

	private List<BhaarathaShares> addNonConsolidated(BhaarathaShares boughtBhaarathaShares) throws AnukyaException {

		File nonConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);

		String dateInddMmmYyyy = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		List<BhaarathaShares> nonConsolidatedList = bhaarathaSharesData.getBhaarathaShares(nonConsolidatedFile);

		BhaarathaShares bhaarathaShares = addBhaarathaShares(boughtBhaarathaShares);

		bhaarathaShares.setCreatedBy(userEmailId);
		bhaarathaShares.setCreatedDate(dateInddMmmYyyy);
		bhaarathaShares.setLastUpdatedBy(userEmailId);
		bhaarathaShares.setLastUpdatedDate(dateInddMmmYyyy);

		nonConsolidatedList.add(bhaarathaShares);

		return nonConsolidatedList;
	}

	private List<BhaarathaShares> addSingleScript(BhaarathaShares boughtBhaarathaShares) throws AnukyaException {

		File singleScriptFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ boughtBhaarathaShares.getBseScriptCode() + AnukyaConstants.JSON_EXTENSION);

		String dateInddMmmYyyy = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		List<BhaarathaShares> singleScriptList = new ArrayList<>();

		if (anukyaDataUtils.isFileExists(singleScriptFile)) {
			singleScriptList = bhaarathaSharesData.getBhaarathaShares(singleScriptFile);
		}

		BhaarathaShares bhaarathaShares = addBhaarathaShares(boughtBhaarathaShares);

		bhaarathaShares.setCreatedBy(userEmailId);
		bhaarathaShares.setCreatedDate(dateInddMmmYyyy);
		bhaarathaShares.setLastUpdatedBy(userEmailId);
		bhaarathaShares.setLastUpdatedDate(dateInddMmmYyyy);

		singleScriptList.add(bhaarathaShares);

		return singleScriptList;
	}

	private BhaarathaShares addBhaarathaShares(BhaarathaShares boughtBhaarathaShares) {

		BhaarathaShares bhaarathaShares = new BhaarathaShares();

		bhaarathaShares.setId(anukyaUtils.generateId());

		bhaarathaShares.setStockExchange(boughtBhaarathaShares.getStockExchange());
		bhaarathaShares.setScriptName(boughtBhaarathaShares.getScriptName());
		bhaarathaShares.setBseScriptCode(boughtBhaarathaShares.getBseScriptCode());
		bhaarathaShares.setNseScriptCode(boughtBhaarathaShares.getNseScriptCode());
		bhaarathaShares.setYahooBseScriptCode(boughtBhaarathaShares.getYahooBseScriptCode());
		bhaarathaShares.setYahooNseScriptCode(boughtBhaarathaShares.getYahooNseScriptCode());
		bhaarathaShares.setIsinCode(boughtBhaarathaShares.getIsinCode());
		bhaarathaShares.setMoneycontrolCode(boughtBhaarathaShares.getMoneycontrolCode());
		bhaarathaShares.setCategory(boughtBhaarathaShares.getCategory());
		bhaarathaShares.setSector(boughtBhaarathaShares.getSector());
		bhaarathaShares.setIndustry(boughtBhaarathaShares.getIndustry());
		bhaarathaShares.setShortTermInvestment(boughtBhaarathaShares.isShortTermInvestment());
		bhaarathaShares.setSharesSplitSelected(false);
		bhaarathaShares.setSharesBonusSelected(false);
		bhaarathaShares.setPurchaseDate(boughtBhaarathaShares.getPurchaseDate());
		bhaarathaShares.setPurchaseQuantity(boughtBhaarathaShares.getPurchaseQuantity());
		bhaarathaShares.setPurchasePrice(String.valueOf(new BigDecimal(boughtBhaarathaShares.getPurchasePrice())));

		BigDecimal purchaseGrossTotal = new BigDecimal(bhaarathaShares.getPurchaseQuantity())
				.multiply(new BigDecimal(bhaarathaShares.getPurchasePrice()));
		bhaarathaShares.setPurchaseGrossTotal(String.valueOf(purchaseGrossTotal));

		BigDecimal purchaseBrokerage = new BigDecimal(boughtBhaarathaShares.getPurchaseBrokerage());
		bhaarathaShares.setPurchaseBrokerage(String.valueOf(purchaseBrokerage));

		BigDecimal purchaseSTT = new BigDecimal(boughtBhaarathaShares.getPurchaseSTT());
		bhaarathaShares.setPurchaseSTT(String.valueOf(purchaseSTT));

		BigDecimal purchaseExpenditure = new BigDecimal(boughtBhaarathaShares.getPurchaseExpenditure());
		bhaarathaShares.setPurchaseExpenditure(String.valueOf(purchaseExpenditure));

		BigDecimal purchaseNonExpenditure = new BigDecimal(boughtBhaarathaShares.getPurchaseNonExpenditure());
		bhaarathaShares.setPurchaseNonExpenditure(String.valueOf(purchaseNonExpenditure));

		BigDecimal purchaseTotal = purchaseGrossTotal.add(purchaseBrokerage).add(purchaseSTT).add(purchaseExpenditure)
				.add(purchaseNonExpenditure);
		bhaarathaShares.setPurchaseTotal(String.valueOf(purchaseTotal));

		BigDecimal purchaseNetPricePerUnit = purchaseTotal.divide(new BigDecimal(bhaarathaShares.getPurchaseQuantity()),
				MathContext.DECIMAL128);
		bhaarathaShares.setPurchaseNetPricePerUnit(String.valueOf(purchaseNetPricePerUnit));

		return bhaarathaShares;
	}

	private AddBhaarathaSharesSplit updateSharesSplit(BhaarathaShares boughtBhaarathaShares) throws AnukyaException {

		// Check if the stock is purchased
		File singleScriptFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ boughtBhaarathaShares.getBseScriptCode() + AnukyaConstants.JSON_EXTENSION);

		File nonConsolidatedFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);

		File consolidatedFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		if (!anukyaDataUtils.isFileExists(singleScriptFile)) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while adding Bhaaratha bought shares -> ");
			logMessage.append("Message: Updating share split error | ");
			logMessage.append("Reason: Internal error");
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0004);
			errorDetails.setErrorMessage("Script is not purchased");
			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while adding bought shares (Updating share split)", errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		List<BhaarathaShares> nonConsolidatedList = bhaarathaSharesData.getBhaarathaShares(nonConsolidatedFile);
		List<BhaarathaShares> consolidatedList = bhaarathaSharesData.getBhaarathaShares(consolidatedFile);
		List<BhaarathaShares> singleScriptList = bhaarathaSharesData.getBhaarathaShares(singleScriptFile);

		AddBhaarathaSharesSplit addBhaarathaSharesSplit = new AddBhaarathaSharesSplit();

		int oldFaceValue = Integer
				.parseInt(boughtBhaarathaShares.getSharesSplitRatio().split(AnukyaConstants.RATIO_CONSTANT)[0]);
		int newFaceValue = Integer
				.parseInt(boughtBhaarathaShares.getSharesSplitRatio().split(AnukyaConstants.RATIO_CONSTANT)[1]);

		boolean isRatioWholeNumber = anukyaUtils.isWholeNumber(oldFaceValue, newFaceValue);

		String dateInddMmmYyyy = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		if (isRatioWholeNumber) {
			for (BhaarathaShares existingBhaarathaShare : consolidatedList) {
				if (boughtBhaarathaShares.getBseScriptCode()
						.equalsIgnoreCase(existingBhaarathaShare.getBseScriptCode())) {

					calculateShareSplit(existingBhaarathaShare, oldFaceValue, newFaceValue);

					existingBhaarathaShare.setLastUpdatedBy(userEmailId);
					existingBhaarathaShare.setLastUpdatedDate(dateInddMmmYyyy);
					break;
				}
			}

			for (BhaarathaShares existingBhaarathaShare : nonConsolidatedList) {
				if (boughtBhaarathaShares.getBseScriptCode()
						.equalsIgnoreCase(existingBhaarathaShare.getBseScriptCode())) {

					updateShareSplitDetails(existingBhaarathaShare, boughtBhaarathaShares);

					calculateShareSplit(existingBhaarathaShare, oldFaceValue, newFaceValue);

					existingBhaarathaShare.setLastUpdatedBy(userEmailId);
					existingBhaarathaShare.setLastUpdatedDate(dateInddMmmYyyy);
				}
			}

			for (BhaarathaShares existingBhaarathaShare : singleScriptList) {

				updateShareSplitDetails(existingBhaarathaShare, boughtBhaarathaShares);

				calculateShareSplit(existingBhaarathaShare, oldFaceValue, newFaceValue);

				existingBhaarathaShare.setLastUpdatedBy(userEmailId);
				existingBhaarathaShare.setLastUpdatedDate(dateInddMmmYyyy);
			}
		} else {

			int scriptPurchaseQuantity = consolidatedList.stream()
					.filter(consolidate -> consolidate.getBseScriptCode()
							.equalsIgnoreCase(boughtBhaarathaShares.getBseScriptCode()))
					.mapToInt(consolidate -> Integer.parseInt(consolidate.getPurchaseQuantity())).sum();
			int scriptUpdatedPurchaseQuantity = (scriptPurchaseQuantity * oldFaceValue) / newFaceValue;

			calculateShareSplit(scriptUpdatedPurchaseQuantity, scriptPurchaseQuantity, consolidatedList,
					boughtBhaarathaShares, dateInddMmmYyyy);

			calculateShareSplit(scriptUpdatedPurchaseQuantity, scriptPurchaseQuantity, nonConsolidatedList,
					boughtBhaarathaShares, dateInddMmmYyyy);

			calculateShareSplit(scriptUpdatedPurchaseQuantity, scriptPurchaseQuantity, singleScriptList,
					boughtBhaarathaShares, dateInddMmmYyyy);
		}

		addBhaarathaSharesSplit.setConsolidatedList(consolidatedList);
		addBhaarathaSharesSplit.setNonConsolidatedList(nonConsolidatedList);
		addBhaarathaSharesSplit.setSingleScriptList(singleScriptList);

		return addBhaarathaSharesSplit;
	}

	private void calculateShareSplit(BhaarathaShares existingBhaarathaShare, int oldFaceValue, int newFaceValue) {

		BigDecimal existingPurchaseQuantity = new BigDecimal(existingBhaarathaShare.getPurchaseQuantity());
		BigDecimal purchaseQuantity = BigDecimal
				.valueOf(existingPurchaseQuantity.multiply(BigDecimal.valueOf(oldFaceValue))
						.divide(BigDecimal.valueOf(newFaceValue), MathContext.DECIMAL128).intValue());
		existingBhaarathaShare.setPurchaseQuantity(String.valueOf(purchaseQuantity));

		BigDecimal existingPurchasePrice = new BigDecimal(existingBhaarathaShare.getPurchasePrice());
		BigDecimal purchasePrice = existingPurchasePrice.multiply(existingPurchaseQuantity).divide(purchaseQuantity,
				MathContext.DECIMAL128);
		existingBhaarathaShare.setPurchasePrice(String.valueOf(purchasePrice));

		BigDecimal existingPurchaseNetPricePerUnit = new BigDecimal(
				existingBhaarathaShare.getPurchaseNetPricePerUnit());
		BigDecimal purchaseNetPricePerUnit = existingPurchaseNetPricePerUnit.multiply(existingPurchaseQuantity)
				.divide(purchaseQuantity, MathContext.DECIMAL128);
		existingBhaarathaShare.setPurchaseNetPricePerUnit(String.valueOf(purchaseNetPricePerUnit));
	}

	private void updateShareSplitDetails(BhaarathaShares existingBhaarathaShare,
			BhaarathaShares boughtBhaarathaShares) {

		String sharesSplit = existingBhaarathaShare.getSharesSplit();
		existingBhaarathaShare.setSharesSplit(sharesSplit + boughtBhaarathaShares.getSharesSplitRatio()
				+ AnukyaConstants.SPACE + AnukyaConstants.ON_CONSTANT + AnukyaConstants.SPACE
				+ boughtBhaarathaShares.getSharesSplitDate() + AnukyaConstants.PIPE_SEPARATOR);

		existingBhaarathaShare.setSharesSplitDisplay(boughtBhaarathaShares.getSharesSplitRatio() + AnukyaConstants.SPACE
				+ AnukyaConstants.ON_CONSTANT + AnukyaConstants.SPACE + boughtBhaarathaShares.getSharesSplitDate());

	}

	private void calculateShareSplit(int scriptUpdatedPurchaseQuantity, int scriptPurchaseQuantity,
			List<BhaarathaShares> existingScriptList, BhaarathaShares boughtBhaarathaShares, String dateInddMmmYyyy) {
		int addingCount = scriptUpdatedPurchaseQuantity - scriptPurchaseQuantity;
		int completedRound = 1;

		while (addingCount > 0) {
			for (BhaarathaShares existingBhaarathaShare : existingScriptList) {

				if (boughtBhaarathaShares.getBseScriptCode()
						.equalsIgnoreCase(existingBhaarathaShare.getBseScriptCode())) {

					BigDecimal existingPurchaseQuantity = new BigDecimal(existingBhaarathaShare.getPurchaseQuantity());
					BigDecimal existingPurchasePrice = new BigDecimal(existingBhaarathaShare.getPurchasePrice());

					if (addingCount >= (existingPurchaseQuantity.intValue() / completedRound)) {

						calculateShareSplit1(existingBhaarathaShare, existingPurchaseQuantity, existingPurchasePrice,
								completedRound);

						existingBhaarathaShare.setLastUpdatedBy(userEmailId);
						existingBhaarathaShare.setLastUpdatedDate(dateInddMmmYyyy);

						addingCount -= existingPurchaseQuantity.intValue() / completedRound;
					} else if (addingCount > 0 && addingCount < existingPurchaseQuantity.intValue()) {

						calculateShareSplit2(existingBhaarathaShare, existingPurchaseQuantity, existingPurchasePrice,
								addingCount);

						existingBhaarathaShare.setLastUpdatedBy(userEmailId);
						existingBhaarathaShare.setLastUpdatedDate(dateInddMmmYyyy);

						addingCount -= addingCount;
					}
				}

				if (addingCount == 0) {
					break;
				}
			}

			completedRound++;
		}
	}

	// When addingCount is greater or equal to existingPurchaseQuantity
	private void calculateShareSplit1(BhaarathaShares existingBhaarathaShare, BigDecimal existingPurchaseQuantity,
			BigDecimal existingPurchasePrice, int completedRound) {

		BigDecimal purchaseQuantity = existingPurchaseQuantity
				.add(existingPurchaseQuantity.divide(BigDecimal.valueOf(completedRound)));
		existingBhaarathaShare.setPurchaseQuantity(String.valueOf(purchaseQuantity.intValue()));

		BigDecimal purchasePrice = existingPurchasePrice.multiply(existingPurchaseQuantity).divide(purchaseQuantity,
				MathContext.DECIMAL128);
		existingBhaarathaShare.setPurchasePrice(String.valueOf(purchasePrice));

		BigDecimal existingPurchaseNetPricePerUnit = new BigDecimal(
				existingBhaarathaShare.getPurchaseNetPricePerUnit());
		BigDecimal purchaseNetPricePerUnit = existingPurchaseNetPricePerUnit.multiply(existingPurchaseQuantity)
				.divide(purchaseQuantity, MathContext.DECIMAL128);
		existingBhaarathaShare.setPurchaseNetPricePerUnit(String.valueOf(purchaseNetPricePerUnit));
	}

	// When addingCount is greater 0 and addingCount less than
	// existingPurchaseQuantity
	private void calculateShareSplit2(BhaarathaShares existingBhaarathaShare, BigDecimal existingPurchaseQuantity,
			BigDecimal existingPurchasePrice, int addingCount) {

		BigDecimal purchaseQuantity = existingPurchaseQuantity.add(BigDecimal.valueOf(addingCount));
		existingBhaarathaShare.setPurchaseQuantity(String.valueOf(purchaseQuantity.intValue()));

		BigDecimal purchasePrice = existingPurchasePrice.multiply(existingPurchaseQuantity).divide(purchaseQuantity,
				MathContext.DECIMAL128);
		existingBhaarathaShare.setPurchasePrice(String.valueOf(purchasePrice));

		BigDecimal existingPurchaseNetPricePerUnit = new BigDecimal(
				existingBhaarathaShare.getPurchaseNetPricePerUnit());
		BigDecimal purchaseNetPricePerUnit = existingPurchaseNetPricePerUnit.multiply(existingPurchaseQuantity)
				.divide(purchaseQuantity, MathContext.DECIMAL128);
		existingBhaarathaShare.setPurchaseNetPricePerUnit(String.valueOf(purchaseNetPricePerUnit));
	}

	private AddBhaarathaSharesBonus updateSharesBonus(BhaarathaShares boughtBhaarathaShares) throws AnukyaException {

		// Check if the stock is purchased
		File singleScriptFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SINGLE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ boughtBhaarathaShares.getBseScriptCode() + AnukyaConstants.JSON_EXTENSION);

		File nonConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);

		File consolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_PURCHASE_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		if (!singleScriptFile.exists()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while adding Bhaaratha bought shares -> ");
			logMessage.append("Message: Updating share bonus error | ");
			logMessage.append("Reason: Internal error");
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0004);
			errorDetails.setErrorMessage("Script is not purchased");
			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while adding bought shares (Updating share bonus)", errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		List<BhaarathaShares> nonConsolidatedList = bhaarathaSharesData.getBhaarathaShares(nonConsolidatedFile);
		List<BhaarathaShares> consolidatedList = bhaarathaSharesData.getBhaarathaShares(consolidatedFile);
		List<BhaarathaShares> singleScriptList = bhaarathaSharesData.getBhaarathaShares(singleScriptFile);

		AddBhaarathaSharesBonus addBhaarathaSharesBonus = new AddBhaarathaSharesBonus();

		int firstValue = Integer
				.parseInt(boughtBhaarathaShares.getSharesBonusRatio().split(AnukyaConstants.RATIO_CONSTANT)[0]);
		int secondValue = Integer
				.parseInt(boughtBhaarathaShares.getSharesBonusRatio().split(AnukyaConstants.RATIO_CONSTANT)[1]);

		// Update missing values in boughtBhaarathaShares
		updateMissingFields(consolidatedList, boughtBhaarathaShares);

		String dateInddMmmYyyy = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		int scriptPurchaseQuantity = consolidatedList.stream()
				.filter(consolidate -> consolidate.getBseScriptCode()
						.equalsIgnoreCase(boughtBhaarathaShares.getBseScriptCode()))
				.mapToInt(consolidate -> Integer.parseInt(consolidate.getPurchaseQuantity())).sum();

		int scriptUpdatedPurchaseQuantity = (scriptPurchaseQuantity * firstValue) / secondValue;

		if (scriptUpdatedPurchaseQuantity == 0) {
			return addBhaarathaSharesBonus;
		}

		Optional<BhaarathaShares> bseOptional = consolidatedList.parallelStream()
				.filter(consolidate -> AnukyaConstants.BSE.equalsIgnoreCase(consolidate.getStockExchange())
						&& consolidate.getBseScriptCode().equalsIgnoreCase(boughtBhaarathaShares.getBseScriptCode()))
				.findFirst();

		Optional<BhaarathaShares> nseOptional = consolidatedList.parallelStream()
				.filter(consolidate -> AnukyaConstants.NSE.equalsIgnoreCase(consolidate.getStockExchange())
						&& consolidate.getBseScriptCode().equalsIgnoreCase(boughtBhaarathaShares.getBseScriptCode()))
				.findFirst();

		int bseQuantity = bseOptional.isPresent() ? Integer.parseInt(bseOptional.get().getPurchaseQuantity()) : 0;
		int nseQuantity = nseOptional.isPresent() ? Integer.parseInt(nseOptional.get().getPurchaseQuantity()) : 0;

		if (nseQuantity == 0) {
			BhaarathaShares bhaarathaShares = createBonusSharesDetails(boughtBhaarathaShares, AnukyaConstants.BSE,
					scriptUpdatedPurchaseQuantity, dateInddMmmYyyy);
			// Updating Bonus details for all existing non consolidated shares
			updateShareBonusDetails(nonConsolidatedList, boughtBhaarathaShares, dateInddMmmYyyy);
			// Updating Bonus details for all existing single shares
			updateShareBonusDetails(singleScriptList, boughtBhaarathaShares, dateInddMmmYyyy);
			// Updating bonus shares details for current bought shares
			updateShareBonusDetails(bhaarathaShares, boughtBhaarathaShares);

			consolidatedList = addConsolidated(bhaarathaShares);

			bhaarathaShares.setCreatedBy(userEmailId);
			bhaarathaShares.setCreatedDate(dateInddMmmYyyy);
			nonConsolidatedList.add(bhaarathaShares);
			singleScriptList.add(bhaarathaShares);

			addBhaarathaSharesBonus.setConsolidatedList(consolidatedList);
			addBhaarathaSharesBonus.setNonConsolidatedList(nonConsolidatedList);
			addBhaarathaSharesBonus.setSingleScriptList(singleScriptList);
		} else if (bseQuantity == 0) {
			BhaarathaShares bhaarathaShares = createBonusSharesDetails(boughtBhaarathaShares, AnukyaConstants.NSE,
					scriptUpdatedPurchaseQuantity, dateInddMmmYyyy);
			// Updating Bonus details for all existing non consolidated shares
			updateShareBonusDetails(nonConsolidatedList, boughtBhaarathaShares, dateInddMmmYyyy);
			// Updating Bonus details for all existing single shares
			updateShareBonusDetails(singleScriptList, boughtBhaarathaShares, dateInddMmmYyyy);
			// Updating bonus shares details for current bought shares
			updateShareBonusDetails(bhaarathaShares, boughtBhaarathaShares, dateInddMmmYyyy);

			consolidatedList = addConsolidated(bhaarathaShares);
			nonConsolidatedList.add(bhaarathaShares);
			singleScriptList.add(bhaarathaShares);

			addBhaarathaSharesBonus.setConsolidatedList(consolidatedList);
			addBhaarathaSharesBonus.setNonConsolidatedList(nonConsolidatedList);
			addBhaarathaSharesBonus.setSingleScriptList(singleScriptList);
		} else {
			Map<String, Integer> updatedQuantityMap = getUpdatedBonusQuantity(bseQuantity, nseQuantity, firstValue,
					secondValue, scriptUpdatedPurchaseQuantity);

			BhaarathaShares bseBhaarathaShares = createBonusSharesDetails(boughtBhaarathaShares, AnukyaConstants.BSE,
					updatedQuantityMap.get(AnukyaConstants.BSE), dateInddMmmYyyy);
			BhaarathaShares nseBhaarathaShares = createBonusSharesDetails(boughtBhaarathaShares, AnukyaConstants.NSE,
					updatedQuantityMap.get(AnukyaConstants.NSE), dateInddMmmYyyy);

			// Updating Bonus details for all existing non consolidated shares
			updateShareBonusDetails(nonConsolidatedList, boughtBhaarathaShares, dateInddMmmYyyy);
			// Updating Bonus details for all existing single shares
			updateShareBonusDetails(singleScriptList, boughtBhaarathaShares, dateInddMmmYyyy);
			// Updating bonus shares details for current bought shares
			updateShareBonusDetails(bseBhaarathaShares, boughtBhaarathaShares, dateInddMmmYyyy);
			updateShareBonusDetails(nseBhaarathaShares, boughtBhaarathaShares, dateInddMmmYyyy);

			consolidatedList = addConsolidated(bseBhaarathaShares, nseBhaarathaShares);
			addBhaarathaSharesBonus.setConsolidatedList(consolidatedList);

			nonConsolidatedList.add(bseBhaarathaShares);
			singleScriptList.add(bseBhaarathaShares);

			nonConsolidatedList.add(nseBhaarathaShares);
			singleScriptList.add(nseBhaarathaShares);

			addBhaarathaSharesBonus.setConsolidatedList(consolidatedList);
			addBhaarathaSharesBonus.setNonConsolidatedList(nonConsolidatedList);
			addBhaarathaSharesBonus.setSingleScriptList(singleScriptList);
		}

		return addBhaarathaSharesBonus;
	}

	private void updateMissingFields(List<BhaarathaShares> consolidatedList, BhaarathaShares boughtBhaarathaShares) {

		Optional<BhaarathaShares> bhaarathaSharesOptional = consolidatedList.parallelStream()
				.filter(consolidate -> consolidate.getBseScriptCode()
						.equalsIgnoreCase(boughtBhaarathaShares.getBseScriptCode()))
				.findFirst();
		BhaarathaShares bhaarathaShares = bhaarathaSharesOptional.isPresent() ? bhaarathaSharesOptional.get()
				: new BhaarathaShares();

		boughtBhaarathaShares.setYahooBseScriptCode(bhaarathaShares.getYahooBseScriptCode());
		boughtBhaarathaShares.setYahooNseScriptCode(bhaarathaShares.getYahooNseScriptCode());
		boughtBhaarathaShares.setIsinCode(bhaarathaShares.getIsinCode());
		boughtBhaarathaShares.setCategory(bhaarathaShares.getCategory());
		boughtBhaarathaShares.setSector(bhaarathaShares.getSector());
		boughtBhaarathaShares.setIndustry(bhaarathaShares.getIndustry());
		boughtBhaarathaShares.setShortTermInvestment(bhaarathaShares.isShortTermInvestment());
	}

	private BhaarathaShares createBonusSharesDetails(BhaarathaShares boughtBhaarathaShares, String stockExchange,
			int quantity, String updatedDate) {

		BhaarathaShares bhaarathaShares = new BhaarathaShares();

		bhaarathaShares.setId(anukyaUtils.generateId());
		bhaarathaShares.setStockExchange(stockExchange);
		bhaarathaShares.setScriptName(boughtBhaarathaShares.getScriptName());
		bhaarathaShares.setBseScriptCode(boughtBhaarathaShares.getBseScriptCode());
		bhaarathaShares.setNseScriptCode(boughtBhaarathaShares.getNseScriptCode());
		bhaarathaShares.setYahooBseScriptCode(boughtBhaarathaShares.getYahooBseScriptCode());
		bhaarathaShares.setYahooNseScriptCode(boughtBhaarathaShares.getYahooNseScriptCode());
		bhaarathaShares.setIsinCode(boughtBhaarathaShares.getIsinCode());
		bhaarathaShares.setMoneycontrolCode(boughtBhaarathaShares.getMoneycontrolCode());
		bhaarathaShares.setCategory(boughtBhaarathaShares.getCategory());
		bhaarathaShares.setSector(boughtBhaarathaShares.getSector());
		bhaarathaShares.setIndustry(boughtBhaarathaShares.getIndustry());
		bhaarathaShares.setShortTermInvestment(boughtBhaarathaShares.isShortTermInvestment());
		bhaarathaShares.setPurchaseDate(boughtBhaarathaShares.getSharesBonusDate());
		bhaarathaShares.setPurchaseQuantity(String.valueOf(quantity));
		bhaarathaShares.setLastUpdatedBy(userEmailId);
		bhaarathaShares.setLastUpdatedDate(updatedDate);

		return bhaarathaShares;
	}

	private void updateShareBonusDetails(List<BhaarathaShares> existingSharesList,
			BhaarathaShares boughtBhaarathaShares, String dateInddMmmYyyy) {

		for (BhaarathaShares existingShare : existingSharesList) {
			if (existingShare.getBseScriptCode().equalsIgnoreCase(boughtBhaarathaShares.getBseScriptCode())) {

				String sharesBonus = existingShare.getSharesBonus();
				existingShare.setSharesBonus(sharesBonus + boughtBhaarathaShares.getSharesBonusRatio()
						+ AnukyaConstants.SPACE + AnukyaConstants.ON_CONSTANT + AnukyaConstants.SPACE
						+ boughtBhaarathaShares.getSharesBonusDate() + AnukyaConstants.PIPE_SEPARATOR);

				existingShare.setSharesBonusDisplay(boughtBhaarathaShares.getSharesBonusRatio() + AnukyaConstants.SPACE
						+ AnukyaConstants.ON_CONSTANT + AnukyaConstants.SPACE
						+ boughtBhaarathaShares.getSharesBonusDate());

				existingShare.setLastUpdatedBy(userEmailId);
				existingShare.setLastUpdatedDate(dateInddMmmYyyy);
			}
		}
	}

	private void updateShareBonusDetails(BhaarathaShares bhaarathaShares, BhaarathaShares boughtBhaarathaShares,
			String dateInddMmmYyyy) {

		String sharesBonus = bhaarathaShares.getSharesBonus();
		bhaarathaShares.setSharesBonus(sharesBonus + boughtBhaarathaShares.getSharesBonusRatio() + AnukyaConstants.SPACE
				+ AnukyaConstants.ON_CONSTANT + AnukyaConstants.SPACE + boughtBhaarathaShares.getSharesBonusDate()
				+ AnukyaConstants.PIPE_SEPARATOR);

		bhaarathaShares.setSharesBonusDisplay(boughtBhaarathaShares.getSharesBonusRatio() + AnukyaConstants.SPACE
				+ AnukyaConstants.ON_CONSTANT + AnukyaConstants.SPACE + boughtBhaarathaShares.getSharesBonusDate());

		bhaarathaShares.setCreatedBy(userEmailId);
		bhaarathaShares.setCreatedDate(dateInddMmmYyyy);
	}

	private Map<String, Integer> getUpdatedBonusQuantity(int bseQuantity, int nseQuantity, int firstValue,
			int secondValue, int scriptUpdatedPurchaseQuantity) {

		Map<String, Integer> updatedBonusQuantity = new HashMap<>();

		int updatedBseQuantity = (bseQuantity * firstValue) / secondValue;
		int updatedNseQuantity = (nseQuantity * firstValue) / secondValue;

		int totalUpdatedQuantity = updatedBseQuantity + updatedNseQuantity;

		if (totalUpdatedQuantity < scriptUpdatedPurchaseQuantity && (updatedBseQuantity < updatedNseQuantity)) {
			updatedBseQuantity = updatedBseQuantity + (scriptUpdatedPurchaseQuantity - totalUpdatedQuantity);
		} else if (totalUpdatedQuantity < scriptUpdatedPurchaseQuantity && (updatedNseQuantity < updatedBseQuantity)) {
			updatedNseQuantity = updatedNseQuantity + (scriptUpdatedPurchaseQuantity - totalUpdatedQuantity);
		}

		updatedBonusQuantity.put(AnukyaConstants.BSE, updatedBseQuantity);
		updatedBonusQuantity.put(AnukyaConstants.NSE, updatedNseQuantity);

		return updatedBonusQuantity;
	}

	private void updateShareBonusDetails(BhaarathaShares existingBhaarathaShare,
			BhaarathaShares boughtBhaarathaShares) {

		String sharesBonus = existingBhaarathaShare.getSharesBonus();
		existingBhaarathaShare.setSharesBonus(sharesBonus + boughtBhaarathaShares.getSharesBonusRatio()
				+ AnukyaConstants.SPACE + AnukyaConstants.ON_CONSTANT + AnukyaConstants.SPACE
				+ boughtBhaarathaShares.getSharesBonusDate() + AnukyaConstants.PIPE_SEPARATOR);

		existingBhaarathaShare.setSharesBonusDisplay(boughtBhaarathaShares.getSharesBonusRatio() + AnukyaConstants.SPACE
				+ AnukyaConstants.ON_CONSTANT + AnukyaConstants.SPACE + boughtBhaarathaShares.getSharesBonusDate());

	}

	private List<BhaarathaShares> getBhaarathaSharesNonConsolidated(String searchTerm, boolean isLongTermOnly)
			throws AnukyaException {

		File nonConsolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_PURCHASE_MAIN_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);

		List<BhaarathaShares> bhaarathaSharesList = bhaarathaSharesData.getBhaarathaShares(nonConsolidatedFile);

		return filterBySearchTerm(bhaarathaSharesList, searchTerm, isLongTermOnly);
	}

	private List<BhaarathaShares> getBhaarathaSharesConsolidated(String searchTerm, boolean isLongTermOnly)
			throws AnukyaException {

		File consolidatedFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.BHA_EQ_PURCHASE_MAIN_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

		List<BhaarathaShares> bhaarathaSharesList = bhaarathaSharesData.getBhaarathaShares(consolidatedFile);

		return filterBySearchTerm(bhaarathaSharesList, searchTerm, isLongTermOnly);
	}

	private List<BhaarathaShares> filterBySearchTerm(List<BhaarathaShares> bhaarathaSharesList, String searchTerm,
			boolean isLongTermOnly) {

		if (!searchTerm.isEmpty() && isLongTermOnly) {

			return bhaarathaSharesList.stream()
					.filter(bhaarathaShares -> !bhaarathaShares.isShortTermInvestment()
							&& (bhaarathaShares.getScriptName().equalsIgnoreCase(searchTerm)
									|| bhaarathaShares.getBseScriptCode().equalsIgnoreCase(searchTerm)
									|| bhaarathaShares.getNseScriptCode().equalsIgnoreCase(searchTerm)
									|| bhaarathaShares.getCategory().equalsIgnoreCase(searchTerm)
									|| bhaarathaShares.getSector().equalsIgnoreCase(searchTerm)
									|| bhaarathaShares.getIndustry().equalsIgnoreCase(searchTerm)
									|| bhaarathaShares.getStockExchange().equalsIgnoreCase(searchTerm)))
					.collect(Collectors.toList());
		} else if (searchTerm.isEmpty() && isLongTermOnly) {
			return bhaarathaSharesList.stream().filter(bhaarathaShares -> !bhaarathaShares.isShortTermInvestment())
					.collect(Collectors.toList());
		} else if (!searchTerm.isEmpty() && !isLongTermOnly) {
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

		return bhaarathaSharesList;
	}

	private BhaarathaSharesResponse calculateBhaarathaShares(List<BhaarathaShares> bhaarathaSharesList)
			throws AnukyaException {

		BhaarathaSharesResponse bhaarathaSharesResponse = new BhaarathaSharesResponse();
		bhaarathaSharesResponse.setBhaarathaSharesTotal(new BhaarathaShares());

		Map<String, Long> sellDepositoryChargesScriptCountMap = bhaarathaSharesList.parallelStream()
				.collect(Collectors.groupingBy(BhaarathaShares::getBseScriptCode, Collectors.counting()));

		return buildBhaarathaSharesResponse(bhaarathaSharesList,
				getBhaarathaSharesCalculationDetails(bhaarathaSharesList), sellDepositoryChargesScriptCountMap);
	}

	private BhaarathaSharesCalculationDetails getBhaarathaSharesCalculationDetails(
			List<BhaarathaShares> bhaarathaSharesList) throws AnukyaException {

		Map<String, BhaarathaSharesLastTradingPrice> latestTradingDetailsMap = new HashMap<>();
		Map<String, BigDecimal> totalSoldQuantityMap = new HashMap<>();
		Map<String, BigDecimal> totalSoldGrossTotalMap = new HashMap<>();

		for (BhaarathaShares bhaarathaShares : bhaarathaSharesList) {

			BigDecimal totalQuantity;
			if (totalSoldQuantityMap.get(bhaarathaShares.getBseScriptCode()) != null) {
				totalQuantity = totalSoldQuantityMap.get(bhaarathaShares.getBseScriptCode())
						.add(new BigDecimal(bhaarathaShares.getPurchaseQuantity()));
			} else {
				totalQuantity = new BigDecimal(bhaarathaShares.getPurchaseQuantity());
			}

			totalSoldQuantityMap.put(bhaarathaShares.getBseScriptCode(), totalQuantity);

			BigDecimal scriptSellPrice = new BigDecimal(AnukyaConstants.NUMBER_0);

			if (AnukyaConstants.BSE.equalsIgnoreCase(bhaarathaShares.getStockExchange())) {
				if (latestTradingDetailsMap.get(bhaarathaShares.getBseScriptCode()) == null) {
					latestTradingDetailsMap.put(bhaarathaShares.getBseScriptCode(), bhaarathaSharesUtils
							.getBhaarathaSharesLastTradingPrice(new BhaarathaSharesScriptCodeDetails(
									AnukyaConstants.BSE, bhaarathaShares.getBseScriptCode(),
									bhaarathaShares.getNseScriptCode(), bhaarathaShares.getMoneycontrolCode(),
									bhaarathaShares.getYahooBseScriptCode(), bhaarathaShares.getYahooNseScriptCode())));
				}
				scriptSellPrice = new BigDecimal(
						latestTradingDetailsMap.get(bhaarathaShares.getBseScriptCode()).getLastTradingPrice());
			} else if (AnukyaConstants.NSE.equalsIgnoreCase(bhaarathaShares.getStockExchange())) {
				if (latestTradingDetailsMap.get(bhaarathaShares.getNseScriptCode()) == null) {
					latestTradingDetailsMap.put(bhaarathaShares.getNseScriptCode(), bhaarathaSharesUtils
							.getBhaarathaSharesLastTradingPrice(new BhaarathaSharesScriptCodeDetails(
									AnukyaConstants.NSE, bhaarathaShares.getBseScriptCode(),
									bhaarathaShares.getNseScriptCode(), bhaarathaShares.getMoneycontrolCode(),
									bhaarathaShares.getYahooBseScriptCode(), bhaarathaShares.getYahooNseScriptCode())));
				}
				scriptSellPrice = new BigDecimal(
						latestTradingDetailsMap.get(bhaarathaShares.getNseScriptCode()).getLastTradingPrice());
			}

			BigDecimal totalGrossTotal = totalQuantity.multiply(scriptSellPrice);
			totalSoldGrossTotalMap.put(bhaarathaShares.getBseScriptCode(), totalGrossTotal);
		}

		BhaarathaSharesCalculationDetails bhaarathaSharesCalculationDetails = new BhaarathaSharesCalculationDetails();

		bhaarathaSharesCalculationDetails.setLatestTradingDetailsMap(latestTradingDetailsMap);
		bhaarathaSharesCalculationDetails.setTotalSoldQuantityMap(totalSoldQuantityMap);
		bhaarathaSharesCalculationDetails.setTotalSoldGrossTotalMap(totalSoldGrossTotalMap);

		return bhaarathaSharesCalculationDetails;
	}

	private BhaarathaSharesResponse buildBhaarathaSharesResponse(List<BhaarathaShares> bhaarathaSharesList,
			BhaarathaSharesCalculationDetails bhaarathaSharesCalculationDetails,
			Map<String, Long> sellDepositoryChargesMap) {

		BhaarathaSharesResponse bhaarathaSharesResponse = new BhaarathaSharesResponse();
		bhaarathaSharesResponse.setBhaarathaSharesTotal(new BhaarathaShares());

		List<BhaarathaShares> bhaarathaSharesResponseList = new ArrayList<>();

		for (BhaarathaShares bhaarathaShare : bhaarathaSharesList) {

			BigDecimal purchaseQuantity = new BigDecimal(bhaarathaShare.getPurchaseQuantity());
			BigDecimal existingTotalPurchaseQuantity = new BigDecimal(
					bhaarathaSharesResponse.getBhaarathaSharesTotal().getPurchaseQuantity());
			BigDecimal totalPurchaseQuantity = purchaseQuantity.add(existingTotalPurchaseQuantity);
			bhaarathaShare.setPurchaseQuantity(String.valueOf(purchaseQuantity));
			bhaarathaSharesResponse.getBhaarathaSharesTotal()
					.setPurchaseQuantity(String.valueOf(totalPurchaseQuantity));

			BigDecimal purchasePrice = new BigDecimal(bhaarathaShare.getPurchasePrice());
			BigDecimal existingTotalPurchasePrice = new BigDecimal(
					bhaarathaSharesResponse.getBhaarathaSharesTotal().getPurchasePrice());
			BigDecimal totalPurchasePrice = purchaseQuantity.multiply(purchasePrice)
					.add(existingTotalPurchaseQuantity.multiply(existingTotalPurchasePrice))
					.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
			bhaarathaShare.setPurchasePrice(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchasePrice));
			bhaarathaSharesResponse.getBhaarathaSharesTotal()
					.setPurchasePrice(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchasePrice));

			BigDecimal purchaseGrossTotal = new BigDecimal(bhaarathaShare.getPurchaseGrossTotal());
			BigDecimal existingTotalPurchaseGrossTotal = new BigDecimal(
					bhaarathaSharesResponse.getBhaarathaSharesTotal().getPurchaseGrossTotal());
			BigDecimal totalPurchaseGrossTotal = purchaseGrossTotal.add(existingTotalPurchaseGrossTotal);
			bhaarathaShare.setPurchaseGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseGrossTotal));
			bhaarathaSharesResponse.getBhaarathaSharesTotal()
					.setPurchaseGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseGrossTotal));

			BigDecimal purchaseBrokerage = new BigDecimal(bhaarathaShare.getPurchaseBrokerage());
			BigDecimal existingTotalPurchaseBrokerage = new BigDecimal(
					bhaarathaSharesResponse.getBhaarathaSharesTotal().getPurchaseBrokerage());
			BigDecimal totalPurchaseBrokerage = purchaseBrokerage.add(existingTotalPurchaseBrokerage);
			bhaarathaShare.setPurchaseBrokerage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseBrokerage));
			bhaarathaSharesResponse.getBhaarathaSharesTotal()
					.setPurchaseBrokerage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseBrokerage));

			BigDecimal purchaseSTT = new BigDecimal(bhaarathaShare.getPurchaseSTT());
			BigDecimal existingTotalPurchaseSTT = new BigDecimal(
					bhaarathaSharesResponse.getBhaarathaSharesTotal().getPurchaseSTT());
			BigDecimal totalPurchaseSTT = purchaseSTT.add(existingTotalPurchaseSTT);
			bhaarathaShare.setPurchaseSTT(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseSTT));
			bhaarathaSharesResponse.getBhaarathaSharesTotal()
					.setPurchaseSTT(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseSTT));

			BigDecimal purchaseExpenditure = new BigDecimal(bhaarathaShare.getPurchaseExpenditure());
			BigDecimal existingPurchaseExpenditure = new BigDecimal(
					bhaarathaSharesResponse.getBhaarathaSharesTotal().getPurchaseExpenditure());
			BigDecimal totalPurchaseExpenditure = purchaseExpenditure.add(existingPurchaseExpenditure);
			bhaarathaShare.setPurchaseExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseExpenditure));
			bhaarathaSharesResponse.getBhaarathaSharesTotal()
					.setPurchaseExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseExpenditure));

			BigDecimal purchaseNonExpenditure = new BigDecimal(bhaarathaShare.getPurchaseNonExpenditure());
			BigDecimal existingPurchaseNonExpenditure = new BigDecimal(
					bhaarathaSharesResponse.getBhaarathaSharesTotal().getPurchaseNonExpenditure());
			BigDecimal totalPurchaseNonExpenditure = purchaseNonExpenditure.add(existingPurchaseNonExpenditure);
			bhaarathaShare
					.setPurchaseNonExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseNonExpenditure));
			bhaarathaSharesResponse.getBhaarathaSharesTotal().setPurchaseNonExpenditure(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseNonExpenditure));

			BigDecimal purchaseTotal = new BigDecimal(bhaarathaShare.getPurchaseTotal());
			BigDecimal existingTotalPurchaseTotal = new BigDecimal(
					bhaarathaSharesResponse.getBhaarathaSharesTotal().getPurchaseTotal());
			BigDecimal totalPurchaseTotal = purchaseTotal.add(existingTotalPurchaseTotal);
			bhaarathaShare.setPurchaseTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseTotal));
			bhaarathaSharesResponse.getBhaarathaSharesTotal()
					.setPurchaseTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseTotal));

			BigDecimal purchaseNetPricePerUnit = new BigDecimal(bhaarathaShare.getPurchaseNetPricePerUnit());
			BigDecimal existingTotalPurchaseNetPricePerUnit = new BigDecimal(
					bhaarathaSharesResponse.getBhaarathaSharesTotal().getPurchaseNetPricePerUnit());
			BigDecimal totalPurchaseNetPricePerUnit = purchaseQuantity.multiply(purchaseNetPricePerUnit)
					.add(existingTotalPurchaseQuantity.multiply(existingTotalPurchaseNetPricePerUnit))
					.divide(totalPurchaseQuantity, MathContext.DECIMAL128);
			bhaarathaShare.setPurchaseNetPricePerUnit(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, purchaseNetPricePerUnit));
			bhaarathaSharesResponse.getBhaarathaSharesTotal().setPurchaseNetPricePerUnit(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalPurchaseNetPricePerUnit));

			BigDecimal sellQuantity = new BigDecimal(bhaarathaShare.getPurchaseQuantity());
			bhaarathaShare.setSellQuantity(String.valueOf(sellQuantity));
			BigDecimal existingTotalSellQuantity = new BigDecimal(
					bhaarathaSharesResponse.getBhaarathaSharesTotal().getSellQuantity());
			BigDecimal totalSellQuantity = sellQuantity.add(existingTotalSellQuantity);
			bhaarathaShare.setSellQuantity(String.valueOf(sellQuantity));
			bhaarathaSharesResponse.getBhaarathaSharesTotal().setSellQuantity(String.valueOf(totalSellQuantity));

			BigDecimal sellPrice = new BigDecimal(AnukyaConstants.NUMBER_0);
			if (AnukyaConstants.BSE.equalsIgnoreCase(bhaarathaShare.getStockExchange())) {
				sellPrice = new BigDecimal(bhaarathaSharesCalculationDetails.getLatestTradingDetailsMap()
						.get(bhaarathaShare.getBseScriptCode()).getLastTradingPrice());
			} else if (AnukyaConstants.NSE.equalsIgnoreCase(bhaarathaShare.getStockExchange())) {
				sellPrice = new BigDecimal(bhaarathaSharesCalculationDetails.getLatestTradingDetailsMap()
						.get(bhaarathaShare.getNseScriptCode()).getLastTradingPrice());
			}
			BigDecimal existingTotalSellPrice = new BigDecimal(
					bhaarathaSharesResponse.getBhaarathaSharesTotal().getSellPrice());
			BigDecimal totalSellPrice = sellQuantity.multiply(sellPrice)
					.add(existingTotalSellQuantity.multiply(existingTotalSellPrice))
					.divide(totalSellQuantity, MathContext.DECIMAL128);
			bhaarathaShare.setSellPrice(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellPrice));
			bhaarathaSharesResponse.getBhaarathaSharesTotal()
					.setSellPrice(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellPrice));

			BigDecimal sellGrossTotal = sellQuantity.multiply(sellPrice);
			BigDecimal existingTotalSellGrossTotal = new BigDecimal(
					bhaarathaSharesResponse.getBhaarathaSharesTotal().getSellGrossTotal());
			BigDecimal totalSellGrossTotal = sellGrossTotal.add(existingTotalSellGrossTotal);
			bhaarathaShare.setSellGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellGrossTotal));
			bhaarathaSharesResponse.getBhaarathaSharesTotal()
					.setSellGrossTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellGrossTotal));

			BigDecimal sellBrokerage = bhaarathaExpenditureUtils.calculateBrokerage(sellGrossTotal, sellQuantity,
					bhaarathaSharesCalculationDetails.getTotalSoldQuantityMap().get(bhaarathaShare.getBseScriptCode()),
					bhaarathaSharesCalculationDetails.getTotalSoldGrossTotalMap()
							.get(bhaarathaShare.getBseScriptCode()));
			BigDecimal existingTotalSellBrokerage = new BigDecimal(
					bhaarathaSharesResponse.getBhaarathaSharesTotal().getSellBrokerage());
			BigDecimal totalSellBrokerage = sellBrokerage.add(existingTotalSellBrokerage);
			bhaarathaShare.setSellBrokerage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellBrokerage));
			bhaarathaSharesResponse.getBhaarathaSharesTotal()
					.setSellBrokerage(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellBrokerage));

			BigDecimal sellSTT = bhaarathaExpenditureUtils.calculateSTT(sellGrossTotal);
			BigDecimal existingTotalSellSTT = new BigDecimal(
					bhaarathaSharesResponse.getBhaarathaSharesTotal().getSellSTT());
			BigDecimal totalSellSTT = sellSTT.add(existingTotalSellSTT);
			bhaarathaShare.setSellSTT(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellSTT));
			bhaarathaSharesResponse.getBhaarathaSharesTotal()
					.setSellSTT(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellSTT));

			BigDecimal sellExpenditure = bhaarathaExpenditureUtils
					.getBhaarathaSharesExpenditure(sellGrossTotal, sellBrokerage).add(bhaarathaExpenditureUtils
							.calculateDepositoryCharges(sellDepositoryChargesMap, bhaarathaShare.getBseScriptCode()));
			BigDecimal existingSellExpenditure = new BigDecimal(
					bhaarathaSharesResponse.getBhaarathaSharesTotal().getSellExpenditure());
			BigDecimal totalSellExpenditure = sellExpenditure.add(existingSellExpenditure);
			bhaarathaShare.setSellExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellExpenditure));
			bhaarathaSharesResponse.getBhaarathaSharesTotal()
					.setSellExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellExpenditure));

			BigDecimal sellNonExpenditure = bhaarathaExpenditureUtils.getBhaarathaSharesNonExpenditure();
			BigDecimal existingSellNonExpenditure = new BigDecimal(
					bhaarathaSharesResponse.getBhaarathaSharesTotal().getSellNonExpenditure());
			BigDecimal totalSellNonExpenditure = sellNonExpenditure.add(existingSellNonExpenditure);
			bhaarathaShare.setSellNonExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellNonExpenditure));
			bhaarathaSharesResponse.getBhaarathaSharesTotal()
					.setSellNonExpenditure(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellNonExpenditure));

			BigDecimal sellTotal = sellGrossTotal.subtract(sellBrokerage).subtract(sellSTT).subtract(sellExpenditure)
					.subtract(sellNonExpenditure);
			BigDecimal existingTotalSellTotal = new BigDecimal(
					bhaarathaSharesResponse.getBhaarathaSharesTotal().getSellTotal());
			BigDecimal totalSellTotal = sellTotal.add(existingTotalSellTotal);
			bhaarathaShare.setSellTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellTotal));
			bhaarathaSharesResponse.getBhaarathaSharesTotal()
					.setSellTotal(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellTotal));

			BigDecimal sellNetPricePerUnit = sellTotal.divide(sellQuantity, MathContext.DECIMAL128);
			BigDecimal existingTotalSellNetPricePerUnit = new BigDecimal(
					bhaarathaSharesResponse.getBhaarathaSharesTotal().getSellNetPricePerUnit());
			BigDecimal totalSellNetPricePerUnit = sellQuantity.multiply(sellNetPricePerUnit)
					.add(existingTotalSellQuantity.multiply(existingTotalSellNetPricePerUnit))
					.divide(totalSellQuantity, MathContext.DECIMAL128);
			bhaarathaShare.setSellNetPricePerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL, sellNetPricePerUnit));
			bhaarathaSharesResponse.getBhaarathaSharesTotal()
					.setSellNetPricePerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalSellNetPricePerUnit));

			BigDecimal grossProfitLoss = sellGrossTotal.subtract(purchaseGrossTotal);
			bhaarathaShare.setGrossProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, grossProfitLoss));
			BigDecimal totalGrossProfitLoss = totalSellGrossTotal.subtract(totalPurchaseGrossTotal);
			bhaarathaSharesResponse.getBhaarathaSharesTotal()
					.setGrossProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalGrossProfitLoss));

			BigDecimal grossProfitLossPercentage;
			try {
				grossProfitLossPercentage = sellGrossTotal.divide(purchaseGrossTotal, MathContext.DECIMAL128)
						.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
						.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
			} catch (ArithmeticException e) {
				grossProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_0);
			}
			bhaarathaShare.setGrossProfitLossPercentage(
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
			bhaarathaSharesResponse.getBhaarathaSharesTotal().setGrossProfitLossPercentage(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalGrossProfitLossPercentage));

			BigDecimal totalProfitLoss = sellTotal.subtract(purchaseTotal);
			bhaarathaShare.setTotalProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLoss));
			BigDecimal totalTotalProfitLoss = totalSellTotal.subtract(totalPurchaseTotal);
			bhaarathaSharesResponse.getBhaarathaSharesTotal()
					.setTotalProfitLoss(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalTotalProfitLoss));

			BigDecimal totalProfitLossPercentage;
			try {
				totalProfitLossPercentage = sellTotal.divide(purchaseTotal, MathContext.DECIMAL128)
						.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
						.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
			} catch (ArithmeticException e) {
				totalProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_0);
			}
			bhaarathaShare.setTotalProfitLossPercentage(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalProfitLossPercentage));
			BigDecimal totalTotalProfitLossPercentage;
			try {
				totalTotalProfitLossPercentage = totalSellTotal.divide(totalPurchaseTotal, MathContext.DECIMAL128)
						.subtract(new BigDecimal(AnukyaConstants.NUMBER_1))
						.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
			} catch (ArithmeticException e) {
				totalTotalProfitLossPercentage = new BigDecimal(AnukyaConstants.NUMBER_1);
			}
			bhaarathaSharesResponse.getBhaarathaSharesTotal().setTotalProfitLossPercentage(
					String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalTotalProfitLossPercentage));

			BigDecimal netProfitLossPerUnit = sellNetPricePerUnit.subtract(purchaseNetPricePerUnit);
			bhaarathaShare
					.setNetProfitLossPerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL, netProfitLossPerUnit));
			BigDecimal totalNetProfitLossPerUnit = totalSellNetPricePerUnit.subtract(totalPurchaseNetPricePerUnit);
			bhaarathaSharesResponse.getBhaarathaSharesTotal()
					.setNetProfitLossPerUnit(String.format(AnukyaConstants.FLOAT_2_DECIMAL, totalNetProfitLossPerUnit));

			bhaarathaSharesResponseList.add(bhaarathaShare);
		}

		bhaarathaSharesResponse.setBhaarathaSharesList(bhaarathaSharesResponseList);

		return bhaarathaSharesResponse;
	}

}
