package com.example.Mentorship_app.Entities;


import com.example.Mentorship_app.Enums.AvailabilityStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "availability_periods")
public class AvailabilityPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus status;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    @ManyToOne
    @JoinColumn(name = "mentee_id", nullable = true)
    private Mentee bookedBy;


    @Column(columnDefinition = "TEXT")
    private String zoomMeetingId;

    public String getZoomJoinUrl() {
        return zoomJoinUrl;
    }

    public void setZoomJoinUrl(String zoomJoinUrl) {
        this.zoomJoinUrl = zoomJoinUrl;
    }

    public String getZoomMeetingId() {
        return zoomMeetingId;
    }

    public void setZoomMeetingId(String zoomMeetingId) {
        this.zoomMeetingId = zoomMeetingId;
    }

    public String getZoomStartUrl() {
        return zoomStartUrl;
    }

    public void setZoomStartUrl(String zoomStartUrl) {
        this.zoomStartUrl = zoomStartUrl;
    }

    @Column(columnDefinition = "TEXT")
    private String zoomJoinUrl;
    @Column(columnDefinition = "TEXT")
    private String zoomStartUrl;


    public AvailabilityPeriod() {
        this.status = AvailabilityStatus.AVAILABLE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public AvailabilityStatus getStatus() {
        return status;
    }

    public void setStatus(AvailabilityStatus status) {
        this.status = status;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Mentee getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(Mentee bookedBy) {
        this.bookedBy = bookedBy;
    }
}
