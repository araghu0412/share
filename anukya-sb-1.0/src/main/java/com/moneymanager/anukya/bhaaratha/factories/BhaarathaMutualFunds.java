package com.moneymanager.anukya.bhaaratha.factories;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.ThreadContext;

import com.moneymanager.anukya.bhaaratha.mf.AbstractBhaarathaMutualFunds;
import com.moneymanager.anukya.bhaaratha.mf.BhaarathaBoughtMutualFunds;
import com.moneymanager.anukya.bhaaratha.mf.BhaarathaSoldMutualFunds;
import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.ErrorDetails;
import com.moneymanager.anukya.utils.AnukyaConstants;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BhaarathaMutualFunds {

	public AbstractBhaarathaMutualFunds getBhaarathaMutualFunds(String type) throws AnukyaException {
		if (type.equals(AnukyaConstants.BOUGHT_CONSTANT)) {
			return new BhaarathaBoughtMutualFunds();
		} else if (type.equals(AnukyaConstants.SOLD_CONSTANT)) {
			return new BhaarathaSoldMutualFunds();
		}

		StringBuilder logMessage = new StringBuilder();
		logMessage.append("Exception occured while creating Bhaaratha Mutual Funds Factory -> ");
		logMessage.append("Message: Unable to create Bhaaratha Mutual Funds Factory | ");
		logMessage.append("Reason: Internal error");
		log.error(logMessage.toString());

		List<ErrorDetails> errorDetailsList = new ArrayList<>();
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setErrorCode(AnukyaErrorConstants.BHA_0001);
		errorDetails.setErrorMessage("Error in creating Bhaaratha Mutual Funds");
		throw new AnukyaException("Error while creating Bhaaratha Mutual Funds factory", errorDetailsList,
				AnukyaErrorConstants.INTERNAL_SERVER_ERROR, ThreadContext.get(AnukyaConstants.TRACE_ID));
	}
}
