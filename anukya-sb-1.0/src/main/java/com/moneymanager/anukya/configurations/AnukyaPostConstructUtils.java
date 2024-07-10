package com.moneymanager.anukya.configurations;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneymanager.anukya.model.CountriesAndServicesOffered;
import com.moneymanager.anukya.model.CountryDetails;
import com.moneymanager.anukya.model.Gender;
import com.moneymanager.anukya.model.AnukyaInitialData;
import com.moneymanager.anukya.model.ServicesOfferedDetails;
import com.moneymanager.anukya.model.SubServicesByServiceCodeDetails;
import com.moneymanager.anukya.model.SubServicesDetails;
import com.moneymanager.anukya.utils.AnukyaConstants;

@Component
public class AnukyaPostConstructUtils {

	@Value("${DATABASE.BASE.LOCATION}")
	private String databaseBaseLocation;

	public AnukyaInitialData getAnukyaInitialData() throws IOException {

		AnukyaInitialData initialData = new AnukyaInitialData();

		// Country Details
		File file = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.COMMON_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.PRE_DATA_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.COUNTRY_DETAILS_JSON);

		String jsonString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

		ObjectMapper mapper = new ObjectMapper();
		List<CountryDetails> countryDetailsList = mapper.readValue(jsonString,
				new TypeReference<List<CountryDetails>>() {
				});

		Map<String, CountryDetails> countryDetailsMap = new LinkedHashMap<>();
		countryDetailsList.forEach(countryDetail -> countryDetailsMap.put(countryDetail.getCountryId(), countryDetail));

		initialData.setCountryDetailsMap(countryDetailsMap);

		// Services Offered Details
		file = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.COMMON_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.PRE_DATA_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SERVICES_OFFERED_DETAILS_JSON);
		jsonString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

		mapper = new ObjectMapper();
		List<ServicesOfferedDetails> servicesOfferedDetailsList = mapper.readValue(jsonString,
				new TypeReference<List<ServicesOfferedDetails>>() {
				});

		Map<String, ServicesOfferedDetails> servicesOfferedDetailsMap = new LinkedHashMap<>();
		servicesOfferedDetailsList.forEach(servicesOfferedDetail -> servicesOfferedDetailsMap
				.put(servicesOfferedDetail.getServiceId(), servicesOfferedDetail));

		initialData.setServicesOfferedDetailsMap(servicesOfferedDetailsMap);

		// Countries and Services offered details
		file = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.COMMON_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.PRE_DATA_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.COUNTRIES_AND_SERVICES_OFFERED_JSON);
		jsonString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

		mapper = new ObjectMapper();
		List<CountriesAndServicesOffered> countriesAndServicesOfferedDetailsList = mapper.readValue(jsonString,
				new TypeReference<List<CountriesAndServicesOffered>>() {
				});

		Map<String, CountriesAndServicesOffered> countriesAndServicesOfferedDetailsMap = new LinkedHashMap<>();
		countriesAndServicesOfferedDetailsList
				.forEach(countriesAndServicesOfferedDetail -> countriesAndServicesOfferedDetailsMap
						.put(countriesAndServicesOfferedDetail.getCountryId(), countriesAndServicesOfferedDetail));

		initialData.setCountriesAndServicesOfferedDetailsMap(countriesAndServicesOfferedDetailsMap);

		// Gender details
		file = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.COMMON_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.PRE_DATA_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.GENDER_LIST_JSON);
		jsonString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

		mapper = new ObjectMapper();
		List<Gender> genderDetailsList = mapper.readValue(jsonString, new TypeReference<List<Gender>>() {
		});

		Map<String, Gender> genderDetailsMap = new LinkedHashMap<>();
		genderDetailsList.forEach(genderDetail -> genderDetailsMap.put(genderDetail.getGenderId(), genderDetail));

		initialData.setGenderDetailsMap(genderDetailsMap);

		// Sub Services Details
		file = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.COMMON_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.PRE_DATA_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SUB_SERVICES_DETAILS_JSON);
		jsonString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

		mapper = new ObjectMapper();
		List<SubServicesDetails> subServicesDetailsList = mapper.readValue(jsonString,
				new TypeReference<List<SubServicesDetails>>() {
				});

		Map<String, SubServicesDetails> subServicesDetailsMap = new LinkedHashMap<>();
		subServicesDetailsList.forEach(
				subServicesDetail -> subServicesDetailsMap.put(subServicesDetail.getSubServiceId(), subServicesDetail));

		initialData.setSubServicesDetailsMap(subServicesDetailsMap);

		// Sub Services details by service code
		file = new File(databaseBaseLocation + AnukyaConstants.FORWARD_SLASH + AnukyaConstants.COMMON_DIRECTORY
				+ AnukyaConstants.FORWARD_SLASH + AnukyaConstants.PRE_DATA_DIRECTORY + AnukyaConstants.FORWARD_SLASH
				+ AnukyaConstants.SUB_SERVICES_BY_SERVICE_CODE_DETAILS_JSON);
		jsonString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

		mapper = new ObjectMapper();
		List<SubServicesByServiceCodeDetails> subServicesByServiceCodeDetailsList = mapper.readValue(jsonString,
				new TypeReference<List<SubServicesByServiceCodeDetails>>() {
				});

		Map<String, SubServicesByServiceCodeDetails> subServicesByServiceCodeDetailsMap = new LinkedHashMap<>();
		subServicesByServiceCodeDetailsList.forEach(subServicesByServiceCodeDetail -> subServicesByServiceCodeDetailsMap
				.put(subServicesByServiceCodeDetail.getServiceCode(), subServicesByServiceCodeDetail));

		initialData.setSubServicesByServiceCodeDetailsMap(subServicesByServiceCodeDetailsMap);

		return initialData;
	}
}
