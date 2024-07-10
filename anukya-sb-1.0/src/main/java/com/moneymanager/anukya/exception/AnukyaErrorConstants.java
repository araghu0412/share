package com.moneymanager.anukya.exception;

public class AnukyaErrorConstants {

	private AnukyaErrorConstants() {
		// Private constructor
	}

	// Status constants
	public static final String BAD_REQUEST = "BAD_REQUEST";
	public static final String UNAUTHORIZED = "UNAUTHORIZED";
	public static final String NO_CONTENT = "NO_CONTENT";
	public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

	// General Error
	public static final String GE_0001 = "GE_0001";
	// Security error
	public static final String SE_0001 = "SE_0001"; // Encrypt data
	public static final String SE_0002 = "SE_0002"; // Decrypt data
	public static final String SE_0003 = "SE_0003"; // Unauthorized error

	// File error
	public static final String FE_0001 = "FE_0001"; // Reading file error
	public static final String FE_0002 = "FE_0002"; // Deleting file error
	public static final String FE_0003 = "FE_0003"; // Copying file error
	public static final String FE_0004 = "FE_0004"; // Creating file error

	// Service call error
	public static final String SC_0001 = "SC_0001"; // Service call error

	// Register user error codes
	public static final String RU_0001 = "RU_0001"; // Email
	public static final String RU_0002 = "RU_0002"; // Password
	public static final String RU_0003 = "RU_0003"; // First Name
	public static final String RU_0004 = "RU_0004"; // Middle name
	public static final String RU_0005 = "RU_0005"; // Last Name
	public static final String RU_0006 = "RU_0006"; // Preferred Name
	public static final String RU_0007 = "RU_0007"; // Opt Countries
	public static final String RU_0008 = "RU_0008"; // Opt services
	public static final String RU_0009 = "RU_0009"; // Gender
	public static final String RU_0010 = "RU_0010"; // Gender
	public static final String RU_0011 = "RU_0010"; // PIN

	// User login error codes
	public static final String UL_0001 = "UL_0001"; // Email
	public static final String UL_0002 = "UL_0002"; // Password
	public static final String UL_0003 = "UL_0003"; // General

	// Refresh Token error codes
	public static final String RT_0001 = "RT_0001";
	public static final String RT_0002 = "RT_0002";
	public static final String RT_0003 = "RT_0003";

	// Bhaaratha error codes
	public static final String BHA_0001 = "BHA_0001"; // Factory creation error

	// Bhaaratha Script Details
	public static final String BHA_SD_0001 = "BHA_SD_0001";
	public static final String BHA_SD_0002 = "BHA_SD_0002";

	// Bhaaratha Shares API Error
	public static final String BHA_API_0001 = "BHA_API_0001";
	public static final String BHA_API_0002 = "BHA_API_0002";

	// Bhaaratha Short Term investment error codes
	public static final String BHA_STI_0001 = "BHA_STI_0001"; // Share name error
	public static final String BHA_STI_0002 = "BHA_STI_0002"; // Stock exchange error
	public static final String BHA_STI_0003 = "BHA_STI_0003"; // BSE script code error
	public static final String BHA_STI_0004 = "BHA_STI_0004"; // NSE script code error
	public static final String BHA_STI_0005 = "BHA_STI_0005"; // Purchase date error
	public static final String BHA_STI_0006 = "BHA_STI_0006"; // Purchase Quantity error
	public static final String BHA_STI_0007 = "BHA_STI_0007"; // Purchase Price error
	public static final String BHA_STI_0008 = "BHA_STI_0008"; // Purchase Brokerage error
	public static final String BHA_STI_0009 = "BHA_STI_0009"; // Purchase STT error
	public static final String BHA_STI_0010 = "BHA_STI_0010"; // Purchase Expenditure error
	public static final String BHA_STI_0011 = "BHA_STI_0011"; // Purchase Non Expenditure error
	public static final String BHA_STI_0012 = "BHA_STI_0012"; // Moneycontrol code error
	public static final String BHA_STI_0013 = "BHA_STI_0013"; // No records
	public static final String BHA_STI_0014 = "BHA_STI_0014"; // Yahoo BSE code
	public static final String BHA_STI_0015 = "BHA_STI_0015"; // Yahoo NSE code

	// Bhaaratha Add/Sell shares error codes
	public static final String BHA_AS_0001 = "BHA_AS_0001"; // Split ratio error
	public static final String BHA_AS_0002 = "BHA_AS_0002"; // Split date empty
	public static final String BHA_AS_0003 = "BHA_AS_0003"; // Stock exchange empty
	public static final String BHA_AS_0004 = "BHA_AS_0004"; // Script name empty
	public static final String BHA_AS_0005 = "BHA_AS_0005"; // BSE Script code empty
	public static final String BHA_AS_0006 = "BHA_AS_0006"; // NSE Script code empty
	public static final String BHA_AS_0007 = "BHA_AS_0007"; // Bonus ratio error
	public static final String BHA_AS_0008 = "BHA_AS_0008"; // Bonus date empty
	public static final String BHA_AS_0009 = "BHA_AS_0009"; // Yahoo BSE script code empty
	public static final String BHA_AS_0010 = "BHA_AS_0010"; // Yahoo NSE script code empty
	public static final String BHA_AS_0011 = "BHA_AS_0011"; // ISIN code empty
	public static final String BHA_AS_0012 = "BHA_AS_0012"; // Money control code empty
	public static final String BHA_AS_0013 = "BHA_AS_0013"; // Category empty
	public static final String BHA_AS_0014 = "BHA_AS_0014"; // Sector empty
	public static final String BHA_AS_0015 = "BHA_AS_0015"; // Industry empty
	public static final String BHA_AS_0016 = "BHA_AS_0016"; // Purchase/Sell date empty
	public static final String BHA_AS_0017 = "BHA_AS_0017"; // Purchase/Sell quantity empty
	public static final String BHA_AS_0018 = "BHA_AS_0018"; // Purchase/Sell price empty
	public static final String BHA_AS_0019 = "BHA_AS_0019"; // Purchase/Sell brokerage empty
	public static final String BHA_AS_0020 = "BHA_AS_0020"; // Purchase/Sell STT empty
	public static final String BHA_AS_0021 = "BHA_AS_0021"; // Purchase/Sell Expenditure
	public static final String BHA_AS_0022 = "BHA_AS_0022"; // Purchase/Sell Non-Expenditure

