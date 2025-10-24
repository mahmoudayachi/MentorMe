package com.example.Mentorship_app.Repositories;

import com.example.Mentorship_app.Entities.AvailabilityPeriod;
import com.example.Mentorship_app.Enums.AvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AvailabilityPeriodRepository extends JpaRepository<AvailabilityPeriod, Long> {

    List<AvailabilityPeriod> findBySessionMentorId(Long mentorId);
    List<AvailabilityPeriod> findBySessionId(Long sessionId);
    List<AvailabilityPeriod> findByBookedBy_IdAndStatus(Long MenteeId, AvailabilityStatus status);
    @Query("SELECT a FROM AvailabilityPeriod a WHERE a.session.mentor.id = :mentorId")
    List<AvailabilityPeriod> findByMentorId(@Param("mentorId") Long mentorId);
    List<AvailabilityPeriod> findByStatusAndZoomMeetingIdIsNullAndStartTimeBetween(
            AvailabilityStatus status, LocalDateTime start, LocalDateTime end);
    List<AvailabilityPeriod> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

}
