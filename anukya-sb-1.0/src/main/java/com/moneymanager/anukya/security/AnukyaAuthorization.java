package com.moneymanager.anukya.security;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.ErrorDetails;
import com.moneymanager.anukya.model.TokenDetails;
import com.moneymanager.anukya.utils.AnukyaConstants;
import com.moneymanager.anukya.utils.AnukyaDataUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class AnukyaAuthorization {

	@Value("${DATABASE.BASE.LOCATION}")
	private String databaseBaseLocation;

	@Autowired
	private AnukyaDataUtils anukyaDataUtils;

	public boolean validateToken(HttpServletRequest httpRequest) throws AnukyaException {

		String loggedInUserEmailId = httpRequest.getHeader(AnukyaConstants.LOGGED_IN_USER_EMAIL_ID);
		String token = httpRequest.getHeader(AnukyaConstants.AUTHORIZATION);

		StringBuilder checkInput = new StringBuilder();
		if (null == loggedInUserEmailId || loggedInUserEmailId.isEmpty()) {
			checkInput.append("Email is required. ");
		}

		if (null == token || token.isEmpty()) {
			checkInput.append("Token is required. ");
		}

		if (!checkInput.toString().isEmpty()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception while validating token -> ");
			logMessage.append("Message: Invalid credentials | ");
			logMessage.append("Reason: " + checkInput.toString());
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.SE_0003);
			errorDetails.setErrorMessage(checkInput.toString());

			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while validating token", errorDetailsList,
					AnukyaErrorConstants.UNAUTHORIZED, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		ObjectMapper mapper = new ObjectMapper();

		File tokenDetailsFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + loggedInUserEmailId
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USER_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USER_AUTHENTICATION_JSON);

		try {
			String jsonString = FileUtils.readFileToString(tokenDetailsFile, StandardCharsets.UTF_8);

			TokenDetails tokenDetails = mapper.readValue(jsonString, TokenDetails.class);

			if (tokenDetails.getAccessToken().isEmpty()) {
				StringBuilder logMessage = new StringBuilder();
				logMessage.append("Exception while validating token -> ");
				logMessage.append("Message: User is not authorized | ");
				logMessage.append("Reason: Token is empty");
				log.error(logMessage.toString());

				List<ErrorDetails> errorDetailsList = new ArrayList<>();

				ErrorDetails errorDetails = new ErrorDetails();
				errorDetails.setErrorCode(AnukyaErrorConstants.SE_0003);
				errorDetails.setErrorMessage("User is not authorized");

				errorDetailsList.add(errorDetails);

				throw new AnukyaException("Error while validating token", errorDetailsList,
						AnukyaErrorConstants.UNAUTHORIZED, ThreadContext.get(AnukyaConstants.TRACE_ID));
			}

			Long checkExperationTime = (System.currentTimeMillis()
					- Long.parseLong(tokenDetails.getTokenGeneratedTime())) / 1000;

			if (checkExperationTime > Long.parseLong(tokenDetails.getAccessTokenExpiresIn())) {
				// Reset token details
				anukyaDataUtils.createOrUpdateFile(tokenDetailsFile, new TokenDetails());

				StringBuilder logMessage = new StringBuilder();
				logMessage.append("Exception while validating token -> ");
				logMessage.append("Message: User is not authorized | ");
				logMessage.append("Reason: Token is expired");
				log.error(logMessage.toString());

				List<ErrorDetails> errorDetailsList = new ArrayList<>();

				ErrorDetails errorDetails = new ErrorDetails();
				errorDetails.setErrorCode(AnukyaErrorConstants.SE_0003);
				errorDetails.setErrorMessage("User is not authorized");

				errorDetailsList.add(errorDetails);

				throw new AnukyaException("Error while validating token", errorDetailsList,
						AnukyaErrorConstants.UNAUTHORIZED, ThreadContext.get(AnukyaConstants.TRACE_ID));
			}

			if (!token.equals(tokenDetails.getTokenType() + AnukyaConstants.SPACE + tokenDetails.getAccessToken())) {
				StringBuilder logMessage = new StringBuilder();
				logMessage.append("Exception while validating token -> ");
				logMessage.append("Message: User is not authorized | ");
				logMessage.append("Reason: Invalid token");
				log.error(logMessage.toString());

				List<ErrorDetails> errorDetailsList = new ArrayList<>();

				ErrorDetails errorDetails = new ErrorDetails();
				errorDetails.setErrorCode(AnukyaErrorConstants.SE_0003);
				errorDetails.setErrorMessage("User is not authorized");

				errorDetailsList.add(errorDetails);

				throw new AnukyaException("Error while validating token", errorDetailsList,
						AnukyaErrorConstants.UNAUTHORIZED, ThreadContext.get(AnukyaConstants.TRACE_ID));
			}
		} catch (IOException e) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception while validating token -> ");
			logMessage.append("Message: " + e.getMessage() + " | ");
			logMessage.append("Reason: Reading file error");
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.FE_0001);
			errorDetails.setErrorMessage("Reading File error");

			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while validating token", errorDetailsList,
					AnukyaErrorConstants.INTERNAL_SERVER_ERROR, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		return true;
	}
}
