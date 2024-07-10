package com.moneymanager.anukya.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UnitedStatesOfAmericaSharesShortTermInvestmentResponse extends Trace {

	List<UnitedStatesOfAmericaSharesShortTermInvestment> shortTermInvestmentScriptsList;
	UnitedStatesOfAmericaSharesShortTermInvestment shortTermInvestmentTotal;

}
