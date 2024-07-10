package com.moneymanager.anukya.exception.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.AnukyaUiFieldMapping;
import com.moneymanager.anukya.exception.ErrorDetails;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaShares;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesShortTermInvestment;
import com.moneymanager.anukya.utils.AnukyaConstants;
import com.moneymanager.anukya.utils.AnukyaUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class UnitedStatesOfAmericaSharesExceptionHandler {

	@Autowired
	private AnukyaUtils anukyaUtils;

	public static final String SCRIPT_NAME_CANNOT_BE_EMPTY = "Script name cannot be empty";
	public static final String SCRIPT_CODE_CANNOT_BE_EMPTY = "Script code cannot be empty";
	public static final String MESSAGE_INPUT_FIELD_ERROR_WITH_PIPE_SEPERATOR = "Message: Input field error | ";
	public static final String REASON_ONE_OR_MORE_INPUT_IS_INVALID = "Reason: One or more input is invalid";

	public void shareNameDetailsException(String scriptCode) throws AnukyaException {

		List<ErrorDetails> errorDetailsList = new ArrayList<>();

		if (!StringUtils.hasLength(scriptCode)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_SD_0001);
			errorDetails.setErrorMessage(SCRIPT_CODE_CANNOT_BE_EMPTY);

			errorDetailsList.add(errorDetails);
		}

		if (!errorDetailsList.isEmpty()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Error while getting script code (United States of America) -> ");
			logMessage.append(MESSAGE_INPUT_FIELD_ERROR_WITH_PIPE_SEPERATOR);
			logMessage.append(REASON_ONE_OR_MORE_INPUT_IS_INVALID);
			log.error(logMessage.toString());

			throw new AnukyaException(
					"Exception occured while retrieving United States of America Shares script details",
					errorDetailsList, AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

	}

	public void boughtSharesException(UnitedStatesOfAmericaShares unitedStatesOfAmericaShares) throws AnukyaException {

		List<ErrorDetails> errorDetailsList = new ArrayList<>();

		if (unitedStatesOfAmericaShares.isSharesSplitSelected()) {

			try {
				if (unitedStatesOfAmericaShares.getSharesSplitRatio().contains(AnukyaConstants.PLUS_CONSTANT)
						|| unitedStatesOfAmericaShares.getSharesSplitRatio().contains(AnukyaConstants.MINUS_CONSTANT)) {
					throw new NumberFormatException();
				}

				if (unitedStatesOfAmericaShares.getSharesSplitRatio().length()
						- unitedStatesOfAmericaShares.getSharesSplitRatio()
								.replace(AnukyaConstants.RATIO_CONSTANT, AnukyaConstants.EMPTY_STRING).length() != 1) {
					throw new NumberFormatException();
				}

				Integer.parseInt(
						unitedStatesOfAmericaShares.getSharesSplitRatio().split(AnukyaConstants.RATIO_CONSTANT)[0]);
				Integer.parseInt(
						unitedStatesOfAmericaShares.getSharesSplitRatio().split(AnukyaConstants.RATIO_CONSTANT)[1]);
			} catch (NumberFormatException e) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0001);
				errorDetails.setErrorMessage("Invalid split ratio. It should in 'number:number' format");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_SPLIT_RATIO);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getSharesSplitDate())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0002);
				errorDetails.setErrorMessage("Split date cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_SPLIT_DATE);

				errorDetailsList.add(errorDetails);
			} else if (!anukyaUtils.isValidDateFormat(unitedStatesOfAmericaShares.getSharesSplitDate())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0002);
				errorDetails.setErrorMessage("Invalid Split date");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_SPLIT_DATE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getScriptName())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0003);
				errorDetails.setErrorMessage(SCRIPT_NAME_CANNOT_BE_EMPTY);
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_SCRIPT_NAME);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getYahooCode())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0006);
				errorDetails.setErrorMessage("Yahoo Script code cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_YAHOO_CODE);

				errorDetailsList.add(errorDetails);
			}

		} else if (unitedStatesOfAmericaShares.isSharesBonusSelected()) {

			try {
				if (unitedStatesOfAmericaShares.getSharesBonusRatio().contains(AnukyaConstants.PLUS_CONSTANT)
						|| unitedStatesOfAmericaShares.getSharesBonusRatio().contains(AnukyaConstants.MINUS_CONSTANT)) {
					throw new NumberFormatException();
				}

				if (unitedStatesOfAmericaShares.getSharesBonusRatio().length()
						- unitedStatesOfAmericaShares.getSharesBonusRatio()
								.replace(AnukyaConstants.RATIO_CONSTANT, AnukyaConstants.EMPTY_STRING).length() != 1) {
					throw new NumberFormatException();
				}

				Integer.parseInt(
						unitedStatesOfAmericaShares.getSharesBonusRatio().split(AnukyaConstants.RATIO_CONSTANT)[0]);
				Integer.parseInt(
						unitedStatesOfAmericaShares.getSharesBonusRatio().split(AnukyaConstants.RATIO_CONSTANT)[1]);
			} catch (NumberFormatException e) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0004);
				errorDetails.setErrorMessage("Invalid bonus ratio. It should in 'number:number' format");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_BONUS_RATIO);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getSharesBonusDate())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0005);
				errorDetails.setErrorMessage("Bonus date cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_BONUS_DATE);

				errorDetailsList.add(errorDetails);
			} else if (!anukyaUtils.isValidDateFormat(unitedStatesOfAmericaShares.getSharesBonusDate())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0005);
				errorDetails.setErrorMessage("Invalid Bonus date");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_BONUS_DATE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getScriptName())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0003);
				errorDetails.setErrorMessage(SCRIPT_NAME_CANNOT_BE_EMPTY);
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_SCRIPT_NAME);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getYahooCode())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0006);
				errorDetails.setErrorMessage("Yahoo Script code cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_YAHOO_CODE);

				errorDetailsList.add(errorDetails);
			}

		} else {
			if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getScriptName())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0003);
				errorDetails.setErrorMessage(SCRIPT_NAME_CANNOT_BE_EMPTY);
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_SCRIPT_NAME);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getYahooCode())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0006);
				errorDetails.setErrorMessage("Yahoo Script code cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_YAHOO_CODE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getIsinCode())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0007);
				errorDetails.setErrorMessage("ISIN code cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_ISIN_CODE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getCusip())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0008);
				errorDetails.setErrorMessage("CUSIP cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_CUSIP);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getCategory())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0009);
				errorDetails.setErrorMessage("Category cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_CATEGORY);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getSector())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0010);
				errorDetails.setErrorMessage("Sector cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_SECTOR);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getIndustry())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0011);
				errorDetails.setErrorMessage("Industry cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_INDUSTRY);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getPurchaseDate())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0012);
				errorDetails.setErrorMessage("Purchase date cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_DATE);

				errorDetailsList.add(errorDetails);
			} else if (!anukyaUtils.isValidDateFormat(unitedStatesOfAmericaShares.getPurchaseDate())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0012);
				errorDetails.setErrorMessage("Invalid Purchase date");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_DATE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getPurchaseQuantity())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0013);
				errorDetails.setErrorMessage("Purchase quantity cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_QUANTITY);

				errorDetailsList.add(errorDetails);
			} else if (!anukyaUtils.isNumber(unitedStatesOfAmericaShares.getPurchaseQuantity())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0013);
				errorDetails.setErrorMessage("Invalid Purchase quantity");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_QUANTITY);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getPurchasePrice())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0014);
				errorDetails.setErrorMessage("Purchase price cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_PRICE);

				errorDetailsList.add(errorDetails);
			} else if (!anukyaUtils.isNumber(unitedStatesOfAmericaShares.getPurchasePrice())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0014);
				errorDetails.setErrorMessage("Invalid Purchase price");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_PRICE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getPurchaseCommission())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0015);
				errorDetails.setErrorMessage("Purchase commission cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_COMMISSION);

				errorDetailsList.add(errorDetails);
			} else if (!anukyaUtils.isNumber(unitedStatesOfAmericaShares.getPurchaseCommission())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0015);
				errorDetails.setErrorMessage("Invalid Purchase commission");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_COMMISSION);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getPurchaseTransactionFees())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0016);
				errorDetails.setErrorMessage("Purchase transaction fees cannot be empty");
				errorDetails.setUiField(
						AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_TRANSACTION_FEES);

				errorDetailsList.add(errorDetails);
			} else if (!anukyaUtils.isNumber(unitedStatesOfAmericaShares.getPurchaseTransactionFees())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0016);
				errorDetails.setErrorMessage("Invalid Purchase transaction fees");
				errorDetails.setUiField(
						AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_TRANSACTION_FEES);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getPurchaseOtherFees())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0017);
				errorDetails.setErrorMessage("Purchase other fees cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_OTHER_FEES);

				errorDetailsList.add(errorDetails);
			} else if (!anukyaUtils.isNumber(unitedStatesOfAmericaShares.getPurchaseOtherFees())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0017);
				errorDetails.setErrorMessage("Invalid Purchase other fees");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_OTHER_FEES);

				errorDetailsList.add(errorDetails);
			}
		}

		if (!errorDetailsList.isEmpty()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Error while adding bought United States of America share -> ");
			logMessage.append(MESSAGE_INPUT_FIELD_ERROR_WITH_PIPE_SEPERATOR);
			logMessage.append(REASON_ONE_OR_MORE_INPUT_IS_INVALID);
			log.error(logMessage.toString());

			throw new AnukyaException("Error while adding bought United States of America share", errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

	}

	public void soldSharesException(UnitedStatesOfAmericaShares unitedStatesOfAmericaShares) throws AnukyaException {

		List<ErrorDetails> errorDetailsList = new ArrayList<>();

		if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getScriptName())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0003);
			errorDetails.setErrorMessage(SCRIPT_NAME_CANNOT_BE_EMPTY);
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_UNITED_STATES_OF_AMERICA_SHARE_SCRIPT_NAME);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getYahooCode())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0006);
			errorDetails.setErrorMessage("Yahoo Script code cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_UNITED_STATES_OF_AMERICA_SHARE_YAHOO_CODE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getSellDate())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0012);
			errorDetails.setErrorMessage("Sell date cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_UNITED_STATES_OF_AMERICA_SHARE_SELL_DATE);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isValidDateFormat(unitedStatesOfAmericaShares.getSellDate())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0012);
			errorDetails.setErrorMessage("Invalid Sell date");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_UNITED_STATES_OF_AMERICA_SHARE_SELL_DATE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getSellQuantity())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0013);
			errorDetails.setErrorMessage("Sell quantity cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_UNITED_STATES_OF_AMERICA_SHARE_SELL_QUANTITY);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(unitedStatesOfAmericaShares.getSellQuantity())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0013);
			errorDetails.setErrorMessage("Invalid Sell quantity");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_UNITED_STATES_OF_AMERICA_SHARE_SELL_QUANTITY);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getSellPrice())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0014);
			errorDetails.setErrorMessage("Sell price cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_UNITED_STATES_OF_AMERICA_SHARE_SELL_PRICE);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(unitedStatesOfAmericaShares.getSellPrice())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0014);
			errorDetails.setErrorMessage("Invalid Sell price");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_UNITED_STATES_OF_AMERICA_SHARE_SELL_PRICE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getSellCommission())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0015);
			errorDetails.setErrorMessage("Sell commission cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_UNITED_STATES_OF_AMERICA_SHARE_SELL_COMMISSION);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(unitedStatesOfAmericaShares.getSellCommission())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0015);
			errorDetails.setErrorMessage("Invalid Sell commission");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_UNITED_STATES_OF_AMERICA_SHARE_SELL_COMMISSION);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getSellTransactionFees())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0016);
			errorDetails.setErrorMessage("Sell transaction fees cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_UNITED_STATES_OF_AMERICA_SHARE_SELL_TRANSACTION_FEES);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(unitedStatesOfAmericaShares.getSellTransactionFees())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0016);
			errorDetails.setErrorMessage("Invalid Sell transaction fees");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_UNITED_STATES_OF_AMERICA_SHARE_SELL_TRANSACTION_FEES);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(unitedStatesOfAmericaShares.getSellOtherFees())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0017);
			errorDetails.setErrorMessage("Purchase other fees cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_UNITED_STATES_OF_AMERICA_SHARE_SELL_OTHER_FEES);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(unitedStatesOfAmericaShares.getPurchaseOtherFees())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_AS_0017);
			errorDetails.setErrorMessage("Invalid Purchase other fees");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_UNITED_STATES_OF_AMERICA_SHARE_SELL_OTHER_FEES);

			errorDetailsList.add(errorDetails);
		}

		if (!errorDetailsList.isEmpty()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Error while adding sold United States of America share -> ");
			logMessage.append(MESSAGE_INPUT_FIELD_ERROR_WITH_PIPE_SEPERATOR);
			logMessage.append(REASON_ONE_OR_MORE_INPUT_IS_INVALID);
			log.error(logMessage.toString());

			throw new AnukyaException("Error while adding sold United States of America share", errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	public void addShortTermInvestmentException(
			UnitedStatesOfAmericaSharesShortTermInvestment unitedStatesOfAmericaSharesShortTermInvestment)
			throws AnukyaException {

		List<ErrorDetails> errorDetailsList = new ArrayList<>();
		if (!StringUtils.hasLength(unitedStatesOfAmericaSharesShortTermInvestment.getScriptName())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_STI_0001);
			errorDetails.setErrorMessage(SCRIPT_NAME_CANNOT_BE_EMPTY);
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_UNITED_STATES_OF_AMERICA_SHARE_SCRIPT_NAME);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(unitedStatesOfAmericaSharesShortTermInvestment.getYahooCode())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_STI_0002);
			errorDetails.setErrorMessage("Yahoo code cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_UNITED_STATES_OF_AMERICA_SHARE_YAHOO_CODE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseDate())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_STI_0003);
			errorDetails.setErrorMessage("Purchase date cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_DATE);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isValidDateFormat(unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseDate())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_STI_0003);
			errorDetails.setErrorMessage("Invalid Purchase date");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_DATE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseQuantity())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_STI_0004);
			errorDetails.setErrorMessage("Purchase quantity cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_QUANTITY);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseQuantity())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_STI_0004);
			errorDetails.setErrorMessage("Invalid Purchase quantity");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_QUANTITY);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(unitedStatesOfAmericaSharesShortTermInvestment.getPurchasePrice())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_STI_0005);
			errorDetails.setErrorMessage("Purchase price cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_PRICE);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(unitedStatesOfAmericaSharesShortTermInvestment.getPurchasePrice())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_STI_0005);
			errorDetails.setErrorMessage("Invalid Purchase price");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_PRICE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseCommission())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_STI_0006);
			errorDetails.setErrorMessage("Purchase commission cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_COMMISSION);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseCommission())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_STI_0006);
			errorDetails.setErrorMessage("Invalid Purchase commission");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_COMMISSION);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseTransactionFees())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_STI_0007);
			errorDetails.setErrorMessage("Purchase transaction fees cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_TRANSACTION_FEES);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseTransactionFees())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_STI_0007);
			errorDetails.setErrorMessage("Invalid Purchase transaction fees");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_TRANSACTION_FEES);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseOtherFees())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_STI_0008);
			errorDetails.setErrorMessage("Purchase other fees cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_OTHER_FEES);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(unitedStatesOfAmericaSharesShortTermInvestment.getPurchaseOtherFees())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_STI_0008);
			errorDetails.setErrorMessage("Invalid Purchase other fees");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_UNITED_STATES_OF_AMERICA_SHARE_PURCHASE_OTHER_FEES);

			errorDetailsList.add(errorDetails);
		}

		if (!errorDetailsList.isEmpty()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Error while adding bought United States of America short term investment -> ");
			logMessage.append(MESSAGE_INPUT_FIELD_ERROR_WITH_PIPE_SEPERATOR);
			logMessage.append(REASON_ONE_OR_MORE_INPUT_IS_INVALID);
			log.error(logMessage.toString());

			throw new AnukyaException("Error while adding bought United States of America short term investment",
					errorDetailsList, AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	public void deleteShortTermInvestmentException(String yahooCode) throws AnukyaException {
		List<ErrorDetails> errorDetailsList = new ArrayList<>();

		if (!StringUtils.hasLength(yahooCode)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.USA_STI_0002);
			errorDetails.setErrorMessage("Yahoo code cannot be empty");

			errorDetailsList.add(errorDetails);
		}

		if (!errorDetailsList.isEmpty()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Error while deleting United States of America Shares short term investment -> ");
			logMessage.append(MESSAGE_INPUT_FIELD_ERROR_WITH_PIPE_SEPERATOR);
			logMessage.append(REASON_ONE_OR_MORE_INPUT_IS_INVALID);
			log.error(logMessage.toString());

			throw new AnukyaException(
					"Exception occured while deleting United States of America Shares short term investment",
					errorDetailsList, AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

}
