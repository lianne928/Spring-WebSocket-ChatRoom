package com.example.WebSocket.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity @Data @Table(name = "bookings")
public class Bookings {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "order_id", nullable = false)
    private Orders order;
    @ManyToOne @JoinColumn(name = "tutor_id", nullable = false)
    private Users tutor;
    @ManyToOne @JoinColumn(name = "student_id", nullable = false)
    private Users student;
    @Column(nullable = false) private LocalDate date;
    @Column(nullable = false) private Byte hour;
    @Column(name = "slot_locked") private Byte slotLocked = 1;
    @Column(nullable = false) private Byte status;
}
