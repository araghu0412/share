package com.moneymanager.anukya.model;

import lombok.Data;

@Data
public class BhaarathaSharesInvestmentResearch {

	private String bseCode;
	private String nseCode;
	private String moneycontrolCode;
	private String scriptName;
	private String dailyRange;
	private String fiftyTwoWeekRange;
	private String ttmDividendYield;
}
