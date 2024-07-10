package com.moneymanager.anukya.model;

import java.util.List;

import lombok.Data;

@Data
public class SubServicesByServiceCodeDetails {

	private String serviceCode;
	private List<String> subServicesDetailsList;
}
