package com.example.WebSocket.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.WebSocket.entity.ShoppingCart;

public interface ShoppingCartRepo extends JpaRepository<ShoppingCart, Long>{
}
