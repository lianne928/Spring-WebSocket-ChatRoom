package com.example.WebSocket.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "members")
public class Member {
    @Id
    private Integer id;
    private String email, passwd, name;
}
