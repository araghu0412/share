package com.moneymanager.anukya.servicecall;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.logging.log4j.ThreadContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.model.BhaarathaSharesLastTradingPrice;
import com.moneymanager.anukya.model.BhaarathaSharesOneShareDetails;
import com.moneymanager.anukya.model.BhaarathaSharesShareNameDetails;
import com.moneymanager.anukya.utils.AnukyaConstants;

@Component
public class BhaarathaSharesServiceCall {

	@Value("${API.MONEYCONTROL.SHARE.DETAILS}")
	private String apiMoneycontrolShareDetails;

	@Value("${API.YAHOO.FINANCE.SHARE.DETAILS}")
	private String apiYahooFinanceShareDetails;

	@Value("${API.YAHOO.FINANCE.DIVIDEND.YIELD}")
	private String apiYahooFinanceDividendYield;

	@Value("${YAHOO.FINANCE.COOKIE}")
	private String yahooFinancenCookie;

	@Autowired
	private ServiceCall serviceCall;

	public BhaarathaSharesShareNameDetails getBhaarathaSharesShareNameDetails(String stockExchange,
			String apiScriptCode) throws AnukyaException {

		BhaarathaSharesShareNameDetails bhaarathaSharesShareNameDetails = new BhaarathaSharesShareNameDetails();

		URI uri;
		try {
			uri = new URI(
					apiMoneycontrolShareDetails.replace(AnukyaConstants.API_STOCK_EXCHANGE, stockExchange.toLowerCase())
							.replace(AnukyaConstants.API_SCRIPT_CODE, apiScriptCode));
		} catch (URISyntaxException e) {

			throw new AnukyaException("URI building error", null, AnukyaErrorConstants.INTERNAL_SERVER_ERROR,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
		String responseByScript = serviceCall.getCall(uri, null);

		JSONObject jsonObject = new JSONObject(responseByScript);

		if (jsonObject.getString(AnukyaConstants.MONEYCONTROL_API_CODE).equalsIgnoreCase(AnukyaConstants.STATUS_201)
				|| jsonObject.getJSONObject(AnukyaConstants.MONEYCONTROL_API_DATA)
						.getString(AnukyaConstants.MONEYCONTROL_API_LP)
						.equalsIgnoreCase(AnukyaConstants.MINUS_CONSTANT)) {

			throw new AnukyaException("Service call error", null, AnukyaErrorConstants.NO_CONTENT,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		String scriptCode = "";
		if (stockExchange.equalsIgnoreCase(AnukyaConstants.BSE)) {
			scriptCode = jsonObject.getJSONObject(AnukyaConstants.MONEYCONTROL_API_DATA)
					.getString(AnukyaConstants.MONEYCONTROL_API_BSE_ID);
		} else if (stockExchange.equalsIgnoreCase(AnukyaConstants.NSE)) {
			scriptCode = jsonObject.getJSONObject(AnukyaConstants.MONEYCONTROL_API_DATA)
					.getString(AnukyaConstants.MONEYCONTROL_API_NSE_ID);
		}

		String scriptName = jsonObject.getJSONObject(AnukyaConstants.MONEYCONTROL_API_DATA)
				.getString(AnukyaConstants.MONEYCONTROL_API_SC_FULL_NM);

		bhaarathaSharesShareNameDetails.setScriptCode(scriptCode);
		bhaarathaSharesShareNameDetails.setScriptName(scriptName);
		bhaarathaSharesShareNameDetails.setStockExchange(stockExchange.toUpperCase());

		return bhaarathaSharesShareNameDetails;
	}

	public BhaarathaSharesLastTradingPrice getMoneycontrolBhaarathaSharesLastTradingPrice(String stockExchange,
			String moneycontrolCode) throws AnukyaException {

		BhaarathaSharesLastTradingPrice bhaarathaSharesLastTradingPrice = new BhaarathaSharesLastTradingPrice();

		URI uri;
		try {
			uri = new URI(
					apiMoneycontrolShareDetails.replace(AnukyaConstants.API_STOCK_EXCHANGE, stockExchange.toLowerCase())
							.replace(AnukyaConstants.API_SCRIPT_CODE, moneycontrolCode));
		} catch (URISyntaxException e) {

			throw new AnukyaException("URI building error", null, AnukyaErrorConstants.INTERNAL_SERVER_ERROR,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
		String responseByScript = serviceCall.getCall(uri, null);

		JSONObject jsonObject = new JSONObject(responseByScript);

		if (jsonObject.getString(AnukyaConstants.MONEYCONTROL_API_CODE).equalsIgnoreCase(AnukyaConstants.STATUS_201)
				|| jsonObject.getJSONObject(AnukyaConstants.MONEYCONTROL_API_DATA)
						.getString(AnukyaConstants.MONEYCONTROL_API_LP)
						.equalsIgnoreCase(AnukyaConstants.MINUS_CONSTANT)) {

			throw new AnukyaException("Service call error", null, AnukyaErrorConstants.NO_CONTENT,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		String scriptName = jsonObject.getJSONObject(AnukyaConstants.MONEYCONTROL_API_DATA)
				.getString(AnukyaConstants.MONEYCONTROL_API_SC_FULL_NM);
		String lastTradingPrice = jsonObject.getJSONObject(AnukyaConstants.MONEYCONTROL_API_DATA)
				.getString(AnukyaConstants.MONEYCONTROL_API_PRICE_CURRENT);
		String moneycontrolAliasCode = jsonObject.getJSONObject(AnukyaConstants.MONEYCONTROL_API_DATA)
				.getString(AnukyaConstants.MONEYCONTROL_API_DISPID);

		bhaarathaSharesLastTradingPrice.setScriptName(scriptName);
		bhaarathaSharesLastTradingPrice.setStockExchange(stockExchange.toUpperCase());
		bhaarathaSharesLastTradingPrice.setLastTradingPrice(lastTradingPrice);
		bhaarathaSharesLastTradingPrice.setMoneycontrolAliasCode(moneycontrolAliasCode);

		return bhaarathaSharesLastTradingPrice;
	}

	public BhaarathaSharesLastTradingPrice getYahooFinanceBhaarathaSharesLastTradingPrice(String stockExchange,
			String yahooScriptCode) throws AnukyaException {

		BhaarathaSharesLastTradingPrice bhaarathaSharesLastTradingPrice = new BhaarathaSharesLastTradingPrice();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(AnukyaConstants.COOKIE, yahooFinancenCookie);

		HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

		URI uri;
		try {
			uri = new URI(apiYahooFinanceShareDetails.replace(AnukyaConstants.API_SCRIPT_CODE,
					URLEncoder.encode(yahooScriptCode, "UTF-8")));
		} catch (UnsupportedEncodingException | URISyntaxException e) {

			throw new AnukyaException("URI building error", null, AnukyaErrorConstants.INTERNAL_SERVER_ERROR,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
		String responseByScript = serviceCall.getCall(uri, httpEntity);

		JSONObject jsonObject = new JSONObject(responseByScript);

		JSONArray resultJsonArray = jsonObject.getJSONObject(AnukyaConstants.YAHOO_FINANCE_QUOTE_RESPONSE)
				.getJSONArray(AnukyaConstants.YAHOO_FINANCE_QUOTE_RESULT);

		if (resultJsonArray.isEmpty()) {
			throw new AnukyaException("Service call error", null, AnukyaErrorConstants.NO_CONTENT,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		JSONObject scriptJsonObject = resultJsonArray.getJSONObject(0);

		String scriptName = scriptJsonObject.getString(AnukyaConstants.YAHOO_FINANCE_QUOTE_LONG_NAME);
		String lastTradingPrice = String
				.valueOf(scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_REGULAR_MARKET_PRICE));

		bhaarathaSharesLastTradingPrice.setScriptName(scriptName);
		bhaarathaSharesLastTradingPrice.setStockExchange(stockExchange.toUpperCase());
		bhaarathaSharesLastTradingPrice.setLastTradingPrice(lastTradingPrice);

		return bhaarathaSharesLastTradingPrice;
	}

	public BhaarathaSharesOneShareDetails getMoneycontrolBhaarathaOneShareDetails(String stockExchange,
			String moneycontrolCode) throws AnukyaException {

		BhaarathaSharesOneShareDetails bhaarathaSharesOneShareDetails = new BhaarathaSharesOneShareDetails();

		URI uri;
		try {
			uri = new URI(
					apiMoneycontrolShareDetails.replace(AnukyaConstants.API_STOCK_EXCHANGE, stockExchange.toLowerCase())
							.replace(AnukyaConstants.API_SCRIPT_CODE, moneycontrolCode));
		} catch (URISyntaxException e) {

			throw new AnukyaException("URI building error", null, AnukyaErrorConstants.INTERNAL_SERVER_ERROR,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
		String responseByScript = serviceCall.getCall(uri, null);

		JSONObject jsonObject = new JSONObject(responseByScript);

		if (jsonObject.getString(AnukyaConstants.MONEYCONTROL_API_CODE).equalsIgnoreCase(AnukyaConstants.STATUS_201)
				|| jsonObject.getJSONObject(AnukyaConstants.MONEYCONTROL_API_DATA)
						.getString(AnukyaConstants.MONEYCONTROL_API_LP)
						.equalsIgnoreCase(AnukyaConstants.MINUS_CONSTANT)) {

			throw new AnukyaException("Service call error", null, AnukyaErrorConstants.NO_CONTENT,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		String scriptName = jsonObject.getJSONObject(AnukyaConstants.MONEYCONTROL_API_DATA)
				.getString(AnukyaConstants.MONEYCONTROL_API_SC_FULL_NM);
		String previousClose = jsonObject.getJSONObject(AnukyaConstants.MONEYCONTROL_API_DATA)
				.getString(AnukyaConstants.MONEYCONTROL_API_PRICE_PREV_CLOSE);
		String openPrice = jsonObject.getJSONObject(AnukyaConstants.MONEYCONTROL_API_DATA)
				.getString(AnukyaConstants.MONEYCONTROL_API_OPN);
		String currentMarketPrice = jsonObject.getJSONObject(AnukyaConstants.MONEYCONTROL_API_DATA)
				.getString(AnukyaConstants.MONEYCONTROL_API_PRICE_CURRENT);
		String currentMarketPriceChange = String.format(AnukyaConstants.FLOAT_2_DECIMAL,
				new BigDecimal(jsonObject.getJSONObject(AnukyaConstants.MONEYCONTROL_API_DATA)
						.getString(AnukyaConstants.MONEYCONTROL_API_PRICE_CHANGE)));
		String currentMarketPriceChangePercentage = String.format(AnukyaConstants.FLOAT_2_DECIMAL,
				new BigDecimal(jsonObject.getJSONObject(AnukyaConstants.MONEYCONTROL_API_DATA)
						.getString(AnukyaConstants.MONEYCONTROL_API_PRICE_PERCENT_CHANGE)));
		String fiftyTwoWeekHigh = jsonObject.getJSONObject(AnukyaConstants.MONEYCONTROL_API_DATA)
				.getString(AnukyaConstants.MONEYCONTROL_API_52H);
		String fiftyTwoWeekLow = jsonObject.getJSONObject(AnukyaConstants.MONEYCONTROL_API_DATA)
				.getString(AnukyaConstants.MONEYCONTROL_API_52L);
		String dailyHigh = jsonObject.getJSONObject(AnukyaConstants.MONEYCONTROL_API_DATA)
				.getString(AnukyaConstants.MONEYCONTROL_API_HP);
		String dailyLow = jsonObject.getJSONObject(AnukyaConstants.MONEYCONTROL_API_DATA)
				.getString(AnukyaConstants.MONEYCONTROL_API_LP);

		bhaarathaSharesOneShareDetails.setScriptName(scriptName);
		bhaarathaSharesOneShareDetails.setStockExchange(stockExchange);
		bhaarathaSharesOneShareDetails.setPreviousClose(previousClose);
		bhaarathaSharesOneShareDetails.setOpenPrice(openPrice);
		bhaarathaSharesOneShareDetails.setCurrentMarketPrice(currentMarketPrice);
		bhaarathaSharesOneShareDetails.setCurrentMarketPriceChange(currentMarketPriceChange);
		bhaarathaSharesOneShareDetails.setCurrentMarketPriceChangePercentage(currentMarketPriceChangePercentage);
		bhaarathaSharesOneShareDetails.setFiftyTwoWeekHigh(fiftyTwoWeekHigh);
		bhaarathaSharesOneShareDetails.setFiftyTwoWeekLow(fiftyTwoWeekLow);
		bhaarathaSharesOneShareDetails.setDailyHigh(dailyHigh);
		bhaarathaSharesOneShareDetails.setDailyLow(dailyLow);

		return bhaarathaSharesOneShareDetails;
	}

	public BhaarathaSharesOneShareDetails getYahooFinanceBhaarathaOneShareDetails(String stockExchange,
			String yahooScriptCode) throws AnukyaException {

		BhaarathaSharesOneShareDetails bhaarathaSharesOneShareDetails = new BhaarathaSharesOneShareDetails();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(AnukyaConstants.COOKIE, yahooFinancenCookie);

		HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

		URI uri;
		try {
			uri = new URI(apiYahooFinanceShareDetails.replace(AnukyaConstants.API_SCRIPT_CODE,
					URLEncoder.encode(yahooScriptCode, "UTF-8")));
		} catch (UnsupportedEncodingException | URISyntaxException e) {

			throw new AnukyaException("URI building error", null, AnukyaErrorConstants.INTERNAL_SERVER_ERROR,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
		String responseByScript = serviceCall.getCall(uri, httpEntity);

		JSONObject jsonObject = new JSONObject(responseByScript);

		JSONArray resultJsonArray = jsonObject.getJSONObject(AnukyaConstants.YAHOO_FINANCE_QUOTE_RESPONSE)
				.getJSONArray(AnukyaConstants.YAHOO_FINANCE_QUOTE_RESULT);

		if (resultJsonArray.isEmpty()) {
			throw new AnukyaException("Service call error", null, AnukyaErrorConstants.NO_CONTENT,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		JSONObject scriptJsonObject = resultJsonArray.getJSONObject(0);

		String scriptName = scriptJsonObject.getString(AnukyaConstants.YAHOO_FINANCE_QUOTE_LONG_NAME);
		String previousClose = String.format(AnukyaConstants.FLOAT_2_DECIMAL,
				scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_REGULAR_MARKET_PREVIOUS_CLOSE));
		String openPrice = String.format(AnukyaConstants.FLOAT_2_DECIMAL,
				scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_REGULAR_MARKET_OPEN));
		String currentMarketPrice = String.format(AnukyaConstants.FLOAT_2_DECIMAL,
				scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_REGULAR_MARKET_PRICE));
		String currentMarketPriceChange = String.format(AnukyaConstants.FLOAT_2_DECIMAL,
				scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_REGULAR_MARKET_CHANGE));
		String currentMarketPriceChangePercentage = String.format(AnukyaConstants.FLOAT_2_DECIMAL,
				scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_REGULAR_MARKET_CHANGE_PERCENTAGE));
		String fiftyTwoWeekHigh = String.format(AnukyaConstants.FLOAT_2_DECIMAL,
				scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_FIFTY_TWO_WEEK_HIGH));
		String fiftyTwoWeekLow = String.format(AnukyaConstants.FLOAT_2_DECIMAL,
				scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_FIFTY_TWO_WEEK_LOW));
		String dailyHigh = String.format(AnukyaConstants.FLOAT_2_DECIMAL,
				scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_REGULAR_MARKET_DAY_HIGH));
		String dailyLow = String.format(AnukyaConstants.FLOAT_2_DECIMAL,
				scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_REGULAR_MARKET_DAY_LOW));

		bhaarathaSharesOneShareDetails.setScriptName(scriptName);
		bhaarathaSharesOneShareDetails.setStockExchange(stockExchange);
		bhaarathaSharesOneShareDetails.setPreviousClose(previousClose);
		bhaarathaSharesOneShareDetails.setOpenPrice(openPrice);
		bhaarathaSharesOneShareDetails.setCurrentMarketPrice(currentMarketPrice);
		bhaarathaSharesOneShareDetails.setCurrentMarketPriceChange(currentMarketPriceChange);
		bhaarathaSharesOneShareDetails.setCurrentMarketPriceChangePercentage(currentMarketPriceChangePercentage);
		bhaarathaSharesOneShareDetails.setFiftyTwoWeekHigh(fiftyTwoWeekHigh);
		bhaarathaSharesOneShareDetails.setFiftyTwoWeekLow(fiftyTwoWeekLow);
		bhaarathaSharesOneShareDetails.setDailyHigh(dailyHigh);
		bhaarathaSharesOneShareDetails.setDailyLow(dailyLow);

		return bhaarathaSharesOneShareDetails;
	}

	public BigDecimal getYahooFinanceTtmDividend(String scriptCode) throws AnukyaException {

		URI uri;
		try {
			uri = new URI(apiYahooFinanceDividendYield.replace(AnukyaConstants.API_SCRIPT_CODE,
					URLEncoder.encode(scriptCode, "UTF-8")));
		} catch (UnsupportedEncodingException | URISyntaxException e) {

			throw new AnukyaException("URI building error", null, AnukyaErrorConstants.INTERNAL_SERVER_ERROR,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
		String responseByScript = serviceCall.getCall(uri, null);

		JSONObject jsonObject = new JSONObject(responseByScript);

		JSONArray resultJsonArray = jsonObject.getJSONObject(AnukyaConstants.YAHOO_FINANCE_DIVIDEND_CHART)
				.getJSONArray(AnukyaConstants.YAHOO_FINANCE_DIVIDEND_RESULT);

		JSONObject dividendJsonObject = new JSONObject();
		if (resultJsonArray.getJSONObject(0).has(AnukyaConstants.YAHOO_FINANCE_DIVIDEND_EVENTS)
				&& resultJsonArray.getJSONObject(0).getJSONObject(AnukyaConstants.YAHOO_FINANCE_DIVIDEND_EVENTS)
						.has(AnukyaConstants.YAHOO_FINANCE_DIVIDEND_DIVIDENDS)) {

			dividendJsonObject = resultJsonArray.getJSONObject(0)
					.getJSONObject(AnukyaConstants.YAHOO_FINANCE_DIVIDEND_EVENTS)
					.getJSONObject(AnukyaConstants.YAHOO_FINANCE_DIVIDEND_DIVIDENDS);
		}

		JSONArray jsonArray = new JSONArray();
		if (!dividendJsonObject.isEmpty()) {
			jsonArray = dividendJsonObject.toJSONArray(dividendJsonObject.names());
		}

		Date today = new Date();
		BigDecimal ttmDividend = new BigDecimal(AnukyaConstants.NUMBER_0);

		for (int i = jsonArray.length() - 1; i >= 0; i--) {

			long epoch = jsonArray.getJSONObject(i).getLong(AnukyaConstants.YAHOO_FINANCE_DIVIDEND_DATE);
			Date exDividendDate = new Date(epoch * 1000);

			long days = (today.getTime() - exDividendDate.getTime()) / (1000 * 60 * 60 * 24);

			if (days < 365) {
				Double dividendAmount = jsonArray.getJSONObject(i)
						.getDouble(AnukyaConstants.YAHOO_FINANCE_DIVIDEND_AMOUNT);

				ttmDividend = ttmDividend.add(BigDecimal.valueOf(dividendAmount));
			} else {
				break;
			}
		}

		return ttmDividend;
	}

}
