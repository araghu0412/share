package com.moneymanager.anukya.model;

import lombok.Data;

@Data
public class BhaarathaSharesShortTermInvestment {

	private String id = "";
	private String shareName = "";
	private String stockExchange = "";
	private String bseCode = "";
	private String nseCode = "";
	private String yahooBseCode = "";
	private String yahooNseCode = "";
	private String moneycontrolCode = "";
	private String purchaseDate = "";
	private String purchaseQuantity = "0";
	private String purchasePrice = "0.00";
	private String purchaseGrossTotal = "0.00";
	private String purchaseBrokerage = "0.00";
	private String purchaseSTT = "0.00";
	private String purchaseExpenditure = "0.00";
	private String purchaseNonExpenditure = "0.00";
	private String purchaseTotal = "0.00";
	private String purchaseNetPricePerUnit = "0.00";

	private String sellQuantity = "0";
	private String sellPrice = "0.00";
	private String sellGrossTotal = "0.00";
	private String sellBrokerage = "0.00";
	private String sellSTT = "0.00";
	private String sellExpenditure = "0.00";
	private String sellNonExpenditure = "0.00";
	private String sellTotal = "0.00";
	private String sellNetPricePerUnit = "0.00";

	private String profitLoss = "0.00";
	private String profitLossPercentage = "0.00";

	private String createdBy = "";
	private String createdDate = "";
	private String lastUpdatedBy = "";
	private String lastUpdatedDate = "";

}
