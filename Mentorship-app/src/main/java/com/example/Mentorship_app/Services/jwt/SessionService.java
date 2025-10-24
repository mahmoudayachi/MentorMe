package com.example.Mentorship_app.Services.jwt;

import com.example.Mentorship_app.Dto.SessionDTO;
import com.example.Mentorship_app.Entities.AvailabilityPeriod;
import com.example.Mentorship_app.Entities.Mentee;
import com.example.Mentorship_app.Entities.Mentor;
import com.example.Mentorship_app.Entities.Session;
import com.example.Mentorship_app.Enums.AccountStatus;
import com.example.Mentorship_app.Enums.AvailabilityStatus;
import com.example.Mentorship_app.Repositories.AvailabilityPeriodRepository;
import com.example.Mentorship_app.Repositories.MenteeRepository;
import com.example.Mentorship_app.Repositories.MentorRepository;
import com.example.Mentorship_app.Repositories.SessionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private  EmailService emailService;
    @Autowired
    private MenteeRepository menteeRepository ;
    @Autowired
    private MentorRepository mentorRepository ;
    @Autowired
    private  AvailabilityPeriodRepository availabilityPeriodRepository;

    @Autowired
    private  ZoomService zoomService;

    private SessionDTO toDTO(Session session) {
        return new SessionDTO(
                session.getId(),
                session.getName(),
                session.getPrice(),
                session.getDescription(),
                session.getStatus() != null ? session.getStatus() : null,
                session.getScheduledAt(),
                session.getDurationMinutes(),
                session.getMentor() != null ? session.getMentor().getFirstname() + " " + session.getMentor().getLastname() : null,
                session.getMentor() != null ? session.getMentor().getId() : null,
                session.getMentee() != null ? session.getMentee().getFirstname() + " " + session.getMentee().getLastname() : null,
                session.getMentee() != null ? session.getMentee().getId() : null
        );
    }
    public Optional <AvailabilityPeriod> updateavailibilityrstatus(Long id, AvailabilityStatus status, String reason) {
        Optional<AvailabilityPeriod> exisitngperiod = availabilityPeriodRepository.findById(id);
        if(exisitngperiod.isPresent()){
            AvailabilityPeriod period = exisitngperiod.get();
            if(status.toString().equals("AVAILABLE")){
                period.setStatus(AvailabilityStatus.AVAILABLE);
                period.setBookedBy(null);
                availabilityPeriodRepository.save(period);
                emailService.sendmail(exisitngperiod.get().getBookedBy().getEmail()," session status","unfortunately your session has been canceled for the felow reason"+reason);
            }


            return Optional.of(period);
        }else{
            return Optional.empty();
        }


    }

    public List<AvailabilityPeriod> getAvailablePeriodsBySession(Long sessionId) {
        return availabilityPeriodRepository.findBySessionId(sessionId);
    }
    public List<AvailabilityPeriod> getallperiodBymentor(Long MentorId){
        return availabilityPeriodRepository.findByMentorId(MentorId);
    }
    public List<AvailabilityPeriod> GetbookedPeriodsByMentee(Long MenteeID){
        return availabilityPeriodRepository.findByBookedBy_IdAndStatus(MenteeID,AvailabilityStatus.BOOKED);
    }
    public List<SessionDTO> getSessionsByMentorId(Long mentorId) {
        List<Session> sessions = sessionRepository.findByMentorId(mentorId);
        return sessions.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<SessionDTO> getAllSessions() {
        return sessionRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AvailabilityPeriod addAvailability(Long sessionId, LocalDateTime start, LocalDateTime end) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        AvailabilityPeriod period = new AvailabilityPeriod();
        period.setStartTime(start);
        period.setEndTime(end);
        period.setSession(session);

        return availabilityPeriodRepository.save(period);
    }
    public void deleteperiodbyId(Long id ){
        if (!availabilityPeriodRepository.existsById(id)) {
            throw new EntityNotFoundException("availabitlity not found with id " + id);
        }
        availabilityPeriodRepository.deleteById(id);
    }


    public SessionDTO getSessionById(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        return toDTO(session);
    }

    public SessionDTO createSession(Session session) {

        if (session.getMentor() != null) {
            Mentor mentor = mentorRepository.findById(session.getMentor().getId())
                    .orElseThrow(() -> new RuntimeException("Mentor not found"));
            session.setMentor(mentor);
        }
        sessionRepository.save(session);
        return toDTO(session);
    }

    public SessionDTO bookSession(Long sessionId, Long menteeId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        Mentee mentee = menteeRepository.findById(menteeId)
                .orElseThrow(() -> new RuntimeException("Mentee not found"));

        session.setMentee(mentee);
        sessionRepository.save(session);

        return toDTO(session);
    }

    public AvailabilityPeriod bookAvailability(Long periodId, Long menteeId) {
        AvailabilityPeriod period = availabilityPeriodRepository.findById(periodId)
                .orElseThrow(() -> new RuntimeException("Availability period not found"));

        if (period.getStatus() != AvailabilityStatus.AVAILABLE) {
            throw new RuntimeException("This slot is not available");
        }

        Mentee mentee = menteeRepository.findById(menteeId)
                .orElseThrow(() -> new RuntimeException("Mentee not found"));

        period.setBookedBy(mentee);
        ZoomMeetingInfo zoomInfo = zoomService.createMeeting(period);
        period.setZoomMeetingId(zoomInfo.getMeetingId());
        period.setZoomJoinUrl(zoomInfo.getJoinUrl());
        period.setZoomStartUrl(zoomInfo.getStartUrl());
        period.setStatus(AvailabilityStatus.BOOKED);
        return availabilityPeriodRepository.save(period);
    }

    public void deleteSessionById(Long id) {
        if (!sessionRepository.existsById(id)) {
            throw new EntityNotFoundException("Session not found with id " + id);
        }
        sessionRepository.deleteById(id);
    }
}
