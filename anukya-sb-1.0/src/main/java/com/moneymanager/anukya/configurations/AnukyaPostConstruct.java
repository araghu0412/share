package com.moneymanager.anukya.configurations;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moneymanager.anukya.model.AnukyaInitialData;

@Component
public class AnukyaPostConstruct {

	@Autowired
	private AnukyaPostConstructUtils anukyaPostConstructUtils;

	private AnukyaInitialData anukyaInitialData;

	public AnukyaInitialData getAnukyaInitialData() {
		return anukyaInitialData;
	}

	public void setAnukyaInitialData(AnukyaInitialData anukyaInitialData) {
		this.anukyaInitialData = anukyaInitialData;
	}

	@PostConstruct
	public void init() throws IOException {
		setAnukyaInitialData(anukyaPostConstructUtils.getAnukyaInitialData());
	}
}
