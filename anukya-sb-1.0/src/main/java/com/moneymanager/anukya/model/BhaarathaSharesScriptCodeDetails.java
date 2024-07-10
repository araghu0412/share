package com.moneymanager.anukya.model;

import lombok.Data;

@Data
public class BhaarathaSharesScriptCodeDetails {

	private String stockExchange;
	private String bseCode;
	private String nseCode;
	private String moneycontrolCode;
	private String yahooBseCode;
	private String yahooNseCode;

	public BhaarathaSharesScriptCodeDetails(String stockExchange, String bseCode, String nseCode, String moneycontrolCode, String yahooBseCode, String yahooNseCode) {
		this.stockExchange = stockExchange;
		this.bseCode = bseCode;
		this.nseCode = nseCode;
		this.moneycontrolCode = moneycontrolCode;
		this.yahooBseCode = yahooBseCode;
		this.yahooNseCode = yahooNseCode;
	}
}
