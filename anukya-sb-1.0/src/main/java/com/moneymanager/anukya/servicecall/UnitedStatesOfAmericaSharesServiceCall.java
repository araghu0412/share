package com.moneymanager.anukya.servicecall;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
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
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesLastTradingPrice;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesOneShareDetails;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesShareNameDetails;
import com.moneymanager.anukya.utils.AnukyaConstants;

@Component
public class UnitedStatesOfAmericaSharesServiceCall {

	@Value("${API.YAHOO.FINANCE.SHARE.DETAILS}")
	private String apiYahooFinanceShareDetails;

	@Value("${YAHOO.FINANCE.COOKIE}")
	private String yahooFinancenCookie;

	@Value("${API.YAHOO.FINANCE.DIVIDEND.YIELD}")
	private String apiYahooFinanceDividendYield;

	@Autowired
	private ServiceCall serviceCall;

	public UnitedStatesOfAmericaSharesShareNameDetails getUnitedStatesOfAmericaSharesShareNameDetails(String scriptCode)
			throws AnukyaException {

		UnitedStatesOfAmericaSharesShareNameDetails unitedStatesOfAmericaSharesShareNameDetails = new UnitedStatesOfAmericaSharesShareNameDetails();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(AnukyaConstants.COOKIE, yahooFinancenCookie);

		HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

		URI uri;
		try {
			uri = new URI(apiYahooFinanceShareDetails.replace(AnukyaConstants.API_SCRIPT_CODE, scriptCode));
		} catch (URISyntaxException e) {

			throw new AnukyaException("URI building error", null, AnukyaErrorConstants.INTERNAL_SERVER_ERROR,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
		String responseByScript = serviceCall.getCall(uri, httpEntity);

		JSONObject jsonObject = new JSONObject(responseByScript);

		JSONArray jsonArray = new JSONArray(jsonObject.getJSONObject(AnukyaConstants.YAHOO_FINANCE_QUOTE_RESPONSE)
				.getJSONArray(AnukyaConstants.YAHOO_FINANCE_QUOTE_RESULT));

		if (jsonArray.isEmpty()) {

			throw new AnukyaException("Service call error", null, AnukyaErrorConstants.NO_CONTENT,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		JSONObject scriptJsonObject = jsonArray.getJSONObject(0);

		unitedStatesOfAmericaSharesShareNameDetails
				.setScriptCode(scriptJsonObject.getString(AnukyaConstants.YAHOO_FINANCE_QUOTE_SYMBOL));
		unitedStatesOfAmericaSharesShareNameDetails
				.setScriptName(scriptJsonObject.getString(AnukyaConstants.YAHOO_FINANCE_QUOTE_LONG_NAME));

		return unitedStatesOfAmericaSharesShareNameDetails;
	}

	public UnitedStatesOfAmericaSharesLastTradingPrice getUnitedStatesOfAmericaSharesLastTradingPrice(String scriptCode)
			throws AnukyaException {

		UnitedStatesOfAmericaSharesLastTradingPrice unitedStatesOfAmericaSharesLastTradingPrice = new UnitedStatesOfAmericaSharesLastTradingPrice();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(AnukyaConstants.COOKIE, yahooFinancenCookie);

		HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

		URI uri;
		try {
			uri = new URI(apiYahooFinanceShareDetails.replace(AnukyaConstants.API_SCRIPT_CODE, scriptCode));
		} catch (URISyntaxException e) {

			throw new AnukyaException("URI building error", null, AnukyaErrorConstants.INTERNAL_SERVER_ERROR,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
		String responseByScript = serviceCall.getCall(uri, httpEntity);

		JSONObject jsonObject = new JSONObject(responseByScript);

		JSONArray jsonArray = new JSONArray(jsonObject.getJSONObject(AnukyaConstants.YAHOO_FINANCE_QUOTE_RESPONSE)
				.getJSONArray(AnukyaConstants.YAHOO_FINANCE_QUOTE_RESULT));

		JSONObject scriptJsonObject = jsonArray.getJSONObject(0);

		unitedStatesOfAmericaSharesLastTradingPrice
				.setScriptCode(scriptJsonObject.getString(AnukyaConstants.YAHOO_FINANCE_QUOTE_SYMBOL));
		unitedStatesOfAmericaSharesLastTradingPrice
				.setScriptName(scriptJsonObject.getString(AnukyaConstants.YAHOO_FINANCE_QUOTE_LONG_NAME));
		unitedStatesOfAmericaSharesLastTradingPrice.setLastTradingPrice(String
				.valueOf(scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_REGULAR_MARKET_PRICE)));

		return unitedStatesOfAmericaSharesLastTradingPrice;
	}

	public BigDecimal getTtmDividend(String scriptCode) throws AnukyaException {

		URI uri;
		try {
			uri = new URI(apiYahooFinanceDividendYield.replace(AnukyaConstants.API_SCRIPT_CODE, scriptCode));
		} catch (URISyntaxException e) {

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

	public UnitedStatesOfAmericaSharesOneShareDetails getOneShareDetails(String scriptCode) throws AnukyaException {

		UnitedStatesOfAmericaSharesOneShareDetails unitedStatesOfAmericaSharesOneShareDetails = new UnitedStatesOfAmericaSharesOneShareDetails();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(AnukyaConstants.COOKIE, yahooFinancenCookie);

		HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

		URI uri;
		try {
			uri = new URI(apiYahooFinanceShareDetails.replace(AnukyaConstants.API_SCRIPT_CODE, scriptCode));
		} catch (URISyntaxException e) {

			throw new AnukyaException("URI building error", null, AnukyaErrorConstants.INTERNAL_SERVER_ERROR,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
		String responseByScript = serviceCall.getCall(uri, httpEntity);

		JSONObject jsonObject = new JSONObject(responseByScript);

		JSONArray jsonArray = new JSONArray(jsonObject.getJSONObject(AnukyaConstants.YAHOO_FINANCE_QUOTE_RESPONSE)
				.getJSONArray(AnukyaConstants.YAHOO_FINANCE_QUOTE_RESULT));

		JSONObject scriptJsonObject = jsonArray.getJSONObject(0);

		unitedStatesOfAmericaSharesOneShareDetails
				.setScriptCode(scriptJsonObject.getString(AnukyaConstants.YAHOO_FINANCE_QUOTE_SYMBOL));
		unitedStatesOfAmericaSharesOneShareDetails
				.setScriptName(scriptJsonObject.getString(AnukyaConstants.YAHOO_FINANCE_QUOTE_LONG_NAME));
		unitedStatesOfAmericaSharesOneShareDetails.setPreviousClose(String.valueOf(
				scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_REGULAR_MARKET_PREVIOUS_CLOSE)));
		unitedStatesOfAmericaSharesOneShareDetails.setOpenPrice(String
				.valueOf(scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_REGULAR_MARKET_OPEN)));
		unitedStatesOfAmericaSharesOneShareDetails.setCurrentMarketPrice(String
				.valueOf(scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_REGULAR_MARKET_PRICE)));
		unitedStatesOfAmericaSharesOneShareDetails.setCurrentMarketPriceChange(String
				.valueOf(scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_REGULAR_MARKET_CHANGE)));
		unitedStatesOfAmericaSharesOneShareDetails.setCurrentMarketPriceChangePercentage(String.valueOf(
				scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_REGULAR_MARKET_CHANGE_PERCENTAGE)));
		unitedStatesOfAmericaSharesOneShareDetails.setFiftyTwoWeekHigh(String
				.valueOf(scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_FIFTY_TWO_WEEK_HIGH)));
		unitedStatesOfAmericaSharesOneShareDetails.setFiftyTwoWeekLow(
				String.valueOf(scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_FIFTY_TWO_WEEK_LOW)));
		unitedStatesOfAmericaSharesOneShareDetails.setDailyHigh(String
				.valueOf(scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_REGULAR_MARKET_DAY_HIGH)));
		unitedStatesOfAmericaSharesOneShareDetails.setDailyLow(String
				.valueOf(scriptJsonObject.getBigDecimal(AnukyaConstants.YAHOO_FINANCE_QUOTE_REGULAR_MARKET_DAY_LOW)));

		return unitedStatesOfAmericaSharesOneShareDetails;
	}

}
