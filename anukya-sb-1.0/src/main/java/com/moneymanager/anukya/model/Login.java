package com.moneymanager.anukya.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Login extends CommonResponse {

	private UserDetails userDetails;
	private TokenDetails tokenDetails;
}
