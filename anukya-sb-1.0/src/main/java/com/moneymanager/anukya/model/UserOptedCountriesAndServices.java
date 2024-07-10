package com.moneymanager.anukya.model;

import java.util.List;

import lombok.Data;

@Data
public class UserOptedCountriesAndServices {

	private List<String> userOptedCountryIdsList;
	private List<UserOptedServices> userOptedServicesList;
	private boolean confirmed;
}
