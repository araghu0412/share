package com.moneymanager.anukya.bhaaratha.eq;

import java.util.List;

import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.model.CommonResponse;
import com.moneymanager.anukya.model.BhaarathaShares;
import com.moneymanager.anukya.model.BhaarathaSharesResponse;

public abstract class AbstractBhaarathaShares {

	public String userEmailId;

	public abstract CommonResponse addShares(BhaarathaShares bhaarathaShares) throws AnukyaException;

	public abstract BhaarathaSharesResponse getBhaarathaShares(boolean isNonConsolidated, String searchTerm,
			boolean isLongTermOnly) throws AnukyaException;

	public abstract BhaarathaSharesResponse getOneShareDetails(List<BhaarathaShares> bhaarathaSharesList)
			throws AnukyaException;

	public abstract CommonResponse addSharesBulkUpload(BhaarathaShares bhaarathaShares) throws AnukyaException;
}
