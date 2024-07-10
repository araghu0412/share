package com.moneymanager.anukya.model;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BhaarathaSharesAnalysis extends Trace {

	private Map<String, BhaarathaSharesSector> bhaarathaSharesSectorMap;
	private Map<String, BhaarathaSharesSector> bhaarathaSharesSectorLongTermOnlyMap;
	private Map<String, BhaarathaSharesCategory> bhaarathaSharesCategoryMap;
	private Map<String, BhaarathaSharesCategory> bhaarathaSharesCategoryLongTermOnlyMap;

	private Map<String, Map<String, BhaarathaSharesOneSectorDetails>> bhaarathaSharesOneSectorDetailsMap;
	private Map<String, Map<String, BhaarathaSharesOneSectorDetails>> bhaarathaSharesOneSectorDetailsLongTermOnlyMap;
	private Map<String, Map<String, BhaarathaSharesOneCategoryDetails>> bhaarathaSharesOneCategoryDetailsMap;
	private Map<String, Map<String, BhaarathaSharesOneCategoryDetails>> bhaarathaSharesOneCategoryDetailsLongTermOnlyMap;
}
