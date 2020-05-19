package com.prokarma.customerdetails.consumer.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.prokarma.customerdetails.consumer.util.MaskingUtil;
import com.prokarma.customerdetails.consumer.util.ObjectMapperUtil;



@Aspect
@Component
public class LoggingAdvice {



	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAdvice.class);


	/**
	 * Point cut expressions that matches all methods of CustomerDetailsConsumer and save method of
	 * Data Repository classes
	 */
	@Pointcut("execution(* com.prokarma.customerdetails.consumer.listeners.CustomerDetailsConsumer.*(..))"
			+ "|| execution(* com.prokarma.customerdetails.consumer.repository.*.save(..))")
	public void logDebugAroundPointcut() {
		// Method is empty as this is just a point cut, the implementations are in the advices.
	}

	/**
	 * Point cut expression that matches all the methods of CustomerDetailsConsumer class for logging
	 * unhandled errors
	 */
	@Pointcut("execution(* com.prokarma.customerdetails.consumer.listeners.CustomerDetailsConsumer.*(..))")
	public void logErrorAfterThrowingPointcut() {
		// Method is empty as this is just a point cut, the implementations are in the advices.
	}



	@Around("logDebugAroundPointcut()")
	public Object applicationLoggerConsumer(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Enter: {}.{}() with argument[s] = {}",
					proceedingJoinPoint.getSignature().getDeclaringTypeName(),
					proceedingJoinPoint.getSignature().getName(), MaskingUtil.getMaskedMessage(
							ObjectMapperUtil.objectToJsonString(proceedingJoinPoint.getArgs())));
		Object result = proceedingJoinPoint.proceed();
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Exit: {}.{}() with result = {}",
					proceedingJoinPoint.getSignature().getDeclaringTypeName(),
					proceedingJoinPoint.getSignature().getName(),
					MaskingUtil.getMaskedMessage(ObjectMapperUtil.objectToJsonString(result)));
		return result;
	}


	@AfterThrowing(pointcut = "logErrorAfterThrowingPointcut()", throwing = "ex")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
		LOGGER.error("Exception in {}.{}() with cause = {}",
				joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
				ex.getMessage());
	}

}
