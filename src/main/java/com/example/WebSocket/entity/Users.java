package com.example.WebSocket.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity @Data @Table(name = "users")
public class Users {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, length = 64)
    private String password;
    private LocalDate birthday;
    @Column(nullable = false)
    private Byte role; // 1:學生, 2:老師
    @Column(nullable = false)
    private Long wallet = 0L;
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;
    
    @Column(name = "social_provider")
    private Byte socialProvider; // 1:Google, 2:LINE, 3:Apple (對應 tinyint)
    @Column(name = "social_id")
    private String socialId; // 第三方回傳的唯一識別識別碼 (如 Google sub)
}