	// Bhaaratha One Share Details
	public static final String BHA_OSD_0001 = "BHA_OSD_0001"; // Type empty
	public static final String BHA_OSD_0002 = "BHA_OSD_0002"; // BSE Code empty
	public static final String BHA_OSD_0003 = "BHA_OSD_0003"; // NSE Code empty
	public static final String BHA_OSD_0004 = "BHA_OSD_0004"; // Moneycontrol Code empty
	public static final String BHA_OSD_0005 = "BHA_OSD_0005"; // Yahoo BSE Code empty
	public static final String BHA_OSD_0006 = "BHA_OSD_0006"; // Yahoo NSE Code empty

	// Bhaaratha Shares Bulk Upload
	public static final String BHA_BU_0001 = "BHA_BU_0001";

	// Update User profile
	public static final String UP_UPDT_PWD_0001 = "UP_UPDT_PWD_0001"; // Email
	public static final String UP_UPDT_PWD_0002 = "UP_UPDT_PWD_0002"; // Password
	public static final String UP_UPDT_PWD_0003 = "UP_UPDT_PWD_0003"; // Pin
	public static final String UP_UPDT_PWD_0004 = "UP_UPDT_PWD_0004"; // Service
	public static final String UP_UPDT_PWD_9999 = "UP_UPDT_PWD_9999"; // Others

	// Update User profile
	public static final String UP_UPDT_PIN_0001 = "UP_UPDT_PIN_0001"; // Email
	public static final String UP_UPDT_PIN_0002 = "UP_UPDT_PIN_0002"; // PIN
	public static final String UP_UPDT_PIN_0003 = "UP_UPDT_PIN_0003"; // New PIN
	public static final String UP_UPDT_PIN_0004 = "UP_UPDT_PIN_0004"; // Confirm New PIN
	public static final String UP_UPDT_PIN_9999 = "UP_UPDT_PIN_9999"; // Others

	// Update Countries and Services
	public static final String UP_UPDT_CAS_0001 = "UP_UPDT_CAS_0001"; // Email
	public static final String UP_UPDT_CAS_0002 = "UP_UPDT_CAS_0002"; // Countries
	public static final String UP_UPDT_CAS_0003 = "UP_UPDT_CAS_0003"; // Services

	// United States Of America error codes
	public static final String USA_0001 = "USA_0001"; // Factory creation error

	// United States of America Script Details
	public static final String USA_SD_0001 = "USA_SD_0001";

	// United States of America Add/Sell shares error codes
	public static final String USA_AS_0001 = "USA_AS_0001"; // Split ratio error
	public static final String USA_AS_0002 = "USA_AS_0002"; // Split date empty
	public static final String USA_AS_0003 = "USA_AS_0003"; // Script name empty
	public static final String USA_AS_0004 = "USA_AS_0004"; // Bonus ratio error
	public static final String USA_AS_0005 = "USA_AS_0005"; // Bonus date empty
	public static final String USA_AS_0006 = "USA_AS_0006"; // Yahoo code empty
	public static final String USA_AS_0007 = "USA_AS_0007"; // ISIN empty
	public static final String USA_AS_0008 = "USA_AS_0008"; // CUSIP empty
	public static final String USA_AS_0009 = "USA_AS_0009"; // Category empty
	public static final String USA_AS_0010 = "USA_AS_0010"; // Sector empty
	public static final String USA_AS_0011 = "USA_AS_0011"; // Industry empty
	public static final String USA_AS_0012 = "USA_AS_0012"; // Purchase/Sell Date empty
	public static final String USA_AS_0013 = "USA_AS_0013"; // Purchase/Sell Quantity empty
	public static final String USA_AS_0014 = "USA_AS_0014"; // Purchase/Sell Price empty
	public static final String USA_AS_0015 = "USA_AS_0015"; // Purchase/Sell Commission empty
	public static final String USA_AS_0016 = "USA_AS_0016"; // purchase/Sell Transaction Fees empty
	public static final String USA_AS_0017 = "USA_AS_0017"; // Purchase/Sell Other Fees empty

	// United States of America Short term Investment
	public static final String USA_STI_0001 = "USA_STI_0001"; // Script name error
	public static final String USA_STI_0002 = "USA_STI_0002"; // yahoo code error
	public static final String USA_STI_0003 = "USA_STI_0003"; // Purchase Date error
	public static final String USA_STI_0004 = "USA_STI_0004"; // Purchase Quantity error
	public static final String USA_STI_0005 = "USA_STI_0005"; // Purchase Price error
	public static final String USA_STI_0006 = "USA_STI_0006"; // Purchase Commission error
	public static final String USA_STI_0007 = "USA_STI_0007"; // purchase Transaction Fees error
	public static final String USA_STI_0008 = "USA_STI_0008"; // Purchase Other Fees error

}
