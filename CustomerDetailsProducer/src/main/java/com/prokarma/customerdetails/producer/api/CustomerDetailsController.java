package com.prokarma.customerdetails.producer.api;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.prokarma.customerdetails.producer.model.CustomerDetailsRequest;
import com.prokarma.customerdetails.producer.model.SuccessResponse;
import com.prokarma.customerdetails.producer.service.KafkaProducer;
import com.prokarma.customerdetails.producer.util.MaskingUtil;
import com.prokarma.customerdetails.producer.util.ObjectMapperUtil;

@RestController
public class CustomerDetailsController {

	@Autowired
	private KafkaProducer kafkaProducer;

	@Value("${spring.kafka.producer.topic-name}")
	private String topic;

	private static final Logger logger = LoggerFactory.getLogger(CustomerDetailsController.class);

	@PostMapping("/crud/v1/addCustomer")
	public ResponseEntity<SuccessResponse> postCustomerDetails(
			@Valid @RequestBody CustomerDetailsRequest customerDetailsRequest,
			@RequestHeader("Authorization") String authorization,
			@RequestHeader("Activity-Id") String activityId,
			@RequestHeader("Application-Id") String applicationId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Customer Details Request: {} ", MaskingUtil
					.getMaskedMessage(ObjectMapperUtil.objectToJsonString(customerDetailsRequest)));
		}
		return kafkaProducer.postKafka(customerDetailsRequest, topic);
	}

}
