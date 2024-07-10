package com.moneymanager.anukya.services;

import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.model.CommonResponse;
import com.moneymanager.anukya.model.Login;
import com.moneymanager.anukya.model.PreData;
import com.moneymanager.anukya.model.CompleteUserDetails;
import com.moneymanager.anukya.model.TokenDetails;
import com.moneymanager.anukya.model.UpdateCountriesAndServicesResponse;
import com.moneymanager.anukya.model.UserOptedCountriesAndServices;

public interface UserServices {

	CommonResponse registerUser(CompleteUserDetails completeUserDetails) throws AnukyaException;

	CommonResponse userAvailability(String userId) throws AnukyaException;

	Login login(String userEmailId, String password) throws AnukyaException;

	CommonResponse logout(String loggedInUserEmailId) throws AnukyaException;

	TokenDetails refreshToken(String loggedInUserEmailId, String pin, String refreshToken) throws AnukyaException;

	PreData getPreData();

	CommonResponse updatePassword(String userEmailId, String oldPassword, String newPassword, String confirmNewPassword)
			throws AnukyaException;

	CommonResponse updatePin(String userEmailId, String oldPin, String newPin, String confirmNewPin)
			throws AnukyaException;

	UpdateCountriesAndServicesResponse updateCountriesAndServices(String header,
			UserOptedCountriesAndServices userOptedCountriesAndServices) throws AnukyaException;
}
