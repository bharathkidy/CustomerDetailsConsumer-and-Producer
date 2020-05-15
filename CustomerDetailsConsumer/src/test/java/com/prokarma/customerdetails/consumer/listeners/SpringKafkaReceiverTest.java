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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import com.prokarma.customerdetails.consumer.model.Address;
import com.prokarma.customerdetails.consumer.model.CustomerDetailsRequest;
import com.prokarma.customerdetails.consumer.model.CustomerDetailsRequest.CustomerStatusEnum;
import com.prokarma.customerdetails.consumer.repository.AuditLogEntityRepository;
import com.prokarma.customerdetails.consumer.repository.ErrorLogEntityRepository;
import com.prokarma.customerdetails.consumer.service.CustomerDetailsConsumerServiceImpl;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@EmbeddedKafka(partitions = 1, topics = {"postCustomerDetails"})
public class SpringKafkaReceiverTest {

  private static String TOPIC = "postCustomerDetails";

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Qualifier(value = "customerDetails")
  @Autowired
  private ConcurrentKafkaListenerContainerFactory<String, CustomerDetailsRequest> containerFactory;

  private KafkaTemplate<String, CustomerDetailsRequest> kafkaTemplate;



  @Mock
  private AuditLogEntityRepository auditLogEntityRepository;

  @Mock
  private ErrorLogEntityRepository errorLogEntityRepository;

  @Autowired
  private CustomerDetailsConsumerServiceImpl customerDetailsConsumerServiceImpl;


  @BeforeEach
  public void setUp() {
    ReflectionTestUtils.setField(customerDetailsConsumerServiceImpl, "auditLogEntityRepository",
        auditLogEntityRepository);
    ReflectionTestUtils.setField(customerDetailsConsumerServiceImpl, "errorLogEntityRepository",
        errorLogEntityRepository);
  }


  @Test
  public void testKafkaListenerStubbingValidAuditRepositoryExpectedCalledTimesOfAudtiStubAsOneAndErrorLogStubAsZero()
      throws Exception {

    Mockito.when(auditLogEntityRepository.save(Mockito.any())).thenReturn(null);
    Mockito.when(errorLogEntityRepository.save(Mockito.any())).thenReturn(null);
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(props));
    kafkaTemplate
        .send(
            MessageBuilder
                .withPayload(new CustomerDetailsRequest()
                    .address(new Address().addressLine1("1234").addressLine2("12345")
                        .postalCode("500532").street("1234"))
                    .birthDate("29-06-1991").country("India").countryCode("IN")
                    .customerNumber("9492050328").customerNumber("C000000001")
                    .customerStatus(CustomerStatusEnum.OPEN).email("bharathkidy@gmail.com")
                    .firstName("Bharath12345678901234567890")
                    .lastName("Mokkal123456789001234567890").mobileNumber("9492050328"))
                .setHeader(KafkaHeaders.TOPIC, TOPIC).build())
        .get(20, TimeUnit.SECONDS);
    TimeUnit.SECONDS.sleep(10);
    Mockito.verify(auditLogEntityRepository, Mockito.times(1)).save(Mockito.any());
    Mockito.verify(errorLogEntityRepository, Mockito.times(0)).save(Mockito.any());
  }


  @Test
  public void testKafkaListenerStubbingInvalidAuditAndErrorRepositoryExpectedCalledTimesOfAudtiStubAsOneAndErrorLogStubAsOne()
      throws Exception {

    Mockito.when(auditLogEntityRepository.save(Mockito.any()))
        .thenThrow(new RuntimeException("error"));
    Mockito.when(errorLogEntityRepository.save(Mockito.any()))
        .thenReturn(new RuntimeException("error"));

    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(props));
    kafkaTemplate.send(MessageBuilder.withPayload(new CustomerDetailsRequest())
        .setHeader(KafkaHeaders.TOPIC, TOPIC).build()).get(20, TimeUnit.SECONDS);
    TimeUnit.SECONDS.sleep(10);

    Mockito.verify(auditLogEntityRepository, Mockito.times(1)).save(Mockito.any());
    Mockito.verify(errorLogEntityRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  public void testKafkaListenerStubbingInvalidAuditAndValidErrorRepositoryExpectedCalledTimesOfAudtiStubAsOneAndErrorLogStubAsOne()
      throws Exception {

    Mockito.when(auditLogEntityRepository.save(Mockito.any()))
        .thenThrow(new RuntimeException("error"));
    Mockito.when(errorLogEntityRepository.save(Mockito.any())).thenReturn(null);

    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(props));
    kafkaTemplate.send(MessageBuilder.withPayload(new CustomerDetailsRequest())
        .setHeader(KafkaHeaders.TOPIC, TOPIC).build()).get(20, TimeUnit.SECONDS);
    TimeUnit.SECONDS.sleep(10);

    Mockito.verify(auditLogEntityRepository, Mockito.times(1)).save(Mockito.any());
    Mockito.verify(errorLogEntityRepository, Mockito.times(1)).save(Mockito.any());
  }
}
