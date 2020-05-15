package com.prokarma.customerdetails.producer.service;


import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;


@Service
public interface KafkaProducer {

  public <T> ResponseEntity<Object> postKafka(Message<T> message);


}
