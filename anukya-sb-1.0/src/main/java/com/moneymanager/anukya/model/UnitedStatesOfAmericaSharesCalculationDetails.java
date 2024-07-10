package com.moneymanager.anukya.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class UnitedStatesOfAmericaSharesCalculationDetails {

	Map<String, UnitedStatesOfAmericaSharesLastTradingPrice> latestTradingDetailsMap = new HashMap<>();
	Map<String, BigDecimal> totalSoldQuantityMap = new HashMap<>();
	Map<String, BigDecimal> totalSoldGrossTotalMap = new HashMap<>();
}
