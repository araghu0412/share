package com.moneymanager.anukya.data.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneymanager.anukya.configurations.AnukyaPostConstruct;
import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.AnukyaUiFieldMapping;
import com.moneymanager.anukya.exception.ErrorDetails;
import com.moneymanager.anukya.model.PreData;
import com.moneymanager.anukya.model.CompleteUserDetails;
import com.moneymanager.anukya.model.TokenDetails;
import com.moneymanager.anukya.utils.AnukyaConstants;
import com.moneymanager.anukya.utils.AnukyaDataUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class UsersData {

	@Value("${DATABASE.BASE.LOCATION}")
	private String databaseBaseLocation;

	@Autowired
	private AnukyaPostConstruct anukyaPostConstruct;

	@Autowired
	private AnukyaDataUtils anukyaDataUtils;

	public static final String READING_FILE_ERROR = "Reading File error";

	public boolean userAvailability(String userId) {

		return new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + userId.toLowerCase()).isDirectory();
	}

	public boolean isRegisterUserDetails(CompleteUserDetails completeUserDetails) {

		StringBuilder handleError = new StringBuilder();

		// Create user directory
		File file = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail().toLowerCase());

		anukyaDataUtils.createDirectory(file);

		// Create user-details directory for the user
		file = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail().toLowerCase()
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USER_DETAILS_DIRECTORY);

		anukyaDataUtils.createDirectory(file);

		// Create main directory for the user
		file = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail().toLowerCase()
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USER_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.MAIN_DIRECTORY);

		anukyaDataUtils.createDirectory(file);

		// Create user-details.json file for the user
		file = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail().toLowerCase()
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USER_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USER_DETAILS_JSON);

		try {
			anukyaDataUtils.createOrUpdateFile(file, completeUserDetails);
		} catch (AnukyaException e) {
			log.warn("File error: {}", e.getMessage());
			handleError.append("File error: " + e.getMessage());
		}

		// Create user-authentication directory for the user
		file = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail().toLowerCase()
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USER_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USER_AUTHENTICATION_JSON);

		try {
			anukyaDataUtils.createOrUpdateFile(file, new TokenDetails());
		} catch (AnukyaException e) {
			log.warn("File error: {}", e.getMessage());
			handleError.append("File error: " + e.getMessage());
		}

		return handleError.toString().isEmpty();
	}

	public boolean isRegisterUserOptedServicesSuccess(CompleteUserDetails completeUserDetails) {

		StringBuilder handleError = new StringBuilder();

		// Creating services-details directory
		File file = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail() + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SERVICES_DETAILS_DIRECTORY);

		anukyaDataUtils.createDirectory(file);

		completeUserDetails.getUserOptedServicesList().forEach(userOptedService -> {
			File countryDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
					+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail()
					+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SERVICES_DETAILS_DIRECTORY
					+ AnukyaConstants.FORWARD_SLASH + anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
							.get(userOptedService.getCountryId()).getCountryCode());

			anukyaDataUtils.createDirectory(countryDirectory);

			userOptedService.getUserOptedServiceIdsList().forEach(serviceId -> {

				File serviceDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
						+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
						+ completeUserDetails.getEmail() + AnukyaConstants.FORWARD_SLASH
						+ AnukyaConstants.SERVICES_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
						+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
								.get(userOptedService.getCountryId()).getCountryCode()
						+ AnukyaConstants.FORWARD_SLASH + anukyaPostConstruct.getAnukyaInitialData()
								.getServicesOfferedDetailsMap().get(serviceId).getServiceCode());

				anukyaDataUtils.createDirectory(serviceDirectory);

				// Creating purchase directory
				File serviceTypeDirectory = new File(
						databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SERVICES_DETAILS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
										.get(userOptedService.getCountryId()).getCountryCode()
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getServicesOfferedDetailsMap()
										.get(serviceId).getServiceCode()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.PURCHASE_DIRECTORY_CONSTANT);

				anukyaDataUtils.createDirectory(serviceTypeDirectory);

				// Creating main directory inside purchase directory
				File serviceMainDirectory = new File(
						databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SERVICES_DETAILS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
										.get(userOptedService.getCountryId()).getCountryCode()
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getServicesOfferedDetailsMap()
										.get(serviceId).getServiceCode()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.PURCHASE_DIRECTORY_CONSTANT
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.MAIN_DIRECTORY);

				anukyaDataUtils.createDirectory(serviceMainDirectory);

				// Creating purchase non consolidated json
				File serviceFile = new File(
						databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SERVICES_DETAILS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
										.get(userOptedService.getCountryId()).getCountryCode()
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getServicesOfferedDetailsMap()
										.get(serviceId).getServiceCode()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.PURCHASE_DIRECTORY_CONSTANT
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.MAIN_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);

				try {
					anukyaDataUtils.createOrUpdateFile(serviceFile, new ArrayList<>());
				} catch (AnukyaException e) {
					log.warn("File error: {}", e.getMessage());
					handleError.append("File error: " + e.getMessage());
				}

				// Create single folder in purchase directory
				File singleDirectory = new File(
						databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SERVICES_DETAILS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
										.get(userOptedService.getCountryId()).getCountryCode()
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getServicesOfferedDetailsMap()
										.get(serviceId).getServiceCode()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.PURCHASE_DIRECTORY_CONSTANT
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.MAIN_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SINGLE_DIRECTORY);

				anukyaDataUtils.createDirectory(singleDirectory);

				// Creating purchase consolidated json
				serviceFile = new File(
						databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SERVICES_DETAILS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
										.get(userOptedService.getCountryId()).getCountryCode()
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getServicesOfferedDetailsMap()
										.get(serviceId).getServiceCode()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.PURCHASE_DIRECTORY_CONSTANT
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.MAIN_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

				try {
					anukyaDataUtils.createOrUpdateFile(serviceFile, new ArrayList<>());
				} catch (AnukyaException e) {
					log.warn("File error: {}", e.getMessage());
					handleError.append("File error: " + e.getMessage());
				}

				// Creating sold directory
				serviceTypeDirectory = new File(
						databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SERVICES_DETAILS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
										.get(userOptedService.getCountryId()).getCountryCode()
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getServicesOfferedDetailsMap()
										.get(serviceId).getServiceCode()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SOLD_DIRECTORY_CONSTANT);

				anukyaDataUtils.createDirectory(serviceTypeDirectory);

				// Creating main directory inside sold directory
				serviceMainDirectory = new File(
						databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SERVICES_DETAILS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
										.get(userOptedService.getCountryId()).getCountryCode()
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getServicesOfferedDetailsMap()
										.get(serviceId).getServiceCode()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SOLD_DIRECTORY_CONSTANT
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.MAIN_DIRECTORY);

				anukyaDataUtils.createDirectory(serviceMainDirectory);

				// Creating sold non consolidated json
				serviceFile = new File(
						databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SERVICES_DETAILS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
										.get(userOptedService.getCountryId()).getCountryCode()
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getServicesOfferedDetailsMap()
										.get(serviceId).getServiceCode()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SOLD_DIRECTORY_CONSTANT
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.MAIN_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.NON_CONSOLIDATED_JSON);

				try {
					anukyaDataUtils.createOrUpdateFile(serviceFile, new ArrayList<>());
				} catch (AnukyaException e) {
					log.warn("File error: {}", e.getMessage());
					handleError.append("File error: " + e.getMessage());
				}

				// Creating sold consolidated json
				serviceFile = new File(
						databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SERVICES_DETAILS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
										.get(userOptedService.getCountryId()).getCountryCode()
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getServicesOfferedDetailsMap()
										.get(serviceId).getServiceCode()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SOLD_DIRECTORY_CONSTANT
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.MAIN_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.CONSOLIDATED_JSON);

				try {
					anukyaDataUtils.createOrUpdateFile(serviceFile, new ArrayList<>());
				} catch (AnukyaException e) {
					log.warn("File error: {}", e.getMessage());
					handleError.append("File error: " + e.getMessage());
				}

				// Create single folder in sold directory
				singleDirectory = new File(
						databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SERVICES_DETAILS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
										.get(userOptedService.getCountryId()).getCountryCode()
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getServicesOfferedDetailsMap()
										.get(serviceId).getServiceCode()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SOLD_DIRECTORY_CONSTANT
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.MAIN_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SINGLE_DIRECTORY);

				anukyaDataUtils.createDirectory(singleDirectory);

				// Creating short-term-investment directory
				File shortTermInvestmentDirectory = new File(
						databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SERVICES_DETAILS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
										.get(userOptedService.getCountryId()).getCountryCode()
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getServicesOfferedDetailsMap()
										.get(serviceId).getServiceCode()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SHORT_TERM_INVESTMENT_DIRECTORY);

				anukyaDataUtils.createDirectory(shortTermInvestmentDirectory);

				// Creating main directory inside short-term-investment directory
				File shortTermInvestmentMainDirectory = new File(
						databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SERVICES_DETAILS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
										.get(userOptedService.getCountryId()).getCountryCode()
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getServicesOfferedDetailsMap()
										.get(serviceId).getServiceCode()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SHORT_TERM_INVESTMENT_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.MAIN_DIRECTORY);

				anukyaDataUtils.createDirectory(shortTermInvestmentMainDirectory);

				// Creating Short term investment file
				File shortTermInvestmentMainFile = new File(
						databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SERVICES_DETAILS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
										.get(userOptedService.getCountryId()).getCountryCode()
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getServicesOfferedDetailsMap()
										.get(serviceId).getServiceCode()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SHORT_TERM_INVESTMENT_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.MAIN_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SHORT_TERM_INVESTMENT_JSON);

				try {
					anukyaDataUtils.createOrUpdateFile(shortTermInvestmentMainFile, new ArrayList<>());
				} catch (AnukyaException e) {
					log.warn("File error: {}", e.getMessage());
					handleError.append("File error: " + e.getMessage());
				}

				// Create single folder in Short term investment file
				singleDirectory = new File(
						databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SERVICES_DETAILS_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
										.get(userOptedService.getCountryId()).getCountryCode()
								+ AnukyaConstants.FORWARD_SLASH
								+ anukyaPostConstruct.getAnukyaInitialData().getServicesOfferedDetailsMap()
										.get(serviceId).getServiceCode()
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SHORT_TERM_INVESTMENT_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.MAIN_DIRECTORY
								+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SINGLE_DIRECTORY);

				anukyaDataUtils.createDirectory(singleDirectory);

			});
		});

		return handleError.toString().isEmpty();
	}

	public void removeUser(String str) throws AnukyaException {

		try {
			FileUtils.deleteDirectory(new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
					+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + str.toLowerCase()));
		} catch (IOException e) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while removing user -> ");
			logMessage.append("Message: Something when wrong | ");
			logMessage.append("Reason: Internal error");
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.FE_0002);
			errorDetails.setErrorMessage(
					"IMPORTANT!!! Something has gone wrong. Please contact the support team to resolve the issue");

			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while registering user", errorDetailsList,
					AnukyaErrorConstants.INTERNAL_SERVER_ERROR, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	public CompleteUserDetails getUserDetails(String userEmailId) throws AnukyaException {

		File userDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId);

		ObjectMapper mapper = new ObjectMapper();

		if (!userDirectory.isDirectory()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while fetching user details -> ");
			logMessage.append("Message: User is not registered | ");
			logMessage.append("Reason: User is not registered");
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.UL_0001);
			errorDetails.setErrorMessage("User is not registered");
			errorDetails.setUiField(AnukyaUiFieldMapping.LOGIN_USER_EMAIL);

			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while fetch user details", errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		File userDetailsFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USER_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USER_DETAILS_JSON);

		try {
			String jsonString = FileUtils.readFileToString(userDetailsFile, StandardCharsets.UTF_8);
			return mapper.readValue(jsonString, CompleteUserDetails.class);
		} catch (Exception e) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while fetching user details -> ");
			logMessage.append("Message: Reading file error | ");
			logMessage.append("Reason: Internal error");
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.FE_0001);
			errorDetails.setErrorMessage(READING_FILE_ERROR);

			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while getting user details", errorDetailsList,
					AnukyaErrorConstants.INTERNAL_SERVER_ERROR, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	public void addUserTokenDetails(String userEmailId, TokenDetails tokenDetails) throws AnukyaException {

		File userAuthenticationFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USER_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USER_AUTHENTICATION_JSON);

		if (!userAuthenticationFile.isFile()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while updating token details -> ");
			logMessage.append("Message: Reading file error | ");
			logMessage.append("Reason: Internal error");
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.FE_0001);
			errorDetails.setErrorMessage(READING_FILE_ERROR);

			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while updating token details in user-authentication", errorDetailsList,
					AnukyaErrorConstants.INTERNAL_SERVER_ERROR, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		anukyaDataUtils.createOrUpdateFile(userAuthenticationFile, tokenDetails);
	}

	public void logout(String loggedInUserEmailId) throws AnukyaException {

		File userAuthenticationFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + loggedInUserEmailId
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USER_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USER_AUTHENTICATION_JSON);

		if (!userAuthenticationFile.isFile()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while updating tokem details -> ");
			logMessage.append("Message: Read file error | ");
			logMessage.append("Reason: Internal error");
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.FE_0001);
			errorDetails.setErrorMessage(READING_FILE_ERROR);

			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while updating token details in user-authentication", errorDetailsList,
					AnukyaErrorConstants.INTERNAL_SERVER_ERROR, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		anukyaDataUtils.createOrUpdateFile(userAuthenticationFile, new TokenDetails());
	}

	public PreData preData() {

		PreData preData = new PreData();
		preData.setCountryDetailsMap(anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap());
		preData.setServicesOfferedDetailsMap(anukyaPostConstruct.getAnukyaInitialData().getServicesOfferedDetailsMap());
		preData.setCountriesAndServicesOfferedDetailsMap(
				anukyaPostConstruct.getAnukyaInitialData().getCountriesAndServicesOfferedDetailsMap());
		preData.setGenderDetailsMap(anukyaPostConstruct.getAnukyaInitialData().getGenderDetailsMap());
		preData.setSubServicesDetailsMap(anukyaPostConstruct.getAnukyaInitialData().getSubServicesDetailsMap());
		preData.setSubServicesByServiceCodeDetailsMap(
				anukyaPostConstruct.getAnukyaInitialData().getSubServicesByServiceCodeDetailsMap());

		return preData;
	}

	public TokenDetails getTokenDetails(String loggedInUserEmailId) throws AnukyaException {

		File tokenDetailsFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + loggedInUserEmailId
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USER_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USER_AUTHENTICATION_JSON);

		ObjectMapper mapper = new ObjectMapper();

		try {
			String jsonString = FileUtils.readFileToString(tokenDetailsFile, StandardCharsets.UTF_8);

			return mapper.readValue(jsonString, TokenDetails.class);
		} catch (IOException e) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while fetching token details -> ");
			logMessage.append("Message: Reading file error | ");
			logMessage.append("Reason: Internal error");
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.FE_0001);
			errorDetails.setErrorMessage(READING_FILE_ERROR);

			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while fetching token details", errorDetailsList,
					AnukyaErrorConstants.INTERNAL_SERVER_ERROR, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}
}
