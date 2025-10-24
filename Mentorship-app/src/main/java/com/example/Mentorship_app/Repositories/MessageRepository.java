package com.example.Mentorship_app.Repositories;

import com.example.Mentorship_app.Entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface MessageRepository  extends JpaRepository<Message, Long> {
    List<Message> findByMentorIdAndMenteeIdOrderByTimestampAsc(Long mentorId, Long menteeId);
}
