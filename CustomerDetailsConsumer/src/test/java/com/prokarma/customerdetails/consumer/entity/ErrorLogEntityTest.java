package com.prokarma.customerdetails.consumer.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ErrorLogEntityTest {

  ErrorLogEntity errorLogEntity;

  @BeforeEach
  void setUp() throws Exception {
    errorLogEntity = new ErrorLogEntity();
  }

  @Test
  void testHashCode() {
    assertNotNull(errorLogEntity.hashCode());
  }

  @SuppressWarnings("unlikely-arg-type")
  @Test
  void testEqualsObject() {
    assertEquals(errorLogEntity, errorLogEntity);
    assertFalse(errorLogEntity.equals("1234"));
    assertFalse(errorLogEntity.equals(null));

    ErrorLogEntity errorLogEntity1 = new ErrorLogEntity();
    errorLogEntity1.setErrorType("1234");
    errorLogEntity1.setErrorDescription("1234");
    errorLogEntity1.setPayload("1234");
    errorLogEntity1.setId(1234l);
    assertFalse(errorLogEntity.equals(errorLogEntity1));
    errorLogEntity.setId(1234l);
    assertFalse(errorLogEntity.equals(errorLogEntity1));
    errorLogEntity.setErrorDescription("1234");
    assertFalse(errorLogEntity.equals(errorLogEntity1));
    errorLogEntity.setErrorType("1234");
    assertFalse(errorLogEntity.equals(errorLogEntity1));
    errorLogEntity.setPayload("1234");


    assertTrue(errorLogEntity.equals(errorLogEntity1));
  }

  @Test
  void testGetId() {
    errorLogEntity.setId(1234l);
    assertEquals(1234l, errorLogEntity.getId());
  }

  @Test
  void testGetErrorType() {
    errorLogEntity.setErrorType("123");
    assertEquals("123", errorLogEntity.getErrorType());
  }

  @Test
  void testGetErrorDescription() {
    errorLogEntity.setErrorDescription("123");
    assertEquals("123", errorLogEntity.getErrorDescription());
  }

  @Test
  void testGetPayload() {
    errorLogEntity.setPayload("123");
    assertEquals("123", errorLogEntity.getPayload());
  }

}
