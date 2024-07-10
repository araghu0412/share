package com.moneymanager.anukya.exception.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.ThreadContext;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.AnukyaUiFieldMapping;
import com.moneymanager.anukya.exception.ErrorDetails;
import com.moneymanager.anukya.model.BhaarathaShares;
import com.moneymanager.anukya.model.BhaarathaSharesShortTermInvestment;
import com.moneymanager.anukya.utils.AnukyaConstants;
import com.moneymanager.anukya.utils.AnukyaUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class BhaarathaSharesExceptionHandler {

	@Autowired
	private AnukyaUtils anukyaUtils;

	public static final String STOCK_EXCHANGE_CANNOT_BE_EMPTY = "Stock Exchange cannot be empty";
	public static final String BSE_CODE_CANNOT_BE_EMPTY = "BSE code cannot be empty";
	public static final String NSE_CODE_CANNOT_BE_EMPTY = "NSE Code cannot be empty";
	public static final String MONEYCONTROL_CODE_CANNOT_BE_EMPTY = "Moneycontrol Code cannot be empty";
	public static final String SCRIPT_NAME_CANNOT_BE_EMPTY = "Script name cannot be empty";
	public static final String MESSAGE_INPUT_FIELD_ERROR_WITH_PIPE_SEPERATOR = "Message: Input field error | ";
	public static final String REASON_ONE_OR_MORE_INPUT_IS_INVALID = "Reason: One or more input is invalid";
	public static final String SHOULD_BE_EMPTY = "should be empty";
	public static final String CANNOT_BE_EMPTY = "cannot be empty";

	// Getting script name details
	public void shareNameDetailsException(String stockExchange, String scriptCode) throws AnukyaException {

		List<ErrorDetails> errorDetailsList = new ArrayList<>();

		if (!StringUtils.hasLength(stockExchange)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_SD_0001);
			errorDetails.setErrorMessage(STOCK_EXCHANGE_CANNOT_BE_EMPTY);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(scriptCode)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_SD_0002);
			errorDetails.setErrorMessage("Script code cannot be empty");

			errorDetailsList.add(errorDetails);
		}

		if (!errorDetailsList.isEmpty()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Error while getting script code -> ");
			logMessage.append(MESSAGE_INPUT_FIELD_ERROR_WITH_PIPE_SEPERATOR);
			logMessage.append(REASON_ONE_OR_MORE_INPUT_IS_INVALID);
			log.error(logMessage.toString());

			throw new AnukyaException("Exception occured while retrieving Bhaaratha Shares script details",
					errorDetailsList, AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	// Adding Short Term investment exception
	public void addShortTermInvestmentException(BhaarathaSharesShortTermInvestment bhaarathaSharesShortTermInvestment)
			throws AnukyaException {

		List<ErrorDetails> errorDetailsList = new ArrayList<>();

		if (!StringUtils.hasLength(bhaarathaSharesShortTermInvestment.getShareName())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0001);
			errorDetails.setErrorMessage("Share name cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_SHARE_NAME);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(bhaarathaSharesShortTermInvestment.getStockExchange())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0002);
			errorDetails.setErrorMessage(STOCK_EXCHANGE_CANNOT_BE_EMPTY);
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_STOCK_EXCHANGE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(bhaarathaSharesShortTermInvestment.getBseCode())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0003);
			errorDetails.setErrorMessage(BSE_CODE_CANNOT_BE_EMPTY);
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_BSE_CODE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(bhaarathaSharesShortTermInvestment.getNseCode())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0004);
			errorDetails.setErrorMessage(NSE_CODE_CANNOT_BE_EMPTY);
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_NSE_CODE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(bhaarathaSharesShortTermInvestment.getPurchaseDate())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0005);
			errorDetails.setErrorMessage("Purchase date cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_PURCHASE_DATE);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isValidDateFormat(bhaarathaSharesShortTermInvestment.getPurchaseDate())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0005);
			errorDetails.setErrorMessage("Invalid Purchase date");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_PURCHASE_DATE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(bhaarathaSharesShortTermInvestment.getPurchaseQuantity())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0006);
			errorDetails.setErrorMessage("Purchase quantity cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_PURCHASE_QUANTITY);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(bhaarathaSharesShortTermInvestment.getPurchaseQuantity())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0006);
			errorDetails.setErrorMessage("Invalid Purchase quantity");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_PURCHASE_QUANTITY);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(bhaarathaSharesShortTermInvestment.getPurchasePrice())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0007);
			errorDetails.setErrorMessage("Purchase price cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_PURCHASE_PRICE);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(bhaarathaSharesShortTermInvestment.getPurchasePrice())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0007);
			errorDetails.setErrorMessage("Invalid Purchase price");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_PURCHASE_PRICE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(bhaarathaSharesShortTermInvestment.getPurchaseBrokerage())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0008);
			errorDetails.setErrorMessage("Purchase brokerage cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_PURCHASE_BROKERAGE);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(bhaarathaSharesShortTermInvestment.getPurchaseBrokerage())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0008);
			errorDetails.setErrorMessage("Invalid Purchase brokerage");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_PURCHASE_BROKERAGE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(bhaarathaSharesShortTermInvestment.getPurchaseSTT())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0009);
			errorDetails.setErrorMessage("Purchase STT cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_PURCHASE_STT);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(bhaarathaSharesShortTermInvestment.getPurchaseSTT())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0009);
			errorDetails.setErrorMessage("Invalid Purchase STT");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_PURCHASE_STT);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(bhaarathaSharesShortTermInvestment.getPurchaseExpenditure())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0010);
			errorDetails.setErrorMessage("Purchase Expenditure cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_PURCHASE_EXPENDITURE);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(bhaarathaSharesShortTermInvestment.getPurchaseExpenditure())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0010);
			errorDetails.setErrorMessage("Invalid Purchase Expenditure");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_PURCHASE_EXPENDITURE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(bhaarathaSharesShortTermInvestment.getPurchaseNonExpenditure())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0011);
			errorDetails.setErrorMessage("Purchase Non Expenditure cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_PURCHASE_NON_EXPENDITURE);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(bhaarathaSharesShortTermInvestment.getPurchaseNonExpenditure())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0011);
			errorDetails.setErrorMessage("Invalid Purchase Non Expenditure");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_PURCHASE_NON_EXPENDITURE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(bhaarathaSharesShortTermInvestment.getMoneycontrolCode())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0012);
			errorDetails.setErrorMessage(MONEYCONTROL_CODE_CANNOT_BE_EMPTY);
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_MONEYCONTROL_CODE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(bhaarathaSharesShortTermInvestment.getYahooBseCode())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0014);
			errorDetails.setErrorMessage("Yahoo BSE code cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_YAHOO_BSE_CODE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(bhaarathaSharesShortTermInvestment.getYahooNseCode())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0015);
			errorDetails.setErrorMessage("Yahoo NSE code cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.STI_BHAARATHA_YAHOO_NSE_CODE);

			errorDetailsList.add(errorDetails);
		}

		if (!errorDetailsList.isEmpty()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Error while adding Bhaaratha short term investment -> ");
			logMessage.append(MESSAGE_INPUT_FIELD_ERROR_WITH_PIPE_SEPERATOR);
			logMessage.append(REASON_ONE_OR_MORE_INPUT_IS_INVALID);
			log.error(logMessage.toString());

			throw new AnukyaException("Exception occured while adding Bhaaratha Shares short term investment",
					errorDetailsList, AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	public void deleteShortTermInvestmentException(String bseCode) throws AnukyaException {
		List<ErrorDetails> errorDetailsList = new ArrayList<>();

		if (!StringUtils.hasLength(bseCode)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_STI_0003);
			errorDetails.setErrorMessage(BSE_CODE_CANNOT_BE_EMPTY);

			errorDetailsList.add(errorDetails);
		}

		if (!errorDetailsList.isEmpty()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Error while deleting Bhaaratha Shares short term investment -> ");
			logMessage.append(MESSAGE_INPUT_FIELD_ERROR_WITH_PIPE_SEPERATOR);
			logMessage.append(REASON_ONE_OR_MORE_INPUT_IS_INVALID);
			log.error(logMessage.toString());

			throw new AnukyaException("Exception occured while deleting Bhaaratha Shares short term investment",
					errorDetailsList, AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	// Add shares exception
	public void boughtSharesException(BhaarathaShares boughtBhaarathaShares) throws AnukyaException {

		List<ErrorDetails> errorDetailsList = new ArrayList<>();

		if (boughtBhaarathaShares.isSharesSplitSelected()) {

			try {
				if (boughtBhaarathaShares.getSharesSplitRatio().contains(AnukyaConstants.PLUS_CONSTANT)
						|| boughtBhaarathaShares.getSharesSplitRatio().contains(AnukyaConstants.MINUS_CONSTANT)) {
					throw new NumberFormatException();
				}

				if (boughtBhaarathaShares.getSharesSplitRatio().length() - boughtBhaarathaShares.getSharesSplitRatio()
						.replace(AnukyaConstants.RATIO_CONSTANT, AnukyaConstants.EMPTY_STRING).length() != 1) {
					throw new NumberFormatException();
				}

				Integer.parseInt(boughtBhaarathaShares.getSharesSplitRatio().split(AnukyaConstants.RATIO_CONSTANT)[0]);
				Integer.parseInt(boughtBhaarathaShares.getSharesSplitRatio().split(AnukyaConstants.RATIO_CONSTANT)[1]);
			} catch (NumberFormatException e) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0001);
				errorDetails.setErrorMessage("Invalid split ratio. It should in 'number:number' format");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_SPLIT_RATIO);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getSharesSplitDate())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0002);
				errorDetails.setErrorMessage("Split date cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_SPLIT_DATE);

				errorDetailsList.add(errorDetails);
			} else if (!anukyaUtils.isValidDateFormat(boughtBhaarathaShares.getSharesSplitDate())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0002);
				errorDetails.setErrorMessage("Invalid Split date");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_SPLIT_DATE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getStockExchange())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0003);
				errorDetails.setErrorMessage(STOCK_EXCHANGE_CANNOT_BE_EMPTY);
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_STOCK_EXCHANGE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getScriptName())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0004);
				errorDetails.setErrorMessage(SCRIPT_NAME_CANNOT_BE_EMPTY);
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_SCRIPT_NAME);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getBseScriptCode())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0005);
				errorDetails.setErrorMessage(BSE_CODE_CANNOT_BE_EMPTY);
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_BSE_SCRIPT_CODE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getNseScriptCode())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0006);
				errorDetails.setErrorMessage(NSE_CODE_CANNOT_BE_EMPTY);
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_NSE_SCRIPT_CODE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getMoneycontrolCode())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0012);
				errorDetails.setErrorMessage(MONEYCONTROL_CODE_CANNOT_BE_EMPTY);
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_MONEY_CONTROL_CODE);

				errorDetailsList.add(errorDetails);
			}
		} else if (boughtBhaarathaShares.isSharesBonusSelected()) {

			try {
				if (boughtBhaarathaShares.getSharesBonusRatio().contains(AnukyaConstants.PLUS_CONSTANT)
						|| boughtBhaarathaShares.getSharesBonusRatio().contains(AnukyaConstants.MINUS_CONSTANT)) {
					throw new NumberFormatException();
				}

				if (boughtBhaarathaShares.getSharesBonusRatio().length() - boughtBhaarathaShares.getSharesBonusRatio()
						.replace(AnukyaConstants.RATIO_CONSTANT, AnukyaConstants.EMPTY_STRING).length() != 1) {
					throw new NumberFormatException();
				}

				Integer.parseInt(boughtBhaarathaShares.getSharesBonusRatio().split(AnukyaConstants.RATIO_CONSTANT)[0]);
				Integer.parseInt(boughtBhaarathaShares.getSharesBonusRatio().split(AnukyaConstants.RATIO_CONSTANT)[1]);
			} catch (NumberFormatException e) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0007);
				errorDetails.setErrorMessage("Invalid bonus ratio. It should in 'number:number' format");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_BONUS_RATIO);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getSharesBonusDate())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0008);
				errorDetails.setErrorMessage("Bonus date cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_BONUS_DATE);

				errorDetailsList.add(errorDetails);
			} else if (!anukyaUtils.isValidDateFormat(boughtBhaarathaShares.getSharesBonusDate())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0008);
				errorDetails.setErrorMessage("Invalid Bonus date");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_BONUS_DATE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getStockExchange())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0003);
				errorDetails.setErrorMessage(STOCK_EXCHANGE_CANNOT_BE_EMPTY);
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_STOCK_EXCHANGE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getScriptName())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0004);
				errorDetails.setErrorMessage(SCRIPT_NAME_CANNOT_BE_EMPTY);
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_SCRIPT_NAME);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getBseScriptCode())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0005);
				errorDetails.setErrorMessage(BSE_CODE_CANNOT_BE_EMPTY);
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_BSE_SCRIPT_CODE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getNseScriptCode())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0006);
				errorDetails.setErrorMessage(NSE_CODE_CANNOT_BE_EMPTY);
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_NSE_SCRIPT_CODE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getMoneycontrolCode())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0012);
				errorDetails.setErrorMessage(MONEYCONTROL_CODE_CANNOT_BE_EMPTY);
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_MONEY_CONTROL_CODE);

				errorDetailsList.add(errorDetails);
			}
		} else {
			if (!StringUtils.hasLength(boughtBhaarathaShares.getStockExchange())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0003);
				errorDetails.setErrorMessage(STOCK_EXCHANGE_CANNOT_BE_EMPTY);
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_STOCK_EXCHANGE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getScriptName())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0004);
				errorDetails.setErrorMessage(SCRIPT_NAME_CANNOT_BE_EMPTY);
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_SCRIPT_NAME);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getBseScriptCode())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0005);
				errorDetails.setErrorMessage(BSE_CODE_CANNOT_BE_EMPTY);
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_BSE_SCRIPT_CODE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getNseScriptCode())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0006);
				errorDetails.setErrorMessage(NSE_CODE_CANNOT_BE_EMPTY);
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_NSE_SCRIPT_CODE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getYahooBseScriptCode())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0009);
				errorDetails.setErrorMessage("BSE Yahoo Script code cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_YAHOO_BSE_SCRIPT_CODE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getYahooNseScriptCode())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0010);
				errorDetails.setErrorMessage("NSE Yahoo Script code cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_YAHOO_NSE_SCRIPT_CODE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getIsinCode())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0011);
				errorDetails.setErrorMessage("ISIN code cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_ISIN_CODE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getMoneycontrolCode())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0012);
				errorDetails.setErrorMessage(MONEYCONTROL_CODE_CANNOT_BE_EMPTY);
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_MONEY_CONTROL_CODE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getCategory())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0013);
				errorDetails.setErrorMessage("Category cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_CATEGORY);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getSector())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0014);
				errorDetails.setErrorMessage("Sector cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_SECTOR);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getIndustry())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0015);
				errorDetails.setErrorMessage("Industry cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_INDUSTRY);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getPurchaseDate())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0016);
				errorDetails.setErrorMessage("Purchase date cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_PURCHASE_DATE);

				errorDetailsList.add(errorDetails);
			} else if (!anukyaUtils.isValidDateFormat(boughtBhaarathaShares.getPurchaseDate())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0016);
				errorDetails.setErrorMessage("Invalid Purchase date");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_PURCHASE_DATE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getPurchaseQuantity())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0017);
				errorDetails.setErrorMessage("Purchase quantity cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_PURCHASE_QUANTITY);

				errorDetailsList.add(errorDetails);
			} else if (!anukyaUtils.isNumber(boughtBhaarathaShares.getPurchaseQuantity())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0017);
				errorDetails.setErrorMessage("Invalid Purchase quantity");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_PURCHASE_QUANTITY);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getPurchasePrice())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0018);
				errorDetails.setErrorMessage("Purchase price cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_PURCHASE_PRICE);

				errorDetailsList.add(errorDetails);
			} else if (!anukyaUtils.isNumber(boughtBhaarathaShares.getPurchasePrice())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0018);
				errorDetails.setErrorMessage("Invalid Purchase price");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_PURCHASE_PRICE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getPurchaseBrokerage())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0019);
				errorDetails.setErrorMessage("Purchase brokerage cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_PURCHASE_BROKERAGE);

				errorDetailsList.add(errorDetails);
			} else if (!anukyaUtils.isNumber(boughtBhaarathaShares.getPurchaseBrokerage())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0019);
				errorDetails.setErrorMessage("Invalid Purchase brokerage");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_PURCHASE_BROKERAGE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getPurchaseSTT())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0020);
				errorDetails.setErrorMessage("Purchase STT cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_PURCHASE_STT);

				errorDetailsList.add(errorDetails);
			} else if (!anukyaUtils.isNumber(boughtBhaarathaShares.getPurchaseSTT())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0020);
				errorDetails.setErrorMessage("Invalid Purchase STT");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_PURCHASE_STT);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getPurchaseExpenditure())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0021);
				errorDetails.setErrorMessage("Purchase expenditure cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_PURCHASE_EXPENDITURE);

				errorDetailsList.add(errorDetails);
			} else if (!anukyaUtils.isNumber(boughtBhaarathaShares.getPurchaseExpenditure())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0021);
				errorDetails.setErrorMessage("Invalid Purchase Expenditure");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_PURCHASE_EXPENDITURE);

				errorDetailsList.add(errorDetails);
			}

			if (!StringUtils.hasLength(boughtBhaarathaShares.getPurchaseNonExpenditure())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0022);
				errorDetails.setErrorMessage("Purchase non expenditure cannot be empty");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_PURCHASE_NON_EXPENDITURE);

				errorDetailsList.add(errorDetails);
			} else if (!anukyaUtils.isNumber(boughtBhaarathaShares.getPurchaseNonExpenditure())) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0022);
				errorDetails.setErrorMessage("Invalid Purchase non expenditure");
				errorDetails.setUiField(AnukyaUiFieldMapping.BOUGHT_BHAARATHA_SHARE_PURCHASE_NON_EXPENDITURE);

				errorDetailsList.add(errorDetails);
			}
		}

		if (!errorDetailsList.isEmpty()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Error while adding bought Bhaaratha share -> ");
			logMessage.append(MESSAGE_INPUT_FIELD_ERROR_WITH_PIPE_SEPERATOR);
			logMessage.append(REASON_ONE_OR_MORE_INPUT_IS_INVALID);
			log.error(logMessage.toString());

			throw new AnukyaException("Error while adding bought Bhaaratha share", errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	public void soldSharesException(BhaarathaShares soldBhaarathaShares) throws AnukyaException {

		List<ErrorDetails> errorDetailsList = new ArrayList<>();

		if (!StringUtils.hasLength(soldBhaarathaShares.getStockExchange())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0003);
			errorDetails.setErrorMessage(STOCK_EXCHANGE_CANNOT_BE_EMPTY);
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_BHAARATHA_SHARE_STOCK_EXCHANGE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(soldBhaarathaShares.getBseScriptCode())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0005);
			errorDetails.setErrorMessage(BSE_CODE_CANNOT_BE_EMPTY);
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_BHAARATHA_SHARE_BSE_SCRIPT_CODE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(soldBhaarathaShares.getNseScriptCode())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0006);
			errorDetails.setErrorMessage(NSE_CODE_CANNOT_BE_EMPTY);
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_BHAARATHA_SHARE_NSE_SCRIPT_CODE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(soldBhaarathaShares.getScriptName())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0004);
			errorDetails.setErrorMessage(SCRIPT_NAME_CANNOT_BE_EMPTY);
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_BHAARATHA_SHARE_SCRIPT_NAME);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(soldBhaarathaShares.getMoneycontrolCode())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0012);
			errorDetails.setErrorMessage(MONEYCONTROL_CODE_CANNOT_BE_EMPTY);
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_BHAARATHA_SHARE_MONEY_CONTROL_CODE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(soldBhaarathaShares.getSellDate())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0016);
			errorDetails.setErrorMessage("Sell date cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_BHAARATHA_SHARE_SELL_DATE);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isValidDateFormat(soldBhaarathaShares.getSellDate())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0016);
			errorDetails.setErrorMessage("Invalid Sell date");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_BHAARATHA_SHARE_SELL_DATE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(soldBhaarathaShares.getSellQuantity())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0017);
			errorDetails.setErrorMessage("Sell Quantity cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_BHAARATHA_SHARE_SELL_QUANTITY);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(soldBhaarathaShares.getSellQuantity())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0017);
			errorDetails.setErrorMessage("Invalid Sell quantity");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_BHAARATHA_SHARE_SELL_QUANTITY);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(soldBhaarathaShares.getSellPrice())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0018);
			errorDetails.setErrorMessage("Sell price cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_BHAARATHA_SHARE_SELL_PRICE);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(soldBhaarathaShares.getSellPrice())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0018);
			errorDetails.setErrorMessage("Invalid Sell price");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_BHAARATHA_SHARE_SELL_PRICE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(soldBhaarathaShares.getSellBrokerage())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0019);
			errorDetails.setErrorMessage("Sell brokerage cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_BHAARATHA_SHARE_SELL_BROKERAGE);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(soldBhaarathaShares.getSellBrokerage())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0019);
			errorDetails.setErrorMessage("Invalid Sell brokerage");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_BHAARATHA_SHARE_SELL_BROKERAGE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(soldBhaarathaShares.getSellSTT())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0020);
			errorDetails.setErrorMessage("Sell STT cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_BHAARATHA_SHARE_SELL_STT);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(soldBhaarathaShares.getSellSTT())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0020);
			errorDetails.setErrorMessage("Invalid Sell STT");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_BHAARATHA_SHARE_SELL_STT);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(soldBhaarathaShares.getSellExpenditure())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0021);
			errorDetails.setErrorMessage("Sell Other expenditure cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_BHAARATHA_SHARE_SELL_EXPENDITURE);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(soldBhaarathaShares.getSellExpenditure())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0021);
			errorDetails.setErrorMessage("Invalid Sell Other Expenditure");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_BHAARATHA_SHARE_SELL_EXPENDITURE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(soldBhaarathaShares.getSellNonExpenditure())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0022);
			errorDetails.setErrorMessage("Sell non expenditure cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_BHAARATHA_SHARE_SELL_NON_EXPENDITURE);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isNumber(soldBhaarathaShares.getSellNonExpenditure())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_AS_0022);
			errorDetails.setErrorMessage("Invalid Sell non expenditure");
			errorDetails.setUiField(AnukyaUiFieldMapping.SOLD_BHAARATHA_SHARE_SELL_NON_EXPENDITURE);

			errorDetailsList.add(errorDetails);
		}

		if (!errorDetailsList.isEmpty()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Error while adding sold Bhaaratha share -> ");
			logMessage.append(MESSAGE_INPUT_FIELD_ERROR_WITH_PIPE_SEPERATOR);
			logMessage.append(REASON_ONE_OR_MORE_INPUT_IS_INVALID);
			log.error(logMessage.toString());

			throw new AnukyaException("Error while adding sold Bhaaratha share", errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	public void oneShareDetailsException(String type, String bseCode, String nseCode, String moneycontrolCode,
			String yahooBseCode, String yahooNseCode) throws AnukyaException {

		List<ErrorDetails> errorDetailsList = new ArrayList<>();

		if (!StringUtils.hasLength(type)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_OSD_0001);
			errorDetails.setErrorMessage("TYPE cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.ONE_SHARE_DETAILS_BHAARATHA_TYPE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(bseCode)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_OSD_0002);
			errorDetails.setErrorMessage(BSE_CODE_CANNOT_BE_EMPTY);
			errorDetails.setUiField(AnukyaUiFieldMapping.ONE_SHARE_DETAILS_BHAARATHA_BSE_CODE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(nseCode)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_OSD_0003);
			errorDetails.setErrorMessage(NSE_CODE_CANNOT_BE_EMPTY);
			errorDetails.setUiField(AnukyaUiFieldMapping.ONE_SHARE_DETAILS_BHAARATHA_NSE_CODE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(moneycontrolCode)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_OSD_0004);
			errorDetails.setErrorMessage(MONEYCONTROL_CODE_CANNOT_BE_EMPTY);
			errorDetails.setUiField(AnukyaUiFieldMapping.ONE_SHARE_DETAILS_BHAARATHA_MONEYCONTROL_CODE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(yahooBseCode)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_OSD_0005);
			errorDetails.setErrorMessage("Yahoo BSE code cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.ONE_SHARE_DETAILS_BHAARATHA_YAHOO_BSE_CODE);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(yahooNseCode)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_OSD_0006);
			errorDetails.setErrorMessage("Yahoo NSE code cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.ONE_SHARE_DETAILS_BHAARATHA_YAHOO_NSE_CODE);

			errorDetailsList.add(errorDetails);
		}

		if (!errorDetailsList.isEmpty()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Error while retrieving Bhaaratha one share details -> ");
			logMessage.append(MESSAGE_INPUT_FIELD_ERROR_WITH_PIPE_SEPERATOR);
			logMessage.append(REASON_ONE_OR_MORE_INPUT_IS_INVALID);
			log.error(logMessage.toString());

			throw new AnukyaException("Exception occured while retrieving Bhaaratha Shares one share details",
					errorDetailsList, AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	public void bulkUploadExcelException(Sheet sheet) throws AnukyaException {

		StringBuilder errorMessageStringBuilder = new StringBuilder();

		// Automating or simplifying
		List<Integer> splitBonusYesAndNoNonEmptyErrorCheckList = Arrays.asList(5, 7, 8, 10, 11, 12, 13, 21, 22, 23, 24,
				25, 26, 27);
		List<Integer> splitBonusYesAndNoEmptyErrorCheckList = Arrays.asList(3, 4, 6, 9, 16, 18, 19);
		List<Integer> buyNonEmptyErrorCheckList = Arrays.asList(18, 19);
		List<Integer> buyEmptyErrorCheckList = Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 21, 22,
				23, 24, 25, 26, 27);
		List<Integer> sellNonEmptyErrorCheckList = Arrays.asList(5, 7, 8, 10, 11, 12, 13, 14, 15, 18, 19);
		List<Integer> sellEmptyErrorCheckList = Arrays.asList(3, 4, 6, 9, 16, 21, 22, 23, 24, 25, 26, 27);

		boolean isTypeEmpty = true;

		DataFormatter formatter = new DataFormatter();

		for (int i = 1; i < 26; i++) {
			Row row = sheet.getRow(i);

			if (isTypeEmpty && (AnukyaConstants.BUY.equalsIgnoreCase(formatter.formatCellValue(row.getCell(1)))
					|| AnukyaConstants.SELL.equalsIgnoreCase(formatter.formatCellValue(row.getCell(1))))) {
				isTypeEmpty = false;
			}

			if (AnukyaConstants.BUY.equalsIgnoreCase(formatter.formatCellValue(row.getCell(1)))) {
				if (AnukyaConstants.YES.equalsIgnoreCase(formatter.formatCellValue(row.getCell(14)))
						&& AnukyaConstants.YES.equalsIgnoreCase(formatter.formatCellValue(row.getCell(15)))) {
					errorMessageStringBuilder.append(getCellReference(row.getCell(14)) + AnukyaConstants.SPACE
							+ AnukyaConstants.AND_CONSTANT + AnukyaConstants.SPACE + getCellReference(row.getCell(15))
							+ " both cannot be <b>Yes</b></br>");
				} else if (AnukyaConstants.YES.equalsIgnoreCase(formatter.formatCellValue(row.getCell(14)))
						|| AnukyaConstants.YES.equalsIgnoreCase(formatter.formatCellValue(row.getCell(15)))) {
					for (int splitBonusYesAndNoNonEmptyErrorCheck : splitBonusYesAndNoNonEmptyErrorCheckList) {
						if (!formatter.formatCellValue(row.getCell(splitBonusYesAndNoNonEmptyErrorCheck)).isEmpty()) {
							errorMessageStringBuilder
									.append(getCellReference(row.getCell(splitBonusYesAndNoNonEmptyErrorCheck))
											+ AnukyaConstants.SPACE + SHOULD_BE_EMPTY + AnukyaConstants.BR_CONSTANT);
						}
					}

					for (int splitBonusYesAndNoEmptyErrorCheck : splitBonusYesAndNoEmptyErrorCheckList) {
						if (formatter.formatCellValue(row.getCell(splitBonusYesAndNoEmptyErrorCheck)).isEmpty()) {
							errorMessageStringBuilder
									.append(getCellReference(row.getCell(splitBonusYesAndNoEmptyErrorCheck))
											+ AnukyaConstants.SPACE + CANNOT_BE_EMPTY + AnukyaConstants.BR_CONSTANT);
						}
					}

					if (AnukyaConstants.YES.equalsIgnoreCase(formatter.formatCellValue(row.getCell(14)))
							&& AnukyaConstants.EMPTY_STRING
									.equalsIgnoreCase(formatter.formatCellValue(row.getCell(15)))) {
						errorMessageStringBuilder.append(getCellReference(row.getCell(15)) + AnukyaConstants.SPACE
								+ CANNOT_BE_EMPTY + AnukyaConstants.BR_CONSTANT);
					}

					if (AnukyaConstants.YES.equalsIgnoreCase(formatter.formatCellValue(row.getCell(15)))
							&& AnukyaConstants.EMPTY_STRING
									.equalsIgnoreCase(formatter.formatCellValue(row.getCell(14)))) {
						errorMessageStringBuilder.append(getCellReference(row.getCell(14)) + AnukyaConstants.SPACE
								+ CANNOT_BE_EMPTY + AnukyaConstants.BR_CONSTANT);
					}
				} else {
					for (int buyNonEmptyErrorCheck : buyNonEmptyErrorCheckList) {
						if (!formatter.formatCellValue(row.getCell(buyNonEmptyErrorCheck)).isEmpty()) {
							errorMessageStringBuilder.append(getCellReference(row.getCell(buyNonEmptyErrorCheck))
									+ AnukyaConstants.SPACE + SHOULD_BE_EMPTY + AnukyaConstants.BR_CONSTANT);
						}
					}

					for (int buyEmptyErrorCheck : buyEmptyErrorCheckList) {
						if (formatter.formatCellValue(row.getCell(buyEmptyErrorCheck)).isEmpty()) {
							errorMessageStringBuilder.append(getCellReference(row.getCell(buyEmptyErrorCheck))
									+ AnukyaConstants.SPACE + CANNOT_BE_EMPTY + AnukyaConstants.BR_CONSTANT);
						}
					}
				}
			} else if (AnukyaConstants.SELL.equalsIgnoreCase(formatter.formatCellValue(row.getCell(1)))) {
				for (int sellNonEmptyErrorCheck : sellNonEmptyErrorCheckList) {
					if (!formatter.formatCellValue(row.getCell(sellNonEmptyErrorCheck)).isEmpty()) {
						errorMessageStringBuilder.append(getCellReference(row.getCell(sellNonEmptyErrorCheck))
								+ AnukyaConstants.SPACE + SHOULD_BE_EMPTY + AnukyaConstants.BR_CONSTANT);
					}
				}

				for (int sellEmptyErrorCheck : sellEmptyErrorCheckList) {
					if (formatter.formatCellValue(row.getCell(sellEmptyErrorCheck)).isEmpty()) {
						errorMessageStringBuilder.append(getCellReference(row.getCell(sellEmptyErrorCheck))
								+ AnukyaConstants.SPACE + CANNOT_BE_EMPTY + AnukyaConstants.BR_CONSTANT);
					}
				}
			}
		}

		if (isTypeEmpty) {
			errorMessageStringBuilder.append("Type is empty. At least 1 type should be <b>BUY</b> or <b>SELL</b></br>");
		}

		if (!errorMessageStringBuilder.toString().isEmpty()) {
			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.BHA_BU_0001);
			errorDetails.setErrorMessage(errorMessageStringBuilder.toString());

			errorDetailsList.add(errorDetails);

			throw new AnukyaException(
					"Exception occured while adding Bhaaratha Shares bulk upload [Excel validation exception]",
					errorDetailsList, AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	// Private methods
	private String getCellReference(Cell cell) {

		int columnIndex = cell.getColumnIndex();
		int rowIndex = cell.getRowIndex();

		return new CellReference(rowIndex, columnIndex).getCellRefParts()[2]
				+ new CellReference(rowIndex, columnIndex).getCellRefParts()[1];
	}
}
