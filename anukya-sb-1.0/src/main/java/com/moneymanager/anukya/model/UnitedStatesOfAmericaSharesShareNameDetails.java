package com.moneymanager.anukya.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UnitedStatesOfAmericaSharesShareNameDetails extends Trace {

	private String scriptCode;
	private String scriptName;
}
