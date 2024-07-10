package com.moneymanager.anukya.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BhaarathaSharesInvestmentResearchResponse extends Trace {

	List<BhaarathaSharesInvestmentResearch> bhaarathaSharesInvestmentResearchList = new ArrayList<>();
}
