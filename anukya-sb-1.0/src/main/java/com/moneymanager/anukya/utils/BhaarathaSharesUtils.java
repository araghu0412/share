package com.moneymanager.anukya.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.model.BhaarathaSharesLastTradingPrice;
import com.moneymanager.anukya.model.BhaarathaSharesOneShareDetails;
import com.moneymanager.anukya.model.BhaarathaSharesScriptCodeDetails;
import com.moneymanager.anukya.servicecall.BhaarathaSharesServiceCall;

@Component
public class BhaarathaSharesUtils {

	@Value("${BHAARATHA.SHARES.EXTERNAL.API.SERVICE}")
	private String bhaarathaSharesExternalApiService;

	@Autowired
	private AnukyaUtils anukyaUtils;

	@Autowired
	private BhaarathaSharesServiceCall bhaarathaSharesServiceCall;

	public boolean isShortTerm(String purchaseDateInString, String sellingDateInString) throws AnukyaException {

		Date purchaseDate = anukyaUtils.convertStringToDate(purchaseDateInString,
				AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);
		Date sellingDate = anukyaUtils.convertStringToDate(sellingDateInString,
				AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		long days = (sellingDate.getTime() - purchaseDate.getTime()) / (1000 * 60 * 60 * 24);

		return days < 365;
	}

	public BhaarathaSharesLastTradingPrice getBhaarathaSharesLastTradingPrice(
			BhaarathaSharesScriptCodeDetails bhaarathaSharesScriptCodeDetails) throws AnukyaException {

		BhaarathaSharesLastTradingPrice bhaarathaSharesLastTradingPrice = new BhaarathaSharesLastTradingPrice();

		String yahooScriptCode = "";
		String scriptCode = "";

		if (AnukyaConstants.BSE.equalsIgnoreCase(bhaarathaSharesScriptCodeDetails.getStockExchange())) {
			yahooScriptCode = bhaarathaSharesScriptCodeDetails.getYahooBseCode();
			scriptCode = bhaarathaSharesScriptCodeDetails.getBseCode();
		} else if (AnukyaConstants.NSE.equalsIgnoreCase(bhaarathaSharesScriptCodeDetails.getStockExchange())) {
			yahooScriptCode = bhaarathaSharesScriptCodeDetails.getYahooNseCode();
			scriptCode = bhaarathaSharesScriptCodeDetails.getNseCode();
		}

		if (bhaarathaSharesExternalApiService.equals(AnukyaConstants.MONEYCONTROL)) {
			bhaarathaSharesLastTradingPrice = bhaarathaSharesServiceCall.getMoneycontrolBhaarathaSharesLastTradingPrice(
					bhaarathaSharesScriptCodeDetails.getStockExchange(),
					bhaarathaSharesScriptCodeDetails.getMoneycontrolCode());
		} else if (bhaarathaSharesExternalApiService.equals(AnukyaConstants.YAHOO_FINANCE)) {
			bhaarathaSharesLastTradingPrice = bhaarathaSharesServiceCall.getYahooFinanceBhaarathaSharesLastTradingPrice(
					bhaarathaSharesScriptCodeDetails.getStockExchange(), yahooScriptCode);
		}

		bhaarathaSharesLastTradingPrice.setScriptCode(scriptCode);
		return bhaarathaSharesLastTradingPrice;
	}

	public BhaarathaSharesOneShareDetails getBhaarathaSharesOneShareDetails(
			BhaarathaSharesScriptCodeDetails bhaarathaSharesScriptCodeDetails) throws AnukyaException {

		BhaarathaSharesOneShareDetails bhaarathaSharesOneShareDetails = new BhaarathaSharesOneShareDetails();

		String yahooScriptCode = "";
		String scriptCode = "";

		if (AnukyaConstants.BSE.equalsIgnoreCase(bhaarathaSharesScriptCodeDetails.getStockExchange())) {
			yahooScriptCode = bhaarathaSharesScriptCodeDetails.getYahooBseCode();
			scriptCode = bhaarathaSharesScriptCodeDetails.getBseCode();
		} else if (AnukyaConstants.NSE.equalsIgnoreCase(bhaarathaSharesScriptCodeDetails.getStockExchange())) {
			yahooScriptCode = bhaarathaSharesScriptCodeDetails.getYahooNseCode();
			scriptCode = bhaarathaSharesScriptCodeDetails.getNseCode();
		}

		if (bhaarathaSharesExternalApiService.equals(AnukyaConstants.MONEYCONTROL)) {
			bhaarathaSharesOneShareDetails = bhaarathaSharesServiceCall.getMoneycontrolBhaarathaOneShareDetails(
					bhaarathaSharesScriptCodeDetails.getStockExchange(),
					bhaarathaSharesScriptCodeDetails.getMoneycontrolCode());
		} else if (bhaarathaSharesExternalApiService.equals(AnukyaConstants.YAHOO_FINANCE)) {
			bhaarathaSharesOneShareDetails = bhaarathaSharesServiceCall.getYahooFinanceBhaarathaOneShareDetails(
					bhaarathaSharesScriptCodeDetails.getStockExchange(), yahooScriptCode);
		}

		bhaarathaSharesOneShareDetails.setScriptCode(scriptCode);
		return bhaarathaSharesOneShareDetails;

	}

}
