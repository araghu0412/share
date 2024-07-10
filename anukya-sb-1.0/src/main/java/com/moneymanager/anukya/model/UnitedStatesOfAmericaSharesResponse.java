package com.moneymanager.anukya.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UnitedStatesOfAmericaSharesResponse extends Trace {

	List<UnitedStatesOfAmericaShares> unitedStatesOfAmericaSharesList = new ArrayList<>();
	UnitedStatesOfAmericaShares unitedStatesOfAmericaSharesTotal = new UnitedStatesOfAmericaShares();
}
