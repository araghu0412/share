package com.moneymanager.anukya.bhaaratha.factories;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moneymanager.anukya.bhaaratha.eq.AbstractBhaarathaShares;
import com.moneymanager.anukya.bhaaratha.eq.BhaarathaBoughtShares;
import com.moneymanager.anukya.bhaaratha.eq.BhaarathaSoldShares;
import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.ErrorDetails;
import com.moneymanager.anukya.utils.AnukyaConstants;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class BhaarathaSharesFactory {

	@Autowired
	private BhaarathaBoughtShares bhaarathaBoughtShares;

	@Autowired
	private BhaarathaSoldShares bhaarathaSoldShares;

	public AbstractBhaarathaShares getBhaarathaShares(String type) throws AnukyaException {
		if (type.equals(AnukyaConstants.BOUGHT_CONSTANT)) {
			return bhaarathaBoughtShares;
		} else if (type.equals(AnukyaConstants.SOLD_CONSTANT)) {
			return bhaarathaSoldShares;
		}

		StringBuilder logMessage = new StringBuilder();
		logMessage.append("Exception occured while creating Bhaaratha Shares Factory -> ");
		logMessage.append("Message: Unable to create Bhaaratha Shares Factory | ");
		logMessage.append("Reason: Internal error");
		log.error(logMessage.toString());

		List<ErrorDetails> errorDetailsList = new ArrayList<>();
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setErrorCode(AnukyaErrorConstants.BHA_0001);
		errorDetails.setErrorMessage("Error in creating Bhaaratha Shares");
		throw new AnukyaException("Error while creating Bhaaratha Shares factory", errorDetailsList,
				AnukyaErrorConstants.INTERNAL_SERVER_ERROR, ThreadContext.get(AnukyaConstants.TRACE_ID));
	}
}
