package com.moneymanager.anukya.services;

import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.model.CommonResponse;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaShares;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesAnalysis;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesInvestmentResearchResponse;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesResponse;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesShareNameDetails;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesShortTermInvestment;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesShortTermInvestmentResponse;

public interface UnitedStatesOfAmericaSharesServices {

	UnitedStatesOfAmericaSharesShareNameDetails getShareNameDetails(String scriptCode) throws AnukyaException;

	CommonResponse addShares(String userEmailId, String type, UnitedStatesOfAmericaShares unitedStatesOfAmericaShares)
			throws AnukyaException;

	UnitedStatesOfAmericaSharesResponse getUnitedStatesOfAmericaShares(String userEmailId, String type,
			boolean isNonConsolidated, String searchTerm, boolean isLongTermOnly) throws AnukyaException;

	UnitedStatesOfAmericaSharesAnalysis getCompleteAnalysis(String userEmailId) throws AnukyaException;

	UnitedStatesOfAmericaSharesAnalysis getDividendYieldAnalysis(String userEmailId) throws AnukyaException;

	UnitedStatesOfAmericaSharesInvestmentResearchResponse getInvestmentResearch(String userEmailId)
			throws AnukyaException;

	CommonResponse addUnitedStatesOfAmericaSharesShortTermInvestment(String userEmailId,
			UnitedStatesOfAmericaSharesShortTermInvestment unitedStatesOfAmericaSharesShortTermInvestment)
			throws AnukyaException;

	UnitedStatesOfAmericaSharesShortTermInvestmentResponse getShortTermInvestment(String userEmailId, String yahooCode)
			throws AnukyaException;

	CommonResponse deleteShortTermInvestment(String userEmailId, String yahooCode) throws AnukyaException;

}
