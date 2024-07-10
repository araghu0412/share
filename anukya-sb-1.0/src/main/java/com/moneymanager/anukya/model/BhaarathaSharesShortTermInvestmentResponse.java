package com.moneymanager.anukya.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BhaarathaSharesShortTermInvestmentResponse extends Trace {

	List<BhaarathaSharesShortTermInvestment> shortTermInvestmentScriptsList;
	BhaarathaSharesShortTermInvestment shortTermInvestmentTotal;

}
