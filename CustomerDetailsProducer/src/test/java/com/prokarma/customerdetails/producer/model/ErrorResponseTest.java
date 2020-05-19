package com.prokarma.customerdetails.producer.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.prokarma.customerdetails.producer.model.ErrorResponse;

class ErrorResponseTest {

	ErrorResponse errorResponse;

	@BeforeEach
	void setUp() throws Exception {
		errorResponse = new ErrorResponse();
	}



	@Test
	void testGetStatus() {
		errorResponse.setStatus("success");
		assertEquals("failure", errorResponse.status("failure").getStatus());
	}

	@Test
	void testGetMessage() {
		errorResponse.setMessage("success");
		assertEquals("failure", errorResponse.message("failure").getMessage());
	}

	@Test
	void testGetErrorType() {
		errorResponse.setErrorType("InvalidRequestException");
		assertEquals("TokenException", errorResponse.errorType("TokenException").getErrorType());
	}

	@Test
	void testEqualsObject() {
		ErrorResponse errorResponse2 = new ErrorResponse().status("success").message("success")
				.errorType("InvalidRequestException");
		assertNotEquals(errorResponse2, null);
		assertNotEquals(errorResponse2, "errorResponse");

		assertNotEquals(errorResponse2, errorResponse);
		assertNotEquals(errorResponse2, errorResponse.status("success"));
		assertNotEquals(errorResponse2, errorResponse.errorType("InvalidRequestException"));
		assertEquals(errorResponse2, errorResponse.message("success"));
		assertEquals(errorResponse2, errorResponse2);
	}

	@Test
	void testToString() {
		assertNotNull(errorResponse.toString());
		assertNotNull(errorResponse.status("success").toString());
	}

	@Test
	void testHashCode() {
		assertNotNull(errorResponse.hashCode());
	}

}
