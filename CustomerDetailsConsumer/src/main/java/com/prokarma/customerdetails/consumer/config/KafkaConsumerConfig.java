package com.prokarma.customerdetails.consumer.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import com.prokarma.customerdetails.consumer.errorhandler.KafkaListenerErrorHandler;
import com.prokarma.customerdetails.consumer.model.CustomerDetailsRequest;


@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;


	@Bean("customerDetails")
	public ConcurrentKafkaListenerContainerFactory<String, CustomerDetailsRequest> kafkaListenerContainerFactoryString() {
		ConcurrentKafkaListenerContainerFactory<String, CustomerDetailsRequest> concurrentKafkaListenerContainerFactory =
				new ConcurrentKafkaListenerContainerFactory<>();
		concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
		concurrentKafkaListenerContainerFactory.setErrorHandler(new KafkaListenerErrorHandler());
		return concurrentKafkaListenerContainerFactory;
	}

	@Bean
	public Map<String, Object> consumerProperties() {
		Map<String, Object> consumerProperties = new HashMap<>();
		consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "json");
		consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		return consumerProperties;
	}

	@Bean
	public JsonDeserializer<CustomerDetailsRequest> jsonDeserializer() {
		JsonDeserializer<CustomerDetailsRequest> jsonDeserializer =
				new JsonDeserializer<>(CustomerDetailsRequest.class);
		jsonDeserializer.setRemoveTypeHeaders(false);
		jsonDeserializer.addTrustedPackages("com.prokarma.customerdetails");
		jsonDeserializer.setUseTypeMapperForKey(true);
		return jsonDeserializer;
	}

	@Bean
	public ConsumerFactory<String, CustomerDetailsRequest> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerProperties(), new StringDeserializer(),
				jsonDeserializer());
	}

}
