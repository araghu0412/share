package com.moneymanager.anukya.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class BhaarathaExpenditureUtils {

	public BigDecimal getBhaarathaSharesExpenditure(BigDecimal grossTotal, BigDecimal brokerage) {

		BigDecimal exchangeCharges = calculateExchangeCharges(grossTotal);
		BigDecimal sebiCharges = calculateSEBICharges(grossTotal);
		BigDecimal sellGST = calculateGST(brokerage, exchangeCharges, sebiCharges);
		BigDecimal sellStampDuty = calculateStampDuty(grossTotal);

		return exchangeCharges.add(sebiCharges).add(sellGST).add(sellStampDuty);
	}

	public BigDecimal getBhaarathaSharesNonExpenditure() {

		return new BigDecimal(AnukyaConstants.NUMBER_0);
	}

	public BigDecimal calculateSTT(BigDecimal grossTotal) {

		return grossTotal.multiply(new BigDecimal(BhaarathaDematConstants.STT)
				.divide(new BigDecimal(BhaarathaDematConstants.NUMBER_100), MathContext.DECIMAL128));
	}

	public BigDecimal calculateBrokerage(BigDecimal grossTotal, BigDecimal quantity, BigDecimal totalQuantity,
			BigDecimal totalGrossTotal) {

		if (totalGrossTotal.compareTo(new BigDecimal(AnukyaConstants.NUMBER_20000)) > 0) {
			return new BigDecimal(AnukyaConstants.NUMBER_100).divide(totalQuantity, MathContext.DECIMAL128)
					.multiply(quantity);
		}

		return grossTotal.multiply(new BigDecimal(BhaarathaDematConstants.BROKERAGE)
				.divide(new BigDecimal(BhaarathaDematConstants.NUMBER_100), MathContext.DECIMAL128));
	}

	public BigDecimal calculateDepositoryCharges(Map<String, Long> depositoryChargesScriptCountMap, String bseCode) {

		return new BigDecimal(BhaarathaDematConstants.MAX_DEPOSITORY_CHARGE)
				.divide(new BigDecimal(depositoryChargesScriptCountMap.get(bseCode)), MathContext.DECIMAL128);
	}

	private BigDecimal calculateExchangeCharges(BigDecimal grossTotal) {

		return grossTotal.multiply(new BigDecimal(BhaarathaDematConstants.EXCHANGE_CHARGE)
				.divide(new BigDecimal(BhaarathaDematConstants.NUMBER_100), MathContext.DECIMAL128));
	}

	private BigDecimal calculateSEBICharges(BigDecimal grossTotal) {

		return grossTotal.multiply(new BigDecimal(BhaarathaDematConstants.SEBI_CHARGES_NUMERATOR)
				.divide(new BigDecimal(BhaarathaDematConstants.SEBI_CHARGES_DENOMINATOR), MathContext.DECIMAL128));
	}

	private BigDecimal calculateGST(BigDecimal brokerage, BigDecimal exchangeCharges, BigDecimal sebiCharges) {

		return (brokerage.add(exchangeCharges).add(sebiCharges)).multiply(new BigDecimal(BhaarathaDematConstants.GST)
				.divide(new BigDecimal(BhaarathaDematConstants.NUMBER_100), MathContext.DECIMAL128));
	}

	private BigDecimal calculateStampDuty(BigDecimal grossTotal) {

		return grossTotal.multiply(new BigDecimal(BhaarathaDematConstants.STAMP_DUTY)
				.divide(new BigDecimal(BhaarathaDematConstants.NUMBER_100), MathContext.DECIMAL128));
	}

}
