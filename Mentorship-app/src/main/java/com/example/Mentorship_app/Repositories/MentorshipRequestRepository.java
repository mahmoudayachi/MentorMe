package com.example.Mentorship_app.Repositories;

import com.example.Mentorship_app.Dto.MentorshipRequestDTO;
import com.example.Mentorship_app.Entities.Mentee;
import com.example.Mentorship_app.Entities.Mentor;
import com.example.Mentorship_app.Entities.MentorshipRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MentorshipRequestRepository extends JpaRepository<MentorshipRequest, Long> {
   List <MentorshipRequest> findByMentorId(Long Id);
   List <MentorshipRequest> findByMenteeId(Long Id);
   List <MentorshipRequest> findByMenteeIdAndMentorId(Long MenteeId, Long MentorId);
   Optional<MentorshipRequest> findById(Long id);
   @Query("SELECT m FROM MentorshipRequest m  WHERE m.status LIKE 'ACCEPTED' ")
   List<MentorshipRequest> searchMentee();
   @Query("SELECT m FROM MentorshipRequest m  WHERE m.status LIKE 'ACCEPTED' AND m.mentee.id = :id  ")
   List<MentorshipRequest> searchrequestsentMentee( Long id );

}
