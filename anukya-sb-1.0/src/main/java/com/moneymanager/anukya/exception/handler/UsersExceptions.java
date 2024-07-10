package com.moneymanager.anukya.exception.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.moneymanager.anukya.configurations.AnukyaPostConstruct;
import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.AnukyaUiFieldMapping;
import com.moneymanager.anukya.exception.ErrorDetails;
import com.moneymanager.anukya.model.CompleteUserDetails;
import com.moneymanager.anukya.model.UserOptedCountriesAndServices;
import com.moneymanager.anukya.model.UserOptedServices;
import com.moneymanager.anukya.utils.AnukyaConstants;
import com.moneymanager.anukya.utils.AnukyaUtils;
import com.moneymanager.anukya.utils.AnukyaSecurityUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class UsersExceptions {

	@Autowired
	private AnukyaPostConstruct anukyaPostConstruct;

	@Autowired
	private AnukyaUtils anukyaUtils;

	@Autowired
	private AnukyaSecurityUtils anukyaSecurityUtils;

	// User Availability exception
	public void userAvailabilityException(String email) throws AnukyaException {
		List<ErrorDetails> errorDetailsList = new ArrayList<>();

		if (!StringUtils.hasLength(email)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RU_0001);
			errorDetails.setErrorMessage("Email cannot be empty and should be in valid format");
			errorDetails.setUiField(AnukyaUiFieldMapping.REGISTER_USER_EMAIL);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isValidEmailId(email)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RU_0001);
			errorDetails.setErrorMessage("Email format is incorrect");
			errorDetails.setUiField(AnukyaUiFieldMapping.REGISTER_USER_EMAIL);

			errorDetailsList.add(errorDetails);
		}

		if (!errorDetailsList.isEmpty()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Error while checking user availability -> ");
			logMessage.append("Message: Input field error | ");
			logMessage.append("Reason: Email is either empty or invalid");
			log.error(logMessage.toString());

			throw new AnukyaException("Error while checking user availability", errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	// Exception for registering a new user
	public void registerUserException(CompleteUserDetails completeUserDetails) throws AnukyaException {
		List<ErrorDetails> errorDetailsList = new ArrayList<>();

		if (!StringUtils.hasLength(completeUserDetails.getEmail())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RU_0001);
			errorDetails.setErrorMessage("Email cannot be empty and should be in valid format");
			errorDetails.setUiField(AnukyaUiFieldMapping.REGISTER_USER_EMAIL);

			errorDetailsList.add(errorDetails);
		} else if (!anukyaUtils.isValidEmailId(completeUserDetails.getEmail())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RU_0001);
			errorDetails.setErrorMessage("Email format is incorrect");
			errorDetails.setUiField(AnukyaUiFieldMapping.REGISTER_USER_EMAIL);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(completeUserDetails.getPassword())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RU_0002);
			errorDetails.setErrorMessage("Password cannot be empty and should contain atleast 8 characters");
			errorDetails.setUiField(AnukyaUiFieldMapping.REGISTER_USER_PASSWORD);

			errorDetailsList.add(errorDetails);
		} else if (completeUserDetails.getPassword().length() < 8) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RU_0002);
			errorDetails.setErrorMessage("Password should contain atleast 8 characters");
			errorDetails.setUiField(AnukyaUiFieldMapping.REGISTER_USER_PASSWORD);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(completeUserDetails.getFirstName())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RU_0003);
			errorDetails.setErrorMessage("First Name cannot be empty and should not contain numbers");
			errorDetails.setUiField(AnukyaUiFieldMapping.REGISTER_USER_FIRST_NAME);

			errorDetailsList.add(errorDetails);
		} else if (anukyaUtils.containsDigit(completeUserDetails.getFirstName())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RU_0003);
			errorDetails.setErrorMessage("First Name should not contain numbers");
			errorDetails.setUiField(AnukyaUiFieldMapping.REGISTER_USER_FIRST_NAME);

			errorDetailsList.add(errorDetails);
		}

		if (StringUtils.hasLength(completeUserDetails.getMiddleName())
				&& anukyaUtils.containsDigit(completeUserDetails.getMiddleName())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RU_0004);
			errorDetails.setErrorMessage("Middle Name should not contain numbers");
			errorDetails.setUiField(AnukyaUiFieldMapping.REGISTER_USER_MIDDLE_NAME);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(completeUserDetails.getLastName())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RU_0005);
			errorDetails.setErrorMessage("Last Name cannot be empty and should not contain numbers");
			errorDetails.setUiField(AnukyaUiFieldMapping.REGISTER_USER_LAST_NAME);

			errorDetailsList.add(errorDetails);
		} else if (anukyaUtils.containsDigit(completeUserDetails.getLastName())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RU_0005);
			errorDetails.setErrorMessage("Last Name should not contain numbers");
			errorDetails.setUiField(AnukyaUiFieldMapping.REGISTER_USER_LAST_NAME);

			errorDetailsList.add(errorDetails);
		}

		if (StringUtils.hasLength(completeUserDetails.getPreferredName())
				&& anukyaUtils.containsDigit(completeUserDetails.getPreferredName())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RU_0006);
			errorDetails.setErrorMessage("Preferred Name should not contain numbers");
			errorDetails.setUiField(AnukyaUiFieldMapping.REGISTER_USER_PREFERRED_NAME);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(completeUserDetails.getGender())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RU_0009);
			errorDetails.setErrorMessage("Gender should not be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.REGISTER_USER_GENDER);

			errorDetailsList.add(errorDetails);
		}

		if (null == completeUserDetails.getUserOptedCountryIdsList()
				|| completeUserDetails.getUserOptedCountryIdsList().isEmpty()) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RU_0007);
			errorDetails.setErrorMessage("Please select country/countries and atleast one service for each country");
			errorDetails.setUiField(AnukyaUiFieldMapping.REGISTER_USER_OPT_COUNTRIES);

			errorDetailsList.add(errorDetails);
		}

		if (null != completeUserDetails.getUserOptedCountryIdsList()) {
			StringBuilder optedServiceErrorMessage = new StringBuilder();
			for (String userOptedCountryId : completeUserDetails.getUserOptedCountryIdsList()) {
				// To determine whether individual country does not have services
				int count = 0;
				// In case if user does not opt service for any countries
				if (completeUserDetails.getUserOptedServicesList().isEmpty()) {
					count++;
					optedServiceErrorMessage.append(optedServiceErrorMessage.toString().isEmpty()
							? AnukyaConstants.BASE_SERVICE_OPT_ERROR + anukyaPostConstruct.getAnukyaInitialData()
									.getCountryDetailsMap().get(userOptedCountryId).getCountryName()
							: AnukyaConstants.PERIOD + AnukyaConstants.SPACE + AnukyaConstants.BASE_SERVICE_OPT_ERROR
									+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
											.get(userOptedCountryId).getCountryName());
				}

				// In case if user has opted for at least 1 country and not others
				for (UserOptedServices userOptedService : completeUserDetails.getUserOptedServicesList()) {
					if (userOptedCountryId.equals(userOptedService.getCountryId())) {
						count++;
						if (userOptedService.getUserOptedServiceIdsList().isEmpty()) {
							optedServiceErrorMessage.append(optedServiceErrorMessage.toString().isEmpty()
									? AnukyaConstants.BASE_SERVICE_OPT_ERROR
											+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
													.get(userOptedCountryId).getCountryName()
									: AnukyaConstants.PERIOD + AnukyaConstants.SPACE
											+ AnukyaConstants.BASE_SERVICE_OPT_ERROR
											+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
													.get(userOptedCountryId).getCountryName());
						}
						break;
					}
				}

				if (count == 0) {
					optedServiceErrorMessage.append(optedServiceErrorMessage.toString().isEmpty()
							? AnukyaConstants.BASE_SERVICE_OPT_ERROR + anukyaPostConstruct.getAnukyaInitialData()
									.getCountryDetailsMap().get(userOptedCountryId).getCountryName()
							: AnukyaConstants.PERIOD + AnukyaConstants.SPACE + AnukyaConstants.BASE_SERVICE_OPT_ERROR
									+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
											.get(userOptedCountryId).getCountryName());
				}
			}

			if (!optedServiceErrorMessage.toString().isEmpty()) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.RU_0008);
				errorDetails.setErrorMessage(optedServiceErrorMessage.toString());
				errorDetails.setUiField(AnukyaUiFieldMapping.REGISTER_USER_OPT_SERVICES);

				errorDetailsList.add(errorDetails);
			}
		}

		if (!StringUtils.hasLength(completeUserDetails.getPin())) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RU_0010);
			errorDetails.setErrorMessage("PIN cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.REGISTER_USER_PIN);

			errorDetailsList.add(errorDetails);
		} else {
			String decryptedPin = anukyaSecurityUtils.rsaDecryptData(completeUserDetails.getPin());

			if (decryptedPin.length() != 4 || !anukyaUtils.containsOnlyDigits(decryptedPin)) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.RU_0010);
				errorDetails.setErrorMessage("PIN should contain only 4 digit numbers");
				errorDetails.setUiField(AnukyaUiFieldMapping.REGISTER_USER_PIN);

				errorDetailsList.add(errorDetails);
			}
		}

		if (!errorDetailsList.isEmpty()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Error while registering user -> ");
			logMessage.append("Message: Input field error | ");
			logMessage.append("Reason: One or more input is invalid");
			log.error(logMessage.toString());

			throw new AnukyaException("Error while registering user", errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	// Exception for login user
	public void loginException(String userEmailId, String password) throws AnukyaException {

		List<ErrorDetails> errorDetailsList = new ArrayList<>();

		if (!StringUtils.hasLength(userEmailId)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.UL_0001);
			errorDetails.setErrorMessage("Email cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.LOGIN_USER_EMAIL);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(password)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.UL_0002);
			errorDetails.setErrorMessage("Password cannot be empty");
			errorDetails.setUiField(AnukyaUiFieldMapping.LOGIN_USER_PASSWORD);

			errorDetailsList.add(errorDetails);
		}

		if (!errorDetailsList.isEmpty()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Error while logging in -> ");
			logMessage.append("Message: Input field error | ");
			logMessage.append("Reason: One or more input is invalid");
			log.error(logMessage.toString());

			throw new AnukyaException("Error while logging in", errorDetailsList, AnukyaErrorConstants.BAD_REQUEST,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	public void refreshTokenException(String userEmailId, String pin, String refreshToken) throws AnukyaException {

		List<ErrorDetails> errorDetailsList = new ArrayList<>();

		if (!StringUtils.hasLength(userEmailId)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RT_0001);
			errorDetails.setErrorMessage("Email is required");

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(refreshToken)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RT_0002);
			errorDetails.setErrorMessage("Refresh Token is required");

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(pin)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.RT_0003);
			errorDetails.setErrorMessage("PIN is required");
			errorDetails.setUiField(AnukyaUiFieldMapping.REFRESH_TOKEN_PIN);

			errorDetailsList.add(errorDetails);
		}

		if (!errorDetailsList.isEmpty()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Error while refreshing token -> ");
			logMessage.append("Message: Input field error | ");
			logMessage.append("Reason: One or more input is invalid");
			log.error(logMessage.toString());

			throw new AnukyaException("Error while refreshing token", errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	public void updatePasswordException(String userEmailId, String oldPassword, String newPassword,
			String confirmNewPassword) throws AnukyaException {

		List<ErrorDetails> errorDetailsList = new ArrayList<>();

		if (!StringUtils.hasLength(userEmailId)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.UP_UPDT_PWD_0001);
			errorDetails.setErrorMessage("Email is required");

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(oldPassword)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.UP_UPDT_PWD_0002);
			errorDetails.setErrorMessage("Old Password is required");
			errorDetails.setUiField(AnukyaUiFieldMapping.USER_PROFILE_OLD_PASSWORD);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(newPassword)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.UP_UPDT_PWD_0003);
			errorDetails.setErrorMessage("New Password is required");
			errorDetails.setUiField(AnukyaUiFieldMapping.USER_PROFILE_NEW_PASSWORD);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(confirmNewPassword)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.UP_UPDT_PWD_0004);
			errorDetails.setErrorMessage("Confirm New Password is required");
			errorDetails.setUiField(AnukyaUiFieldMapping.USER_PROFILE_CONFIRM_NEW_PASSWORD);

			errorDetailsList.add(errorDetails);
		}

		if (!errorDetailsList.isEmpty()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Error while updating password -> ");
			logMessage.append("Message: Input field error | ");
			logMessage.append("Reason: One or more input is invalid");
			log.error(logMessage.toString());

			throw new AnukyaException("Error while updating password", errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	public void updatePinException(String userEmailId, String oldPin, String newPin, String confirmNewPin)
			throws AnukyaException {

		List<ErrorDetails> errorDetailsList = new ArrayList<>();

		if (!StringUtils.hasLength(userEmailId)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.UP_UPDT_PWD_0001);
			errorDetails.setErrorMessage("Email is required");

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(oldPin)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.UP_UPDT_PIN_0002);
			errorDetails.setErrorMessage("Old PIN is required");
			errorDetails.setUiField(AnukyaUiFieldMapping.USER_PROFILE_OLD_PIN);

			errorDetailsList.add(errorDetails);
		}

		if (!StringUtils.hasLength(newPin)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.UP_UPDT_PIN_0003);
			errorDetails.setErrorMessage("New PIN is required");
			errorDetails.setUiField(AnukyaUiFieldMapping.USER_PROFILE_NEW_PIN);

			errorDetailsList.add(errorDetails);
		} else {
			String decryptedPin = anukyaSecurityUtils.rsaDecryptData(newPin);

			if (decryptedPin.length() != 4 || !anukyaUtils.containsOnlyDigits(decryptedPin)) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.UP_UPDT_PIN_0003);
				errorDetails.setErrorMessage("PIN should contain only 4 digit numbers");
				errorDetails.setUiField(AnukyaUiFieldMapping.USER_PROFILE_NEW_PIN);

				errorDetailsList.add(errorDetails);
			}
		}

		if (!StringUtils.hasLength(confirmNewPin)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.UP_UPDT_PIN_0004);
			errorDetails.setErrorMessage("Confirm New PIN is required");
			errorDetails.setUiField(AnukyaUiFieldMapping.USER_PROFILE_CONFIRM_NEW_PIN);

			errorDetailsList.add(errorDetails);
		} else {
			String decryptedPin = anukyaSecurityUtils.rsaDecryptData(confirmNewPin);

			if (decryptedPin.length() != 4 || !anukyaUtils.containsOnlyDigits(decryptedPin)) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.UP_UPDT_PIN_0004);
				errorDetails.setErrorMessage("PIN should contain only 4 digit numbers");
				errorDetails.setUiField(AnukyaUiFieldMapping.USER_PROFILE_CONFIRM_NEW_PIN);

				errorDetailsList.add(errorDetails);
			}
		}

		if (!errorDetailsList.isEmpty()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Error while updating PIN -> ");
			logMessage.append("Message: Input field error | ");
			logMessage.append("Reason: One or more input is invalid");
			log.error(logMessage.toString());

			throw new AnukyaException("Error while updating PIN", errorDetailsList, AnukyaErrorConstants.BAD_REQUEST,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	public void updateCountriesAndServicesException(String userEmailId,
			UserOptedCountriesAndServices userOptedCountriesAndServices) throws AnukyaException {
		List<ErrorDetails> errorDetailsList = new ArrayList<>();

		if (!StringUtils.hasLength(userEmailId)) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.UP_UPDT_CAS_0001);
			errorDetails.setErrorMessage("Email is required");

			errorDetailsList.add(errorDetails);
		}

		if (null == userOptedCountriesAndServices.getUserOptedCountryIdsList()
				|| userOptedCountriesAndServices.getUserOptedCountryIdsList().isEmpty()) {
			ErrorDetails errorDetails = new ErrorDetails();

			errorDetails.setErrorCode(AnukyaErrorConstants.UP_UPDT_CAS_0002);
			errorDetails.setErrorMessage("Please select country/countries and atleast one service for each country");
			errorDetails.setUiField(AnukyaUiFieldMapping.USER_PROFILE_OPT_COUNTRIES);

			errorDetailsList.add(errorDetails);
		}

		if (null != userOptedCountriesAndServices.getUserOptedCountryIdsList()) {
			StringBuilder optedServiceErrorMessage = new StringBuilder();
			for (String userOptedCountryId : userOptedCountriesAndServices.getUserOptedCountryIdsList()) {
				// To determine whether individual country does not have services
				int count = 0;
				// In case if user does not opt service for any countries
				if (userOptedCountriesAndServices.getUserOptedServicesList().isEmpty()) {
					count++;
					optedServiceErrorMessage.append(optedServiceErrorMessage.toString().isEmpty()
							? AnukyaConstants.BASE_SERVICE_OPT_ERROR + anukyaPostConstruct.getAnukyaInitialData()
									.getCountryDetailsMap().get(userOptedCountryId).getCountryName()
							: AnukyaConstants.PERIOD + AnukyaConstants.SPACE + AnukyaConstants.BASE_SERVICE_OPT_ERROR
									+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
											.get(userOptedCountryId).getCountryName());
				}

				// In case if user has opted for at least 1 country and not others
				for (UserOptedServices userOptedService : userOptedCountriesAndServices.getUserOptedServicesList()) {
					if (userOptedCountryId.equals(userOptedService.getCountryId())) {
						count++;
						if (userOptedService.getUserOptedServiceIdsList().isEmpty()) {
							optedServiceErrorMessage.append(optedServiceErrorMessage.toString().isEmpty()
									? AnukyaConstants.BASE_SERVICE_OPT_ERROR
											+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
													.get(userOptedCountryId).getCountryName()
									: AnukyaConstants.PERIOD + AnukyaConstants.SPACE
											+ AnukyaConstants.BASE_SERVICE_OPT_ERROR
											+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
													.get(userOptedCountryId).getCountryName());
						}
						break;
					}
				}

				if (count == 0) {
					optedServiceErrorMessage.append(optedServiceErrorMessage.toString().isEmpty()
							? AnukyaConstants.BASE_SERVICE_OPT_ERROR + anukyaPostConstruct.getAnukyaInitialData()
									.getCountryDetailsMap().get(userOptedCountryId).getCountryName()
							: AnukyaConstants.PERIOD + AnukyaConstants.SPACE + AnukyaConstants.BASE_SERVICE_OPT_ERROR
									+ anukyaPostConstruct.getAnukyaInitialData().getCountryDetailsMap()
											.get(userOptedCountryId).getCountryName());
				}
			}

			if (!optedServiceErrorMessage.toString().isEmpty()) {
				ErrorDetails errorDetails = new ErrorDetails();

				errorDetails.setErrorCode(AnukyaErrorConstants.UP_UPDT_CAS_0003);
				errorDetails.setErrorMessage(optedServiceErrorMessage.toString());
				errorDetails.setUiField(AnukyaUiFieldMapping.USER_PROFILE_OPT_SERVICES);

				errorDetailsList.add(errorDetails);
			}
		}

		if (!errorDetailsList.isEmpty()) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Error while updating countries and services -> ");
			logMessage.append("Message: Input field error | ");
			logMessage.append("Reason: One or more input is invalid");
			log.error(logMessage.toString());

			throw new AnukyaException("Error while updating countries and services", errorDetailsList,
					AnukyaErrorConstants.BAD_REQUEST, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}
}
