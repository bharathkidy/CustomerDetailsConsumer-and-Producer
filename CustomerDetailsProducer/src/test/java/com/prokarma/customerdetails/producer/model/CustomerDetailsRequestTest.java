package com.prokarma.customerdetails.producer.model;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.prokarma.customerdetails.producer.model.Address;
import com.prokarma.customerdetails.producer.model.CustomerDetailsRequest;
import com.prokarma.customerdetails.producer.model.CustomerDetailsRequest.CustomerStatusEnum;

class CustomerDetailsRequestTest {


  CustomerDetailsRequest customerDetailsRequest;

  @BeforeEach
  void setUp() throws Exception {
    customerDetailsRequest = new CustomerDetailsRequest();

  }

  @Test
  void testHashCode() {
    assertNotNull(customerDetailsRequest.hashCode());
  }

  @Test
  void testCustomerNumber() {
    customerDetailsRequest.setCustomerNumber("C000000001");
    assertEquals("A000000001",
        customerDetailsRequest.customerNumber("A000000001").getCustomerNumber());
  }

  @Test
  void testFirstName() {
    customerDetailsRequest.setFirstName("Bharath");
    assertEquals("Bharath       ",
        customerDetailsRequest.firstName("Bharath       ").getFirstName());
  }

  @Test
  void testLastName() {
    customerDetailsRequest.setLastName("Mokkala");
    assertEquals("Mokkala        ",
        customerDetailsRequest.lastName("Mokkala        ").getLastName());
  }

  @Test
  void testBirthdate() {
    customerDetailsRequest.setBirthDate("29-06-1991");
    assertEquals("29-06-1991", customerDetailsRequest.birthDate("29-06-1991").getBirthDate());
  }

  @Test
  void testCountry() {
    customerDetailsRequest.setCountry("India");
    assertEquals("India", customerDetailsRequest.country("India").getCountry());
  }

  @Test
  void testCountryCode() {
    customerDetailsRequest.setCountryCode("IN");
    assertEquals("US", customerDetailsRequest.countryCode("US").getCountryCode());
  }

  @Test
  void testMobileNumber() {
    customerDetailsRequest.setMobileNumber("9492050328");
    assertEquals("9492050329", customerDetailsRequest.mobileNumber("9492050329").getMobileNumber());
  }

  @Test
  void testEmail() {
    customerDetailsRequest.setEmail("bmokkala@pkglobal.com");
    assertEquals("bmokkala@prokarma.com",
        customerDetailsRequest.email("bmokkala@prokarma.com").getEmail());
  }

  @Test
  void testCustomerStatus() {
    customerDetailsRequest.setCustomerStatus(CustomerStatusEnum.CLOSE);
    assertEquals(CustomerStatusEnum.OPEN,
        customerDetailsRequest.customerStatus(CustomerStatusEnum.OPEN).getCustomerStatus());
  }

  @Test
  void testAddress() {
    customerDetailsRequest.setAddress(null);
    Address address = new Address().addressLine1("line1").addressLine2("line2").postalCode("500032")
        .street("123");
    assertEquals(address, customerDetailsRequest.address(address).getAddress());
  }

  @Test
  void testEqualsObject() {
    CustomerDetailsRequest customerDetailsRequest2 = new CustomerDetailsRequest()
        .address(new Address()).mobileNumber("9492050328").customerNumber("C000000001")
        .firstName("Bharath").lastName("Mokkala").birthDate("29-06-1991").country("India")
        .countryCode("IN").customerStatus(CustomerStatusEnum.OPEN).email("bmokkala@pkglobal.com");

    assertNotEquals(customerDetailsRequest2, null);
    assertNotEquals(customerDetailsRequest2, "customerDetailsRequest");
    assertNotEquals(customerDetailsRequest2, customerDetailsRequest);
    assertNotEquals(customerDetailsRequest2, customerDetailsRequest.customerNumber("C000000001"));
    assertNotEquals(customerDetailsRequest2, customerDetailsRequest.firstName("Bharath"));
    assertNotEquals(customerDetailsRequest2, customerDetailsRequest.lastName("Mokkala"));
    assertNotEquals(customerDetailsRequest2, customerDetailsRequest.birthDate("29-06-1991"));
    assertNotEquals(customerDetailsRequest2, customerDetailsRequest.country("India"));
    assertNotEquals(customerDetailsRequest2, customerDetailsRequest.countryCode("IN"));
    assertNotEquals(customerDetailsRequest2, customerDetailsRequest.mobileNumber("9492050328"));
    assertNotEquals(customerDetailsRequest2, customerDetailsRequest.email("bmokkala@pkglobal.com"));
    assertNotEquals(customerDetailsRequest2,
        customerDetailsRequest.customerStatus(CustomerStatusEnum.OPEN));
    assertEquals(customerDetailsRequest2, customerDetailsRequest.address(new Address()));
    assertEquals(customerDetailsRequest2, customerDetailsRequest2);
  }

  @Test
  void testToString() {
    assertNotNull(customerDetailsRequest.toString());
    assertNotNull(customerDetailsRequest.customerNumber("C000000001").toString());
  }

  @Test
  void testCustomerStatusEnum() {
    assertEquals(CustomerStatusEnum.OPEN, CustomerStatusEnum.fromValue("Open"));
    assertEquals(null, CustomerStatusEnum.fromValue("open"));
    assertEquals("Open", CustomerStatusEnum.OPEN.toString());
  }

}
