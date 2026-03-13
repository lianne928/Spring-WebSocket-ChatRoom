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

@Entity @Data @Table(name = "orders")
public class Orders {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "user_id", nullable = false)
    private Users user;
    @ManyToOne @JoinColumn(name = "course_id", nullable = false)
    private Courses course;
    @Column(name = "unit_price", nullable = false) private Integer unitPrice;
    @Column(name = "discount_price", nullable = false) private Integer discountPrice;
    @Column(name = "lesson_count", nullable = false) private Integer lessonCount;
    @Column(name = "lesson_used", nullable = false) private Integer lessonUsed = 0;
    @Column(nullable = false) private Byte status;
}
