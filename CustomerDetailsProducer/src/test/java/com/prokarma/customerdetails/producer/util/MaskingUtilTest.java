package com.prokarma.customerdetails.producer.util;


import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prokarma.customerdetails.producer.model.CustomerDetailsRequest;

class MaskingUtilTest {

	private CustomerDetailsRequest customerDetailsRequest;


	@BeforeEach
	public void setUp() {
		customerDetailsRequest = new CustomerDetailsRequest();
		customerDetailsRequest.setBirthDate("29-06-1991");
		customerDetailsRequest.setCustomerNumber("1234567890");
		customerDetailsRequest.setEmail("abc@gmail.com");
	}

	@Test
	void test_getMaskedMessage_whenBirthDateIsSetInCustomerDetailsRequest_expectedMaskedBirthDate()
			throws JsonMappingException, JsonProcessingException {
		CustomerDetailsRequest customerDetailsRequestMasked = new ObjectMapper().readValue(
				MaskingUtil.getMaskedMessage(ObjectMapperUtil.objectToJsonString(customerDetailsRequest)),
				CustomerDetailsRequest.class);
		assertEquals("xx-xx-1991", customerDetailsRequestMasked.getBirthDate());
	}

	@Test
	void test_getMaskedMessage_whenCustomerNumberIsSetInCustomerDetailsRequest_expectedMaskedCustomerNumber()
			throws JsonMappingException, JsonProcessingException {
		CustomerDetailsRequest customerDetailsRequestMasked = new ObjectMapper().readValue(
				MaskingUtil.getMaskedMessage(ObjectMapperUtil.objectToJsonString(customerDetailsRequest)),
				CustomerDetailsRequest.class);
		assertEquals("123456xxxx", customerDetailsRequestMasked.getCustomerNumber());
	}

	@Test
	void test_getMaskedMessage_whenEmailIsSetInCustomerDetailsRequest_expectedMaskedEmail()
			throws JsonMappingException, JsonProcessingException {
		CustomerDetailsRequest customerDetailsRequestMasked = new ObjectMapper().readValue(
				MaskingUtil.getMaskedMessage(ObjectMapperUtil.objectToJsonString(customerDetailsRequest)),
				CustomerDetailsRequest.class);
		assertEquals("xxxxgmail.com", customerDetailsRequestMasked.getEmail());
	}

}
