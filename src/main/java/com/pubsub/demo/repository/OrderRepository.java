package com.pubsub.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pubsub.demo.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
