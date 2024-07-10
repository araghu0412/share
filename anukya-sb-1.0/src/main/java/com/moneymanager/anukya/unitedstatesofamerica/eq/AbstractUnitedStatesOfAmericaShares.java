package com.moneymanager.anukya.unitedstatesofamerica.eq;

import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.model.CommonResponse;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaShares;
import com.moneymanager.anukya.model.UnitedStatesOfAmericaSharesResponse;

public abstract class AbstractUnitedStatesOfAmericaShares {

	public String userEmailId;

	public abstract CommonResponse addShares(UnitedStatesOfAmericaShares unitedStatesOfAmericaShares)
			throws AnukyaException;

	public abstract UnitedStatesOfAmericaSharesResponse getUnitedStatesOfAmericaShares(boolean isNonConsolidated,
			String searchTerm, boolean isLongTermOnly) throws AnukyaException;
}
