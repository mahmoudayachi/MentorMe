package com.example.Mentorship_app.Repositories;

import com.example.Mentorship_app.Entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository  extends JpaRepository<Session, Long> {
    List<Session> findByMentorId(Long mentorId);
    List<Session> findByMenteeId(Long menteeId);
    List<Session> findByStatus(String status);

}
