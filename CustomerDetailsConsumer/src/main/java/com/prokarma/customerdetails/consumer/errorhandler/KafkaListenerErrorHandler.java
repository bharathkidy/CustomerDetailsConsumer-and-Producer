package com.prokarma.customerdetails.consumer.errorhandler;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.ErrorHandler;

public class KafkaListenerErrorHandler implements ErrorHandler {

	@Override
	public void handle(Exception thrownException, ConsumerRecord<?, ?> data) {
		// OverRidden GenericMethod to have loggers in sl4j using spring AOP
	}

}
