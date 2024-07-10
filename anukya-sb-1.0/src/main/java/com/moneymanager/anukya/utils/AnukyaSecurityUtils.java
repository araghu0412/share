package com.moneymanager.anukya.utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.ErrorDetails;

@Component
public class AnukyaSecurityUtils {

	@Value("${RSA.PUBLIC.KEY}")
	private String rsaPublicKey;

	@Value("${RSA.PRIVATE.KEY}")
	private String rsaPrivateKey;

	public String rsaEncryptData(String data) throws AnukyaException {

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(rsaPublicKey.getBytes()));

		try {
			KeyFactory keyFactory = KeyFactory.getInstance(AnukyaConstants.RSA);
			PublicKey publicKey = keyFactory.generatePublic(keySpec);
			Cipher cipher = Cipher.getInstance(AnukyaConstants.RSA_PADDING);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			return new String(Base64.getEncoder().encode(cipher.doFinal(data.getBytes())));
		} catch (Exception e) {
			List<ErrorDetails> error = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.SE_0001);
			errorDetails.setErrorMessage("Failed to encrypt data");
			error.add(errorDetails);

			throw new AnukyaException("Error while encrypting data", error, AnukyaErrorConstants.INTERNAL_SERVER_ERROR,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}

	public String rsaDecryptData(String data) throws AnukyaException {

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(rsaPrivateKey.getBytes()));

		try {
			KeyFactory keyFactor = KeyFactory.getInstance(AnukyaConstants.RSA);
			PrivateKey privateKey = keyFactor.generatePrivate(keySpec);
			Cipher cipher = Cipher.getInstance(AnukyaConstants.RSA_PADDING);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			return new String(cipher.doFinal(Base64.getDecoder().decode(data.getBytes())));
		} catch (Exception e) {
			List<ErrorDetails> error = new ArrayList<>();

			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode(AnukyaErrorConstants.SE_0002);
			errorDetails.setErrorMessage("Failed to decrypt data");
			error.add(errorDetails);

			throw new AnukyaException("Error while decrypting data", error, AnukyaErrorConstants.INTERNAL_SERVER_ERROR,
					ThreadContext.get(AnukyaConstants.TRACE_ID));
		}
	}
}
