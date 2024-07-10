package com.moneymanager.anukya.model;

import lombok.Data;

@Data
public class BhaarathaShares {

	private String id = "";
	private String stockExchange = "";
	private String bseScriptCode = "";
	private String nseScriptCode = "";
	private String yahooBseScriptCode = "";
	private String yahooNseScriptCode = "";
	private String isinCode = "";
	private String moneycontrolCode = "";
	private String scriptName = "";
	private String category = "";
	private String sector = "";
	private String industry = "";
	private boolean sharesSplitSelected;
	private String sharesSplitRatio = "";
	private String sharesSplitDate = "";
	private String sharesSplit = "";
	private String sharesSplitDisplay = "";
	private boolean sharesBonusSelected;
	private String sharesBonusRatio = "";
	private String sharesBonusDate = "";
	private String sharesBonus = "";
	private String sharesBonusDisplay = "";
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
	private String sellDate = "";
	private String sellQuantity = "0";
	private String sellPrice = "0.00";
	private String sellGrossTotal = "0.00";
	private String sellBrokerage = "0.00";
	private String sellSTT = "0.00";
	private String sellExpenditure = "0.00";
	private String sellNonExpenditure = "0.00";
	private String sellTotal = "0.00";
	private String sellNetPricePerUnit = "0.00";
	private boolean shortTermInvestment;
	private boolean shortTerm;
	private String grossProfitLoss = "0.00";
	private String grossProfitLossPercentage = "0.00";
	private String totalProfitLoss = "0.00";
	private String totalProfitLossPercentage = "0.00";
	private String netProfitLossPerUnit = "0.00";
	private String createdBy = "";
	private String createdDate = "";
	private String lastUpdatedBy = "";
	private String lastUpdatedDate = "";

}
