package com.moneymanager.anukya.model;

import java.util.List;

import lombok.Data;

@Data
public class CountriesAndServicesOffered {

	private String countryId;
	private List<String> servicesOfferedDetailsIdsList;
}
