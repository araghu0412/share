package com.moneymanager.anukya.utils;

import java.math.BigDecimal;
import java.math.MathContext;

import org.springframework.stereotype.Component;

@Component
public class UnitedStatesOfAmericaExpenditureUtils {

	public BigDecimal calculateCommission() {

		return new BigDecimal(AnukyaConstants.NUMBER_0);
	}

	public BigDecimal calculateTransactionFees(Long totalScriptCount) {

		return new BigDecimal(UnitedStatesOfAmericaPlatformConstants.TRANSACTION_FEES)
				.divide(new BigDecimal(totalScriptCount), MathContext.DECIMAL128);
	}

	public BigDecimal getOtherFees() {

		return new BigDecimal(AnukyaConstants.NUMBER_0);
	}

}
