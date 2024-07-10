package com.moneymanager.anukya.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.ErrorDetails;

@Component
public class AnukyaDataUtils {

	public static final String MESSAGE = "Message: ";
	public static final String REASON_INTERNAL_ERROR = "Reason: Internal error";

	private Logger log = LoggerFactory.getLogger(AnukyaDataUtils.class);

	public void createDirectory(File file) {
		file.mkdir();
	}

	public void copyDirectory(File firstDirectory, File secondDirectory) throws AnukyaException {
		try {
			FileUtils.copyDirectory(firstDirectory, secondDirectory);
		} catch (IOException e) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while copying directory -> ");
			logMessage.append(MESSAGE + e.getMessage() + " | ");
			logMessage.append(REASON_INTERNAL_ERROR);
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();
			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.FE_0003);
			errorDetails.setErrorMessage("Copying error");
			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while copying directory", errorDetailsList,
					AnukyaErrorConstants.INTERNAL_SERVER_ERROR, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	public void deleteDirectory(File directory) throws AnukyaException {
		try {
			FileUtils.deleteDirectory(directory);
		} catch (IOException e) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while deleting a directory -> ");
			logMessage.append(MESSAGE + e.getMessage() + " | ");
			logMessage.append(REASON_INTERNAL_ERROR);
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();
			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.FE_0002);
			errorDetails.setErrorMessage("Deleting error");
			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while deleting a directory", errorDetailsList,
					AnukyaErrorConstants.INTERNAL_SERVER_ERROR, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}

	}

	public void deleteFile(File file) throws AnukyaException {
		try {
			Files.delete(file.toPath());
		} catch (IOException e) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while deleting a file -> ");
			logMessage.append(MESSAGE + e.getMessage() + " | ");
			logMessage.append(REASON_INTERNAL_ERROR);
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();
			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.FE_0002);
			errorDetails.setErrorMessage("Deleting error");
			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while deleting a file", errorDetailsList,
					AnukyaErrorConstants.INTERNAL_SERVER_ERROR, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	public void createOrUpdateFile(File file, Object object) throws AnukyaException {

		try {
			PrintWriter writer = new PrintWriter(file);
			writer.print(AnukyaConstants.EMPTY_STRING);
			writer.close();

			ObjectMapper mapper = new ObjectMapper();

			String objectToString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);

			writer = new PrintWriter(file);
			writer.print(objectToString);
			writer.close();
		} catch (FileNotFoundException | JsonProcessingException e) {
			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Exception occured while creating or updating file -> ");
			logMessage.append(MESSAGE + e.getMessage() + " | ");
			logMessage.append(REASON_INTERNAL_ERROR);
			log.error(logMessage.toString());

			List<ErrorDetails> errorDetailsList = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.FE_0004);
			errorDetails.setErrorMessage("Creating/Updating file error");

			errorDetailsList.add(errorDetails);

			throw new AnukyaException("Error while updating token details in user-authentication", errorDetailsList,
					AnukyaErrorConstants.INTERNAL_SERVER_ERROR, ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	public boolean isFileExists(File file) {

		return file.exists();
	}
}
