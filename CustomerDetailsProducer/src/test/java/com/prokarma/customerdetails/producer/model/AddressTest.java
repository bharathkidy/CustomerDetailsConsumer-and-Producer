package com.prokarma.customerdetails.producer.model;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddressTest {

	Address address;

	@BeforeEach
	public void setUp() throws Exception {
		address = new Address();
	}

	@Test
	public void testGetAddressLine1() {
		address.setAddressLine1("1234");
		assertEquals("5678", address.addressLine1("5678").getAddressLine1());
	}

	@Test
	public void testGetAddressLine2() {
		address.setAddressLine2("1234");
		assertEquals("5678", address.addressLine2("5678").getAddressLine2());
	}

	@Test
	public void testGetStreet() {
		address.setStreet("1234");
		assertEquals("5678", address.street("5678").getStreet());
	}

	@Test
	public void testGetPostalCode() {
		address.setPostalCode("1234");
		assertEquals("5678", address.postalCode("5678").getPostalCode());
	}

	@Test
	public void testEqualsObject() {
		address.addressLine1("1234").addressLine2("5678").street("5678").postalCode("5678");
		Address adddress2 = new Address();
		assertNotEquals(address, null);
		assertNotEquals(address, "address");
		assertNotEquals(address, adddress2);
		assertNotEquals(address, adddress2.addressLine1("1234"));
		assertNotEquals(address, adddress2.addressLine2("5678"));
		assertNotEquals(address, adddress2.street("5678"));
		assertEquals(address, adddress2.postalCode("5678"));
	}

	@Test
	public void testToString() {
		assertNotNull(address.toString());
		assertNotNull(address.addressLine1("1234").toString());
	}

	@Test
	public void testHashCode() {
		assertNotNull(address.hashCode());
	}

}
