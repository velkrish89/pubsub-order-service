package com.pubsub.demo.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;

@Component
public class ProducerServiceActivator {
	
	@Bean
	@ServiceActivator(inputChannel = "outboundMessageChannel")
	public PubSubMessageHandler messageSender(PubSubTemplate template) {
		return new PubSubMessageHandler(template, "order-topic");
	}
}
