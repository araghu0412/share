package com.moneymanager.anukya.model;

import lombok.Data;

@Data
public class UserAuthentication {

	private String accessToken;
	private String tokenGeneratedTime;
	private String tokenActiveTime;
}
