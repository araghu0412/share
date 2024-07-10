package com.moneymanager.anukya.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TokenDetails extends Trace {

	private String tokenType = "";
	private String accessToken = "";
	private String refreshToken = "";
	private String tokenGeneratedTime = "";
	private String accessTokenExpiresIn = "";
	private String refreshTokenExpiresIn = "";
}
