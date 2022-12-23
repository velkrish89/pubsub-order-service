package com.pubsub.demo.controller;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pubsub.demo.exception.NoOrderFoundException;
import com.pubsub.demo.gateway.OutboundChannel;
import com.pubsub.demo.model.Order;
import com.pubsub.demo.repository.OrderRepository;
import com.pubsub.demo.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/orderservice")
public class OrderController {
	
	private static Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OutboundChannel outboundChannel;
	
	@Autowired
	private OrderService orderService;
	
	
	@Operation(summary = "To add an order in DB and push the order to google pub/sub topic")
	@ApiResponses(value= {
			@ApiResponse(responseCode = "200", 
					description = "Added order to DB and pushed the order to google pub/sub topic"),
			@ApiResponse(responseCode = "500", 
			description = "Internal Server Error")
	})
	@PostMapping("/createOrder")
	public ResponseEntity<String> createOrder(@RequestBody Order order) {
		
		Order result = orderService.createOrder(order);
		
		ObjectMapper mapper = new ObjectMapper();
		String orderMessage = null;
		
		try {
			orderMessage = mapper.writeValueAsString(result).toString();
			logger.info("Order Message: " + orderMessage);
			
			outboundChannel.publishMessageToPubSub(orderMessage);
			
			logger.info("Order Message published to publisher topic: " + orderMessage);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>(orderMessage, HttpStatus.CREATED);
	}
	
	
	@Operation(summary = "To fetch an order by order id from DB")
	@ApiResponses(value= {
			@ApiResponse(responseCode = "200", 
					description = "Fetched order by order id from DB"),
			@ApiResponse(responseCode = "404", 
			description = "No records found with given order id"),
			@ApiResponse(responseCode = "500", 
			description = "Internal Server Error")
	})
	@GetMapping("/order/{orderId}")
	public ResponseEntity<?> getOrderById(@PathVariable long orderId) {
		
		Order order = orderService
				.getOrderById(orderId)
				.orElseThrow(() -> new NoOrderFoundException());
		
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

}
