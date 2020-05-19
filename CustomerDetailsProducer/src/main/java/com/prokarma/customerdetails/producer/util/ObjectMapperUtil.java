package com.prokarma.customerdetails.producer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prokarma.customerdetails.producer.exceptions.GeneralException;


public class ObjectMapperUtil {



	private ObjectMapperUtil() {

	}

	public static String objectToJsonString(Object inputRequest) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(inputRequest);
		} catch (JsonProcessingException e) {
			throw new GeneralException("GeneralException", e.getMessage(), "error");
		}
	}

}
