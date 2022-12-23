package com.pubsub.demo.gateway;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(name="pubsubGateway", defaultRequestChannel = "outboundMessageChannel")
public interface OutboundChannel {
	
	public void publishMessageToPubSub(String message);
}
