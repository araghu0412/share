package com.moneymanager.anukya.model;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PreData extends Trace {

	private Map<String, CountryDetails> countryDetailsMap;
	private Map<String, ServicesOfferedDetails> servicesOfferedDetailsMap;
	private Map<String, CountriesAndServicesOffered> countriesAndServicesOfferedDetailsMap;
	private Map<String, Gender> genderDetailsMap;
	private Map<String, SubServicesDetails> subServicesDetailsMap;
	private Map<String, SubServicesByServiceCodeDetails> subServicesByServiceCodeDetailsMap;
}
