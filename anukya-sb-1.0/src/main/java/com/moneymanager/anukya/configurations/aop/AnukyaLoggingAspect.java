package com.moneymanager.anukya.configurations.aop;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.moneymanager.anukya.exception.AnukyaErrorConstants;
import com.moneymanager.anukya.exception.AnukyaException;
import com.moneymanager.anukya.exception.ErrorDetails;
import com.moneymanager.anukya.utils.AnukyaConstants;

import lombok.extern.log4j.Log4j2;

@Aspect
@Log4j2
@Component
public class AnukyaLoggingAspect {

	@Around("execution(* com.moneymanager.anukya.security.AnukyaAuthorization.*(..)) || "
			+ "execution (* com.moneymanager.anukya..controller..*(..)) || "
			+ "execution (* com.moneymanager.anukya.services..impl..*(..)) || "
			+ "execution (* com.moneymanager.anukya.data..impl..*(..)) || "
			+ "execution (* com.moneymanager.anukya..servicecall..*(..))")
	public Object anukyaLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

		String packageName = methodSignature.getDeclaringType().getPackageName();
		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();

		log.info(
				"PRAVESHA - " + packageName + AnukyaConstants.PERIOD + className + AnukyaConstants.PERIOD + methodName);
		StopWatch stopWatch = new StopWatch();

		Object result;
		stopWatch.start();

		try {
			result = proceedingJoinPoint.proceed();
			stopWatch.stop();
			log.info("Execution time of " + packageName + AnukyaConstants.PERIOD + className + AnukyaConstants.PERIOD
					+ methodName + " is " + stopWatch.getTotalTimeMillis() + "ms");
		} catch (Exception e) {
			stopWatch.stop();
			log.error("Execution time, with exception, of " + packageName + AnukyaConstants.PERIOD + className
					+ AnukyaConstants.PERIOD + methodName + " is " + stopWatch.getTotalTimeMillis() + "ms");

			if (!(e instanceof AnukyaException)) {
				log.error(e);
				List<ErrorDetails> errorDetailsList = new ArrayList<>();
				ErrorDetails errorDetails = new ErrorDetails();
				errorDetails.setErrorCode(AnukyaErrorConstants.GE_0001);
				errorDetails.setErrorMessage(e.getMessage());
				errorDetailsList.add(errorDetails);

				throw new AnukyaException("Unknown Error", errorDetailsList, AnukyaErrorConstants.INTERNAL_SERVER_ERROR,
						ThreadContext.get(AnukyaConstants.TRACE_ID));
			}
			throw e;
		}

		log.info("NIRGAMANA - " + packageName + AnukyaConstants.PERIOD + className + AnukyaConstants.PERIOD
				+ methodName);
		return result;
	}
}
