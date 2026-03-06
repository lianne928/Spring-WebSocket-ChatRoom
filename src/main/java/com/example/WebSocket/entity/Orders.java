package com.example.WebSocket.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id ;
	
	//FK
	@Column(name="user_id")
	private Long userId ;
	
	@Column(name="course_id")
	private Long coursesId ;
	
	@Column(name="merchant_trade_no", unique = true)
	private String merchantTradeNo;
	
	@Column(name="total_amount")
	private Integer totalAmount;
	
	@Column(name="created_at")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
}
