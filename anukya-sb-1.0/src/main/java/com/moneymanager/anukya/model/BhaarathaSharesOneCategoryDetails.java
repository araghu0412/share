package com.moneymanager.anukya.model;

import lombok.Data;

@Data
public class BhaarathaSharesOneCategoryDetails {

	private String scriptName = "";
	private String bseCode = "";
	private String nseCode = "";
	private String moneycontrolCode = "";
	private String investedValue = "0.00";
	private String investedValuePercentage = "0.00";
	private String currentValue = "0.00";
	private String currentValuePercentage = "0.00";
	private String profitLoss = "0.00";
	private String profitLossPercentage = "0.00";
}
