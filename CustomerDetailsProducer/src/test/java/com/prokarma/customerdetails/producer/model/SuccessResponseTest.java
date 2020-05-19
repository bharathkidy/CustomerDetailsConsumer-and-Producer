package com.prokarma.customerdetails.producer.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.prokarma.customerdetails.producer.model.SuccessResponse;

class SuccessResponseTest {

	SuccessResponse successResponse;

	@BeforeEach
	void setUp() throws Exception {
		successResponse = new SuccessResponse();
	}



	@Test
	void testGetStatus() {
		successResponse.setStatus("success");
		assertEquals("failure", successResponse.status("failure").getStatus());
	}

	@Test
	void testGetMessage() {
		successResponse.setMessage("success");
		assertEquals("failure", successResponse.message("failure").getMessage());
	}

	@Test
	void testEqualsObject() {
		SuccessResponse successResponse2 = new SuccessResponse().status("success").message("success");
		assertNotEquals(successResponse, null);
		assertNotEquals(successResponse, "successResponse");

		assertNotEquals(successResponse2, successResponse);
		assertNotEquals(successResponse2, successResponse.status("success"));
		assertEquals(successResponse2, successResponse.message("success"));
		assertEquals(successResponse2, successResponse2);
	}

	@Test
	void testToString() {
		assertNotNull(successResponse.toString());
		assertNotNull(successResponse.status("success").toString());
	}

	@Test
	void testHashCode() {
		assertNotNull(successResponse.hashCode());
	}

}
