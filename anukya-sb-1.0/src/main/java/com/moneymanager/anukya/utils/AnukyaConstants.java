package com.moneymanager.anukya.utils;

public class AnukyaConstants {

	private AnukyaConstants() {
		// private constructor
	}

	// Logging Custom fields
	public static final String TRACE_ID = "traceId";
	public static final String USER_ID = "userId";

	public static final String TOTAL_CONSTANT = "total";

	public static final String EMPTY_STRING = "";
	public static final String SPACE = " ";
	public static final String PIPE_SEPARATOR = "|";
	public static final String RATIO_CONSTANT = ":";
	public static final String PERIOD = ".";
	public static final String FORWARD_SLASH = "/";
	public static final String PLUS_CONSTANT = "+";
	public static final String MINUS_CONSTANT = "-";
	public static final String UNDERSCORE = "_";
	public static final String NEW_LINE = "\n";
	public static final String COMMA_CONSTANT = ",";
	public static final String SINGLE_QUOTE = "'";

	public static final String NUMBER_0 = "0";
	public static final String NUMBER_1 = "1";
	public static final String NUMBER_2 = "2";
	public static final String NUMBER_100 = "100";
	public static final String NUMBER_356 = "356";
	public static final String NUMBER_124 = "124";
	public static final String NUMBER_840 = "840";
	public static final String NUMBER_20000 = "20000";

	public static final String BHA = "BHA";
	public static final String EQ = "EQ";
	public static final String MF = "MF";

	public static final String LOGGED_IN_USER_EMAIL_ID = "loggedInUserEmailId";
	public static final String AUTHORIZATION = "Authorization";

	public static final String DATE_FORMAT_DD_MMM_YYYY = "dd-MMM-yyyy";
	public static final String DATE_FORMAT_DD_MM_YYYY = "dd-MM-yyyy";
	public static final String FLOAT_0_DECIMAL = "%.0f";
	public static final String FLOAT_2_DECIMAL = "%.2f";
	public static final String FLOAT_4_DECIMAL = "%.4f";
	public static final String FLOAT_6_DECIMAL = "%.6f";

	public static final String YES = "Yes";
	public static final String NO = "No";

	public static final String ON_CONSTANT = "on";
	public static final String AND_CONSTANT = "and";

	// UI fields
	public static final String BOUGHT_CONSTANT = "bought";
	public static final String SOLD_CONSTANT = "sold";

	// RSA
	public static final String RSA = "RSA";
	public static final String RSA_PADDING = "RSA/ECB/PKCS1Padding";

	// directory names
	public static final String COMMON_DIRECTORY = "common";
	public static final String USERS_DIRECTORY = "users";
	public static final String DATA_PROCESSING_DIRECTORY = "data-processing";
	public static final String BACKUP_DIRECTORY = "backup";
	public static final String USER_DETAILS_DIRECTORY = "user-details";
	public static final String SERVICES_DETAILS_DIRECTORY = "services-details";
	public static final String PURCHASE_DIRECTORY_CONSTANT = "purchase";
	public static final String SOLD_DIRECTORY_CONSTANT = "sold";
	public static final String MAIN_DIRECTORY = "main";
	public static final String UPDATE_DIRECTORY = "update";
	public static final String SINGLE_DIRECTORY = "single";
	public static final String SHORT_TERM_INVESTMENT_DIRECTORY = "short-term-investment";
	public static final String DOCUMENTS_DIRECTORY = "Documents";
	public static final String PRE_DATA_DIRECTORY = "pre-data";

	// json File names
	public static final String COUNTRY_DETAILS_JSON = "country-details.json";
	public static final String SERVICES_OFFERED_DETAILS_JSON = "services-offered-details.json";
	public static final String USER_DETAILS_JSON = "user-details.json";
	public static final String COUNTRIES_AND_SERVICES_OFFERED_JSON = "countries-and-services-offered.json";
	public static final String GENDER_LIST_JSON = "gender-list.json";
	public static final String SUB_SERVICES_DETAILS_JSON = "sub-services-details.json";
	public static final String SUB_SERVICES_BY_SERVICE_CODE_DETAILS_JSON = "sub-services-by-service-code-details.json";
	public static final String USER_AUTHENTICATION_JSON = "user-authentication.json";
	public static final String NON_CONSOLIDATED_JSON = "non-consolidated.json";
	public static final String CONSOLIDATED_JSON = "consolidated.json";
	public static final String SHORT_TERM_INVESTMENT_JSON = "short-term-investment.json";
	public static final String JSON_EXTENSION = ".json";

