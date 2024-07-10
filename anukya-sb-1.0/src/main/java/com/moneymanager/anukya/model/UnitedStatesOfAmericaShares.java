package com.moneymanager.anukya.model;

import lombok.Data;

@Data
public class UnitedStatesOfAmericaShares {

	private String id = "";
	private String scriptName = "";
	private String yahooCode = "";
	private String isinCode = "";
	private String cusip = "";
	private String category = "";
	private String sector = "";
	private String industry = "";
	private boolean shortTermInvestment = false;
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
	private String purchaseCommission = "0.00";
	private String purchaseTransactionFees = "0.00";
	private String purchaseOtherFees = "0.00";
	private String purchaseTotal = "0.00";
	private String purchaseNetPricePerUnit = "0.00";
	private String sellDate = "";
	private String sellQuantity = "0";
	private String sellPrice = "0.00";
	private String sellGrossTotal = "0.00";
	private String sellCommission = "0.00";
	private String sellTransactionFees = "0.00";
	private String sellOtherFees = "0.00";
	private String sellTotal = "0.00";
	private String sellNetPricePerUnit = "0.00";
	private String grossProfitLoss = "0.00";
	private String grossProfitLossPercentage = "0.00";
	private String totalProfitLoss = "0.00";
	private String totalProfitLossPercentage = "0.00";
	private String netProfitLossPerUnit = "0.00";
	private boolean shortTerm = false;
	private String createdBy = "";
	private String createdDate = "";
	private String lastUpdatedBy = "";
	private String lastUpdatedDate = "";

}
