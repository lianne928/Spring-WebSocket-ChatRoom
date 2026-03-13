package com.example.WebSocket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity @Data @Table(name = "courses")
public class Courses {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "tutor_id", nullable = false)
    private Users tutor;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false)
    private Integer subject; // 11:低年級, 12:中年級, 13:高年級, 21:GEPT, 22:YLE, 23:國中先修, 31:其他

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Integer price;

    @Column(name = "is_active", nullable = false)
    private Byte isActive = (byte) 1;
}

