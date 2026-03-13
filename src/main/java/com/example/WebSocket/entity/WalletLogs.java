package com.example.WebSocket.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity @Data @Table(name = "wallet_logs")
public class WalletLogs {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "user_id", nullable = false)
    private Users user;
    @Column(name = "transaction_type", nullable = false) private Byte transactionType;
    @Column(nullable = false) private Long amount;
    @Column(name = "related_type") private Byte relatedType;
    @Column(name = "related_id") private Long relatedId;
    @Column(name = "merchant_trade_no", unique = true, length = 100) private String merchantTradeNo;
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}