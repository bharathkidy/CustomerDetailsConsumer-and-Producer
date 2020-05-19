package com.prokarma.customerdetails.producer.service;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.prokarma.customerdetails.producer.model.SuccessResponse;


@Service
public interface KafkaProducer {

	public <T> ResponseEntity<SuccessResponse> postKafka(T inputRequest, String topicName);


}
