package com.prokarma.customerdetails.consumer.listeners;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.prokarma.customerdetails.consumer.model.CustomerDetailsRequest;
import com.prokarma.customerdetails.consumer.service.CustomerDetailsConsumerService;

@Component
public class KafkaConsumer {



  @Autowired
  private CustomerDetailsConsumerService customerDetailsConsumerService;



  @KafkaListener(topics = "${customerDetails.topic.name}", containerFactory = "customerDetails")
  public void receive(@Payload CustomerDetailsRequest customerDetailsRequest,
      @Headers MessageHeaders headers) {

    customerDetailsConsumerService.postAuditLogEntity(customerDetailsRequest);
  }

}


