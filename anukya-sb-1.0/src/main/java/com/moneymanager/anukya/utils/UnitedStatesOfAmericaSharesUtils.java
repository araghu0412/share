package com.moneymanager.anukya.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaShares;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesLastTradingPrice;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesShortTermInvestment;
import com.moneymanager.anukya.servicecall.UnitedStatesOfAmericaSharesServiceCall;

@Component
public class UnitedStatesOfAmericaSharesUtils {

	@Autowired
	private UnitedStatesOfAmericaSharesServiceCall unitedStatesOfAmericaSharesServiceCall;

	@Autowired
	private AnukyaUtils anukyaUtils;

	public Map<String, UnitedStatesOfAmericaSharesLastTradingPrice> getUnitedStatesOfAmericaSharesLastTradingPriceMap(
			List<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesList) throws AnukyaException {

		Map<String, UnitedStatesOfAmericaSharesLastTradingPrice> latestTradingDetailsMap = new HashMap<>();

		for (UnitedStatesOfAmericaShares unitedStatesOfAmericaShares : unitedStatesOfAmericaSharesList) {
			if (!latestTradingDetailsMap.containsKey(unitedStatesOfAmericaShares.getYahooCode())) {
				latestTradingDetailsMap.put(unitedStatesOfAmericaShares.getYahooCode(),
						unitedStatesOfAmericaSharesServiceCall.getUnitedStatesOfAmericaSharesLastTradingPrice(
								unitedStatesOfAmericaShares.getYahooCode()));
			}
		}

		return latestTradingDetailsMap;
	}

	public Map<String, UnitedStatesOfAmericaSharesLastTradingPrice> getUnitedStatesOfAmericaShortTermInvestmentLastTradingPriceMap(
			List<UnitedStatesOfAmericaSharesShortTermInvestment> unitedStatesOfAmericaSharesShortTermInvestmentList)
			throws AnukyaException {

		Map<String, UnitedStatesOfAmericaSharesLastTradingPrice> latestTradingDetailsMap = new HashMap<>();

		for (UnitedStatesOfAmericaSharesShortTermInvestment unitedStatesOfAmericaSharesShortTermInvestment : unitedStatesOfAmericaSharesShortTermInvestmentList) {
			if (!latestTradingDetailsMap.containsKey(unitedStatesOfAmericaSharesShortTermInvestment.getYahooCode())) {
				latestTradingDetailsMap.put(unitedStatesOfAmericaSharesShortTermInvestment.getYahooCode(),
						unitedStatesOfAmericaSharesServiceCall.getUnitedStatesOfAmericaSharesLastTradingPrice(
								unitedStatesOfAmericaSharesShortTermInvestment.getYahooCode()));
			}
		}

		return latestTradingDetailsMap;
	}

	public boolean isShortTerm(String purchaseDateInString, String sellingDateInString) throws AnukyaException {

		Date purchaseDate = anukyaUtils.convertStringToDate(purchaseDateInString,
				AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);
		Date sellingDate = anukyaUtils.convertStringToDate(sellingDateInString,
				AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		long days = (sellingDate.getTime() - purchaseDate.getTime()) / (1000 * 60 * 60 * 24);

		return days < 365;
	}

}
