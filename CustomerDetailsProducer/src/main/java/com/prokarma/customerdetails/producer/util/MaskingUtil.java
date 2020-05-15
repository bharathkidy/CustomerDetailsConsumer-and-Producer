package com.prokarma.customerdetails.producer.util;

public class MaskingUtil {

  private MaskingUtil() {

  }

  private static final String MASKING_RULE_BIRTHDATE = "(\"birthDate\":\")(\\d{2}-\\d{2}-)";
  private static final String MASKING_RULE_CUSTOMERNUMBER = "(\"customerNumber\":\".{6})(.{4}\")";
  private static final String MASKING_RULE_EMAIL = "(\"email\":\")(.{4})";



  private static final String MASKING_VALUE_BIRTHDATE = "$1xx-xx-";
  private static final String MASKING_VALUE_CUSTOMERNUMBER = "$1xxxx";
  private static final String MASKING_VALUE_EMAIL = "$1xxxx";



  public static String getMaskedMessage(String jsonString) {
    return jsonString.replaceAll(MASKING_RULE_BIRTHDATE, MASKING_VALUE_BIRTHDATE)
        .replaceAll(MASKING_RULE_CUSTOMERNUMBER, MASKING_VALUE_CUSTOMERNUMBER)
        .replaceAll(MASKING_RULE_EMAIL, MASKING_VALUE_EMAIL);
  }



}
