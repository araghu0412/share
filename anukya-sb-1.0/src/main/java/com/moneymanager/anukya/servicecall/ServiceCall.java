package com.moneymanager.anukya.servicecall;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.ErrorDetails;
import com.moneymanager.anukya.utils.AnukyaConstants;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ServiceCall {

	public String getCall(URI uri, HttpEntity<String> httpEntity) throws AnukyaException {
		log.info("Inside Service call and url is... " + uri.toString());

		RestTemplate restTemplate = new RestTemplate();

		try {
			return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class).getBody();
		} catch (Exception e) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while performing service call -> ");
			logMessage.append("Message: " + e.getMessage());
			logMessage.append("Reason: Internal error");
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();
			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.SC_0001);
			errorDetails.setErrorMessage("Service call error");
			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Service call error", errorDetailsList,
					AnukyaErrorConstants.INTERNAL_SERVER_ERROR, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}
}
