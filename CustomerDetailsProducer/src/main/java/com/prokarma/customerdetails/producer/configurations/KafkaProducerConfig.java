package com.prokarma.customerdetails.producer.configurations;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaProducerConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${spring.kafka.producer.client-id}")
  private String kafkaProducerClientId;

  @Value("${spring.kafka.max.block.ms}")
  private int kafkaProducerRequestMaxBlockTime;

  @Bean
  public Map<String, Object> producerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProducerClientId);
    props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, kafkaProducerRequestMaxBlockTime);

    return props;
  }

  @Bean
  public <T> ProducerFactory<String, T> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfigs());
  }

  @Bean
  public <T> KafkaTemplate<String, T> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

}
