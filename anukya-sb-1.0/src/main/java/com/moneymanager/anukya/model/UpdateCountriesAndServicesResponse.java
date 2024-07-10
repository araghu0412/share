package com.moneymanager.anukya.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateCountriesAndServicesResponse extends Trace {

	private String message;
	private UserDetails userDetails;
	private boolean confirmationRequired;
	private boolean sameData;
	private boolean dataAdded;
}
