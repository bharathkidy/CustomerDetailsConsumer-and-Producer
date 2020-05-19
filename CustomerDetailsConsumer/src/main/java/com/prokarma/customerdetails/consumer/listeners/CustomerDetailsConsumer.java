package com.prokarma.customerdetails.consumer.listeners;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.prokarma.customerdetails.consumer.model.CustomerDetailsRequest;
import com.prokarma.customerdetails.consumer.service.CustomerDetailsConsumerService;


@Component
public class CustomerDetailsConsumer {



	@Autowired
	private CustomerDetailsConsumerService customerDetailsConsumerService;



	@KafkaListener(topics = "${customerDetails.topic.name}", containerFactory = "customerDetails")
	public void receive(@Payload CustomerDetailsRequest customerDetailsRequest,
			@Header(KafkaHeaders.TIMESTAMP_TYPE) String timesTampType,
			@Header(KafkaHeaders.RECEIVED_TIMESTAMP) long receivedTimesTamp,
			@Header(KafkaHeaders.RECEIVED_TOPIC) String receivedTopic) {
		customerDetailsConsumerService.postAuditLogEntity(customerDetailsRequest);
	}

}


