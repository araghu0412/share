package com.moneymanager.anukya.unitedstatesofamerica.factories;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.ErrorDetails;
import com.moneymanager.anukya.unitedstatesofamerica.eq.AbstractUnitedStatesOfAmericaShares;
import com.moneymanager.anukya.unitedstatesofamerica.eq.UnitedStatesOfAmericaBoughtShares;
import com.moneymanager.anukya.unitedstatesofamerica.eq.UnitedStatesOfAmericaSoldShares;
import com.moneymanager.anukya.utils.AnukyaConstants;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class UnitedStatesOfAmericaSharesFactory {

	@Autowired
	private UnitedStatesOfAmericaBoughtShares unitedStatesOfAmericaBoughtShares;

	@Autowired
	private UnitedStatesOfAmericaSoldShares unitedStatesOfAmericaSoldShares;

	public AbstractUnitedStatesOfAmericaShares getUnitedStatesOfAmericaShares(String type) throws AnukyaException {
		if (type.equals(AnukyaConstants.BOUGHT_CONSTANT)) {
			return unitedStatesOfAmericaBoughtShares;
		} else if (type.equals(AnukyaConstants.SOLD_CONSTANT)) {
			return unitedStatesOfAmericaSoldShares;
		}

		StringBuilder logMessage = new StringBuilder();
		logMessage.append("Exception occured while creating UnitedStatesOfAmerica Shares Factory -> ");
		logMessage.append("Message: Unable to create UnitedStatesOfAmerica Shares Factory | ");
		logMessage.append("Reason: Internal error");
		log.error(logMessage.toString());

		List<ErrorDetails> errorDetailsList = new ArrayList<>();
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setErrorCode(AnukyaErrorConstants.USA_0001);
		errorDetails.setErrorMessage("Error in creating UnitedStatesOfAmerica Shares");
		throw new AnukyaException("Error while creating UnitedStatesOfAmerica Shares factory", errorDetailsList,
				AnukyaErrorConstants.INTERNAL_SERVER_ERROR, ThreadContext.get(AnukyaConstants.TRACE_ID));
	}
}
