package com.prokarma.customerdetails.consumer.util;


import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prokarma.customerdetails.consumer.entity.AuditLogEntity;
import com.prokarma.customerdetails.consumer.model.CustomerDetailsRequest;

class MaskingUtilTest {

	private CustomerDetailsRequest customerDetailsRequest;

	private AuditLogEntity auditLogEntity;

	@BeforeEach
	public void setUp() {
		customerDetailsRequest = new CustomerDetailsRequest();
		customerDetailsRequest.setBirthDate("29-06-1991");
		customerDetailsRequest.setCustomerNumber("1234567890");
		customerDetailsRequest.setEmail("abc@gmail.com");

		auditLogEntity = new AuditLogEntity();
		auditLogEntity.setPayload(ObjectMapperUtil.objectToJsonString(customerDetailsRequest));
		auditLogEntity.setCustomerNumber(customerDetailsRequest.getCustomerNumber());
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

	@Test
	void testGetMaskedMessage_whenBirthDateIsSetInAuditLogEntityRequest_expectedMaskedBirthDate()
			throws JsonMappingException, JsonProcessingException {
		AuditLogEntity auditLogEntityMasked = new ObjectMapper().readValue(
				MaskingUtil.getMaskedMessage(ObjectMapperUtil.objectToJsonString(auditLogEntity)),
				AuditLogEntity.class);
		CustomerDetailsRequest customerDetailsRequestMasked = new ObjectMapper()
				.readValue(auditLogEntityMasked.getPayload(), CustomerDetailsRequest.class);
		assertEquals("xx-xx-1991", customerDetailsRequestMasked.getBirthDate());
	}

	@Test
	void testGetMaskedMessage_whenCustomerNumberIsSetInAuditLogEntityRequestJson_expectedMaskedCustomerNumberInJson()
			throws JsonMappingException, JsonProcessingException {
		AuditLogEntity auditLogEntityMasked = new ObjectMapper().readValue(
				MaskingUtil.getMaskedMessage(ObjectMapperUtil.objectToJsonString(auditLogEntity)),
				AuditLogEntity.class);
		CustomerDetailsRequest customerDetailsRequestMasked = new ObjectMapper()
				.readValue(auditLogEntityMasked.getPayload(), CustomerDetailsRequest.class);
		assertEquals("123456xxxx", customerDetailsRequestMasked.getCustomerNumber());
	}

	@Test
	void testGetMaskedMessage_whenCustomerNumberIsSetInAuditLogEntityRequest_expectedMaskedCustomerNumber()
			throws JsonMappingException, JsonProcessingException {
		AuditLogEntity auditLogEntityMasked = new ObjectMapper().readValue(
				MaskingUtil.getMaskedMessage(ObjectMapperUtil.objectToJsonString(auditLogEntity)),
				AuditLogEntity.class);
		assertEquals("123456xxxx", auditLogEntityMasked.getCustomerNumber());
	}

	@Test
	void testGetMaskedMessage_whenEmailIsSetInAuditLogEntityRequest_expectedMaskedEmail()
			throws JsonMappingException, JsonProcessingException {
		AuditLogEntity auditLogEntityMasked = new ObjectMapper().readValue(
				MaskingUtil.getMaskedMessage(ObjectMapperUtil.objectToJsonString(auditLogEntity)),
				AuditLogEntity.class);
		CustomerDetailsRequest customerDetailsRequestMasked = new ObjectMapper()
				.readValue(auditLogEntityMasked.getPayload(), CustomerDetailsRequest.class);
		assertEquals("xxxxgmail.com", customerDetailsRequestMasked.getEmail());
	}

}