	// Path
	public static final String SHORT_TERM_INVESTMENT_BHA_EQ_MAIN_DIRECTORY = "/services-details/BHA/EQ/short-term-investment/main";
	public static final String SHORT_TERM_INVESTMENT_BHA_EQ_UPDATE_DIRECTORY = "/services-details/BHA/EQ/short-term-investment/update";
	public static final String SHORT_TERM_INVESTMENT_BHA_EQ_MAIN_BACKUP_DIRECTORY = "/services-details/BHA/EQ/short-term-investment/main-backup";
	public static final String BHA_EQ_PURCHASE_MAIN_DIRECTORY = "/services-details/BHA/EQ/purchase/main";
	public static final String BHA_EQ_PURCHASE_UPDATE_DIRECTORY = "/services-details/BHA/EQ/purchase/update";
	public static final String BHA_EQ_PURCHASE_MAIN_BACKUP_DIRECTORY = "/services-details/BHA/EQ/purchase/main-backup";
	public static final String BHA_EQ_SOLD_MAIN_DIRECTORY = "/services-details/BHA/EQ/sold/main";
	public static final String BHA_EQ_SOLD_UPDATE_DIRECTORY = "/services-details/BHA/EQ/sold/update";
	public static final String BHA_EQ_SOLD_MAIN_BACKUP_DIRECTORY = "/services-details/BHA/EQ/sold/main-backup";
	public static final String USA_EQ_PURCHASE_MAIN_DIRECTORY = "/services-details/USA/EQ/purchase/main";
	public static final String USA_EQ_PURCHASE_UPDATE_DIRECTORY = "/services-details/USA/EQ/purchase/update";
	public static final String USA_EQ_PURCHASE_MAIN_BACKUP_DIRECTORY = "/services-details/USA/EQ/purchase/main-backup";
	public static final String USA_EQ_SOLD_MAIN_DIRECTORY = "/services-details/USA/EQ/sold/main";
	public static final String USA_EQ_SOLD_UPDATE_DIRECTORY = "/services-details/USA/EQ/sold/update";
	public static final String USA_EQ_SOLD_MAIN_BACKUP_DIRECTORY = "/services-details/USA/EQ/sold/main-backup";
	public static final String SHORT_TERM_INVESTMENT_USA_EQ_MAIN_DIRECTORY = "/services-details/USA/EQ/short-term-investment/main";
	public static final String SHORT_TERM_INVESTMENT_USA_EQ_UPDATE_DIRECTORY = "/services-details/USA/EQ/short-term-investment/update";
	public static final String SHORT_TERM_INVESTMENT_USA_EQ_MAIN_BACKUP_DIRECTORY = "/services-details/USA/EQ/short-term-investment/main-backup";
	public static final String USER_DETAILS_MAIN_DIRECTORY = "/user-details/main";
	public static final String USER_DETAILS_UPDATE_DIRECTORY = "/user-details/update";
	public static final String USER_DETAILS_MAIN_BACKUP_DIRECTORY = "/user-details/main-backup";

	// Exception constants
	public static final String BASE_SERVICE_OPT_ERROR = "Please select at least one service for ";

	// API constants
	public static final String COOKIE = "Cookie";

