package com.example.Mentorship_app.Controller;

import com.example.Mentorship_app.Dto.BookingRequest;
import com.example.Mentorship_app.Dto.SessionDTO;
import com.example.Mentorship_app.Entities.AvailabilityPeriod;
import com.example.Mentorship_app.Entities.Session;
import com.example.Mentorship_app.Enums.AvailabilityStatus;
import com.example.Mentorship_app.Services.jwt.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping
    public List<SessionDTO> getAllSessions() {
        return sessionService.getAllSessions();
    }
    @GetMapping("/mentor/{mentorId}")
    public List<SessionDTO> getSessionsByMentorId(@PathVariable Long mentorId) {
        return sessionService.getSessionsByMentorId(mentorId);
    }

    @PostMapping("/{sessionId}/availability")
    public ResponseEntity<AvailabilityPeriod> addAvailability(
            @PathVariable Long sessionId,
            @RequestBody Map<String, String> payload) {

        LocalDateTime start = LocalDateTime.parse(payload.get("startTime"));
        LocalDateTime end = LocalDateTime.parse(payload.get("endTime"));

        AvailabilityPeriod created = sessionService.addAvailability(sessionId, start, end);
        return ResponseEntity.ok(created);
    }
    @GetMapping("/{id}")
    public SessionDTO getSessionById(@PathVariable Long id) {
        return sessionService.getSessionById(id);
    }

    @PostMapping("/create")
    public SessionDTO createSession(@RequestBody Session session) {
        return sessionService.createSession(session);
    }

    @PostMapping("/book/{availabilityId}/{menteeId}")
    public ResponseEntity<AvailabilityPeriod> bookSession(
            @PathVariable Long availabilityId,
            @PathVariable Long menteeId) {

        return ResponseEntity.ok(sessionService.bookAvailability(availabilityId, menteeId));
    }
    @GetMapping("/{sessionId}/availability")
    public List<AvailabilityPeriod> getAvailabilityBySession(@PathVariable Long sessionId) {
        return sessionService.getAvailablePeriodsBySession(sessionId);
    }

    @GetMapping("/{MentorId}/All/availability")
    public List<AvailabilityPeriod> getAllAvailabilityBySession(@PathVariable Long MentorId) {
        return sessionService.getallperiodBymentor(MentorId);
    }
    @GetMapping("/{menteeId}/booked")
    public List<AvailabilityPeriod> GetbookedPeriodsBySession(@PathVariable Long menteeId) {
        return sessionService.GetbookedPeriodsByMentee(menteeId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionService.deleteSessionById(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/delete/availibility/{id}")
    public ResponseEntity<Void> deleteperiod(@PathVariable Long id) {
        sessionService.deleteperiodbyId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}/{status}/{reason}")
    public ResponseEntity<?> updatestatus(@PathVariable Long id , @PathVariable AvailabilityStatus status , @PathVariable String reason){
        return ResponseEntity.ok().body(sessionService.updateavailibilityrstatus(id,status,reason));
    }
}



