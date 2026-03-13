package com.example.WebSocket.service;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.WebSocket.entity.Users;


public interface UsersRepo extends JpaRepository<Users, Long>{
}
