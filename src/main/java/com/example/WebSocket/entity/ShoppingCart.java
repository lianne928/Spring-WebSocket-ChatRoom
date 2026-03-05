package com.example.WebSocket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="")
@Data
public class ShoppingCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="")
	private Long shopId ;
	
	@Column(name="")
	private String coursesName;
	
	@Column(name="")
	private Integer coursesPrice;
	
	@Column(name="")
	private Integer totalPrice;
	
}
