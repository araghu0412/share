package com.moneymanager.anukya.model;

import java.util.List;

import lombok.Data;

@Data
public class CompleteUserDetails {

	private String email;
	private String password;
	private String pin;
	private String firstName;
	private String middleName;
	private String lastName;
	private String preferredName;
	private String gender;
	private List<String> userOptedCountryIdsList;
	private List<UserOptedServices> userOptedServicesList;
	private String createdBy;
	private String createdDate;
	private String lastUpdatedBy;
	private String lastUpdatedDate;
}
