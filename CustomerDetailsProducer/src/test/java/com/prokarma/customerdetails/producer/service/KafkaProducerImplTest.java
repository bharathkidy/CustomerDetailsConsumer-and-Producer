package com.prokarma.customerdetails.producer.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.util.concurrent.SettableListenableFuture;
import com.prokarma.customerdetails.producer.exceptions.GeneralException;
import com.prokarma.customerdetails.producer.model.CustomerDetailsRequest;

class KafkaProducerImplTest {

	@InjectMocks
	KafkaProducerImpl kafkaProducerImpl;

	@Mock
	KafkaTemplate<String, CustomerDetailsRequest> kafkaTemplate;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testPostKafka_whenKafaPostIsSuccessful_shouldReturnStatusCode200()
			throws InterruptedException, ExecutionException, TimeoutException {
		@SuppressWarnings("unchecked")
		SettableListenableFuture<SendResult<String, CustomerDetailsRequest>> settableListenableFuture =
				Mockito.mock(SettableListenableFuture.class);
		Mockito.when(settableListenableFuture.get(Mockito.anyLong(), Mockito.any()))
				.thenReturn(new SendResult<String, CustomerDetailsRequest>(null, null));
		Mockito.when(kafkaTemplate.send(Mockito.any(Message.class)))
				.thenReturn(settableListenableFuture);
		assertEquals(HttpStatus.OK,
				kafkaProducerImpl.postKafka(new CustomerDetailsRequest(), "").getStatusCode());
	}

	@Test
	void testPostKafka_whenKafaPostIsUnSuccessful_shouldThrowGeneralException()
			throws InterruptedException, ExecutionException, TimeoutException {
		Mockito.when(kafkaTemplate.send(Mockito.any(Message.class)))
				.thenThrow(new RuntimeException(""));
		assertThatExceptionOfType(GeneralException.class).isThrownBy(() -> {
			kafkaProducerImpl.postKafka(new CustomerDetailsRequest(), "");
		});
	}
}
