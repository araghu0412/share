package com.moneymanager.anukya.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BhaarathaSharesOneShareDetailsResponse extends Trace {

	private String bseCode;
	private String nseCode;
	private String moneycontrolCode;
	private BhaarathaSharesResponse bhaarathaSharesResponse;
	private BhaarathaSharesOneShareDetails bseOneShareDetails;
	private BhaarathaSharesOneShareDetails nseOneShareDetails;
}
