
package com.prokarma.customerdetails.producer.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.prokarma.customerdetails.producer.model.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  private static final String ERROR = "error";

  private static final String INVALID_REQUEST_EXCEPTION = "InvalidRequestException";

  private static final String TOKEN_EXCEPTION = "TokenException";



  @ExceptionHandler(
      value = {HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
  public ResponseEntity<Object> invalidRequestxception(Exception ex) {
    if (ex instanceof MethodArgumentNotValidException) {
      MethodArgumentNotValidException methodArgumentNotValidException =
          (MethodArgumentNotValidException) ex;
      StringBuilder message = new StringBuilder();
      for (FieldError error : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
        message.append("Invalid Field :" + error.getField() + ",");
      }

      ErrorResponse errorResponse = new ErrorResponse().status(ERROR).message(message.toString())
          .errorType(INVALID_REQUEST_EXCEPTION);

      log.error("{}", message);
      return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    } else {
      ErrorResponse errorResponse = new ErrorResponse().status(ERROR)
          .message(ex.getLocalizedMessage()).errorType(INVALID_REQUEST_EXCEPTION);

      log.error("{}", ex.getLocalizedMessage());
      return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
  }



  @ExceptionHandler(value = {AuthenticationException.class})
  public ResponseEntity<Object> tokenException(Exception e) {
    String message = e.getMessage();
    log.error(message);
    return new ResponseEntity<>(
        new ErrorResponse().message(message).status(ERROR).errorType(TOKEN_EXCEPTION),
        HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(value = {GeneralException.class})
  public ResponseEntity<Object> generalException(GeneralException e) {
    String message = e.getMessage();
    log.error(message);
    return new ResponseEntity<>(new ErrorResponse().message(e.getMessage()).status(e.getStatus())
        .errorType(e.getErrorType()), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}

