package com.prokarma.customerdetails.consumer.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {


  private ObjectMapperUtil() {

  }

  public static String objectToJsonString(Object inputRequest) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.writeValueAsString(inputRequest);
    } catch (Exception e) {
      return null;
    }

  }

}
