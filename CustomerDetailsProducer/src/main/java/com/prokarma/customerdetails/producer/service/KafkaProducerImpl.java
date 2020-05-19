package com.prokarma.customerdetails.producer.service;


import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import com.prokarma.customerdetails.producer.exceptions.GeneralException;
import com.prokarma.customerdetails.producer.model.CustomerDetailsRequest;
import com.prokarma.customerdetails.producer.model.SuccessResponse;
import com.prokarma.customerdetails.producer.util.MessageConverterUtil;

@Service
public class KafkaProducerImpl implements KafkaProducer {

	@Autowired
	private KafkaTemplate<String, CustomerDetailsRequest> kafkaTemplate;

	@Override
	public <T> ResponseEntity<SuccessResponse> postKafka(T inputRequest, String topicName) {
		Message<T> message = MessageConverterUtil.getMessage(inputRequest, topicName);
		try {
			kafkaTemplate.send(message).get(3, TimeUnit.SECONDS);
			return new ResponseEntity<>(new SuccessResponse()
					.message("Customer Details Request succesfully Inserted in Kafka Topic")
					.status("success"), HttpStatus.OK);
		} catch (Exception e) {
			throw new GeneralException("GeneralException",
					"failed to post message to kafka: " + e.getMessage(), "error");
		}

	}
}
