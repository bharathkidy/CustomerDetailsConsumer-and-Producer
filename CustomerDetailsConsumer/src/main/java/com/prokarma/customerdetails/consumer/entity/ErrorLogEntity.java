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
@Table(name = "ERROR_LOG")
public class ErrorLogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "ERROR_TYPE")
	private String errorType;


	@Column(name = "ERROR_DESCRIPTION")
	private String errorDescription;

	@ColumnTransformer(forColumn = "PAYLOAD",
			read = "CUSTOMER_DETAILS_JSON_UNMASKING(PAYLOAD, 'password')",
			write = "CUSTOMER_DETAILS_JSON_MASKING(?,'password')")
	@Column(name = "PAYLOAD", unique = true, nullable = false)
	private String payload;


	@Override
	public int hashCode() {
		return Objects.hash(id, errorDescription, errorType, payload);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ErrorLogEntity errorLogEntity = (ErrorLogEntity) o;
		return Objects.equals(this.id, errorLogEntity.id)
				&& Objects.equals(this.errorDescription, errorLogEntity.errorDescription)
				&& Objects.equals(this.errorType, errorLogEntity.errorType)
				&& Objects.equals(this.payload, errorLogEntity.payload);
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}



}
