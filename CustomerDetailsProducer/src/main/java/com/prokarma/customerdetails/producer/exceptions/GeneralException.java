package com.prokarma.customerdetails.producer.exceptions;


public class GeneralException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private final String errorType;
  private final String message;
  private final String status;

  public GeneralException(String errorType, String message, String status) {
    super(message);
    this.errorType = errorType;
    this.message = message;
    this.status = status;
  }



  public String getErrorType() {
    return errorType;
  }



  public String getMessage() {
    return message;
  }

  public String getStatus() {
    return status;
  }



}
