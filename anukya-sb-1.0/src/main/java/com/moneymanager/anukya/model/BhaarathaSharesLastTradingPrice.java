package com.moneymanager.anukya.model;

import lombok.Data;

@Data
public class BhaarathaSharesLastTradingPrice {

	private String scriptCode;
	private String scriptName;
	private String lastTradingPrice;
	private String stockExchange;
	private String moneycontrolAliasCode;
}
