package com.prokarma.customerdetails.consumer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prokarma.customerdetails.consumer.exception.GeneralException;

public class ObjectMapperUtil {


	private ObjectMapperUtil() {

	}

	public static String objectToJsonString(Object inputRequest) {
		try {
			return new ObjectMapper().writeValueAsString(inputRequest);
		} catch (JsonProcessingException e) {
			throw new GeneralException("GeneralException", e.getMessage(), "error");
		}
	}

}