	// Bhaaratha
	public static final String BSE = "BSE";
	public static final String NSE = "NSE";
	public static final String API_STOCK_EXCHANGE = "@stockExchange";
	public static final String API_SCRIPT_CODE = "@scriptCode";
	public static final String BHAARATHA_BOUGHT_SHARES_LIST = "bhaarathaBoughtSharesList";
	public static final String BHAARATHA_SOLD_SHARES_LIST = "bhaarathaSoldSharesList";
	public static final String BHAARATHA_CONSOLIDATED_CALCULATION_MAP = "bhaarathaConsolidatedCalculationMap";
	public static final String BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_QUANTITY = "bhaarathaConsolidatedCalculationPurchasedQuantity";
	public static final String BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_PRICE = "bhaarathaConsolidatedCalculationPurchasePrice";
	public static final String BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_GROSS_TOTAL = "bhaarathaConsolidatedCalculationPurchaseGrossTotal";
	public static final String BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_BROKERAGE = "bhaarathaConsolidatedCalculationPurchaseBrokerage";
	public static final String BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_STT = "bhaarathaConsolidatedCalculationPurchaseSTT";
	public static final String BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_EXPENDITURE = "bhaarathaConsolidatedCalculationPurchaseExpenditure";
	public static final String BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_NON_EXPENDITURE = "bhaarathaConsolidatedCalculationPurchaseNonExpenditure";
	public static final String BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_TOTAL = "bhaarathaConsolidatedCalculationPurchaseTotal";
	public static final String BHAARATHA_CONSOLIDATED_CALCULATION_PURCHASE_NET_PRICE_PER_UNIT = "bhaarathaConsolidatedCalculationPurchaseNetPricePerUnit";
	public static final String LATEST_TRADING_DETAILS_MAP = "latestTradingDetailsMap";
	public static final String TOTAL_SOLD_QUANTITY_MAP = "totalSoldQuantityMap";
	public static final String TOTAL_SOLD_GROSS_TOTAL_MAP = "totalSoldGrossTotalMap";

	// BSE India API
	public static final String BSE_DIVIDEND_TABLE = "Table";
	public static final String BSE_DIVIDEND_AMOUNT = "Amount";
	public static final String BSE_DIVIDEND_BCRD_FORM = "BCRD_from";

	// Moneycontrol API
	public static final String MONEYCONTROL = "moneycontrol";
	public static final String MONEYCONTROL_API_CODE = "code";
	public static final String MONEYCONTROL_API_DATA = "data";
	public static final String MONEYCONTROL_API_NSE_ID = "NSEID";
	public static final String MONEYCONTROL_API_BSE_ID = "BSEID";
	public static final String MONEYCONTROL_API_SC_FULL_NM = "SC_FULLNM";
	public static final String MONEYCONTROL_API_PRICE_CURRENT = "pricecurrent";
	public static final String MONEYCONTROL_API_PRICE_CHANGE = "pricechange";
	public static final String MONEYCONTROL_API_PRICE_PERCENT_CHANGE = "pricepercentchange";
	public static final String MONEYCONTROL_API_OPN = "OPN";
	public static final String MONEYCONTROL_API_PRICE_PREV_CLOSE = "priceprevclose";
	public static final String MONEYCONTROL_API_HP = "HP";
	public static final String MONEYCONTROL_API_LP = "LP";
	public static final String MONEYCONTROL_API_52H = "52H";
	public static final String MONEYCONTROL_API_52L = "52L";
	public static final String MONEYCONTROL_API_DISPID = "DISPID";
	public static final String MONEYCONTROL_SC_TTM_CONS = "sc_ttm_cons";
	public static final String MONEYCONTROL_PECONS = "PECONS";
	public static final String MONEYCONTROL_PBCONS = "PBCONS";
	public static final String MONEYCONTROL_DYCONS = "DYCONS";
	public static final String MONEYCONTROL_BHA_PE = "IND_PE";

	// United States of America
	public static final String UNITED_STATES_OF_AMERICA_BOUGHT_SHARES_LIST = "unitedStatesOfAmericaBoughtSharesList";
	public static final String UNITED_STATES_OF_AMERICA_SOLD_SHARES_LIST = "unitedStatesOfAmericaSoldSharesList";
	public static final String UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_MAP = "unitedStatesOfAmericaConsolidatedCalculationMap";
	public static final String UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_QUANTITY = "unitedStatesOfAmericaConsolidatedCalculationPurchasedQuantity";
	public static final String UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_PRICE = "unitedStatesOfAmericaConsolidatedCalculationPurchasePrice";
	public static final String UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_GROSS_TOTAL = "unitedStatesOfAmericaConsolidatedCalculationPurchaseGrossTotal";
	public static final String UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_COMMISSION = "unitedStatesOfAmericaConsolidatedCalculationPurchaseCommission";
	public static final String UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_TRANSACTION_FEES = "unitedStatesOfAmericaConsolidatedCalculationPurchaseTransactionFees";
	public static final String UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_OTHER_FEES = "unitedStatesOfAmericaConsolidatedCalculationPurchaseOtherFees";
	public static final String UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_TOTAL = "unitedStatesOfAmericaConsolidatedCalculationPurchaseTotal";
	public static final String UNITED_STATES_OF_AMERICA_CONSOLIDATED_CALCULATION_PURCHASE_NET_PRICE_PER_UNIT = "unitedStatesOfAmericaConsolidatedCalculationPurchaseNetPricePerUnit";

