package com.moneymanager.anukya.model;

import java.util.List;

import lombok.Data;

@Data
public class AddUnitedStatesOfAmericaSharesSplit {

	List<UnitedStatesOfAmericaShares> consolidatedList;
	List<UnitedStatesOfAmericaShares> nonConsolidatedList;
	List<UnitedStatesOfAmericaShares> singleScriptList;
}
