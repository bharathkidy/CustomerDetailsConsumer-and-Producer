package com.prokarma.customerdetails.consumer.listeners;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import com.prokarma.customerdetails.consumer.model.CustomerDetailsRequest;
import com.prokarma.customerdetails.consumer.repository.AuditLogEntityRepository;
import com.prokarma.customerdetails.consumer.repository.ErrorLogEntityRepository;
import com.prokarma.customerdetails.consumer.service.CustomerDetailsConsumerServiceImpl;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EmbeddedKafka(partitions = 1, topics = {"postCustomerDetails"})
public class SpringKafkaReceiverTest {

	private static String TOPIC = "postCustomerDetails";

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Qualifier(value = "customerDetails")
	@Autowired
	private ConcurrentKafkaListenerContainerFactory<String, CustomerDetailsRequest> concurrentKafkaListenerContainerFactory;

	private KafkaTemplate<String, CustomerDetailsRequest> kafkaTemplate;



	@Mock
	private AuditLogEntityRepository auditLogEntityRepository;

	@Mock
	private ErrorLogEntityRepository errorLogEntityRepository;

	@Autowired
	private CustomerDetailsConsumerServiceImpl customerDetailsConsumerServiceImpl;

	@TestConfiguration
	static class TestConfig {

		@Bean
		public AuditLogEntityRepository auditLogEntityRepository() {
			return Mockito.mock(AuditLogEntityRepository.class);
		}

		@Bean
		public ErrorLogEntityRepository errorLogEntityRepository() {
			return Mockito.mock(ErrorLogEntityRepository.class);
		}

	}

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(props));
		ReflectionTestUtils.setField(customerDetailsConsumerServiceImpl, "auditLogEntityRepository",
				auditLogEntityRepository);
		ReflectionTestUtils.setField(customerDetailsConsumerServiceImpl, "errorLogEntityRepository",
				errorLogEntityRepository);
	}


	@Test
	public void test_CustomerDetailsConsumer_whenAuditRepositoryCallSucess_expectingAuditRepositoryCallTimesAsExactlyOne()
			throws Exception {
		Mockito.when(auditLogEntityRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(errorLogEntityRepository.save(Mockito.any())).thenReturn(null);
		kafkaTemplate.send(MessageBuilder.withPayload(new CustomerDetailsRequest())
				.setHeader(KafkaHeaders.TOPIC, TOPIC).build()).get(20, TimeUnit.SECONDS);
		TimeUnit.SECONDS.sleep(5);
		Mockito.verify(auditLogEntityRepository, Mockito.times(1)).save(Mockito.any());
	}


	@Test
	public void test_CustomerDetailsConsumer_whenAuditRepositoryCallFails_expectingErrorRepositoryCallsAsExactlyOne()
			throws Exception {
		Mockito.when(auditLogEntityRepository.save(Mockito.any()))
				.thenThrow(new RuntimeException("error"));
		Mockito.when(errorLogEntityRepository.save(Mockito.any()))
				.thenThrow(new RuntimeException("error"));
		kafkaTemplate.send(MessageBuilder.withPayload(new CustomerDetailsRequest())
				.setHeader(KafkaHeaders.TOPIC, TOPIC).build()).get(20, TimeUnit.SECONDS);
		TimeUnit.SECONDS.sleep(5);
		Mockito.verify(errorLogEntityRepository, Mockito.times(1)).save(Mockito.any());
	}

}
