package com.prokarma.customerdetails.consumer.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.prokarma.customerdetails.consumer.util.MaskingUtil;
import com.prokarma.customerdetails.consumer.util.ObjectMapperUtil;

@Aspect
@Component
public class LoggingAdvice {

  private static final Log LOG = LogFactory.getLog(LoggingAdvice.class);



  @Around("execution(* com.prokarma.customerdetails.consumer.listeners.KafkaConsumer.receive(..)) ||"
      + "execution(* org.springframework.data.repository.*.*(..))")
  public Object applicationLoggerConsumer(ProceedingJoinPoint pjp) throws Throwable {
    String methodName = pjp.getSignature().getName();
    String className = pjp.getSignature().getClass().getName();
    Object[] args = pjp.getArgs();
    if (LOG.isDebugEnabled())
      LOG.debug("Invoking Method " + className + "." + methodName + "()" + " with arguments: "
          + MaskingUtil.getMaskedMessage(
              ObjectMapperUtil.objectToJsonString(args[0]).replaceAll("\\\\\"", "\"")));
    Object object = pjp.proceed();
    if (LOG.isDebugEnabled())
      LOG.debug("Invoked Method " + className + "." + methodName + "()"
          + ", and received Response: " + MaskingUtil.getMaskedMessage(
              ObjectMapperUtil.objectToJsonString(object).replaceAll("\\\\\"", "\"")));
    return object;

  }



  @AfterThrowing(
      pointcut = "execution(* com.prokarma.customerdetails.consumer.service.CustomerDetailsConsumerServiceImpl.*(..))",
      throwing = "ex")
  public void logAfterThrowingAllMethods(Exception ex) {
    if (LOG.isDebugEnabled())
      LOG.debug(ex.getMessage());

  }

}
