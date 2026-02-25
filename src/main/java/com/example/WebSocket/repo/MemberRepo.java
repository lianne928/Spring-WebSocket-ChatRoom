package com.example.WebSocket.repo;

import com.example.WebSocket.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepo extends JpaRepository<Member, Integer> {
    Optional<Member> findByEmail(String email);

}
