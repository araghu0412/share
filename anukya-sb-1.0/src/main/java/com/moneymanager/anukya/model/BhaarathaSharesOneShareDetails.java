package com.moneymanager.anukya.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BhaarathaSharesOneShareDetails {

	private String scriptCode;
	private String scriptName;
	private String stockExchange;
	private String previousClose;
	private String openPrice;
	private String currentMarketPrice;
	private String currentMarketPriceChange;
	private String currentMarketPriceChangePercentage;
	private String fiftyTwoWeekHigh;
	private String fiftyTwoWeekLow;
	private String fiftyTwoWeekHighLowValue;
	private String dailyHigh;
	private String dailyLow;
	private String dailyHighLowValue;
}
