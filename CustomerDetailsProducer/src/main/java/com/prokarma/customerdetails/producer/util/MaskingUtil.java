package com.prokarma.customerdetails.producer.util;

public class MaskingUtil {

	private MaskingUtil() {

	}

	private static final String REGEX_MASKING_PATTERN_BIRTHDATE =
			"((\"birthDate\":\")|(\\\\\"birthDate\\\\\":\\\\\"))(\\d{2}-\\d{2}-)";
	private static final String REGEX_MASKING_PATTERN_CUSTOMERNUMBER =
			"((\"customerNumber\":\".{6})|(\\\\\"customerNumber\\\\\":\\\\\".{6}))(.{4})";
	private static final String REGEX_MASKING_PATTERN_EMAIL =
			"((\"email\":\")|(\\\\\"email\\\\\":\\\\\"))(.{4})";



	private static final String REGEX_MASKING_VALUE_BIRTHDATE = "$1xx-xx-";
	private static final String REGEX_MASKING_VALUE_CUSTOMERNUMBER = "$1xxxx";
	private static final String REGEX_MASKING_VALUE_EMAIL = "$1xxxx";



	public static String getMaskedMessage(String jsonString) {
		return jsonString.replaceAll(REGEX_MASKING_PATTERN_BIRTHDATE, REGEX_MASKING_VALUE_BIRTHDATE)
				.replaceAll(REGEX_MASKING_PATTERN_CUSTOMERNUMBER, REGEX_MASKING_VALUE_CUSTOMERNUMBER)
				.replaceAll(REGEX_MASKING_PATTERN_EMAIL, REGEX_MASKING_VALUE_EMAIL);
	}



}
