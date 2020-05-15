package com.prokarma.customerdetails.consumer.service;


import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.prokarma.customerdetails.consumer.entity.AuditLogEntity;
import com.prokarma.customerdetails.consumer.entity.ErrorLogEntity;
import com.prokarma.customerdetails.consumer.model.CustomerDetailsRequest;
import com.prokarma.customerdetails.consumer.repository.AuditLogEntityRepository;
import com.prokarma.customerdetails.consumer.repository.ErrorLogEntityRepository;

class CustomerDetailsConsumerServiceImplTest {

  @InjectMocks
  CustomerDetailsConsumerServiceImpl customerDetailsConsumerServiceImpl;

  @Mock
  private AuditLogEntityRepository auditLogEntityRepository;

  @Mock
  private ErrorLogEntityRepository errorLogEntityRepository;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testPostAuditLogEntityWhenAuditLogIsDoneSuccessFullyExpectedErrorLogCallAsZero() {
    CustomerDetailsRequest customerDetailsRequest = new CustomerDetailsRequest();
    Mockito.when(auditLogEntityRepository.save(Mockito.any())).thenReturn(new AuditLogEntity());
    Mockito.when(errorLogEntityRepository.save(Mockito.any())).thenThrow(new RuntimeException(""));
    customerDetailsConsumerServiceImpl.postAuditLogEntity(customerDetailsRequest);
    Mockito.verify(errorLogEntityRepository, Mockito.times(0)).save(Mockito.any());
  }

  @Test
  void testPostAuditLogEntityWhenAuditLogIsFailedExpectedErrorLogCallAsZero() {
    CustomerDetailsRequest customerDetailsRequest = new CustomerDetailsRequest();
    Mockito.when(auditLogEntityRepository.save(Mockito.any())).thenThrow(new RuntimeException(""));
    Mockito.when(errorLogEntityRepository.save(Mockito.any())).thenReturn(new ErrorLogEntity());
    customerDetailsConsumerServiceImpl.postAuditLogEntity(customerDetailsRequest);
    Mockito.verify(errorLogEntityRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  void testPostAuditLogEntityWhenAuditAndErrorLogUpdateFailedExpectedThrowsGeneralException() {
    CustomerDetailsRequest customerDetailsRequest = new CustomerDetailsRequest();
    Mockito.when(auditLogEntityRepository.save(Mockito.any())).thenThrow(new RuntimeException(""));
    Mockito.when(errorLogEntityRepository.save(Mockito.any())).thenThrow(new RuntimeException(""));

    assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> {
      customerDetailsConsumerServiceImpl.postAuditLogEntity(customerDetailsRequest);
    });

  }

}
