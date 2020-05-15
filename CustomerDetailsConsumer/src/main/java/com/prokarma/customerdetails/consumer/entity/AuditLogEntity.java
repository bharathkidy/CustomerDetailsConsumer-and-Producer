package com.prokarma.customerdetails.consumer.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.ColumnTransformer;


@Entity
@Table(name = "AUDIT_LOG")
public class AuditLogEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", unique = true, nullable = false)
  private Long id;

  @Override
  public int hashCode() {
    return Objects.hash(id, customerNumber, payload);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuditLogEntity auditLogEntity = (AuditLogEntity) o;
    return Objects.equals(this.id, auditLogEntity.id)
        && Objects.equals(this.customerNumber, auditLogEntity.customerNumber)
        && Objects.equals(this.payload, auditLogEntity.payload);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }

  public String getCustomerNumber() {
    return customerNumber;
  }

  public void setCustomerNumber(String customerNumber) {
    this.customerNumber = customerNumber;
  }


  @ColumnTransformer(forColumn = "PAYLOAD",
      read = "CUSTOMER_DETAILS_JSON_UNMASKING(PAYLOAD, 'password')",
      write = "CUSTOMER_DETAILS_JSON_MASKING(?,'password')")
  @Column(name = "PAYLOAD", unique = true, nullable = false)
  private String payload;


  @ColumnTransformer(forColumn = "customerNumber",
      read = "AES_DECRYPT(CUSTOMER_NUMBER, 'password')", write = "AES_ENCRYPT(?, 'password')")
  @Column(name = "CUSTOMER_NUMBER", unique = true, nullable = false)
  private String customerNumber;
}
