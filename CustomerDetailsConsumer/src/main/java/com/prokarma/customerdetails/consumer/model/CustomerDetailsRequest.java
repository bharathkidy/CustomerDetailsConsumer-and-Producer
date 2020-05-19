package com.prokarma.customerdetails.consumer.model;



import java.util.Objects;



public class CustomerDetailsRequest {
	private String customerNumber = null;
	private String firstName = null;
	private String lastName = null;
	private String birthDate = null;

	private String country = null;
	private String countryCode = null;
	private String mobileNumber = null;
	private String email = null;

	private CustomerStatusEnum customerStatus = null;
	private Address address = null;

	public enum CustomerStatusEnum {
		OPEN("Open"), CLOSE("Close"), SUSPENDED("Suspended"), RESTORED("Restored");

		private String value;

		CustomerStatusEnum(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}

		public static CustomerStatusEnum fromValue(String text) {
			for (CustomerStatusEnum b : CustomerStatusEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	public CustomerDetailsRequest customerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
		return this;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public CustomerDetailsRequest firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public CustomerDetailsRequest lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public CustomerDetailsRequest birthDate(String birthDate) {
		this.birthDate = birthDate;
		return this;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public CustomerDetailsRequest country(String country) {
		this.country = country;
		return this;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public CustomerDetailsRequest countryCode(String countryCode) {
		this.countryCode = countryCode;
		return this;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public CustomerDetailsRequest mobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
		return this;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public CustomerDetailsRequest email(String email) {
		this.email = email;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CustomerDetailsRequest customerStatus(CustomerStatusEnum customerStatus) {
		this.customerStatus = customerStatus;
		return this;
	}

	public CustomerStatusEnum getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(CustomerStatusEnum customerStatus) {
		this.customerStatus = customerStatus;
	}

	public CustomerDetailsRequest address(Address address) {
		this.address = address;
		return this;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CustomerDetailsRequest customerDetailsRequest = (CustomerDetailsRequest) o;
		return Objects.equals(this.customerNumber, customerDetailsRequest.customerNumber)
				&& Objects.equals(this.firstName, customerDetailsRequest.firstName)
				&& Objects.equals(this.lastName, customerDetailsRequest.lastName)
				&& Objects.equals(this.birthDate, customerDetailsRequest.birthDate)
				&& Objects.equals(this.country, customerDetailsRequest.country)
				&& Objects.equals(this.countryCode, customerDetailsRequest.countryCode)
				&& Objects.equals(this.mobileNumber, customerDetailsRequest.mobileNumber)
				&& Objects.equals(this.email, customerDetailsRequest.email)
				&& Objects.equals(this.customerStatus, customerDetailsRequest.customerStatus)
				&& Objects.equals(this.address, customerDetailsRequest.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerNumber, firstName, lastName, birthDate, country, countryCode,
				mobileNumber, email, customerStatus, address);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class CustomerDetailsRequest {\n");

		sb.append("    customerNumber: ").append(toIndentedString(customerNumber)).append("\n");
		sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
		sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
		sb.append("    birthDate: ").append(toIndentedString(birthDate)).append("\n");
		sb.append("    country: ").append(toIndentedString(country)).append("\n");
		sb.append("    countryCode: ").append(toIndentedString(countryCode)).append("\n");
		sb.append("    mobileNumber: ").append(toIndentedString(mobileNumber)).append("\n");
		sb.append("    email: ").append(toIndentedString(email)).append("\n");
		sb.append("    customerStatus: ").append(toIndentedString(customerStatus)).append("\n");
		sb.append("    address: ").append(toIndentedString(address)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}


