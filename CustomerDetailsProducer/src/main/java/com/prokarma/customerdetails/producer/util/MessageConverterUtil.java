package com.prokarma.customerdetails.producer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public class MessageConverterUtil {

  private static final Logger logger = LoggerFactory.getLogger(MessageConverterUtil.class);

  private MessageConverterUtil() {

  }

  public static <T> Message<T> getMessage(T inputRequest, String topicName) {
    Message<T> message =
        MessageBuilder.withPayload(inputRequest).setHeader(KafkaHeaders.TOPIC, topicName).build();
    if (logger.isDebugEnabled()) {
      logger.debug("Customer Details Request: {} ",
          MaskingUtil.getMaskedMessage(ObjectMapperUtil.objectToJsonString(message)));
    }

    return message;

  }
}
