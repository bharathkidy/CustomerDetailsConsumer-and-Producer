package com.prokarma.customerdetails.consumer.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuditLogEntityTest {

  AuditLogEntity auditLogEntity;

  @BeforeEach
  void setUp() throws Exception {
    auditLogEntity = new AuditLogEntity();
  }

  @Test
  void testHashCode() {
    assertNotNull(auditLogEntity.hashCode());
  }

  @SuppressWarnings("unlikely-arg-type")
  @Test
  void testEqualsObject() {
    assertEquals(auditLogEntity, auditLogEntity);
    assertFalse(auditLogEntity.equals("1234"));
    assertFalse(auditLogEntity.equals(null));

    AuditLogEntity auditLogEntity1 = new AuditLogEntity();
    auditLogEntity1.setCustomerNumber("1234");
    auditLogEntity1.setPayload("1234");
    auditLogEntity1.setId(1234l);
    assertFalse(auditLogEntity.equals(auditLogEntity1));
    auditLogEntity.setId(1234l);
    assertFalse(auditLogEntity.equals(auditLogEntity1));
    auditLogEntity.setCustomerNumber("1234");
    assertFalse(auditLogEntity.equals(auditLogEntity1));
    auditLogEntity.setPayload("1234");


    assertTrue(auditLogEntity.equals(auditLogEntity1));
  }

  @Test
  void testGetId() {
    auditLogEntity.setId(1234l);
    assertEquals(1234l, auditLogEntity.getId());
  }

  @Test
  void testGetPayload() {
    auditLogEntity.setPayload("123");
    assertEquals("123", auditLogEntity.getPayload());
  }

  @Test
  void testGetCustomerNumber() {
    auditLogEntity.setCustomerNumber("1234");
    assertEquals("1234", auditLogEntity.getCustomerNumber());
  }

}
