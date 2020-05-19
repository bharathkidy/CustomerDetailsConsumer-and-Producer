package com.prokarma.customerdetails.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.prokarma.customerdetails.consumer.entity.AuditLogEntity;
import com.prokarma.customerdetails.consumer.entity.ErrorLogEntity;
import com.prokarma.customerdetails.consumer.model.CustomerDetailsRequest;
import com.prokarma.customerdetails.consumer.repository.AuditLogEntityRepository;
import com.prokarma.customerdetails.consumer.repository.ErrorLogEntityRepository;
import com.prokarma.customerdetails.consumer.util.ObjectMapperUtil;

@Service
public class CustomerDetailsConsumerServiceImpl implements CustomerDetailsConsumerService {

	@Autowired
	private AuditLogEntityRepository auditLogEntityRepository;

	@Autowired
	private ErrorLogEntityRepository errorLogEntityRepository;

	public void postAuditLogEntity(CustomerDetailsRequest customerDetailsRequest) {
		String customerDetailsRequestJsonString =
				ObjectMapperUtil.objectToJsonString(customerDetailsRequest);
		try {
			auditLogEntityRepository
					.save(createAuditLogEntity(customerDetailsRequest, customerDetailsRequestJsonString));
		} catch (RuntimeException e) {
			errorLogEntityRepository.save(createErrorLogEntity(e, customerDetailsRequestJsonString));
		}
	}

	private AuditLogEntity createAuditLogEntity(CustomerDetailsRequest customerDetailsRequest,
			String customerDetailsRequestJsonString) {
		AuditLogEntity auditLogEntity = new AuditLogEntity();
		auditLogEntity.setCustomerNumber(customerDetailsRequest.getCustomerNumber());
		auditLogEntity.setPayload(customerDetailsRequestJsonString);
		return auditLogEntity;
	}

	private ErrorLogEntity createErrorLogEntity(RuntimeException e,
			String customerDetailsRequestJsonString) {
		ErrorLogEntity errorLogEntity = new ErrorLogEntity();
		errorLogEntity.setErrorType("GeneralException");
		errorLogEntity.setErrorDescription(e.getMessage());
		errorLogEntity.setPayload(customerDetailsRequestJsonString);
		return errorLogEntity;
	}

}
