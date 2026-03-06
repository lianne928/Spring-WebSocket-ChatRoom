package com.example.WebSocket.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.WebSocket.entity.Member;
import com.example.WebSocket.entity.Orders;


public interface OrdersRepo extends JpaRepository<Orders, Long>{
}
