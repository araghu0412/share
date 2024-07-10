package com.moneymanager.anukya.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;

import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;

@Component
public class AnukyaUtils {

	public String convertDateToString(Date date, String strDateFormat) {

		DateFormat dateFormat = new SimpleDateFormat(strDateFormat);

		return dateFormat.format(date).toUpperCase();
	}

	public boolean isValidEmailId(String str) {
		return str.matches("^[a-zA-Z]+[a-zA-Z0-9+._]*[a-zA-Z0-9]+@+[a-zA-Z]+(\\.)+[a-zA-Z]*[a-zA-Z]");
	}

	public boolean containsDigit(String str) {
		return str.matches(".*\\d.*");
	}

	public boolean containsOnlyDigits(String str) {
		return str.matches("\\d+");
	}

	public boolean isWholeNumber(int firstValue, int secondValue) {
		return (((double) firstValue / secondValue) % 1) == 0;
	}

	public boolean isValidDateFormat(String date) {
		DateFormat dateFormat = new SimpleDateFormat(AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		try {
			dateFormat.parse(date);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public boolean isNumber(String value) {
		try {
			Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public Date convertStringToDate(String dateInString, String dateFormat) throws AnukyaException {

		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = formatter.parse(dateInString);
		} catch (Exception e) {
			throw new AnukyaException("Error while converting String to Date", null, AnukyaErrorConstants.BAD_REQUEST,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		return date;
	}

	public String generateId() {
		return String.valueOf(System.nanoTime());
	}

	public BigDecimal calculateHighLow(BigDecimal low, BigDecimal high, BigDecimal current) {

		BigDecimal value;
		try {
			value = current.subtract(low).divide(high.subtract(low), MathContext.DECIMAL128)
					.multiply(new BigDecimal(AnukyaConstants.NUMBER_100));
		} catch (ArithmeticException ae) {
			return null;
		}

		if (value.compareTo(new BigDecimal(AnukyaConstants.NUMBER_0)) == 0) {
			return null;
		}

		if (value.compareTo(new BigDecimal(AnukyaConstants.NUMBER_0)) < 0) {
			return new BigDecimal(AnukyaConstants.NUMBER_0);
		} else if (value.compareTo(new BigDecimal(AnukyaConstants.NUMBER_100)) > 0) {
			return new BigDecimal(AnukyaConstants.NUMBER_100);
		}

		return value;
	}
}
