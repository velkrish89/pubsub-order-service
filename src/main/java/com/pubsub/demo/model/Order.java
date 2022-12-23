package com.pubsub.demo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.pubsub.demo.utils.OrderStatus;
import com.pubsub.demo.utils.ShippingStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Customer_Order")
public class Order {
	
	@Id
	private long orderId;
	private String orderDate;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="fk_order_product", referencedColumnName = "orderId")
	private List<Product> products;
	
	private double price;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	
	public Order() {
		this.orderDate = new Date().toString();
		this.orderStatus = OrderStatus.CREATED;
	}
	
	public Order(long orderId, List<Product> products, double price) {
		this.orderId = orderId;
		this.products = products;
		this.price = price;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Order(long orderId, String orderDate, List<Product> products, double price,
			OrderStatus orderStatus) {
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.products = products;
		this.price = price;
		this.orderStatus = orderStatus;
	}
	
	
}
