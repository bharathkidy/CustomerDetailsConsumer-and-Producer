package com.prokarma.customerdetails.consumer.service;

import com.prokarma.customerdetails.consumer.model.CustomerDetailsRequest;

public interface CustomerDetailsConsumerService {
	public void postAuditLogEntity(CustomerDetailsRequest customerDetailsRequest);
}