	// Yahoo API API
	public static final String YAHOO_FINANCE = "yahooFinance";
	public static final String YAHOO_FINANCE_QUOTE_RESPONSE = "quoteResponse";
	public static final String YAHOO_FINANCE_QUOTE_RESULT = "result";
	public static final String YAHOO_FINANCE_QUOTE_SYMBOL = "symbol";
	public static final String YAHOO_FINANCE_QUOTE_LONG_NAME = "longName";
	public static final String YAHOO_FINANCE_QUOTE_REGULAR_MARKET_PRICE = "regularMarketPrice";
	public static final String YAHOO_FINANCE_QUOTE_REGULAR_MARKET_PREVIOUS_CLOSE = "regularMarketPreviousClose";
	public static final String YAHOO_FINANCE_QUOTE_REGULAR_MARKET_OPEN = "regularMarketOpen";
	public static final String YAHOO_FINANCE_QUOTE_REGULAR_MARKET_CHANGE = "regularMarketChange";
	public static final String YAHOO_FINANCE_QUOTE_REGULAR_MARKET_CHANGE_PERCENTAGE = "regularMarketChangePercent";
	public static final String YAHOO_FINANCE_QUOTE_FIFTY_TWO_WEEK_HIGH = "fiftyTwoWeekHigh";
	public static final String YAHOO_FINANCE_QUOTE_FIFTY_TWO_WEEK_LOW = "fiftyTwoWeekLow";
	public static final String YAHOO_FINANCE_QUOTE_REGULAR_MARKET_DAY_HIGH = "regularMarketDayHigh";
	public static final String YAHOO_FINANCE_QUOTE_REGULAR_MARKET_DAY_LOW = "regularMarketDayLow";
	public static final String YAHOO_FINANCE_DIVIDEND_CHART = "chart";
	public static final String YAHOO_FINANCE_DIVIDEND_RESULT = "result";
	public static final String YAHOO_FINANCE_DIVIDEND_EVENTS = "events";
	public static final String YAHOO_FINANCE_DIVIDEND_DIVIDENDS = "dividends";
	public static final String YAHOO_FINANCE_DIVIDEND_DATE = "date";
	public static final String YAHOO_FINANCE_DIVIDEND_AMOUNT = "amount";

	// Bulk Upload Constants
	public static final String BUY = "BUY";
	public static final String SELL = "SELL";
	public static final String ROW = "ROW";
	public static final String BHAARATHA_SHARES_BULK_UPLOAD_FILE_NAME = "Bhaaratha-Shares-Bulk-Upload.xlsm";
	public static final String TRANSACTIONS = "Transactions";

	// http status
	public static final String STATUS_201 = "201";

	// User profile Update Countries and Services
	public static final String COUNTRY_SERVICE_UPDATE_ALL_SERVICES_TERMINATED = "<li><b>All</b> opted services will be <b>terminated</b></li>";
	public static final String COUNTRY_SERVICE_UPDATE_IMPORTANT_WARNING = "<li><b>This cannot be retrieved</b></li><li>However, these services can be opted anytime later and details can be added again</li>";
	public static final String COUNTRY_SERVICE_UPDATE_ONE_SERVICE_TERMINATED_1 = "<li>Currently opted <b>";
	public static final String COUNTRY_SERVICE_UPDATE_ONE_SERVICE_TERMINATED_2 = "</b> service will be <b>terminated</b></li>";

	// HTML Constants
	public static final String BR_CONSTANT = "</br>";
	public static final String UL_OPEN_CONSTANT = "<ul>";
	public static final String UL_CLOSE_CONSTANT = "</ul>";
	public static final String LI_OPEN_CONSTANT = "<li>";
	public static final String LI_CLOSE_CONSTANT = "</li>";
}
