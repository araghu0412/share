package com.moneymanager.anukya.model;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UnitedStatesOfAmericaSharesAnalysis extends Trace {

	private Map<String, UnitedStatesOfAmericaSharesSector> unitedStatesOfAmericaSharesSectorMap;
	private Map<String, UnitedStatesOfAmericaSharesSector> unitedStatesOfAmericaSharesSectorLongTermOnlyMap;
	private Map<String, UnitedStatesOfAmericaSharesCategory> unitedStatesOfAmericaSharesCategoryMap;
	private Map<String, UnitedStatesOfAmericaSharesCategory> unitedStatesOfAmericaSharesCategoryLongTermOnlyMap;

	private Map<String, Map<String, UnitedStatesOfAmericaSharesOneSectorDetails>> unitedStatesOfAmericaSharesOneSectorDetailsMap;
	private Map<String, Map<String, UnitedStatesOfAmericaSharesOneSectorDetails>> unitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap;
	private Map<String, Map<String, UnitedStatesOfAmericaSharesOneCategoryDetails>> unitedStatesOfAmericaSharesOneCategoryDetailsMap;
	private Map<String, Map<String, UnitedStatesOfAmericaSharesOneCategoryDetails>> unitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap;
}
