package com.moneymanager.anukya.services.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.moneymanager.anukya.data.impl.UsersData;
import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.AnukyaUiFieldMapping;
import com.moneymanager.anukya.exception.ErrorDetails;
import com.moneymanager.anukya.exception.handler.UsersExceptions;
import com.moneymanager.anukya.model.CommonResponse;
import com.moneymanager.anukya.model.CompleteUserDetails;
import com.moneymanager.anukya.model.Login;
import com.moneymanager.anukya.model.PreData;
import com.moneymanager.anukya.model.TokenDetails;
import com.moneymanager.anukya.model.UpdateCountriesAndServicesResponse;
import com.moneymanager.anukya.model.UserDetails;
import com.moneymanager.anukya.model.UserOptedCountriesAndServices;
import com.moneymanager.anukya.model.UserOptedServices;
import com.moneymanager.anukya.services.UserServices;
import com.moneymanager.anukya.utils.AnukyaConstants;
import com.moneymanager.anukya.utils.AnukyaDataUtils;
import com.moneymanager.anukya.utils.AnukyaUtils;
import com.moneymanager.anukya.utils.AnukyaSecurityUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserServicesImpl implements UserServices {

	@Value("${ACCESS.TOKEN.EXPIRATION.TIME}")
	String accessTokenExpirationTime;

	@Value("${TOKEN.TYPE}")
	String tokenType;

	@Value("${REFRESH.TOKEN.PERCENTAGE}")
	String refreshTokenPercentage;

	@Value("${DATABASE.BASE.LOCATION}")
	private String databaseBaseLocation;

	@Autowired
	private UsersExceptions usersExceptions;

	@Autowired
	private UsersData usersData;

	@Autowired
	private AnukyaUtils anukyaUtils;

	@Autowired
	private AnukyaSecurityUtils anukyaSecurityUtils;

	@Autowired
	private AnukyaDataUtils anukyaDataUtils;

	@Override
	public CommonResponse userAvailability(String userId) throws AnukyaException {

		usersExceptions.userAvailabilityException(userId);

		CommonResponse response = new CommonResponse();
		boolean isUserExists = usersData.userAvailability(userId);
		if (!isUserExists) {
			response.setStatus(!isUserExists);
			response.setMessage("User id/Email is available");
			return response;
		}

		StringBuilder logMessage = new StringBuilder();
		logMessage.append("Exception occured while checking user availablity -> ");
		logMessage.append("Message: User id/Email is not available | ");
		logMessage.append("Reason: User id/Email already exists");
		log.error(logMessage.toString());

		List<ErrorDetails> error = new ArrayList<>();

		ErrorDetails errorDetails = new ErrorDetails();

		errorDetails.setErrorCode(AnukyaErrorConstants.RU_0001);
		errorDetails.setErrorMessage("User id/Email is not available");
		errorDetails.setUiField(AnukyaUiFieldMapping.REGISTER_USER_EMAIL);

		error.add(errorDetails);

		throw new AnukyaException("Error occured while checking user availability", error,
				AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
	}

	@Override
	public CommonResponse registerUser(CompleteUserDetails completeUserDetails) throws AnukyaException {

		usersExceptions.registerUserException(completeUserDetails);

		userAvailability(completeUserDetails.getEmail());

		String strDate = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		completeUserDetails.setCreatedBy(completeUserDetails.getEmail());
		completeUserDetails.setCreatedDate(strDate);
		completeUserDetails.setLastUpdatedBy(completeUserDetails.getEmail());
		completeUserDetails.setLastUpdatedDate(strDate);

		Collections.sort(completeUserDetails.getUserOptedCountryIdsList());

		completeUserDetails.getUserOptedServicesList()
				.forEach(userOptedServices -> Collections.sort(userOptedServices.getUserOptedServiceIdsList()));

		// Creating directory and user files
		boolean isRegisterUserDetailsSuccess = usersData.isRegisterUserDetails(completeUserDetails);
		// Adding user details
		boolean isRegisterUserCountryDetailsSuccess = usersData.isRegisterUserOptedServicesSuccess(completeUserDetails);

		if (!isRegisterUserDetailsSuccess || !isRegisterUserCountryDetailsSuccess) {
			// Removing the user because of error while registering user
			usersData.removeUser(completeUserDetails.getEmail());

			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while registering user -> ");
			logMessage.append("Message: Unable to register user | ");
			logMessage.append("Reason: Internal error");
			log.error(logMessage.toString());

			List<ErrorDetails> error = new ArrayList<>();
			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.FE_0001);
			errorDetails.setErrorMessage(
					"Unable to register the user due to internal error. Please try again later or contact support team");

			error.add(errorDetails);

			throw new AnukyaException("Error while registering user", error, AnukyaErrorConstants.INTERNAL_SERVER_ERROR,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		CommonResponse response = new CommonResponse();
		response.setStatus(true);
		response.setMessage("User registered successfully");

		return response;
	}

	@Override
	public Login login(String userEmailId, String password) throws AnukyaException {

		usersExceptions.loginException(userEmailId, password);

		Login login = new Login();

		CompleteUserDetails completeUserDetails = usersData.getUserDetails(userEmailId);

		if (anukyaSecurityUtils.rsaDecryptData(password)
				.equals(anukyaSecurityUtils.rsaDecryptData(completeUserDetails.getPassword()))) {

			TokenDetails tokenDetails = createTokenDetails();

			login.setMessage("User logged in successfully");
			login.setStatus(true);
			login.setTokenDetails(tokenDetails);

			usersData.addUserTokenDetails(userEmailId, tokenDetails);
		} else {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while logging in -> ");
			logMessage.append("Message: Invalid credentials | ");
			logMessage.append("Reason: Email and password does not match");
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.UL_0003);
			errorDetails.setErrorMessage("User email and password does not match");

			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while logging in", errorDetailsList, AnukyaErrorConstants.UNAUTHORIZED,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		UserDetails userDetails = new UserDetails();
		userDetails.setEmail(completeUserDetails.getEmail());
		userDetails.setFirstName(completeUserDetails.getFirstName());
		userDetails.setMiddleName(completeUserDetails.getMiddleName());
		userDetails.setLastName(completeUserDetails.getLastName());
		userDetails.setPreferredName(completeUserDetails.getPreferredName());
		userDetails.setGender(completeUserDetails.getGender());
		userDetails.setUserOptedCountryIdsList(completeUserDetails.getUserOptedCountryIdsList());
		userDetails.setUserOptedServicesList(completeUserDetails.getUserOptedServicesList());
		userDetails.setCreatedBy(completeUserDetails.getCreatedBy());
		userDetails.setCreatedDate(completeUserDetails.getCreatedDate());
		userDetails.setLastUpdatedBy(completeUserDetails.getLastUpdatedBy());
		userDetails.setLastUpdatedDate(completeUserDetails.getLastUpdatedDate());

		login.setUserDetails(userDetails);

		return login;
	}

	@Override
	public CommonResponse logout(String loggedInUserEmailId) throws AnukyaException {

		CommonResponse commonResponse = new CommonResponse();

		// Remove token details from user-authentication
		usersData.logout(loggedInUserEmailId);

		commonResponse.setStatus(true);
		commonResponse.setMessage("User Logged out successfully");

		return commonResponse;
	}

	@Override
	public PreData getPreData() {

		return usersData.preData();
	}

	@Override
	public TokenDetails refreshToken(String loggedInUserEmailId, String pin, String refreshToken)
			throws AnukyaException {

		usersExceptions.refreshTokenException(loggedInUserEmailId, pin, refreshToken);

		// Get Registered User Details to verify pin
		CompleteUserDetails completeUserDetails = usersData.getUserDetails(loggedInUserEmailId);

		// Get token details to verify refreshToken
		TokenDetails tokenDetails = usersData.getTokenDetails(loggedInUserEmailId);

		List<ErrorDetails> errorDetailsList = new ArrayList<>();

		if (!refreshToken.equals(tokenDetails.getRefreshToken())) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while refreshing the token -> ");
			logMessage.append("Message: Invalid credentials | ");
			logMessage.append("Reason: Invalid Refresh Token");
			log.error(logMessage.toString());

			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RT_0002);
			errorDetails.setErrorMessage("Invalid Refresh Token");

			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while refreshing the token", errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		if (!anukyaSecurityUtils.rsaDecryptData(pin)
				.equals(anukyaSecurityUtils.rsaDecryptData(completeUserDetails.getPin()))) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while refreshing the token -> ");
			logMessage.append("Message: Invalid credentials | ");
			logMessage.append("Reason: Invalid PIN");
			log.error(logMessage.toString());

			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RT_0003);
			errorDetails.setErrorMessage("Invalid PIN");
			errorDetails.setUiField(AnukyaUiFieldMapping.REFRESH_TOKEN_PIN);

			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while refreshing the token", errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		tokenDetails = createTokenDetails();
		usersData.addUserTokenDetails(loggedInUserEmailId, tokenDetails);

		return tokenDetails;
	}

	@Override
	public CommonResponse updatePassword(String userEmailId, String oldPassword, String newPassword,
			String confirmNewPassword) throws AnukyaException {

		usersExceptions.updatePasswordException(userEmailId, oldPassword, newPassword, confirmNewPassword);

		String decryptedOldPassword = anukyaSecurityUtils.rsaDecryptData(oldPassword);
		String decryptedNewPassword = anukyaSecurityUtils.rsaDecryptData(newPassword);
		String decryptedConfirmNewPassword = anukyaSecurityUtils.rsaDecryptData(confirmNewPassword);

		CompleteUserDetails completeUserDetails = usersData.getUserDetails(userEmailId);

		String decryptedUserPassword = anukyaSecurityUtils.rsaDecryptData(completeUserDetails.getPassword());

		StringBuilder errorMessageStringBuilder = new StringBuilder();
		int errorCount = 1;

		if (!decryptedOldPassword.equals(decryptedUserPassword)) {
			errorMessageStringBuilder.append(errorCount++ + AnukyaConstants.RATIO_CONSTANT + AnukyaConstants.SPACE
					+ "Old Password is incorrect." + AnukyaConstants.BR_CONSTANT);
		}

		if (!decryptedNewPassword.equals(decryptedConfirmNewPassword)) {
			errorMessageStringBuilder.append(errorCount++ + AnukyaConstants.RATIO_CONSTANT + AnukyaConstants.SPACE
					+ "New Password and Confirm New Password not matching." + AnukyaConstants.BR_CONSTANT);
		}

		if (decryptedNewPassword.equalsIgnoreCase(decryptedConfirmNewPassword)
				&& decryptedUserPassword.equalsIgnoreCase(decryptedNewPassword)) {
			errorMessageStringBuilder.append(errorCount++ + AnukyaConstants.RATIO_CONSTANT + AnukyaConstants.SPACE
					+ "Old Password and New Password cannot be same." + AnukyaConstants.BR_CONSTANT);
		}

		if (!errorMessageStringBuilder.toString().isEmpty()) {

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.UP_UPDT_PWD_9999);
			errorDetails.setErrorMessage(errorMessageStringBuilder.toString());

			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while updating password", errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		File mainDirectory = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USER_DETAILS_MAIN_DIRECTORY);
		File updateDirectory = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USER_DETAILS_UPDATE_DIRECTORY);
		File mainBackUpDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.USER_DETAILS_MAIN_BACKUP_DIRECTORY);

		// Delete update and main backup directory
		anukyaDataUtils.deleteDirectory(updateDirectory);
		anukyaDataUtils.deleteDirectory(mainBackUpDirectory);

		// Copy main directory into update and main backup directory
		anukyaDataUtils.copyDirectory(mainDirectory, updateDirectory);
		anukyaDataUtils.copyDirectory(mainDirectory, mainBackUpDirectory);

		// Modify files in baseLocation/update
		String strDate = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		completeUserDetails.setPassword(newPassword);
		completeUserDetails.setLastUpdatedBy(userEmailId);
		completeUserDetails.setLastUpdatedDate(strDate);

		File userDetailsFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USER_DETAILS_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USER_DETAILS_JSON);
		anukyaDataUtils.deleteFile(userDetailsFile);
		anukyaDataUtils.createOrUpdateFile(userDetailsFile, completeUserDetails);

		// Delete main directory
		anukyaDataUtils.deleteDirectory(mainDirectory);

		// Copy from update directory to main directory
		anukyaDataUtils.copyDirectory(updateDirectory, mainDirectory);

		// Finally delete the update directory
		anukyaDataUtils.deleteDirectory(updateDirectory);

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setStatus(true);
		commonResponse.setMessage("Password updated successfully");

		return commonResponse;
	}

	@Override
	public CommonResponse updatePin(String userEmailId, String oldPin, String newPin, String confirmNewPin)
			throws AnukyaException {

		usersExceptions.updatePinException(userEmailId, oldPin, newPin, confirmNewPin);

		String decryptedOldPin = anukyaSecurityUtils.rsaDecryptData(oldPin);
		String decryptedNewPin = anukyaSecurityUtils.rsaDecryptData(newPin);
		String decryptedConfirmNewPin = anukyaSecurityUtils.rsaDecryptData(confirmNewPin);

		CompleteUserDetails completeUserDetails = usersData.getUserDetails(userEmailId);

		String decryptedUserPin = anukyaSecurityUtils.rsaDecryptData(completeUserDetails.getPin());

		StringBuilder errorMessageStringBuilder = new StringBuilder();
		int errorCount = 1;

		if (!decryptedOldPin.equals(decryptedUserPin)) {
			errorMessageStringBuilder.append(errorCount++ + AnukyaConstants.RATIO_CONSTANT + AnukyaConstants.SPACE
					+ "Old PIN is incorrect." + AnukyaConstants.BR_CONSTANT);
		}

		if (!decryptedNewPin.equals(decryptedConfirmNewPin)) {
			errorMessageStringBuilder.append(errorCount++ + AnukyaConstants.RATIO_CONSTANT + AnukyaConstants.SPACE
					+ "New PIN and Confirm New PIN not matching." + AnukyaConstants.BR_CONSTANT);
		}

		if (decryptedNewPin.equalsIgnoreCase(decryptedConfirmNewPin)
				&& decryptedUserPin.equalsIgnoreCase(decryptedNewPin)) {
			errorMessageStringBuilder.append(errorCount++ + AnukyaConstants.RATIO_CONSTANT + AnukyaConstants.SPACE
					+ "Old PIN and New PIN cannot be same." + AnukyaConstants.BR_CONSTANT);
		}

		if (!errorMessageStringBuilder.toString().isEmpty()) {

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.UP_UPDT_PIN_9999);
			errorDetails.setErrorMessage(errorMessageStringBuilder.toString());

			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while updating PIN", errorDetailsList, AnukyaErrorConstants.BAD_REQUEST,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

		File mainDirectory = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USER_DETAILS_MAIN_DIRECTORY);
		File updateDirectory = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USER_DETAILS_UPDATE_DIRECTORY);
		File mainBackUpDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + userEmailId
				+ AnukyaConstants.USER_DETAILS_MAIN_BACKUP_DIRECTORY);

		// Delete update and main backup directory
		anukyaDataUtils.deleteDirectory(updateDirectory);
		anukyaDataUtils.deleteDirectory(mainBackUpDirectory);

		// Copy main directory into update and main backup directory
		anukyaDataUtils.copyDirectory(mainDirectory, updateDirectory);
		anukyaDataUtils.copyDirectory(mainDirectory, mainBackUpDirectory);

		// Modify files in baseLocation/update
		String strDate = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);

		completeUserDetails.setPin(newPin);
		completeUserDetails.setLastUpdatedBy(userEmailId);
		completeUserDetails.setLastUpdatedDate(strDate);

		File userDetailsFile = new File(
				databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + userEmailId + AnukyaConstants.USER_DETAILS_UPDATE_DIRECTORY
						+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USER_DETAILS_JSON);
		anukyaDataUtils.deleteFile(userDetailsFile);
		anukyaDataUtils.createOrUpdateFile(userDetailsFile, completeUserDetails);

		// Delete main directory
		anukyaDataUtils.deleteDirectory(mainDirectory);

		// Copy from update directory to main directory
		anukyaDataUtils.copyDirectory(updateDirectory, mainDirectory);

		// Finally delete the update directory
		anukyaDataUtils.deleteDirectory(updateDirectory);

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setStatus(true);
		commonResponse.setMessage("PIN updated successfully");

		return commonResponse;
	}

	@Override
	public UpdateCountriesAndServicesResponse updateCountriesAndServices(String userEmailId,
			UserOptedCountriesAndServices userOptedCountriesAndServices) throws AnukyaException {

		usersExceptions.updateCountriesAndServicesException(userEmailId, userOptedCountriesAndServices);

		CompleteUserDetails completeUserDetails = usersData.getUserDetails(userEmailId);

		Set<String> existingUserOptedCountryIdsSet = new HashSet<>(completeUserDetails.getUserOptedCountryIdsList());
		Map<String, Set<String>> existingUserOptedServiceIdsSetByCountryMap = new HashMap<>();
		for (UserOptedServices userOptedService : completeUserDetails.getUserOptedServicesList()) {
			Set<String> existingUserOptedServiceIdsSet = new HashSet<>(userOptedService.getUserOptedServiceIdsList());
			existingUserOptedServiceIdsSetByCountryMap.put(userOptedService.getCountryId(),
					existingUserOptedServiceIdsSet);
		}

		Set<String> userOptedCountryIdsSet = new HashSet<>(userOptedCountriesAndServices.getUserOptedCountryIdsList());
		Map<String, Set<String>> userOptedServiceIdsListByCountryMap = new HashMap<>();
		for (UserOptedServices userOptedService : userOptedCountriesAndServices.getUserOptedServicesList()) {
			Set<String> userOptedServiceIdsSet = new HashSet<>(userOptedService.getUserOptedServiceIdsList());
			userOptedServiceIdsListByCountryMap.put(userOptedService.getCountryId(), userOptedServiceIdsSet);
		}

		UpdateCountriesAndServicesResponse updateCountriesAndServicesResponse = new UpdateCountriesAndServicesResponse();

		if (!userOptedCountriesAndServices.isConfirmed()) {
			// When not confirmed by the user, build a confirmation message
			PreData preData = usersData.preData();

			StringBuilder confirmationMessageBuilder = new StringBuilder();

			confirmationMessageBuilder.append(AnukyaConstants.UL_OPEN_CONSTANT);
			for (String userOptedCountryId : completeUserDetails.getUserOptedCountryIdsList()) {
				if (!userOptedCountryIdsSet.contains(userOptedCountryId)) {
					confirmationMessageBuilder.append(AnukyaConstants.LI_OPEN_CONSTANT)
							.append(preData.getCountryDetailsMap().get(userOptedCountryId).getCountryName())
							.append(AnukyaConstants.LI_CLOSE_CONSTANT);
					confirmationMessageBuilder.append(AnukyaConstants.UL_OPEN_CONSTANT);
					confirmationMessageBuilder.append(AnukyaConstants.COUNTRY_SERVICE_UPDATE_ALL_SERVICES_TERMINATED);
					confirmationMessageBuilder.append(AnukyaConstants.COUNTRY_SERVICE_UPDATE_IMPORTANT_WARNING);
					confirmationMessageBuilder.append(AnukyaConstants.UL_CLOSE_CONSTANT);
				} else {
					for (String userOptedServiceId : existingUserOptedServiceIdsSetByCountryMap
							.get(userOptedCountryId)) {
						if (!userOptedServiceIdsListByCountryMap.get(userOptedCountryId).contains(userOptedServiceId)) {
							confirmationMessageBuilder.append(AnukyaConstants.LI_OPEN_CONSTANT
									+ preData.getCountryDetailsMap().get(userOptedCountryId).getCountryName()
									+ AnukyaConstants.LI_CLOSE_CONSTANT);
							confirmationMessageBuilder.append(AnukyaConstants.UL_OPEN_CONSTANT);
							confirmationMessageBuilder
									.append(AnukyaConstants.COUNTRY_SERVICE_UPDATE_ONE_SERVICE_TERMINATED_1)
									.append(preData.getServicesOfferedDetailsMap().get(userOptedServiceId)
											.getServiceName())
									.append(AnukyaConstants.COUNTRY_SERVICE_UPDATE_ONE_SERVICE_TERMINATED_2);
							confirmationMessageBuilder.append(AnukyaConstants.COUNTRY_SERVICE_UPDATE_IMPORTANT_WARNING);
							confirmationMessageBuilder.append(AnukyaConstants.UL_CLOSE_CONSTANT);
						}
					}
				}
			}
			confirmationMessageBuilder.append(AnukyaConstants.UL_CLOSE_CONSTANT);
			if (!confirmationMessageBuilder.toString()
					.equals(AnukyaConstants.UL_OPEN_CONSTANT + AnukyaConstants.UL_CLOSE_CONSTANT)) {
				updateCountriesAndServicesResponse.setConfirmationRequired(true);
				updateCountriesAndServicesResponse.setMessage(confirmationMessageBuilder.toString());

				return updateCountriesAndServicesResponse;
			}
		} else if (userOptedCountriesAndServices.isConfirmed()) {
			// Add logic to modify the files and folder
			updateCountriesAndServicesFilesAndDirectories(completeUserDetails, userOptedCountriesAndServices,
					existingUserOptedServiceIdsSetByCountryMap, userOptedCountryIdsSet,
					userOptedServiceIdsListByCountryMap);

			StringBuilder message = new StringBuilder();
			message.append(AnukyaConstants.UL_OPEN_CONSTANT);
			message.append(AnukyaConstants.LI_OPEN_CONSTANT
					+ "Some of the countries and/or services were either opted out or opted in"
					+ AnukyaConstants.LI_CLOSE_CONSTANT);
			message.append(AnukyaConstants.LI_OPEN_CONSTANT
					+ "You will be logged out. Please log back in to see the new services opted"
					+ AnukyaConstants.LI_CLOSE_CONSTANT);
			message.append(AnukyaConstants.UL_CLOSE_CONSTANT);
			updateCountriesAndServicesResponse.setDataAdded(true);
			updateCountriesAndServicesResponse.setMessage(message.toString());

			completeUserDetails = usersData.getUserDetails(userEmailId);

			UserDetails userDetails = new UserDetails();
			userDetails.setEmail(completeUserDetails.getEmail());
			userDetails.setFirstName(completeUserDetails.getFirstName());
			userDetails.setMiddleName(completeUserDetails.getMiddleName());
			userDetails.setLastName(completeUserDetails.getLastName());
			userDetails.setPreferredName(completeUserDetails.getPreferredName());
			userDetails.setGender(completeUserDetails.getGender());
			userDetails.setUserOptedCountryIdsList(completeUserDetails.getUserOptedCountryIdsList());
			userDetails.setUserOptedServicesList(completeUserDetails.getUserOptedServicesList());
			userDetails.setCreatedBy(completeUserDetails.getCreatedBy());
			userDetails.setCreatedDate(completeUserDetails.getCreatedDate());
			userDetails.setLastUpdatedBy(completeUserDetails.getLastUpdatedBy());
			userDetails.setLastUpdatedDate(completeUserDetails.getLastUpdatedDate());

			updateCountriesAndServicesResponse.setUserDetails(userDetails);

			return updateCountriesAndServicesResponse;
		}

		// Check if data is same and message accordingly
		boolean isSameData = false;
		for (String userOptedCountryId : userOptedCountriesAndServices.getUserOptedCountryIdsList()) {
			isSameData = existingUserOptedCountryIdsSet.contains(userOptedCountryId);
			if (!isSameData) {
				break;
			}
		}

		if (isSameData) {
			for (UserOptedServices userOptedServices : userOptedCountriesAndServices.getUserOptedServicesList()) {
				for (String userOptedServiceId : userOptedServices.getUserOptedServiceIdsList()) {
					isSameData = existingUserOptedServiceIdsSetByCountryMap.get(userOptedServices.getCountryId())
							.contains(userOptedServiceId);
					if (!isSameData) {
						break;
					}
				}
				if (!isSameData) {
					break;
				}
			}
		}

		if (isSameData) {
			updateCountriesAndServicesResponse.setSameData(isSameData);
			updateCountriesAndServicesResponse
					.setMessage("Selected countries and services are same as per our records. No changes made.");
		} else {
			// Add logic to modify the files and folder
			updateCountriesAndServicesFilesAndDirectories(completeUserDetails, userOptedCountriesAndServices,
					existingUserOptedServiceIdsSetByCountryMap, userOptedCountryIdsSet,
					userOptedServiceIdsListByCountryMap);

			completeUserDetails = usersData.getUserDetails(userEmailId);

			UserDetails userDetails = new UserDetails();
			userDetails.setEmail(completeUserDetails.getEmail());
			userDetails.setFirstName(completeUserDetails.getFirstName());
			userDetails.setMiddleName(completeUserDetails.getMiddleName());
			userDetails.setLastName(completeUserDetails.getLastName());
			userDetails.setPreferredName(completeUserDetails.getPreferredName());
			userDetails.setGender(completeUserDetails.getGender());
			userDetails.setUserOptedCountryIdsList(completeUserDetails.getUserOptedCountryIdsList());
			userDetails.setUserOptedServicesList(completeUserDetails.getUserOptedServicesList());
			userDetails.setCreatedBy(completeUserDetails.getCreatedBy());
			userDetails.setCreatedDate(completeUserDetails.getCreatedDate());
			userDetails.setLastUpdatedBy(completeUserDetails.getLastUpdatedBy());
			userDetails.setLastUpdatedDate(completeUserDetails.getLastUpdatedDate());

			updateCountriesAndServicesResponse.setUserDetails(userDetails);

			StringBuilder message = new StringBuilder();
			message.append(AnukyaConstants.UL_OPEN_CONSTANT);
			message.append(AnukyaConstants.LI_OPEN_CONSTANT
					+ "No services were terminated. Added new countries and/or services selected"
					+ AnukyaConstants.LI_CLOSE_CONSTANT);
			message.append(AnukyaConstants.LI_OPEN_CONSTANT
					+ "You will be logged out. Please log back in to see the new services opted"
					+ AnukyaConstants.LI_CLOSE_CONSTANT);
			message.append(AnukyaConstants.UL_CLOSE_CONSTANT);
			updateCountriesAndServicesResponse.setDataAdded(true);
			updateCountriesAndServicesResponse.setMessage(message.toString());

			updateCountriesAndServicesResponse.setDataAdded(true);
			updateCountriesAndServicesResponse.setMessage(message.toString());
		}

		return updateCountriesAndServicesResponse;
	}

	// Private methods
	private TokenDetails createTokenDetails() throws AnukyaException {

		TokenDetails tokenDetails = new TokenDetails();

		String accessToken = anukyaSecurityUtils.rsaEncryptData("AT" + anukyaUtils.generateId());
		String refreshToken = anukyaSecurityUtils.rsaEncryptData("RT" + anukyaUtils.generateId());

		String tokenGeneratedTime = String.valueOf(System.currentTimeMillis());

		tokenDetails.setAccessToken(accessToken);
		tokenDetails.setAccessTokenExpiresIn(accessTokenExpirationTime);
		tokenDetails.setRefreshToken(refreshToken);
		tokenDetails.setTokenGeneratedTime(tokenGeneratedTime);
		tokenDetails.setTokenType(tokenType);

		Long refreshTokenTime = Long.parseLong(accessTokenExpirationTime) * Long.parseLong(refreshTokenPercentage)
				/ 100;

		tokenDetails.setRefreshTokenExpiresIn(String.valueOf(refreshTokenTime));

		return tokenDetails;
	}

	private void updateCountriesAndServicesFilesAndDirectories(CompleteUserDetails completeUserDetails,
			UserOptedCountriesAndServices userOptedCountriesAndServices,
			Map<String, Set<String>> existingUserOptedServiceIdsSetByCountryMap, Set<String> userOptedCountryIdsSet,
			Map<String, Set<String>> userOptedServiceIdsListByCountryMap) throws AnukyaException {

		// Backing up existing user
		File mainUserDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail());

		File backupUserDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.BACKUP_DIRECTORY + AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail());

		File updateUserDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail());

		// Delete update and main backup directory
		anukyaDataUtils.deleteDirectory(updateUserDirectory);
		anukyaDataUtils.deleteDirectory(backupUserDirectory);

		// Copy main directory into update and main backup directory
		anukyaDataUtils.copyDirectory(mainUserDirectory, updateUserDirectory);
		anukyaDataUtils.copyDirectory(mainUserDirectory, backupUserDirectory);

		PreData preData = usersData.preData();

		// Delete existing countries which are terminated
		for (String userOptedCountryId : completeUserDetails.getUserOptedCountryIdsList()) {
			if (!userOptedCountryIdsSet.contains(userOptedCountryId)) {
				String countryCode = preData.getCountryDetailsMap().get(userOptedCountryId).getCountryCode();
				File deleteCountryDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
						+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
						+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
						+ AnukyaConstants.UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
						+ completeUserDetails.getEmail() + AnukyaConstants.FORWARD_SLASH
						+ AnukyaConstants.SERVICES_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + countryCode);

				anukyaDataUtils.deleteDirectory(deleteCountryDirectory);

				completeUserDetails.getUserOptedServicesList()
						.removeIf(userOptedService -> userOptedService.getCountryId().equals(userOptedCountryId));
			}
		}

		Collections.sort(userOptedCountriesAndServices.getUserOptedCountryIdsList());
		completeUserDetails.setUserOptedCountryIdsList(userOptedCountriesAndServices.getUserOptedCountryIdsList());

		// Delete existing services which are terminated
		for (UserOptedServices userOptedService : completeUserDetails.getUserOptedServicesList()) {
			for (String userOptedServiceId : userOptedService.getUserOptedServiceIdsList()) {
				if (!userOptedServiceIdsListByCountryMap.get(userOptedService.getCountryId())
						.contains(userOptedServiceId)) {
					String countryCode = preData.getCountryDetailsMap().get(userOptedService.getCountryId())
							.getCountryCode();
					String serviceCode = preData.getServicesOfferedDetailsMap().get(userOptedServiceId)
							.getServiceCode();
					File deleteServiceDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ completeUserDetails.getEmail() + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SERVICES_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + countryCode
							+ AnukyaConstants.FORWARD_SLASH + serviceCode);

					anukyaDataUtils.deleteDirectory(deleteServiceDirectory);
				}
			}
		}

		StringBuilder handleError = new StringBuilder();

		// Add newly subscribed services
		for (UserOptedServices userOptedService : userOptedCountriesAndServices.getUserOptedServicesList()) {
			for (String userOptedServiceId : userOptedService.getUserOptedServiceIdsList()) {
				if (!existingUserOptedServiceIdsSetByCountryMap.containsKey(userOptedService.getCountryId())
						|| !existingUserOptedServiceIdsSetByCountryMap.get(userOptedService.getCountryId())
								.contains(userOptedServiceId)) {
					String countryCode = preData.getCountryDetailsMap().get(userOptedService.getCountryId())
							.getCountryCode();

					File addCountryDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ completeUserDetails.getEmail() + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SERVICES_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + countryCode);

					if (!addCountryDirectory.exists()) {
						anukyaDataUtils.createDirectory(addCountryDirectory);
					}

					String serviceCode = preData.getServicesOfferedDetailsMap().get(userOptedServiceId)
							.getServiceCode();
					File addServiceDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ completeUserDetails.getEmail() + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SERVICES_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + countryCode
							+ AnukyaConstants.FORWARD_SLASH + serviceCode);

					anukyaDataUtils.createDirectory(addServiceDirectory);

					// Creating purchase directory
					File serviceTypeDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ completeUserDetails.getEmail() + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SERVICES_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + countryCode
							+ AnukyaConstants.FORWARD_SLASH + serviceCode + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.PURCHASE_DIRECTORY_CONSTANT);

					anukyaDataUtils.createDirectory(serviceTypeDirectory);

					// Creating main directory inside purchase directory
					File serviceMainDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ completeUserDetails.getEmail() + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SERVICES_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + countryCode
							+ AnukyaConstants.FORWARD_SLASH + serviceCode + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.PURCHASE_DIRECTORY_CONSTANT + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.MAIN_DIRECTORY);

					anukyaDataUtils.createDirectory(serviceMainDirectory);

					// Creating purchase non consolidated json
					File serviceFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ completeUserDetails.getEmail() + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SERVICES_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + countryCode
							+ AnukyaConstants.FORWARD_SLASH + serviceCode + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.PURCHASE_DIRECTORY_CONSTANT + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.NON_CONSOLIDATED_JSON);

					try {
						anukyaDataUtils.createOrUpdateFile(serviceFile, new ArrayList<>());
					} catch (AnukyaException e) {
						log.warn("File error: {}", e.getMessage());
						handleError.append("File error: " + e.getMessage());
					}

					// Create single folder in purchase directory
					File singleDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ completeUserDetails.getEmail() + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SERVICES_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + countryCode
							+ AnukyaConstants.FORWARD_SLASH + serviceCode + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.PURCHASE_DIRECTORY_CONSTANT + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SINGLE_DIRECTORY);

					anukyaDataUtils.createDirectory(singleDirectory);

					// Creating purchase consolidated json
					serviceFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ completeUserDetails.getEmail() + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SERVICES_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + countryCode
							+ AnukyaConstants.FORWARD_SLASH + serviceCode + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.PURCHASE_DIRECTORY_CONSTANT + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.CONSOLIDATED_JSON);

					try {
						anukyaDataUtils.createOrUpdateFile(serviceFile, new ArrayList<>());
					} catch (AnukyaException e) {
						log.warn("File error: {}", e.getMessage());
						handleError.append("File error: " + e.getMessage());
					}

					// Creating sold directory
					serviceTypeDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ completeUserDetails.getEmail() + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SERVICES_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + countryCode
							+ AnukyaConstants.FORWARD_SLASH + serviceCode + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SOLD_DIRECTORY_CONSTANT);

					anukyaDataUtils.createDirectory(serviceTypeDirectory);

					// Creating main directory inside sold directory
					serviceMainDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ completeUserDetails.getEmail() + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SERVICES_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + countryCode
							+ AnukyaConstants.FORWARD_SLASH + serviceCode + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SOLD_DIRECTORY_CONSTANT + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.MAIN_DIRECTORY);

					anukyaDataUtils.createDirectory(serviceMainDirectory);

					// Creating sold non consolidated json
					serviceFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ completeUserDetails.getEmail() + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SERVICES_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + countryCode
							+ AnukyaConstants.FORWARD_SLASH + serviceCode + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SOLD_DIRECTORY_CONSTANT + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.NON_CONSOLIDATED_JSON);

					try {
						anukyaDataUtils.createOrUpdateFile(serviceFile, new ArrayList<>());
					} catch (AnukyaException e) {
						log.warn("File error: {}", e.getMessage());
						handleError.append("File error: " + e.getMessage());
					}

					// Creating sold consolidated json
					serviceFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ completeUserDetails.getEmail() + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SERVICES_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + countryCode
							+ AnukyaConstants.FORWARD_SLASH + serviceCode + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SOLD_DIRECTORY_CONSTANT + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.CONSOLIDATED_JSON);

					try {
						anukyaDataUtils.createOrUpdateFile(serviceFile, new ArrayList<>());
					} catch (AnukyaException e) {
						log.warn("File error: {}", e.getMessage());
						handleError.append("File error: " + e.getMessage());
					}

					// Create single folder in sold directory
					singleDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ completeUserDetails.getEmail() + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SERVICES_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + countryCode
							+ AnukyaConstants.FORWARD_SLASH + serviceCode + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SOLD_DIRECTORY_CONSTANT + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SINGLE_DIRECTORY);

					anukyaDataUtils.createDirectory(singleDirectory);

					// Creating short-term-investment directory
					File shortTermInvestmentDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ completeUserDetails.getEmail() + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SERVICES_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + countryCode
							+ AnukyaConstants.FORWARD_SLASH + serviceCode + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SHORT_TERM_INVESTMENT_DIRECTORY);

					anukyaDataUtils.createDirectory(shortTermInvestmentDirectory);

					// Creating main directory inside short-term-investment directory
					File shortTermInvestmentMainDirectory = new File(databaseBaseLocation
							+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USERS_DIRECTORY
							+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.DATA_PROCESSING_DIRECTORY
							+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.UPDATE_DIRECTORY
							+ AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail()
							+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SERVICES_DETAILS_DIRECTORY
							+ AnukyaConstants.FORWARD_SLASH + countryCode + AnukyaConstants.FORWARD_SLASH + serviceCode
							+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.SHORT_TERM_INVESTMENT_DIRECTORY
							+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.MAIN_DIRECTORY);

					anukyaDataUtils.createDirectory(shortTermInvestmentMainDirectory);

					// Creating Short term investment file
					File shortTermInvestmentMainFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ completeUserDetails.getEmail() + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SERVICES_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + countryCode
							+ AnukyaConstants.FORWARD_SLASH + serviceCode + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SHORT_TERM_INVESTMENT_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SHORT_TERM_INVESTMENT_JSON);

					try {
						anukyaDataUtils.createOrUpdateFile(shortTermInvestmentMainFile, new ArrayList<>());
					} catch (AnukyaException e) {
						log.warn("File error: {}", e.getMessage());
						handleError.append("File error: " + e.getMessage());
					}

					// Create single folder in Short term investment file
					singleDirectory = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ completeUserDetails.getEmail() + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SERVICES_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH + countryCode
							+ AnukyaConstants.FORWARD_SLASH + serviceCode + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SHORT_TERM_INVESTMENT_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH
							+ AnukyaConstants.SINGLE_DIRECTORY);

					anukyaDataUtils.createDirectory(singleDirectory);

				}
			}
		}

		if (!handleError.toString().isEmpty()) {

			log.error(handleError.toString());

			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while updating countries and services -> ");
			logMessage.append("Message: Creating directories and files failed | ");
			logMessage.append("Reason: Internal error");
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();
			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.FE_0001);
			errorDetails.setErrorMessage(
					"Unable to update countries and services due to internal error. Please try again later or contact support team");

			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while updating countries and services", errorDetailsList,
					AnukyaErrorConstants.INTERNAL_SERVER_ERROR, ThreadContext.get(AnukyaConstants.TRACE_ID));

		}

		List<UserOptedServices> userOptedServicesList = new ArrayList<>();
		for (String userOptedCountryId : completeUserDetails.getUserOptedCountryIdsList()) {
			List<String> userOptedServiceIdsList = new ArrayList<>(
					new TreeSet<>(userOptedServiceIdsListByCountryMap.get(userOptedCountryId)));
			UserOptedServices userOptedServices = new UserOptedServices();
			userOptedServices.setCountryId(userOptedCountryId);
			userOptedServices.setUserOptedServiceIdsList(userOptedServiceIdsList);
			userOptedServicesList.add(userOptedServices);
		}

		completeUserDetails.setUserOptedServicesList(userOptedServicesList);

		String strDate = anukyaUtils.convertDateToString(new Date(), AnukyaConstants.DATE_FORMAT_DD_MMM_YYYY);
		completeUserDetails.setLastUpdatedBy(completeUserDetails.getEmail());
		completeUserDetails.setLastUpdatedDate(strDate);

		// Remove existing user-details.json
		File userDetailsFile = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.USERS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.DATA_PROCESSING_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.UPDATE_DIRECTORY + AnukyaConstants.FORWARD_SLASH + completeUserDetails.getEmail()
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USER_DETAILS_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.MAIN_DIRECTORY + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.USER_DETAILS_JSON);

		anukyaDataUtils.deleteFile(userDetailsFile);
		anukyaDataUtils.createOrUpdateFile(userDetailsFile, completeUserDetails);

		// Delete main directory
		anukyaDataUtils.deleteDirectory(mainUserDirectory);

		// Copy from update directory to main directory
		anukyaDataUtils.copyDirectory(updateUserDirectory, mainUserDirectory);

		// Finally delete the update directory
		anukyaDataUtils.deleteDirectory(updateUserDirectory);

	}
}
