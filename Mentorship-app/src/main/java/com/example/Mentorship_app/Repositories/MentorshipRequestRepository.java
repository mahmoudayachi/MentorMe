package com.example.Mentorship_app.Repositories;

import com.example.Mentorship_app.Dto.MentorshipRequestDTO;
import com.example.Mentorship_app.Entities.Mentee;
import com.example.Mentorship_app.Entities.Mentor;
import com.example.Mentorship_app.Entities.MentorshipRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MentorshipRequestRepository extends JpaRepository<MentorshipRequest, Long> {
   Optional <MentorshipRequest> findByMentorId(Long Id);
}
