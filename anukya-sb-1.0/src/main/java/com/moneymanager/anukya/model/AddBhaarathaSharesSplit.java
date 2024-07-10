package com.moneymanager.anukya.model;

import java.util.List;

import lombok.Data;

@Data
public class AddBhaarathaSharesSplit {

	List<BhaarathaShares> consolidatedList;
	List<BhaarathaShares> nonConsolidatedList;
	List<BhaarathaShares> singleScriptList;
}
