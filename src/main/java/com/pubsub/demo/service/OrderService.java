package com.pubsub.demo.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pubsub.demo.model.Order;
import com.pubsub.demo.repository.OrderRepository;

@Service
public class OrderService {
	
	private static Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	@Autowired
	private OrderRepository orderRepository;
	
	public Order createOrder(Order order) {
		Order result = orderRepository.save(order);
		logger.info("Order added to Db: " + result);
		return result;
	}
	
	public Optional<Order> getOrderById(long orderId) {
		Optional<Order> result = orderRepository.findById(orderId);
		logger.info("find order by Id : " + "orderId and result: " + result);
		return result;
	}

}
