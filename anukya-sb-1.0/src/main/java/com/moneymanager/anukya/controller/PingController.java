package com.moneymanager.anukya.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/ping")
public class PingController {

	@Value("${APP.VERSION}")
	private String appVersion;

	@Value("${SPRING.ACTIVE.PROFILE}")
	private String springActiveProfile;

	@GetMapping(value = "/app-working")
	public String ping() {
		StringBuilder sb = new StringBuilder("Hello! Welcome to Money Manager\n");

		sb.append("App Version: " + appVersion + "\n");
		sb.append("Active profile: " + springActiveProfile);

		return sb.toString();
	}

	@GetMapping(value = "/app-details")
	public String getAppDetails() {

		StringBuilder appDetails = new StringBuilder();

		appDetails.append("ANUKYA (" + springActiveProfile + "): " + "v" + appVersion);

		return appDetails.toString();
	}
}
