package com.moneymanager.anukya.model;

import lombok.Data;

@Data
public class UnitedStatesOfAmericaSharesShortTermInvestment {

	private String id = "";
	private String scriptName = "";
	private String yahooCode = "";
	private String purchaseDate = "";
	private String purchaseQuantity = "0";
	private String purchasePrice = "0.00";
	private String purchaseGrossTotal = "0.00";
	private String purchaseCommission = "0.00";
	private String purchaseTransactionFees = "0.00";
	private String purchaseOtherFees = "0.00";
	private String purchaseTotal = "0.00";
	private String purchaseNetPricePerUnit = "0.00";

	private String sellQuantity = "0";
	private String sellPrice = "0.00";
	private String sellGrossTotal = "0.00";
	private String sellCommission = "0.00";
	private String sellTransactionFees = "0.00";
	private String sellOtherFees = "0.00";
	private String sellTotal = "0.00";
	private String sellNetPricePerUnit = "0.00";

	private String profitLoss = "0.00";
	private String profitLossPercentage = "0.00";

	private String createdBy = "";
	private String createdDate = "";
	private String lastUpdatedBy = "";
	private String lastUpdatedDate = "";

}
