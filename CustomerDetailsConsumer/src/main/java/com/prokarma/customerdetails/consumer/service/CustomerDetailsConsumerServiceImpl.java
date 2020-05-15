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

    try {
      insertAuditLog(customerDetailsRequest);
    } catch (RuntimeException e) {
      ErrorLogEntity errorLogEntity = new ErrorLogEntity();
      errorLogEntity.setErrorType("GeneralException");
      errorLogEntity.setErrorDescription(e.getMessage());
      errorLogEntity.setPayload(ObjectMapperUtil.objectToJsonString(customerDetailsRequest));
      errorLogEntityRepository.save(errorLogEntity);
    }
  }

  private void insertAuditLog(CustomerDetailsRequest customerDetailsRequest) {

    AuditLogEntity auditLogEntity = new AuditLogEntity();
    auditLogEntity.setCustomerNumber(customerDetailsRequest.getCustomerNumber());
    auditLogEntity.setPayload(ObjectMapperUtil.objectToJsonString(customerDetailsRequest));
    auditLogEntityRepository.save(auditLogEntity);
  }



}
